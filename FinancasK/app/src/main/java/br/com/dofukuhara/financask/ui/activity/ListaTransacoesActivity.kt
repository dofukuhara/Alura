package br.com.dofukuhara.financask.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import br.com.dofukuhara.financask.R
import br.com.dofukuhara.financask.model.Tipo
import br.com.dofukuhara.financask.model.Transacao
import br.com.dofukuhara.financask.ui.adapter.ListaTransacoesAdapter
import kotlinx.android.synthetic.main.activity_lista_transacoes.*
import java.math.BigDecimal
import java.util.*

class ListaTransacoesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

        val transacoes : List<Transacao> = transacoesDeExemplo()

        configuraLista(transacoes)

        configuraResumo(transacoes)


        /*
            OBJECT EXPRESSIONS

            Similar ao Java, para implementarmos o evento de clique, temos que implementar seu listener.
            Em Kotlin não temos a 'keywork' new para instânciar um objeto. Nesse caso, utilizamos a
            keywork object com o intuito de informar que vamos passar um objeto e explicitamos o seu
            tipo com o caracter :

            Note que, similar como em Java, quando implementamos funções anônimas, a referência ao 'this'
            é do escopo da função implementada (View) e não da Activity a qual estamos definindo.
            Para isso, caso queria referenciar o this para essa Activity (ou para qualquer outra classe),
            podemos utilizar uma 'label' para fazer essa refência (this@ListaTransacoesActivity)
        */
        /*
            Como sugerido pelo Studio, além de implementar a interface utilizando Object Expression,
            podemos também implementá-lo utilizando lambda expression.

            IMPORTANTE: Note que a implementação ficou 'menos verbosa' e que, ao utilizar lambda expression,
            o escopo da função é elevada ao nível de quem a chama, então nesse caso o this passa a se
            referenciar à nossa Activity, podendo assim remover o label "@ListaTransacoesActivity" de this.
         */
        lista_transacoes_adiciona_receita.setOnClickListener {
            Toast.makeText(
                    this,
                    "Clique em nova receita",
                    Toast.LENGTH_SHORT).show()
        }

    }

    private fun configuraResumo(transacoes: List<Transacao>) {
        val view = window.decorView
        val resumoView = ResumoView(this, view, transacoes)

        resumoView.atualiza()
    }


    private fun configuraLista(transacoes: List<Transacao>) {
        lista_transacoes_listview.adapter = ListaTransacoesAdapter(transacoes, this)
    }

    private fun transacoesDeExemplo(): List<Transacao> {
        return listOf(
                Transacao(valor = BigDecimal(2.5), categoria = "Macarrão da dadsdkasjdka jkaj kadj kadjakdj ak", tipo = Tipo.DESPESA, data = Calendar.getInstance()),
                Transacao(valor = BigDecimal(100.0), categoria = "Economia", tipo = Tipo.RECEITA),
                Transacao(valor = BigDecimal(10000.0), categoria = "Civic", tipo = Tipo.DESPESA)
        )
    }
}