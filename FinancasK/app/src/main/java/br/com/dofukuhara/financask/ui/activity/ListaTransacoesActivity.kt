package br.com.dofukuhara.financask.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import br.com.dofukuhara.financask.R
import br.com.dofukuhara.financask.delegate.TransacaoDelegate
import br.com.dofukuhara.financask.model.Tipo
import br.com.dofukuhara.financask.model.Transacao
import br.com.dofukuhara.financask.ui.adapter.ListaTransacoesAdapter
import br.com.dofukuhara.financask.ui.dialog.AdicionaTransacaoDialog
import br.com.dofukuhara.financask.ui.dialog.AlteraTransacaoDialog
import kotlinx.android.synthetic.main.activity_lista_transacoes.*

class ListaTransacoesActivity : AppCompatActivity() {

    private val transacoes: MutableList<Transacao> = mutableListOf()
    /*
        Ao utilizar o operador ?, estamos informando ao Kotlin que a variável (no caso, do tipo
        View) pode aceitar também valor NULL (mark a type as nullable)
    */
    private var viewDaActivity: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

        viewDaActivity = window.decorView

        configuraLista()
        configuraResumo()
        configuraFab()

    }

    private fun configuraResumo() {
        val resumoView = ResumoView(this, viewDaActivity, transacoes)

        resumoView.atualiza()
    }

    private fun configuraFab() {
        lista_transacoes_adiciona_receita
                .setOnClickListener {
                    chamaDialogDeAdicao(Tipo.RECEITA)
                }

        lista_transacoes_adiciona_despesa
                .setOnClickListener {
                    chamaDialogDeAdicao(Tipo.DESPESA)
                }
    }

    private fun configuraLista() {
        val listaTransacoesAdapter = ListaTransacoesAdapter(transacoes, this)
        with(lista_transacoes_listview) {
            adapter = listaTransacoesAdapter
            setOnItemClickListener { _, _, posicao, _ ->
                val transacao = transacoes[posicao]
                chamaDialogDeAlteracao(transacao, posicao)
            }
        }
    }

    private fun chamaDialogDeAdicao(tipo: Tipo) {
        AdicionaTransacaoDialog(
                viewDaActivity as ViewGroup,
                this)
                .show(tipo,
                        object : TransacaoDelegate {
                            override fun delegate(transacao: Transacao) {
                                adiciona(transacao)
                                lista_transacoes_adiciona_menu.close(true)
                            }
                        })
    }


    private fun chamaDialogDeAlteracao(transacao: Transacao, posicao: Int) {
        AlteraTransacaoDialog(viewDaActivity as ViewGroup, this)
                .show(transacao, object : TransacaoDelegate {
                    override fun delegate(transacao: Transacao) {
                        altera(transacao, posicao)
                    }
                })
    }

    private fun adiciona(transacao: Transacao) {
        transacoes.add(transacao)
        atualizaTransacoes()
    }

    private fun altera(transacao: Transacao, posicao: Int) {
        transacoes[posicao] = transacao
        atualizaTransacoes()
    }

    private fun atualizaTransacoes() {
        configuraLista()
        configuraResumo()
    }

}