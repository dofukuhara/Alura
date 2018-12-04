package br.com.dofukuhara.financask.extension

import java.text.SimpleDateFormat
import java.util.Calendar

fun Calendar.formatToBrazilian() : String {
    val brazilianFormat = "dd/MM/yyyy"
    val format = SimpleDateFormat(brazilianFormat)
    return format.format(this.time)
}