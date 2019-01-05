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
            Anteriormente utilizamos a abordagem de non-null asserted (!!) para informar ao Kotlin
            que o código pode ser compilado pois garantiremos que a variável não será nula durante
            o seu acesso.
            Porém, essa não é uma boa abordagem para lidar com esse cenário.

            1 das maneiras que podemos realizar é uma bem similar no Java, que é a de cercar seu
            acesso através de um IF que verifique se a variável é nula, e somente se não for, acessar
            seu conteúdo.

            Obs: Note que neste caso, o Kotlin realizou um "Smart Cast" da variável 'view' para
            "android.view.View" --> Isso ocorre pois como já fizemos a checagem antes de null, estamos
            garantindo que a variável nunca será nula, então o Kotlin automaticamente faz o cast de
            View? para View

            Veja no método "adicionaDespesa()" outra abordagem para lidar com Null
        */

        if (view != null) {
            with (view.resumo_card_receita) {
                setTextColor(corReceita)
                text = totalReceitas.formatToBrazilizan()
            }
        }
    }


    private fun adicionaDespesa() {

        val totalDespesas = resumo.despesa

        /*
            Outra abordagem que podemos utilizar é a de utilizar o operador de "Safe Call" '?'
            Ao utilizar esse operador, o Kotlin só irá executar/acessar o valor da variável se a
            mesma não for nula.

            Note que, no caso abaixo, fizemos a utilização de view? para acessar o 'resumo_card_despesa',
            porém, como o 'resumo_card_despesa' também pode ser nulo, devemos também fazer uma "chamada
            segura" dessa property.

            Obs: Como o "with()" não aceita a utilização desse operador, tivemos que "sacrificar" seu
            uso

            Veja no método "adicionaTotal()" outra abordagem para lidar com Null
        */

        view?.resumo_card_despesa?.setTextColor(corDespesa)
        view?.resumo_card_despesa?.text = totalDespesas.formatToBrazilizan()
    }

    private fun adicionaTotal() {

        val resumoTotal = resumo.total
        val cor = corPor(resumoTotal)

        /*
            Uma abordagem bastante utilizada no mundo Kotlin é a de utilizar a função 'let {...}'
            Essa função executa seu block (lambda) utilizando o objeto chamador como parâmetro e
            retorna o resultado.
            Note que estamos utilizando também a "Safe Call" da variável com a let{}. Dessa forma,
            instruímos ao Kotlin que o block de let só será executado se a variável não for nula!
            Similar à primeira abordagem, como garantimos que a variável não será nula, o Kotlin já
            realiza um Smart Cast de View? para android.view.View
        */
        view?.let {
            /*
                Porém, como estamos dentro do block da 'let', podemos trocar a chamada da variável
                "view" pelo seu objeto subentendido 'it'
            */
            with(it.resumo_card_total) {
                setTextColor(cor)
                text = resumoTotal.formatToBrazilizan()
            }
        }
    }

    private fun corPor(valor: BigDecimal): Int {

        if (valor >= BigDecimal.ZERO) {
            return corReceita
        }
        return corDespesa
    }
}