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

/*
    Superclass 'FormularioTransacaoDialog', gerada a partir de "AdicionaTransacaoDialog" através
    do feature "Refactor This" (Ctrl + Alt + Shift + T) --> "Superclass..." da IDE

    Note que, antes da KeyWord 'class', foi também adicionada a KeyWord 'open'
    Por padrão, no Kotlin todas as classes são 'final' (ou seja, são imutáveis, não-herdáveis),
    então para permitir que essa classe seja herdável por outras classes, é necessário utilizar esse
    modificador 'open'

 */
open class FormularioTransacaoDialog(
        private val context: Context,
        private val viewGroup: ViewGroup) {

    private val viewCriada = criaLayout()
    private val campoCategoria = viewCriada.form_transacao_categoria
    private val campoData = viewCriada.form_transacao_data
    private val campoValor = viewCriada.form_transacao_valor

    fun show(tipo: Tipo, transacaoDelegate: TransacaoDelegate) {

        configuraCampoData()
        configuraCampoCategoria(tipo)
        configuraFormulario(tipo, transacaoDelegate)
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

        campoData.setText(Calendar.getInstance().formatToBrazilian())
        campoData.setOnClickListener {
            DatePickerDialog(
                    context,
                    { _, ano, mes, dia ->
                        val dataSelecionada = Calendar.getInstance()
                        dataSelecionada.set(ano, mes, dia)
                        campoData.setText(dataSelecionada.formatToBrazilian())
                    },
                    ano, mes, dia)
                    .show()
        }
    }

    private fun configuraCampoCategoria(tipo: Tipo) {

        val categorias = categoriasPor(tipo)

        val adapter = ArrayAdapter.createFromResource(
                context,
                categorias,
                android.R.layout.simple_dropdown_item_1line
        )

        campoCategoria.adapter = adapter
    }

    private fun configuraFormulario(tipo: Tipo, transacaoDelegate: TransacaoDelegate) {

        val titulo = tituloPor(tipo)

        AlertDialog.Builder(context)
                .setTitle(titulo)
                .setView(viewCriada)
                .setPositiveButton("Adicionar"
                ) { _, _ ->
                    val valorEmTexto = campoValor.text.toString()
                    val dataEmTexto = campoData.text.toString()
                    val categoriaEmTexto = campoCategoria.selectedItem.toString()

                    val valor = converteCampoValor(valorEmTexto)

                    val data = dataEmTexto.converteParaCalendar()

                    val transacaoCriada = Transacao(tipo = tipo,
                            valor = valor,
                            data = data,
                            categoria = categoriaEmTexto)

                    transacaoDelegate.delegate(transacaoCriada)

                }
                .setNegativeButton("Cancelar", null)
                .show()
    }

    private fun tituloPor(tipo: Tipo): Int {
        if (tipo == Tipo.RECEITA) {
            return R.string.adiciona_receita
        }
        return R.string.adiciona_despesa
    }

    private fun categoriasPor(tipo: Tipo): Int {
        if (tipo == Tipo.RECEITA) {
            return R.array.categorias_de_receita
        }
        return R.array.categorias_de_despesa
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