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

class ResumoView (private val context : Context,
                  private val view : View,
                  transacoes: List<Transacao>) {

    private val resumo = Resumo(transacoes)

    // Ctrl + Alt + F - Extract Fields == transforma o objeto em property da classe
    private val corReceita = ContextCompat.getColor(context, R.color.receita)
    private val corDespesa = ContextCompat.getColor(context, R.color.despesa)

    fun adicionaReceita() {

        val totalReceitas = resumo.receita()

        /*
            view.resumo_card_receita
                .setTextColor(corReceita)
            view.resumo_card_receita.text = totalReceitas.formatToBrazilizan()

         */
        /*
            O código executa várias chamadas ao objeto 'view.resumo_card_receita' a fim de alterar
            suas properties.
            No Kotlin, existe o recurso 'with', que permite com que o objeto seja chamado uma única
            vez e todas as modificações de suas properties seja feita em bloco.
         */
        with (view.resumo_card_receita) {
            setTextColor(corReceita)
            text = totalReceitas.formatToBrazilizan()
        }
    }


    fun adicionaDespesa() {

        val totalDespesas = resumo.despesa()
        with(view.resumo_card_despesa) {
            setTextColor(corDespesa)
            text = totalDespesas.formatToBrazilizan()
        }
    }

    fun adicionaTotal() {

        val resumoTotal = resumo.total()
        val cor = corPor(resumoTotal)

        with(view.resumo_card_total) {
            setTextColor(cor)
            text = resumoTotal.formatToBrazilizan()
        }
    }

    private fun corPor(valor: BigDecimal): Int {

        /*

            valor.compareTo(BigDecimal.ZERO)

         */
        /*
            Diferente do mundo Java, o Kotlin não possui tipos (obj) primitivos.
            Logo, para comparar objetos considerados em Java como "não primitivos", não precisamos
            utilizar o 'compareTo()' ou o 'equals()'... podemos simplismente utilizar os operadores
            lógicos, por exemplo, >= ou ==, pois no Kotlin, tudo acaba sendo comparação de objetos.
         */

        if (valor >= BigDecimal.ZERO) {
            return corReceita
        }
        return corDespesa
    }
}