package br.com.alura.agenda.tasks;

import android.os.AsyncTask;
import android.util.Log;

import br.com.alura.agenda.converter.AlunoConverter;
import br.com.alura.agenda.modelo.Aluno;
import br.com.alura.agenda.web.WebClient;

public class InsereAlunoTask extends AsyncTask<String, Void, Void> {
    private final Aluno aluno;

    public InsereAlunoTask(Aluno aluno) {
        this.aluno = aluno;
    }

    @Override
    protected Void doInBackground(String...  stringParams) {
        String url = "http://localhost:8080";
        if (stringParams.length == 1) {
            url = stringParams[0];
        }
        Log.d("FUKUHARALOG", "doInBackground: url: " + url);
        String json = new AlunoConverter().converteParaJSONCompleto(aluno);
        new WebClient().insere(url, json);
        return null;
    }
}
