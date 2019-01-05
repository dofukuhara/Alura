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

class ResumoView (context : Context,
                  private val view : View?,
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

        /*
            Na assinatura do construtor dessa classe, foi ajustado para que o tipo de view seja
            "View?", ou seja, a variável passada como parâmetro pode ser nula.
            Então, em todas as chamadas a essa variável, o Kotlin irá indicar como possível ponto de
            falha e não permitirá que o código compile.
            Para isso, podemos utilizar o operador !! => non-null asserted(!!)
            Ao utilizarmos esse operador, informamos ao Kotlin que nós iremos nos responsibilizar de
            que ela "nunca" seja nula e que permita assim a compilação do código.

            Obs: Ao utilizar essa estratégia, caso a variável (com o !!), ao ser invocada tenha valor
            nulo, ao invés de lançar um 'tradicional' NullPointerException, será lançado um erro
            KotlinNullPointerException
        */
        with (view!!.resumo_card_receita) {
            setTextColor(corReceita)
            text = totalReceitas.formatToBrazilizan()
        }
    }


    private fun adicionaDespesa() {

        val totalDespesas = resumo.despesa
        with(view!!.resumo_card_despesa) {
            setTextColor(corDespesa)
            text = totalDespesas.formatToBrazilizan()
        }
    }

    private fun adicionaTotal() {

        val resumoTotal = resumo.total
        val cor = corPor(resumoTotal)

        with(view!!.resumo_card_total) {
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