package br.com.dofukuhara.financask.model

import java.math.BigDecimal

class Resumo(private val transacoes : List<Transacao>) {

//    fun receita() = somaPorTipo(Tipo.RECEITA)

    // Convertendo a função para uma property da Classe
    val receita get() = somaPorTipo(Tipo.RECEITA)

    val despesa get() = somaPorTipo(Tipo.DESPESA)

    val total get() = receita.subtract(despesa)


    private fun somaPorTipo(tipo : Tipo) : BigDecimal {

        val someDeTransacoesPorTipo = transacoes
                .filter { it.tipo == tipo }
                .sumByDouble { it.valor.toDouble() }
        return BigDecimal(someDeTransacoesPorTipo)
    }
}