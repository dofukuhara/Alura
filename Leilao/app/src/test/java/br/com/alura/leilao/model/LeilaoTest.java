package br.com.alura.leilao.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class LeilaoTest {

    @Test
    public void getDescricao() {
        // Criar cenário de teste
        Leilao console = new Leilao("Console");

        // executar ação esperada
        String descricaoDevolvida = console.getDescricao();

        // testar resultado esperado
        assertEquals("Console", descricaoDevolvida);
    }

    @Test
    public void getMaiorLance() {
        Leilao console = new Leilao("Console");
        console.propoe(new Lance(new Usuario("Alex"), 200.0));

        double maiorLanceDevolvido = console.getMaiorLance();

        //     O parãmetro Delta pega a diferença entre os valores com ponto flutuante e se ele for
        // maior, significa que os valores são equivalentes
        //     Quando se trabalha com Double, um valor comum de se utilizar para o delta é de 0.0001.
        // Esse valor pode ser ajustado, caso a aplicação necessite de trabalhar com uma precisão
        // maior ao computar números com ponto flutuante.
        assertEquals(200.0, maiorLanceDevolvido, 0.0001);
    }
}