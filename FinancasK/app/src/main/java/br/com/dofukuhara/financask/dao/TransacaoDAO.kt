package br.com.dofukuhara.financask.dao

import br.com.dofukuhara.financask.model.Transacao

class TransacaoDAO {

    /*
        Criação de uma property, com o mesmo nome da variável de Companion Object, a qual recebe uma
        referência Read-Only, a fim de proteger o conteúdo dela (somente podendo ser alterada via
        funções de acesso.
     */
    val transacoes: List<Transacao> = Companion.transacoes

    /*
        Já que no Kotlin não temos o modificar 'static', uma solução que podemos utilizar é o
        "Companion Objects".
        Dessa forma, a variável "transacoes" seria uma referência estática à MutableList, fazendo
        sua persistência quando a aplicação foi recriada após a rotação do dispositivo.
        Porém, se fossemos manter seu acesso 'publico', qualquer um teria acesso a modificar seu
        conteúdo. Para resolver isso, podemos manter a variável como public e fazer com que a property,
        com o mesmo nome, tenha uma referência Read-Only de seu conteúdo
     */
    companion object{
        private val transacoes: MutableList<Transacao> = mutableListOf()
    }

    /*
        Já que temos a property (read-only) e a variável de Companion Object com o mesmo nome, caso
        seja necessário acessar a property, basta chamá-la pelo seu nome (ex: 'transacoes'). Já para
        acessar a Companion Object, podemos acessá-la como sendo uma property do objeto "Companion"
        (ex: "Companion.transacoes.add(transacao)")
     */
    fun adiciona(transacao: Transacao) {
        Companion.transacoes.add(transacao)
    }

    fun altera(transacao: Transacao, posicao: Int) {
        Companion.transacoes[posicao] = transacao
    }

    fun remove(posicao: Int) {
        Companion.transacoes.removeAt(posicao)
    }
}