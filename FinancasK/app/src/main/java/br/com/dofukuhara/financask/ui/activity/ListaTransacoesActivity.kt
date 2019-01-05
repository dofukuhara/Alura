package br.com.dofukuhara.financask.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
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

    private val viewDaActivity by lazy {
        window.decorView
    }

    /*
        A estratégia de Lazy Init deve ser utilizada com cautela, pois no caso abaixo, se fôssemos
        iniciar uma outra property da classe diretamente, com a variável que está sendo inicializada via lay:
            private val viewGroupDaActivity = viewDaActivity as ViewGroup
        isso iria fazer com que inicialização postergada de "viewDaActivity" ocorresse nesse momento,
        e no caso, a property "window" ainda seria nula, o que, consequentemente, iria atribuir nulo
        tanto à "viewDaActivity" quanto "viewGroupDaActivity"

        Mais estratégias de delegação de properties pode ser vista na documentação oficial do Kotlin em
        https://kotlinlang.org/docs/reference/delegated-properties.html
    */
    private val viewGroupDaActivity by lazy {
        viewDaActivity as ViewGroup
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

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
        AdicionaTransacaoDialog(viewGroupDaActivity, this)
                .show(tipo,
                        object : TransacaoDelegate {
                            override fun delegate(transacao: Transacao) {
                                adiciona(transacao)
                                lista_transacoes_adiciona_menu.close(true)
                            }
                        })
    }


    private fun chamaDialogDeAlteracao(transacao: Transacao, posicao: Int) {
        AlteraTransacaoDialog(viewGroupDaActivity, this)
                .show(transacao,
                        object : TransacaoDelegate {
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