class NegociacoesView {
    
    constructor(elemento) {
        this._elemento = elemento
    }

    /*
        Uma das vantagens de se utilizar Template Strings é o de pode incluir texto com quebra de linhas.
        Outra vantagem é a de aceitar expressões ${}, que iremos utilizar para incluir novas tr com os dados
        da lista de negociacoes.
        No caso, utilizamos o ".join" com critério '' pois o returno de map irá retornar um array com N 
        strings, referentes a cada item da lista. Ao utilizar esse join, iremos converter as strings do
        array em uma única string, possibilitando assim a inclusão desta na Template String.
    */
    /*
        Immediately-Invoked Function Expression (IIFE)
            - (function() {})()
            - Função auto-invocável, que pode ser utilizada para criar escopo e, nesse caso, permitir
              com que expressão com mais de uma instrução seja executada
    */
    /*
        Ao invés de utilizar IIFEs, nesse caso podemos fazer utilização de programação funcional.
        Neste caso, podemos utilizar o método reduce(), que irá processar um array e retornar um 
        único resultado.
        - array.reduce(function(variavelQueIraAcumularOValor, itemDoArray) 
                                    { // return X }, 
                                    parametroInicial de 'variavelQueIraAcumularOValor' )
    */
    _template(model) {
        return `
            <table class="table table-hover table-bordered">
                <thead>
                    <tr>
                        <th>DATA</th>
                        <th>QUANTIDADE</th>
                        <th>VALOR</th>
                        <th>VOLUME</th>
                    </tr>
                </thead>
                
                <tbody>
                    ${model.negociacoes.map(n => `
                        <tr>
                            <td>${DateHelper.dataParaTexto(n.data)}</td>
                            <td>${n.quantidade}</td>
                            <td>${n.valor}</td>
                            <td>${n.volume}</td>
                        </tr>
                    `).join('')}
                </tbody>
                
                <tfoot>
                    <td colspan="3"></td>
                    <td>${
                        model.negociacoes.reduce(function(total, n) {
                            return total + n.volume
                        }, 0.0)
                    }</td>
                </tfoot>
            </table>`
    }

    update(model) {
        // A propriedade innerHTML irá converter a string em elementos do DOM
        this._elemento.innerHTML = this._template(model)
    }
}