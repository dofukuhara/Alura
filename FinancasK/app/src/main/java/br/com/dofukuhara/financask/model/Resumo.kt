package br.com.dofukuhara.financask.model

import java.math.BigDecimal

class Resumo(private val transacoes : List<Transacao>) {

    fun receita() : BigDecimal {

        return somaPorTipo(Tipo.RECEITA)
    }

    fun despesa() : BigDecimal {

        return somaPorTipo(Tipo.DESPESA)
    }


//    fun total() : BigDecimal {
//
//        return receita().subtract(despesa())
//    }

    /*
        Single Expression Function
        No caso de a função ter apenas uma instrução, podemos utilizar esse recurso de Single Expression
        Function do Kotlin. Nesse caso, não precisamos explicitar o tipo do retorno da função, podemos
        omitir a keyword return e fazer com que o retorno seja "atribuído" ao nome da função.
    */
    fun total() = receita().subtract(despesa())

    private fun somaPorTipo(tipo : Tipo) : BigDecimal {

        /*
            Outro recurso da lambda function é que ele já possui um objeto subentendido dentro dela, do
            item que estamos manipulando.
            Podemos acessá-lo através do objeto 'it'.
            Então, ao invés de fazermos a chamada:
                - .filter { transacao -> transacao.tipo == tipo }
            podemos fazer da seguinte maneira:
                .filter { it.tipo == tipo }
        */

        val someDeTransacoesPorTipo = transacoes
                .filter { it.tipo == tipo }
                .sumByDouble { it.valor.toDouble() }
        return BigDecimal(someDeTransacoesPorTipo)
    }
}