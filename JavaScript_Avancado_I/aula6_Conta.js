/*
  No JavaScript não h[a uma forma de evitar alguém dar NEW na classe Conta.
  No mundo OO, podemos fazer isso declarando a classe como 'abstract', mas em
  JavaScript não possui esse recurso.
 */

class Conta {
	constructor(saldo) {
    this._saldo = saldo          
  }

  get saldo() {
    return this._saldo
  }

  atualiza(taxa) {
    throw new Error('Deve ser implementado na classe filha')
  }
}

module.exports = Conta
