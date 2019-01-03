package br.com.dofukuhara.financask.delegate

import br.com.dofukuhara.financask.model.Transacao

interface TransacaoDelegate {

    fun delegate(transacao: Transacao)
}