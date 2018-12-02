class View {

    constructor(elemento) {
        this._elemento = elemento
    }

    template(model) {
        throw new Error('O método template deve ser implementado pela classe filha')
    }

    update(model) {
        // A propriedade innerHTML irá converter a string em elementos do DOM
        this._elemento.innerHTML = this.template(model)
    }
}