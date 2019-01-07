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
import br.com.dofukuhara.financask.extension.converteParaCalendar
import br.com.dofukuhara.financask.extension.formatToBrazilian
import br.com.dofukuhara.financask.model.Tipo
import br.com.dofukuhara.financask.model.Transacao
import kotlinx.android.synthetic.main.form_transacao.view.*
import java.math.BigDecimal
import java.util.*

abstract class FormularioTransacaoDialog(
        private val context: Context,
        private val viewGroup: ViewGroup) {

    private val viewCriada = criaLayout()
    protected val campoCategoria = viewCriada.form_transacao_categoria
    protected val campoData = viewCriada.form_transacao_data
    protected val campoValor = viewCriada.form_transacao_valor

    protected abstract val tituloBotaoPositivo: String

    /*
        Como uma alternativa à solução anterior (implementação de Object Expression via Interface),
        pode-se utilizar uma estratégia de implementar a Interface em um arquivo JAVA e desenvolvê-la
        utilizando Lambda Expression ao invés de Obejct Expression. Isso é possivel pois, por questões
        de retrocompatibilidade, o Kotlin fornece o SAM (Single Abstract Method) Conversion proveniente
        do Java 8. Então, somente Interfaces em arquivo JAVA com um único método podem utilizar esse
        recurso e serem desenvolvidas utilizando Lambda Expression.

        Mas, o Kotlin fornece um outro recurso, chamado de "HIGH ORDER FUNCTION".
        Dessa forma, informamos que o "parâmetro" será uma função com as seguintes propriedades:
            - exemplo: fun show (delegate: (transacao: Transacao) -> Unit)
            -> 'delegate' : Nome da variável que irá representar a função
            -> ': (transacao: Transacao)' : Dentro dos parêntesis indica quais os parâmetros devem
               ser passados para a função. Caso não possua nenhum parâmetro, deve-se declarar os
               parêntesis sem conteúdo
            -> '-> Unit' : Após a arrow, deve ser informado o tipo de retorno da função. Como nesse
               nosso exemplo não realizamos nenhum retorno, estamos retornando UNIT.
     */
    fun show(tipo: Tipo, delegate: (transacao: Transacao) -> Unit) {
    //fun show(tipo: Tipo, delegate: TransacaoDelegate) {

        configuraCampoData()
        configuraCampoCategoria(tipo)
        configuraFormulario(tipo, delegate)
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

    /*
        Como não temos mais a Interface TransacaoDelegate, todos os pontos onde a High Order
        Function é passada como parâmetro, devemos ajustar a assinatura do método
     */
    private fun configuraFormulario(tipo: Tipo, delegate: (transacao: Transacao) -> Unit) {

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

                    delegate(transacaoCriada)

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