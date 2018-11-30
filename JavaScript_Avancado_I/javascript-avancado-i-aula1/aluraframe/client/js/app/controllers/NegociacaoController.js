class NegociacaoController {

    constructor() {
        let $ = document.querySelector.bind(document)
    
        this._inputData = $("#data")
        this._inputQuantidade = $("#quantidade")
        this._inputValor = $("#valor")

        this._listNegociacoes = new ListaNegociacoes()

        /*
            Ao instanciar NegociacoesView, passamos no construtor o elemento do DOM ao qual essa view
            será atrelada.
            No método update de NegociacoesView, passamos a lista das negociacoes a ser exibida na view
        */
        this._negociacoesView = new NegociacoesView($("#negociacoesView"))
        this._negociacoesView.update(this._listNegociacoes)
    }

    adiciona(event) {
        event.preventDefault()

        /*
            new Date(ano, mes, dia) : Uma particularidade é que quando passamos ao construtor dessa classe 3 parâmetros
                    para a data, o parâmetro de mês é na verdade um índice com base 0, ou seja, se desejamos
                    configurar Novembro (mês 11), na verdade devemos passar como parâmetro 10
                    Utilizando as formas de instanciação abaixo, esse 'problema' não é encontrado:
                        new Date("2018,11,23") --> 23 de Novembro de 2018
                        new Date(["2018","11","23"]) --> 23 de Novembro de 2018
            ... -> Spread Operator : separa e passa cada item do array como sendo um argumento da funçao
                   eg: $ let array = ['2018', '11', '23']
                       $ new Date(...array)
                    fazendo essa chamada, será o mesmo que: new Date('2018', '11', '23')
            => -> Arrow Function: 'simplificação' de function, fazendo com que não precisamos escrever a
                    palavra 'function' e, no caso de a função ter apenas uma instrução, podemos também
                    omitir tanto as chaves {} quanto a palavra 'return'
        */

        /*
        Implementação sem a utilização de Arrow Function:

        let data = new Date(
            ...this._inputData.value
            .split('-')
            .map(function(item, indice) {
                // if (indice == 1) {
                //     return item - 1
                // }
                // return item

                return item - indice % 2
            })
        )
        */

        this._listNegociacoes.adiciona(this._criaNegociacao())
        this._limpaFormulario()

        // Além de chamar o método update() no construtor, iremos chamar em adiciona, pois aqui
        // a lista sobre alterações.
        this._negociacoesView.update(this._listNegociacoes)
    }

    /*
        Creating aux functions.
        They were named with the preffix '_' to indicate to developers that those functions
        can only be called inside this class.
    */
    _criaNegociacao() {
        return new Negociacao(
            DateHelper.textoParaData(this._inputData.value),
            this._inputQuantidade.value,
            this._inputValor.value
        )
    }

    _limpaFormulario() {
        this._inputData.value = ''
        this._inputQuantidade.value = 1
        this._inputValor.value = 0.0

        this._inputData.focus()
    }
}