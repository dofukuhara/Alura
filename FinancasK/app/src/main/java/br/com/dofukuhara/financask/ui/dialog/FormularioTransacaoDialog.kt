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
    Note que, como tornamos o método "tituloPor()" abstrato, temos que tornar essa classe abstrata
    também (pois como ele não possui a implementação de todos os métodos, essa classe não pode mais
    ser instanciável, somente suas classes filhas [desde que não sejam abstratas também])

    Dessa forma, como toda classe abstrata tem que ser extendida, o modificar 'open' não é mais
    necessário. Essa keyword pode ser utilizada junto com abstract, mas seu uso se torna 'redundante',
    pois debaixo dos panos, ela se torna 'aberta' para outras classes fazerem a herança desta.

 */
abstract class FormularioTransacaoDialog(
        private val context: Context,
        private val viewGroup: ViewGroup) {

    private val viewCriada = criaLayout()
    protected val campoCategoria = viewCriada.form_transacao_categoria
    protected val campoData = viewCriada.form_transacao_data
    protected val campoValor = viewCriada.form_transacao_valor

    /*
        Ao declarar properties abstratas da classe, note que:
            - Seu modificador de acesso deve ser protected ou public (da mesma forma que nos métodos)
            - O tipo da variável deve ser explícito (o Kotlin precisa dessa informação, pois senão
              as subclasses poderiam guardar qualquer tipo de conteúdo)
            - A property não pode ser inicializada (a inicialização fica a cargo das classes filhas)
     */
    protected abstract val tituloBotaoPositivo: String

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
                .setPositiveButton(tituloBotaoPositivo
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

    protected abstract fun tituloPor(tipo: Tipo): Int

    protected fun categoriasPor(tipo: Tipo): Int {
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