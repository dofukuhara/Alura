package br.com.alura.leilao.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LeilaoTest {

    /*
         Variáveis que são utilizadas por todos os testes (ou pelo menos na maioria dos testes), vale a
     pena mantê-los como uma instância da classe. Objetos que serão utilizados por poucos testes, o
     ideal é manter sua inicialização somente no escopo de teste onde for necessário.
         Já que as variáveis serão uma instância de classe, para evitar que as mesmas tenham suas
     referências alteradas, uma boa prática é declará-las como 'final'
     */
    private final Leilao console = new Leilao("Console");
    private final Usuario alex = new Usuario("Alex");

    @Test
    public void deve_DevolverDescricao_QuandoRecebeDescricao() {
        String descricaoDevolvida = console.getDescricao();
        assertEquals("Console", descricaoDevolvida);
    }

    @Test
    public void deve_DevolverMaiorLance_QuandoRecebeApenasUmLance() {
        console.propoe(new Lance(alex, 200.0));

        double maiorLanceDevolvido = console.getMaiorLance();

        assertEquals(200.0, maiorLanceDevolvido, 0.0001);
    }

    @Test
    public void deve_DevolverMaiorLance_QuandoRecebeMaisDeUmLanceEmOrdemCrescente() {
        console.propoe(new Lance(alex, 100.0));
        console.propoe(new Lance(new Usuario("Fran"), 200.0));

        double maiorLanceDevolvido = console.getMaiorLance();

        assertEquals(200.0, maiorLanceDevolvido, 0.0001);
    }

    @Test
    public void deve_DevolverMaiorLance_QuandoRecebeMaisDeUmLanceEmOrdemDecrescente() {
        console.propoe(new Lance(alex,10000.0));
        console.propoe(new Lance(new Usuario("Fran"), 9000.0));

        double maiorLanceDevolvido = console.getMaiorLance();

        assertEquals(10000.0, maiorLanceDevolvido, 0.0001);
    }

    @Test
    public void deve_DevolverMenorLance_QuandoRecebeApenasUmLanc() {
        console.propoe(new Lance(alex, 200.0));

        double menorLanceDevolvido = console.getMenorLance();

        assertEquals(200.0, menorLanceDevolvido, 0.0001);
    }

    @Test
    public void deve_DevolverMenorLance_QuandoRecebeMaisDeUmLanceEmOrdemCrescente() {
        console.propoe(new Lance(alex, 100.0));
        console.propoe(new Lance(new Usuario("Fran"), 200.0));

        double menorLanceDevolvido = console.getMenorLance();

        assertEquals(100.0, menorLanceDevolvido, 0.0001);
    }

    @Test
    public void deve_DevolverMenorLance_QuandoRecebeMaisDeUmLanceEmOrdemDecrescente() {
        console.propoe(new Lance(alex,10000.0));
        console.propoe(new Lance(new Usuario("Fran"), 9000.0));

        double menorLanceDevolvido = console.getMenorLance();

        assertEquals(9000.0, menorLanceDevolvido, 0.0001);
    }
}