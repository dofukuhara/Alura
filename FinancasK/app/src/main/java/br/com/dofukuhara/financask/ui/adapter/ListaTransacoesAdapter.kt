package br.com.dofukuhara.financask.ui.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import br.com.dofukuhara.financask.R
import br.com.dofukuhara.financask.extension.formatToBrazilian
import br.com.dofukuhara.financask.extension.formatToBrazilizan
import br.com.dofukuhara.financask.extension.limitUntil
import br.com.dofukuhara.financask.model.Tipo
import br.com.dofukuhara.financask.model.Transacao
import kotlinx.android.synthetic.main.transacao_item.view.*


class ListaTransacoesAdapter(private val transacoes: List<Transacao>,
                             private val context: Context) : BaseAdapter() {

    /*
        Ao fazer incluir 'private val' na chamada do construtor, não é mais necessário fazer essa
        atribuição a variáveis locais, já que essa declaração e atribuição ficou declarada no
        construtor da classe!
     */
//    private val transacoes = transacoes
//    private val context = context

    /*
        No Kotlin, é mais comum realizar a declaração de properties de classe (constante) em Camel Case,
        e não em caixa alta como no mundo Java. Mas, não tem nenhum problema, caso queira utilizar
        o padrão de nomenclatura do formato Java.
     */
    private val limitOfCharacters = 14

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        val createdView = LayoutInflater.from(context).inflate(R.layout.transacao_item, parent, false)

        val transacao = transacoes[position]

        adicionaValor(transacao, createdView)
        adicionaIcone(transacao, createdView)
        adicionaCategoria(createdView, transacao)
        adicionaData(createdView, transacao)

        return createdView
    }

    private fun adicionaData(createdView: View, transacao: Transacao) {
        createdView.transacao_data.text = transacao.data.formatToBrazilian()
    }

    private fun adicionaCategoria(createdView: View, transacao: Transacao) {
        createdView.transacao_categoria.text = transacao.categoria.limitUntil(limitOfCharacters)
    }

    private fun adicionaValor(transacao: Transacao, createdView: View) {

        /*
            IF EXPRESSIONS
            Um dos recursos do Kotlin é o 'IF Expressions', no qual podemos fazer com que o IF retorne
            um valor à variável.
            Porém isso pode se tornar perigoso, pois pode ser que seja retornado tipos incompatíveis
            à variável (podendo então retornar um tipo 'any' e fazer com que funções que utilizem essa
            varável não compile. Para evitar esse problema, podemos utilizar a declaração implícita.
            Dessa forma, caso o retorno do IF seja imcompatível com o tipo da variável, o próprio
            IF indicará erro
         */
        var cor : Int = corPor(transacao.tipo)
        createdView.transacao_valor
                .setTextColor(cor)
        createdView.transacao_valor
                .text = transacao.valor.formatToBrazilizan()
    }

    private fun corPor(tipo: Tipo): Int {
        return if (tipo == Tipo.DESPESA) {
            ContextCompat.getColor(context, R.color.despesa)
        } else {
            ContextCompat.getColor(context, R.color.receita)
        }
    }

    private fun adicionaIcone(transacao: Transacao, createdView: View) {
        val icone = iconePor(transacao)
        createdView.transacao_icone
                .setBackgroundResource(icone)
    }

    private fun iconePor(transacao: Transacao): Int {
        /*
            Apesar de termos novos recursos no Kotlin, como o IF EXPRESSION visto acima, isso não é
            compatível com outras linguagens, como para o Java, e pode ser de difícil entendimento para
            programadores não familiarizados com esse método. Então, nesses casos é bom analisar a
            utilização desses recursos e verificar se não tem outra possibilidade, até mais simples.
            Nesse caso, podemos utilizar a estratégia de "EARLY RETURN". É compatível com outras
            linguagens e é bem simples também!
         */
        if (transacao.tipo == Tipo.DESPESA) {
            return R.drawable.icone_transacao_item_despesa
        }
        return R.drawable.icone_transacao_item_receita
    }

    override fun getItem(position: Int): Transacao {
        return transacoes[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return transacoes.size
    }
}