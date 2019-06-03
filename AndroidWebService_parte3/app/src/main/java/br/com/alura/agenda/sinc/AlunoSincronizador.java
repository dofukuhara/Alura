package br.com.alura.agenda.sinc;

import android.content.Context;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.dto.AlunoSync;
import br.com.alura.agenda.event.AtualizaListaAlunoEvent;
import br.com.alura.agenda.preferences.AlunoPreferences;
import br.com.alura.agenda.retrofit.RetrofitInicializador;
import br.com.alura.agenda.services.AlunoService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlunoSincronizador {
    private final Context context;
    private EventBus bus = EventBus.getDefault();
    private final AlunoPreferences preferences;

    public AlunoSincronizador(Context context) {
        this.context = context;
        preferences = new AlunoPreferences(this.context);
    }

    public void buscaTodos() {
        /*
            Caso tenha uma versão salva na shared preference, então irá realizar a chamada de busca
            de alunos através do endpoint de 'diff'. Caso não tenha nenhuma versão salva na preference,
            então será utilizado o endpoint que retorna todos os alunos salvos no servidor.
         */
        if (preferences.temVersao()) {
            buscaNovos();
        } else {
            buscaAlunos();
        }
    }

    private void buscaNovos() {
        String versao = preferences.getVersao();
        Call<AlunoSync> call = new RetrofitInicializador().getAlunoService().novos(versao);

        call.enqueue(getAlunosCallback());
    }

    private void buscaAlunos() {
        Call<AlunoSync> call = new RetrofitInicializador().getAlunoService().lista();

        call.enqueue(getAlunosCallback());
    }

    private Callback<AlunoSync> getAlunosCallback() {
        return new Callback<AlunoSync>() {
            @Override
            public void onResponse(Call<AlunoSync> call, Response<AlunoSync> response) {
                AlunoSync alunoSync = response.body();
                String versao = alunoSync.getMomentoDaUltimaModificacao();

                preferences.salvaVersao(versao);

                AlunoDAO dao = new AlunoDAO(context);
                dao.sincroniza(alunoSync.getAlunos());
                dao.close();


                Log.i("AlunoSincronizador", "onResponse: " + preferences.getVersao());

                // Ao invés de acoplar o método 'carregaLista()' do ListAlunosActivity, podemos
                // utilizar o EventBus e notificar a activity para realizar o carregamento dos alunos
                // através do evento 'AtualizaListaAlunoEvent'
                bus.post(new AtualizaListaAlunoEvent());

                // Agora, o evento de 'swipe.setRefreshing(false)' foi passado para dentro do tratamento
                // de 'atualizaListAlunoEvent()' na activity. Dessa forma, não precisamos lidar, ou
                // ter ciência do estado e do controle da exibição do swipe refresh
            }

            @Override
            public void onFailure(Call<AlunoSync> call, Throwable t) {
                bus.post(new AtualizaListaAlunoEvent());
                Log.e("onFailure chamado", t.getMessage());
            }
        };
    }
}