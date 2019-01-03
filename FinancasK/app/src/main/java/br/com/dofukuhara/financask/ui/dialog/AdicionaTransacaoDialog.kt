package br.com.dofukuhara.financask.ui.dialog

import android.app.DatePickerDialog
import android.content.Context
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import br.com.dofukuhara.financask.R
import br.com.dofukuhara.financask.delegate.TransacaoDelegate
import br.com.dofukuhara.financask.extension.converteParaCalendar
import br.com.dofukuhara.financask.extension.formatToBrazilian
import br.com.dofukuhara.financask.model.Tipo
import br.com.dofukuhara.financask.model.Transacao
import kotlinx.android.synthetic.main.form_transacao.view.*
import java.math.BigDecimal
import java.util.*

class AdicionaTransacaoDialog(private val viewGroup: ViewGroup,
                              private val context: Context) {

    private val viewCriada = criaLayout()

    fun configuraDialog(transacaoDelegate: TransacaoDelegate) {

        configuraCampoData()
        configuraCampoCategoria()
        configuraFormulario(transacaoDelegate)
    }

    private fun criaLayout(): View {

        return LayoutInflater
                .from(context)
                .inflate(
                        R.layout.form_transacao,
                        viewGroup,
                        false)
    }

    private fun configuraCampoData() {

        val hoje = Calendar.getInstance()

        val ano = hoje.get(Calendar.YEAR)
        val mes = hoje.get(Calendar.MONTH)
        val dia = hoje.get(Calendar.DAY_OF_MONTH)

        viewCriada.form_transacao_data
                .setText(Calendar.getInstance().formatToBrazilian())
        viewCriada.form_transacao_data
                .setOnClickListener {

                    DatePickerDialog(
                            context,
                            DatePickerDialog.OnDateSetListener { _, ano, mes, dia ->
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
    }

    private fun configuraCampoCategoria() {

        val adapter = ArrayAdapter.createFromResource(
                context,
                R.array.categorias_de_receita,
                android.R.layout.simple_dropdown_item_1line
        )

        viewCriada.form_transacao_categoria.adapter = adapter
    }

    private fun configuraFormulario(transacaoDelegate: TransacaoDelegate) {

        AlertDialog.Builder(context)
                .setTitle(R.string.adiciona_receita)
                .setView(viewCriada)
                .setPositiveButton("Adicionar"
                ) { _, _ ->
                    val valorEmTexto = viewCriada
                            .form_transacao_valor.text.toString()
                    val dataEmTexto = viewCriada
                            .form_transacao_data.text.toString()
                    val categoriaEmTexto = viewCriada
                            .form_transacao_categoria.selectedItem.toString()

                    val valor = converteCampoValor(valorEmTexto)

                    val data = dataEmTexto.converteParaCalendar()

                    val transacaoCriada = Transacao(tipo = Tipo.RECEITA,
                            valor = valor,
                            data = data,
                            categoria = categoriaEmTexto)

                    /*

                        Utilizando o design patter DELEGATE, para delegar a responsabilidade de
                        lidar com a transacao que acabou de ser criada (incluí-la na Lista de
                        Transcações, atualizar a VIEW com essa nova entrada, etc)

                    */
                    transacaoDelegate.delegate(transacaoCriada)

                }
                .setNegativeButton("Cancelar", null)
                .show()
    }



    private fun converteCampoValor(valorEmTexto: String): BigDecimal {
        return try {
            BigDecimal(valorEmTexto)
        } catch (exception: NumberFormatException) {
            Toast.makeText(context,
                    "Falha na conversão de número",
                    Toast.LENGTH_LONG).show()
            BigDecimal.ZERO
        }
    }
}