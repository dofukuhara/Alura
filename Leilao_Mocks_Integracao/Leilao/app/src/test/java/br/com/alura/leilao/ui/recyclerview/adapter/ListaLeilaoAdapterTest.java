package br.com.alura.leilao.ui.recyclerview.adapter;

import android.content.Context;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;

import br.com.alura.leilao.model.Leilao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ListaLeilaoAdapterTest {

    @Test
    public void deve_AtualizarListaDeLeiloes_QuandoReceberListaDeLeiloes() {
        /*
            Como precisamos passar um contexto no construtor de ListaLeilaoAdapter, podemos criar um
            a partir do "Mockito.mock()". Com isso, o Mockito irá criar um objeto a partir da refe-
            rência de classe passada como parâmetro.
         */
        Context context = Mockito.mock(Context.class);
        ListaLeilaoAdapter adapter = Mockito.spy(new ListaLeilaoAdapter(context));
        /*
            Como o Mockito não consegue mockar métodos estáticos e final, não conseguimos 'espiar' se
            o "notifyDataSetChanged()" (método final do RecyclerView [context]), encapsulamos essa
            chamada em uma função pública "atualizaLista()" de ListaLeilaoAdapter e, a partir desda
            função, espiamos o objeto e, ao ser invocada, passamos a não executar nenhuma ação,
            através do método estático do Mockito "doNothing()".

            A diferença do Mockito.mock() e Mockito.spy() é que no spy, as funçoes da classe são de
            fato invocadas, enquanto que na mock não (caso alguma função desse objeto mock seja
            invocada, ela simplesmente não executará/retornará nada)
         */
        Mockito.doNothing().when(adapter).atualizaLista();

        adapter.atualiza(new ArrayList<>(Arrays.asList(
                new Leilao("Console"),
                new Leilao("Computador")
        )));

        int quantidadeLeiloesDevolvida = adapter.getItemCount();

        /*
            Já que no início do teste, estamos falando para o Mockito que quando o método "atualizaLista()"
            do objeto que ele está 'espiando' (ListaLeilaoAdapter), queremos garantir que esse método seja,
            de fato, chamado dentro da função "getItemCount()". Para isso, podemos utilizar o método estátio
            "verify()".
         */
        Mockito.verify(adapter).atualizaLista();

        assertThat(quantidadeLeiloesDevolvida, is(2));
    }
}