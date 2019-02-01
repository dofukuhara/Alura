package br.com.alura.leilao.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import br.com.alura.leilao.exception.LanceSeguidoDoMesmoUsuarioException;
import br.com.alura.leilao.exception.UsuarioJaDeuCincoLancesException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class LeilaoTest {

    private static final double DELTA = 0.0001;
    private final Leilao console = new Leilao("Console");
    private final Usuario alex = new Usuario("Alex");

    /*
    (2)
        Uma das formas de verificar se a aplicação lançou uma exceção 'esperada', podemos utilizar o
        ExpectedException. Para isso, devemos declará-lo como uma variável pública da classe e
        inicializá-lo com "ExcepectedException.none()"
     */
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void deve_DevolverDescricao_QuandoRecebeDescricao() {
        String descricaoDevolvida = console.getDescricao();
        assertEquals("Console", descricaoDevolvida);
    }

    @Test
    public void deve_DevolverMaiorLance_QuandoRecebeApenasUmLance() {
        console.propoe(new Lance(alex, 200.0));

        double maiorLanceDevolvido = console.getMaiorLance();

        assertEquals(200.0, maiorLanceDevolvido, DELTA);
    }

    @Test
    public void deve_DevolverMaiorLance_QuandoRecebeMaisDeUmLanceEmOrdemCrescente() {
        console.propoe(new Lance(alex, 100.0));
        console.propoe(new Lance(new Usuario("Fran"), 200.0));

        double maiorLanceDevolvido = console.getMaiorLance();

        assertEquals(200.0, maiorLanceDevolvido, DELTA);
    }

    @Test
    public void deve_DevolverMenorLance_QuandoRecebeApenasUmLanc() {
        console.propoe(new Lance(alex, 200.0));

        double menorLanceDevolvido = console.getMenorLance();

        assertEquals(200.0, menorLanceDevolvido, DELTA);
    }

    @Test
    public void deve_DevolverMenorLance_QuandoRecebeMaisDeUmLanceEmOrdemCrescente() {
        console.propoe(new Lance(alex, 100.0));
        console.propoe(new Lance(new Usuario("Fran"), 200.0));

        double menorLanceDevolvido = console.getMenorLance();

        assertEquals(100.0, menorLanceDevolvido, DELTA);
    }

    @Test
    public void deve_devolverTresMaioresLances_QuandoRecebeExatosTresLances() {
        console.propoe(new Lance(alex, 200.0));
        console.propoe(new Lance(new Usuario("Fran"), 300.0));
        console.propoe(new Lance(alex, 400.0));

        List<Lance> tresMaioresLancesDevolvidos = console.tresMaioresLances();

        assertEquals(3, tresMaioresLancesDevolvidos.size());
        assertEquals(400,
                tresMaioresLancesDevolvidos.get(0).getValor(), DELTA);
        assertEquals(300,
                tresMaioresLancesDevolvidos.get(1).getValor(), DELTA);
        assertEquals(200,
                tresMaioresLancesDevolvidos.get(2).getValor(), DELTA);
    }

    @Test
    public void deve_devolverTresMaioresLances_QuandoNaoRecebeLances() {
        List<Lance> tresMaioresLancesDevolvidos = console.tresMaioresLances();

        assertEquals(0, tresMaioresLancesDevolvidos.size());
    }

    @Test
    public void deve_devolverTresMaioresLances_QuandoRecebeApenasUmLance() {
        console.propoe(new Lance(alex, 200));

        List<Lance> tresMaioresLancesDevolvidos = console.tresMaioresLances();

        assertEquals(1, tresMaioresLancesDevolvidos.size());
        assertEquals(200.0, tresMaioresLancesDevolvidos.get(0).getValor(), DELTA);
    }

    @Test
    public void deve_devolverTresMaioresLances_QuandoRecebeApenasDoisLances() {
        console.propoe(new Lance(alex, 300));
        console.propoe(new Lance(new Usuario("Fran"), 400.0));

        List<Lance> tresMaioresLancesDevolvidos = console.tresMaioresLances();

        assertEquals(2, tresMaioresLancesDevolvidos.size());
        assertEquals(400.0, tresMaioresLancesDevolvidos.get(0).getValor(), DELTA);
        assertEquals(300.0, tresMaioresLancesDevolvidos.get(1).getValor(), DELTA);
    }

    @Test
    public void deve_devolverTresMaioresLances_QuandoRecebeMaisDeTresLances() {
        final Usuario fran = new Usuario("Fran");
        console.propoe(new Lance(alex, 300.0));
        console.propoe(new Lance(fran, 400.0));
        console.propoe(new Lance(alex, 500.0));
        console.propoe(new Lance(fran, 600.0));

        final List<Lance> tresMaioresLancesDevolvidosParaQuatroLances = console.tresMaioresLances();

        assertEquals(3, tresMaioresLancesDevolvidosParaQuatroLances.size());

        assertEquals(600.0, tresMaioresLancesDevolvidosParaQuatroLances.get(0).getValor(), DELTA);
        assertEquals(500.0, tresMaioresLancesDevolvidosParaQuatroLances.get(1).getValor(), DELTA);
        assertEquals(400.0, tresMaioresLancesDevolvidosParaQuatroLances.get(2).getValor(), DELTA);

        console.propoe(new Lance(alex, 700.0));
        List<Lance> tresMaioresLancesDevolvidosParaCincoLances = console.tresMaioresLances();

        assertEquals(3, tresMaioresLancesDevolvidosParaCincoLances.size());

        assertEquals(700.0, tresMaioresLancesDevolvidosParaCincoLances.get(0).getValor(), DELTA);
        assertEquals(600.0, tresMaioresLancesDevolvidosParaCincoLances.get(1).getValor(), DELTA);
        assertEquals(500.0, tresMaioresLancesDevolvidosParaCincoLances.get(2).getValor(), DELTA);
    }

    @Test
    public void deve_devolverValorZeroParaMaiorLance_QuandoNaoTiverLances() {
        double maiorLanceDevolvido = console.getMaiorLance();

        assertEquals(0.0, maiorLanceDevolvido, DELTA);
    }

    @Test
    public void deve_devolverValorZeroParaMenorLance_QuandoNaoTiverLances() {
        double menorLanceDevolvido = console.getMenorLance();

        assertEquals(0.0, menorLanceDevolvido, DELTA);
    }

    /*
    (1)
        Uma das formas de se verificar por uma exception lancada, é a de encapsular o método que
        irá lançar essa exceção em um bloco try/catch e colocar um "Assert.fail()" logo abaixo.
        Opcionalmente, no 'catch', ainda podemos verificar se a mensagem da exception condiz com a
        que esperamos.
     */
    @Test
    public void naoDeve_AdicionarLance_QuandoForMenorQueOMaiorLance() {
        console.propoe(new Lance(alex, 500.0));
        try {
            console.propoe(new Lance(new Usuario("Fran"), 400.0));
            fail("Excecao LanceMenorQueUltimoLanceException esperada");
        } catch (Exception exception) {
            // Caso tenha alguma mensagem customizada que esteja esperando, podemos fazer um:
            // assertEquals("CUSTOM MESSAGE", exception.getMessage());
        }
    }

    /*
    (2)
        Outra forma é a de utilizar o ExpectedException.
        Dessa forma, podemos informar ao teste qual o tipo de Exception que esperamos capturar,
        bem como sua mensagem.
     */
    @Test
    public void naoDeve_AdicionarLance_QuandoForOMesmoUsuarioDoUltimoLance() {
        exception.expect(LanceSeguidoDoMesmoUsuarioException.class);

        //Caso tenha alguma mensagem customizada que esteja esperando, podemos fazer um:
        // exception.expectMessage("CUSTOM MESSAGE");

        console.propoe(new Lance(alex, 500.0));
        console.propoe(new Lance(new Usuario("Alex"), 600.0));

        int quantidadeLancesDevolvida = console.quantidadeLances();

        assertEquals(1, quantidadeLancesDevolvida);
    }

    /*
    (3)
        Uma opção mais simples oferecida pela lib do JUnit é a de se utilizar o atributo "excepted"
        junto à annotation "@Test", informando qual o tipo de objeto estamos esperando que o teste
        tenha que capturar.
        Dessa forma, não precisamos fazer uso de captura via bloco try/catch com verificação via
        "Assert.fail()" ou a de instânciar um ExpcetecException e configurá-lo com a exceção esperada.
     */
    @Test(expected = UsuarioJaDeuCincoLancesException.class)
    public void naoDeve_AdicionarLance_QuandoUsuarioDerCincoLances() {
        final Usuario fran = new Usuario("Fran");

        console.propoe(new Lance(alex, 100.0));
        console.propoe(new Lance(fran, 200.0));
        console.propoe(new Lance(alex, 300.0));
        console.propoe(new Lance(fran, 400.0));
        console.propoe(new Lance(alex, 500.0));
        console.propoe(new Lance(fran, 600.0));
        console.propoe(new Lance(alex, 700.0));
        console.propoe(new Lance(fran, 800.0));
        console.propoe(new Lance(alex, 900.0));
        console.propoe(new Lance(fran, 1000.0));
        console.propoe(new Lance(alex, 1100.0));
        console.propoe(new Lance(fran, 1200.0));
    }
}