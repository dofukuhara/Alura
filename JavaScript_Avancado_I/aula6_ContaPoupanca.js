const Conta = require('./aula6_Conta.js')

class ContaPoupanca extends Conta {
  atualiza(taxa) {
    this._saldo += taxa * 2
  }
}

let contaPo = new ContaPoupanca(10)
console.log(contaPo)

contaPo.atualiza(3)
console.log(contaPo)

module.exports = ContaPoupanca
