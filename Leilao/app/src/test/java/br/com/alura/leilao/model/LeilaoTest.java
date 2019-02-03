package br.com.alura.leilao.model;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import br.com.alura.leilao.exception.LanceSeguidoDoMesmoUsuarioException;
import br.com.alura.leilao.exception.UsuarioJaDeuCincoLancesException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.CombinableMatcher.both;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class LeilaoTest {

    private static final double DELTA = 0.0001;
    private final Leilao console = new Leilao("Console");
    private final Usuario alex = new Usuario("Alex");

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void deve_DevolverDescricao_QuandoRecebeDescricao() {
        String descricaoDevolvida = console.getDescricao();
//        assertEquals("Console", descricaoDevolvida);


//        assertThat(descricaoDevolvida, equalTo("Console"));
//        assertThat(descricaoDevolvida, is("Console"));
        // Os casos acima funcionam, porém, podemos implementar o teste conforme abaixo, utilizando
        // a combinação dos matchers 'is()' e 'equalTo()', a fim de melhorar a legibilidade do teste
        assertThat(descricaoDevolvida, is(equalTo("Console")));
    }

    @Test
    public void deve_DevolverMaiorLance_QuandoRecebeApenasUmLance() {
        console.propoe(new Lance(alex, 200.0));

        double maiorLanceDevolvido = console.getMaiorLance();

//        assertEquals(200.0, maiorLanceDevolvido, DELTA);
        /*
            Para utilizar o 'closeTo()' é necessário importar a dependência completa do Hamcrest
            a partir do pacote org.hamcrest:hamcrest-all:1.3
         */
        assertThat(200.0, closeTo(maiorLanceDevolvido, DELTA));
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

//        assertEquals(3, tresMaioresLancesDevolvidos.size());

//        assertThat(tresMaioresLancesDevolvidos, hasSize(3));
        // O caso acima já realiza o teste de forma experada, entretanto, conforme visto no primeiro
        // teste, podemos utilizar o mather "equalTo()", a fim de melhorar a legibilidade do teste.
        assertThat(tresMaioresLancesDevolvidos, hasSize(equalTo(3)));

//        assertEquals(400,
//                tresMaioresLancesDevolvidos.get(0).getValor(), DELTA);
//        assertEquals(300,
//                tresMaioresLancesDevolvidos.get(1).getValor(), DELTA);
//        assertEquals(200,
//                tresMaioresLancesDevolvidos.get(2).getValor(), DELTA);

        // Verifica se um item em específico está contido na Lista
        assertThat(tresMaioresLancesDevolvidos, hasItem(new Lance(alex, 400.0)));
        // Verifica se todos os itens, na ordem especificada, está contida na Lista
        assertThat(tresMaioresLancesDevolvidos, contains(
                new Lance(alex, 400.0),
                new Lance(new Usuario("Fran"), 300.0),
                new Lance(alex, 200.0)
        ));

        /*
            Uma outra forma de melhorar o caso de teste deste cenário, ondem temos 2 verificações
            (tamanho da lista) e a ordem dos elementos da mesma, podemos utilizar o matcher "both()"
            e "and()", assim podemos fazer a descrição dos testes em um único AssertThat().
         */
        assertThat(tresMaioresLancesDevolvidos,
                both(Matchers.<Lance>hasSize(3))
                        .and(contains(
                                new Lance(alex, 400.0),
                                new Lance(new Usuario("Fran"), 300.0),
                                new Lance(alex, 200.0))
                        )
        );
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

    @Test
    public void naoDeve_AdicionarLance_QuandoForMenorQueOMaiorLance() {
        console.propoe(new Lance(alex, 500.0));
        try {
            console.propoe(new Lance(new Usuario("Fran"), 400.0));
            fail("Excecao LanceMenorQueUltimoLanceException esperada");
        } catch (Exception exception) {
        }
    }

    @Test
    public void naoDeve_AdicionarLance_QuandoForOMesmoUsuarioDoUltimoLance() {
        exception.expect(LanceSeguidoDoMesmoUsuarioException.class);

        console.propoe(new Lance(alex, 500.0));
        console.propoe(new Lance(new Usuario("Alex"), 600.0));

        int quantidadeLancesDevolvida = console.quantidadeLances();

        assertEquals(1, quantidadeLancesDevolvida);
    }

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