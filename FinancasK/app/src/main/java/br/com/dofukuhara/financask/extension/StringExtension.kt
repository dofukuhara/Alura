package br.com.dofukuhara.financask.extension

fun String.limitUntil(characters : Int) : String {
    if (this.length > characters) {
        val primeiroCaracter = 0
        return "${this.substring(primeiroCaracter, characters)}..."
    }
    return this
}