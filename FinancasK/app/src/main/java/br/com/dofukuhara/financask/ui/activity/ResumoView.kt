package br.com.dofukuhara.financask.ui.activity

import android.content.Context

import android.support.v4.content.ContextCompat
import android.view.View
import br.com.dofukuhara.financask.R
import br.com.dofukuhara.financask.extension.formatToBrazilizan
import br.com.dofukuhara.financask.model.Resumo
import br.com.dofukuhara.financask.model.Transacao
import kotlinx.android.synthetic.main.resumo_card.view.*
import java.math.BigDecimal

class ResumoView(context: Context,
                 private val view: View,
                 transacoes: List<Transacao>) {

    private val resumo = Resumo(transacoes)

    // Ctrl + Alt + F - Extract Fields == transforma o objeto em property da classe
    private val corReceita = ContextCompat.getColor(context, R.color.receita)
    private val corDespesa = ContextCompat.getColor(context, R.color.despesa)

    fun atualiza() {
        adicionaReceita()
        adicionaDespesa()
        adicionaTotal()
    }

    private fun adicionaReceita() {

        val totalReceitas = resumo.receita

        with(view.resumo_card_receita) {
            setTextColor(corReceita)
            text = totalReceitas.formatToBrazilizan()
        }
    }


    private fun adicionaDespesa() {

        val totalDespesas = resumo.despesa

        with(view.resumo_card_despesa) {
            setTextColor(corDespesa)
            text = totalDespesas.formatToBrazilizan()
        }
    }

    private fun adicionaTotal() {

        val resumoTotal = resumo.total
        val cor = corPor(resumoTotal)

        with(view.resumo_card_total) {
            setTextColor(cor)
            text = resumoTotal.formatToBrazilizan()
        }
    }

    private fun corPor(valor: BigDecimal): Int {

        if (valor >= BigDecimal.ZERO) {
            return corReceita
        }
        return corDespesa
    }
}