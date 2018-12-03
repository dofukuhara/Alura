const Conta = require('./aula6_Conta.js')

class ContaCorrente extends Conta {
  atualiza(taxa) {
    this._saldo += taxa
  }
}

let contaCo = new ContaCorrente(10)
console.log(contaCo)
contaCo.atualiza(3)
console.log(contaCo)

module.exports = ContaCorrente
