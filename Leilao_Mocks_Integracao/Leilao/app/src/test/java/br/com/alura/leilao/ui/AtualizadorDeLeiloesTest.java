package br.com.alura.leilao.ui;

import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.alura.leilao.api.retrofit.client.LeilaoWebClient;
import br.com.alura.leilao.api.retrofit.client.RespostaListener;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.ui.recyclerview.adapter.ListaLeilaoAdapter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AtualizadorDeLeiloesTest {
    @Mock
    Context context;
    @Mock
    private ListaLeilaoAdapter adapter;
    @Mock
    private LeilaoWebClient client;

    @Test
    public void deve_AtualizarListaDeLeiloes_QuandoBuscarLeiloesDaApi() {
        AtualizadorDeLeiloes atualizador = new AtualizadorDeLeiloes();
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                RespostaListener<List<Leilao>> argument = invocation.getArgument(0);
                argument.sucesso(new ArrayList<Leilao>(Arrays.asList(
                        new Leilao("Computador"),
                        new Leilao("Carro")
                )));
                return null;
            }
        }).when(client).todos(any(RespostaListener.class));

        atualizador.buscaLeiloes(adapter, client, context);

//        int quantidadeLeilioesDevolvida = adapter.getItemCount();
//        assertThat(quantidadeLeilioesDevolvida, is(2));

        /*
            Não precisamos testar se o adapter contém o número de elementos após realizar todas essas
            operações, pois a funcionalidade do Adapter já foi coberta em outros testes.
            Além disso, não precisamos testar se a "API está funcinando", pois não é o escopo de
            cobertura esperado, devemos apenas testar a unidade lógica que nosso código pretende atuar.
            Então, ao verificar o método "buscaLeiloes()", vemos que o que ele faz é, invocar o método
            "todos()" do RestClient (client) e invocar o método "atualiza()" do Adapter, passando como
            parâmetro a lista de leilões que foi passada lá no "doAnswer()" (no ambiente simulado). Isso
            é o que o teste abaixo cobre:
         */
        verify(client).todos(any(RespostaListener.class));
        verify(adapter).atualiza(new ArrayList<Leilao>(Arrays.asList(
                new Leilao("Computador"),
                new Leilao("Carro"))));
    }

}