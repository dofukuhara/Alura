package br.com.dofukuhara.financask.ui.dialog

import android.content.Context
import android.view.ViewGroup

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
        context: Context) : FormularioTransacaoDialog(context, viewGroup)