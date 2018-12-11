package br.com.dofukuhara.financask.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
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

        //val transacoes = listOf("Macarrão - R$ 2,40", "Economia - R$ 100,00", "Civic - R$ 10000,00")

        // val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, transacoes)

        /*
            Não é necessário, mas para facilitar o entendimento do que o método 'transacoesDeExemplo()'
            retorna, podemos explicitá-lo com a utilização da indicação " : List<Transacao> "
         */
        val transacoes : List<Transacao> = transacoesDeExemplo()

        configuraLista(transacoes)

        configuraResumo(transacoes)

    }

    private fun configuraResumo(transacoes: List<Transacao>) {
        val view = window.decorView
        val resumoView = ResumoView(this, view, transacoes)

//        resumoView.adicionaReceita()
//        resumoView.adicionaDespesa()
//        resumoView.adicionaTotal()

        // Removendo a responsabilidade da Activity. Delegando a responsabilidade de como o resumo
        // deve ser exibido/calculado para o próprio ResumoView.
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