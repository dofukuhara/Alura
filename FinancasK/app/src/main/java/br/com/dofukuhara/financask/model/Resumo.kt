package br.com.dofukuhara.financask.model

import java.math.BigDecimal

class Resumo(private val transacoes : List<Transacao>) {

    fun receita() : BigDecimal {

//        var totalReceitas = BigDecimal.ZERO
//        for (transacao in transacoes) {
//            if (transacao.tipo == Tipo.RECEITA)
//                totalReceitas = totalReceitas.plus(transacao.valor)
//        }
//
//        return totalReceitas
        val somaDeReceita: Double = somaPorTipo(Tipo.RECEITA)

        return BigDecimal(somaDeReceita)
    }

    fun despesa() : BigDecimal {

        val somaDeDespesas: Double = somaPorTipo(Tipo.DESPESA)

        return BigDecimal(somaDeDespesas)
    }

    fun total() : BigDecimal {

        return receita().subtract(despesa())
    }

    private fun somaPorTipo(tipo : Tipo) : Double {

        /*
            Utilização de Lambda Function na manipulação de array.
            Note que, quando a função lambda (função anônima) fornecida como parâmetro possui apenas
            uma função, não precisamos colocar os parênteses, podemos passar diretamente a função.
        */

        return transacoes
                .filter { transacao -> transacao.tipo == tipo }
                .sumByDouble { transacao -> transacao.valor.toDouble() }
    }
}