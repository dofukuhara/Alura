package br.com.dofukuhara.financask.ui.dialog

import android.content.Context
import android.view.ViewGroup
import br.com.dofukuhara.financask.R
import br.com.dofukuhara.financask.model.Tipo

/*
    Note que, como foram movidos todos os métodos para a SuperClasse, as variáveis passadas como
    parâmetro para ela não são mais usadas como properties.
    Por esse motivo, o construtor de AdicionaTransacaoDialog pode ser modificado de:
        -  (private val viewGroup: ViewGroup, private val context: Context)
    para:
        - (viewGroup: ViewGroup, context: Context)
    dessa forma, o Kotlin não irá gerar as properties de classe 'viewGroup' e 'context'
 */
class AdicionaTransacaoDialog(
        viewGroup: ViewGroup,
        context: Context) : FormularioTransacaoDialog(context, viewGroup) {

    override val tituloBotaoPositivo: String
        get() = "Adicionar"

    override fun tituloPor(tipo: Tipo): Int {
        if (tipo == Tipo.RECEITA) {
            return R.string.adiciona_receita
        }
        return R.string.adiciona_despesa
    }
}