package br.com.alura.leilao.ui.recyclerview.adapter;

import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;

import br.com.alura.leilao.model.Leilao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doNothing;

@RunWith(MockitoJUnitRunner.class)
public class ListaLeilaoAdapterTest {

    // Injeção de Dependência do Contexto e do objeto SPY de ListaLeilaoAdapter
    @Mock
    private Context context;
    @Spy
    private ListaLeilaoAdapter adapter = new ListaLeilaoAdapter(context);

    @Test
    public void deve_AtualizarListaDeLeiloes_QuandoReceberListaDeLeiloes() {
        /*
            Como estamos executando os testes utilizando o runner "MockitoJUnitRunner", não será
            necessário executar o initMocks.
         */
//        MockitoAnnotations.initMocks(this);

        doNothing().when(adapter).atualizaLista();

        adapter.atualiza(new ArrayList<>(Arrays.asList(
                new Leilao("Console"),
                new Leilao("Computador")
        )));

        int quantidadeLeiloesDevolvida = adapter.getItemCount();

        Mockito.<ListaLeilaoAdapter>verify(adapter).atualizaLista();

        assertThat(quantidadeLeiloesDevolvida, is(2));
    }
}