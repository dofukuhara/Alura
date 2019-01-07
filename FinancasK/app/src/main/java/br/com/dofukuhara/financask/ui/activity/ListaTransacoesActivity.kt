package br.com.dofukuhara.financask.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import br.com.dofukuhara.financask.R
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

    /*
        Após o ajuste feito em FormularioTransacaoDialog para declarar a High Order Function, devemos
        desenvolvê-la onde ela for invocada.

        Note que:
        - Quando uma High Order Function (ou Lambda Function) for o ultimo parâmetro do método,
          podemos implementá-la fora dos parântesis
        - Da mesma forma que em Lambda Expression, caso a função tenha apenas 1 parâmetro, podemos
          nos referenciar à ela através do objeto subtendido "it"
        - Entretanto, realizar a utilização do obj "it" (além da Lambda Function sem informar o nome
          do método), reduz bastante a legibilidade do código.
          Então, nesse caso, se explicitarmos o nome do parâmetro e damos um nome mais coerente com
          o seu propósito (transacaoCriada no lugar de 'it' ou de apenas 'transaca') já melhora um
          pouco a legibilidade/compreensão do seu propósito
     */
    private fun chamaDialogDeAdicao(tipo: Tipo) {
        AdicionaTransacaoDialog(viewGroupDaActivity, this)
                .show(tipo) {transacaoCriada ->
                    adiciona(transacaoCriada)
                    lista_transacoes_adiciona_menu.close(true)
                }
    }

    /*
        Com relação ao ponto de legibilidade mencionado no comentário anterior, note que nesse exemplo,
        caso fizessemos a seguinte implementação
            private fun chamaDialogDeAlteracao(transacao: Transacao, posicao: Int) {
                AlteraTransacaoDialog(viewGroupDaActivity, this)
                    .show(transacao) {
                        altera(transacao, posicao)
            }
        o código compilaria, pois como a expressao lambda consegue acessar as variáveis definidas no
        escopo acima do seu, ela interpretaria que ao invés de utilizar a variável passada via
        parâmetro na função 'show()' de 'AlteraTransacaoDialog', ela iria utilizar a variável
        'transacao' passada via parâmetro para "chamaDialogDeAlteracao", ou seja, sem ter seu valor
        alterado... o que faria com que o programa não executasse da forma desejada.
    }
     */
    /*
        Uma outra possível solução, para melhorar um pouco mais a legibilidade seria a de utilizar
        o NAMED PARAMETER.
        Nesse caso, fica um pouco mais claro o contexto do High-Order Function.
        Note que no caso abaixo, estamos dando o nome para esse parâmetro referente à funcao de
        "transacaoDelegate". Outro ponto a se notar é que, como agora é passado uma variável como
        parâmetro, a implementação deve ser feita dentro dos parêntesis da função.

            AlteraTransacaoDialog(viewGroupDaActivity, this)
                .show(transacao, transacaoDelegate = {transacaoAlterada ->
                    altera(transacaoAlterada, posicao)
                })
     */
    private fun chamaDialogDeAlteracao(transacao: Transacao, posicao: Int) {
        AlteraTransacaoDialog(viewGroupDaActivity, this)
                .show(transacao) {transacaoAlterada ->
                    altera(transacaoAlterada, posicao)
                }
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