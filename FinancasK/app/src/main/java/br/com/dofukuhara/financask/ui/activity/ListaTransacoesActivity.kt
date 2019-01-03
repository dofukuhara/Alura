package br.com.dofukuhara.financask.ui.activity

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import br.com.dofukuhara.financask.R
import br.com.dofukuhara.financask.extension.formatToBrazilian
import br.com.dofukuhara.financask.model.Tipo
import br.com.dofukuhara.financask.model.Transacao
import br.com.dofukuhara.financask.ui.adapter.ListaTransacoesAdapter
import kotlinx.android.synthetic.main.activity_lista_transacoes.*
import kotlinx.android.synthetic.main.form_transacao.view.*
import java.lang.NumberFormatException
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*

class ListaTransacoesActivity : AppCompatActivity() {

    /*
        Lembrando que no Kotlin, todas as variáveis/properties devem ser inicializadas

        A função "listOf()" retorna uma lista vazia imutáveç (read-only)
            private val transacoes: List<Transacao> = listOf()
        Porém, essa abordagem nos retorna uma lista imutável, onde não será possível adicionar itens
        na lista, como por exemplo "transacoes.add(transacaoCriada)". Para podermos utilizar
        um objeto do tipo Collection e que seja mutável, podemos utilizar o objeto "MutableList" ao
        invés do "List", além de trocar o método que a inicializa, de "listOf()" para "mutableListOf()"
            private val transacoes: MutableList<Transacao> = mutableListOf()
     */
    private val transacoes: MutableList<Transacao> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

        configuraLista()

        configuraResumo()


        /*
            No Kotlin, para realizarmos cast explícito, devemos utilizar a keyword 'as', como no caso
            abaixo, onde passamos uma VIEW para o inflate(), mas essa função pede como arguemento uma
            VIEWGROUP, porém a VIEW que estamos passamos, sabemos que de fato ela é também uma VIEWGROUP:
                val view = window.decorView
                .infalte(<LayoutID>, view as ViewGroup, <attachToRoot>)
         */
        lista_transacoes_adiciona_receita
                .setOnClickListener {
                    val view = window.decorView
                    val viewCriada = LayoutInflater
                            .from(this)
                            .inflate(
                                    R.layout.form_transacao,
                                    view as ViewGroup,
                                    false)

                    viewCriada.form_transacao_data
                            .setText(Calendar.getInstance().formatToBrazilian())
                    viewCriada.form_transacao_data
                            .setOnClickListener {

                                val ano = 2018
                                val mes = 11
                                val dia = 26

                                DatePickerDialog(
                                        this,
                                        DatePickerDialog.OnDateSetListener { view, ano, mes, dia ->
                                            val dataSelecionada = Calendar.getInstance()
                                            dataSelecionada.set(ano, mes, dia)

                                            viewCriada.form_transacao_data
                                                    .setText(dataSelecionada.formatToBrazilian())
                                        },
                                        ano,
                                        mes,
                                        dia)
                                        .show()
                            }

                    val adapter = ArrayAdapter.createFromResource(
                            this,
                            R.array.categorias_de_receita,
                            android.R.layout.simple_dropdown_item_1line
                    )

                    viewCriada.form_transacao_categoria.adapter = adapter

                    AlertDialog.Builder(this)
                            .setTitle(R.string.adiciona_receita)
                            .setView(viewCriada)
                            .setPositiveButton("Adicionar"
                            ) { dialogInterface, i ->
                                val valorEmTexto = viewCriada
                                        .form_transacao_valor.text.toString()
                                val dataEmTexto = viewCriada
                                        .form_transacao_data.text.toString()
                                val categoriaEmTexto = viewCriada
                                        .form_transacao_categoria.selectedItem.toString()

                                // val valor = BigDecimal(valorEmTexto)
                                /*
                                    A inicialização acima "BigDecimal(valorEmTexto)" pode gerar uma
                                    exceção NumberFormatException caso o valor de valorEmTexto seja
                                    vazio. Para isso, podemos lidar com esse problema utilizando
                                    Try-Catch
                                */

//                                var valor = BigDecimal.ZERO
//                                try {
//                                    valor = BigDecimal(valorEmTexto)
//                                } catch (exceptoin : NumberFormatException) {
//                                    Toast.makeText(this,
//                                            "Falha na conversão de número",
//                                            Toast.LENGTH_LONG).show()
//                                }

                                /*
                                    Porém, note que na solução acima, tivemos que mudar a variável
                                    "valor" de 'val' para 'var'
                                    Então, para melhorar esse código, podemos utilizar um recurso do
                                    Kotlin chamado "Try Expression" que, similar ao If Expression,
                                    possibilita a inicialização de uma variável de acordo com o
                                    sucesso/falha da operação, dessa forma, podemos voltar a variável
                                    valor de var para val:
                                */

                                val valor = try {
                                    BigDecimal(valorEmTexto)
                                } catch (exception : NumberFormatException) {
                                    Toast.makeText(this,
                                            "Falha na conversão de número",
                                            Toast.LENGTH_LONG).show()
                                    BigDecimal.ZERO
                                }

                                /*
                                    Outro ponto importante com relação a Tratamento de Exceções no
                                    Kotlin:
                                        - Todas as exceptions são consideradas como 'unchecked'
                                        - Não é mais obrigatório tratar as exceptions, mesmo que
                                        no Java seja.
                                */

                                val formatoBrasileiro = SimpleDateFormat("dd/MM/yyyy")
                                val dataConvertida: Date = formatoBrasileiro.parse(dataEmTexto)
                                val data = Calendar.getInstance()
                                data.time = dataConvertida

                                val transacaoCriada = Transacao(tipo = Tipo.RECEITA,
                                        valor = valor,
                                        data = data,
                                        categoria = categoriaEmTexto)

                                /*
                                    Atualização da lista global de transações, bem como reconfigurar
                                    o Adapter e atualizar a View com a nova lista
                                 */
                                atualizaTransacoes(transacaoCriada)

                                /*
                                    A função "close()" do FloatingActionMenu fecha o menu criado ao
                                    clicar no FAB. O parâmetro indica se ao fechar o menu será feito
                                    uma animação de saída ou não
                                 */
                                lista_transacoes_adiciona_menu.close(true)

                            }
                            .setNegativeButton("Cancelar", null)
                            .show()
        }

    }

    private fun atualizaTransacoes(transacao: Transacao) {
        transacoes.add(transacao)
        configuraLista()
        configuraResumo()
    }

    private fun configuraResumo() {
        val view = window.decorView
        val resumoView = ResumoView(this, view, transacoes)

        resumoView.atualiza()
    }


    private fun configuraLista() {
        lista_transacoes_listview.adapter = ListaTransacoesAdapter(transacoes, this)
    }

}