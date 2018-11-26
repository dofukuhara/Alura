class NegociacaoController {

    constructor() {
        let $ = document.querySelector.bind(document)
    
        this._inputData = $("#data")
        this._inputQuantidade = $("#quantidade")
        this._inoputValor = $("#valor")
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

        let data = new Date(
            ...this._inputData.value
                .split('-')
                .map((item, indice) => item - indice % 2)
        )

        let negociacao = new Negociacao(
            data,
            this._inputQuantidade.value,
            this._inoputValor.value
        )

        console.log(negociacao)
    }
}