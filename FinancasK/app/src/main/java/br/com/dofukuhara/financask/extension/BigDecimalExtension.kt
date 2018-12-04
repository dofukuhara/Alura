package br.com.dofukuhara.financask.extension

import java.math.BigDecimal
import java.text.DecimalFormat
import java.util.Locale

fun BigDecimal.formatToBrazilizan() : String {
    val currencyFormatter = DecimalFormat.getCurrencyInstance(Locale("pt", "br"))
    //return currencyFormatter.format(this).replace("(.)(\\d{1,})", "$0 $1")
    return currencyFormatter.format(this)
            .replace("R$", "R$ ")
            .replace("-R$ ", "R$ -")
}