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
        Modificador LATEINIT
        A fim de melhoarar o código, podemos substituir a abordagem anterior, de inicializar a variável
        com NULL e fazer a verificação em todos os pontos onde a mesma é utilizada pelo modificador
        lateinit.
        Com ele, informamos ao Kotlin que iremos declarar a variável nesse ponto, mas iremos inicializá-la
        posteriormente, em outra parte do código.
        Note que:
            = Somos obrigados a manter a variável como VAR, pois o lateinit necessita de uma variável
              mutável para alterar seu valor posteriormente
            = Não podemos mais utilizar o operador de indicação de variável nullable ?
        Outro benefício é que diferente da abordagem anterior, o lateinit é 'fail fast', ou seja, a
        aplicação irá crashar logo na primeira utilização dessa variável, e não apenas quando for tentar
        acessar o seu conteúdo ou de suas properties.

        Um contra-ponto é que dependendo do número de variáveis lateinit e do tamanho do código, possa
        ficar complicado de gerenciar se as variáveis estão sendo inicializadas nos momentos corretos.

    */
    private lateinit var viewDaActivity: View

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