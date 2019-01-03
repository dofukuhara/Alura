package br.com.dofukuhara.financask.extension

import java.text.SimpleDateFormat
import java.util.*

fun String.limitUntil(characters : Int) : String {
    if (this.length > characters) {
        val primeiroCaracter = 0
        return "${this.substring(primeiroCaracter, characters)}..."
    }
    return this
}

fun String.converteParaCalendar() : Calendar {

    val formatoBrasileiro = SimpleDateFormat("dd/MM/yyyy")
    val dataConvertida: Date = formatoBrasileiro.parse(this)
    val data = Calendar.getInstance()
    data.time = dataConvertida

    return data
}