package br.com.dofukuhara.financask.ui.activity

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import br.com.dofukuhara.financask.R
import br.com.dofukuhara.financask.extension.formatToBrazilian
import br.com.dofukuhara.financask.model.Tipo
import br.com.dofukuhara.financask.model.Transacao
import br.com.dofukuhara.financask.ui.adapter.ListaTransacoesAdapter
import kotlinx.android.synthetic.main.activity_lista_transacoes.*
import kotlinx.android.synthetic.main.form_transacao.view.*
import java.math.BigDecimal
import java.util.Calendar

class ListaTransacoesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

        val transacoes : List<Transacao> = transacoesDeExemplo()

        configuraLista(transacoes)

        configuraResumo(transacoes)


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
                            .setPositiveButton("Adicionar", null)
                            .setNegativeButton("Cancelar", null)
                            .show()
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