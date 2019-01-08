package br.com.dofukuhara.financask.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.AdapterView
import br.com.dofukuhara.financask.R
import br.com.dofukuhara.financask.dao.TransacaoDAO
import br.com.dofukuhara.financask.model.Tipo
import br.com.dofukuhara.financask.model.Transacao
import br.com.dofukuhara.financask.ui.adapter.ListaTransacoesAdapter
import br.com.dofukuhara.financask.ui.dialog.AdicionaTransacaoDialog
import br.com.dofukuhara.financask.ui.dialog.AlteraTransacaoDialog
import kotlinx.android.synthetic.main.activity_lista_transacoes.*

class ListaTransacoesActivity : AppCompatActivity() {

    private val dao = TransacaoDAO()
    private val transacoes = dao.transacoes

    private val viewDaActivity by lazy {
        window.decorView
    }

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

            // setOnCreateContextMenuListener é responsável por exibir o Menu de Contexto ao realizar
            // um LongPress no item da lista
            // Como não utilizamos as variáveis view e menuInfo, podemos substituí-las por _
            setOnCreateContextMenuListener { menu, _, _ ->
                // grupo do menu, id, ordem, título
                menu.add(Menu.NONE, 1, Menu.NONE, "Remover")
            }
        }
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        // Note que estamos realizando uma Safe Call (?) pois o parâmetro item pode receber valor
        // nulo (MenuItem?)
        val idDoMenu = item?.itemId
        if (idDoMenu == 1) {
            /*
                Por padrão, o tipo da property 'item.menuInfo' é "ContextMenuInfo", que é uma classe
                mais genérica e não nos dá muita informação sobre os itens da lista
                Para isso, podemos fazer um Cast para um tipo mais específico, o "AdapterView.AdapterContextMenuInfo"
                Com esse tipo de variável, somos capazes de acessar a posição do item a qual o menu
                foi gerado.
             */
            val adapterMenuInfo = item.menuInfo as AdapterView.AdapterContextMenuInfo
            val posicaoDaTransacao = adapterMenuInfo.position
            remove(posicaoDaTransacao)
        }

        return super.onContextItemSelected(item)
    }

    private fun remove(posicao: Int) {
        dao.remove(posicao)
        atualizaTransacoes()
    }

    private fun chamaDialogDeAdicao(tipo: Tipo) {
        AdicionaTransacaoDialog(viewGroupDaActivity, this)
                .show(tipo) {transacaoCriada ->
                    adiciona(transacaoCriada)
                    lista_transacoes_adiciona_menu.close(true)
                }
    }

    private fun chamaDialogDeAlteracao(transacao: Transacao, posicao: Int) {
        AlteraTransacaoDialog(viewGroupDaActivity, this)
                .show(transacao) {transacaoAlterada ->
                    altera(transacaoAlterada, posicao)
                }
    }

    private fun adiciona(transacao: Transacao) {
        dao.adiciona(transacao)
        atualizaTransacoes()
    }

    private fun altera(transacao: Transacao, posicao: Int) {
        dao.altera(transacao, posicao)
        atualizaTransacoes()
    }

    private fun atualizaTransacoes() {
        configuraLista()
        configuraResumo()
    }

}