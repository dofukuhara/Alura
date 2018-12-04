package br.com.dofukuhara.financask.model

import java.math.BigDecimal

class Resumo(private val transacoes : List<Transacao>) {

    fun receita() : BigDecimal {
        var totalReceitas = BigDecimal.ZERO
        for (transacao in transacoes) {
            if (transacao.tipo == Tipo.RECEITA)
                totalReceitas = totalReceitas.plus(transacao.valor)
        }

        return totalReceitas
    }

    fun despesa() : BigDecimal {
        var totalDespesas = BigDecimal.ZERO
        for (transacao in transacoes) {
            if (transacao.tipo == Tipo.DESPESA)
                totalDespesas = totalDespesas.plus(transacao.valor)
        }

        return totalDespesas
    }

    fun total() : BigDecimal {

        return receita().subtract(despesa())
    }
}