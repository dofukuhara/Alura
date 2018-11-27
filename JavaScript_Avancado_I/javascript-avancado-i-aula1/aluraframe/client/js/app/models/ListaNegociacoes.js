class ListaNegociacoes {

    constructor() {
        this._negociacoes = [];
    }

    adiciona(negociacao) {
        this._negociacoes.push(negociacao)
    }

    get negociacoes() {
        /*
            By just returning with 'return this._negociacoes', we are leading to a breach,
            where a developer can change the content of the array by modifing it's properties.
            E.g:
                this._listNegociacoes = new ListNegociacoes()
                this._listNegociacoes.adiciona(negociacao)

                // in this case, the push method from the returned array can be accessed, leading
                // to add new 'negociacao' without using the method 'adiciona'
                this._listNegociacoes.negociacoes.push(negociacao)

                // in this case, the length property from the array is being modified to zero.
                // By doing so, JS will automatically delete all the contents from the array
                this._listNegociacoes.negociacoes.length = 0
        */

        /*
            To resolve this, we can use the "Defensive Programming" strategy, where we will return
            a COPY of the array, insted of returning the original array.
            By doing so, all changes made in there will be done in the 'copied' array, presenving though 
            the original array
        */
        return [].concat(this._negociacoes)
    }
}