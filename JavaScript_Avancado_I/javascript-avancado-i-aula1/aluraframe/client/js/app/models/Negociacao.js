// No mundo javascript, não é comum ter o nome do arquivo começando em letra maiúscula (pascal case),
// porém, nesse caso, isso indica especificamente que o arquivo representa uma definição
// de CLASSE

class Negociacao {

    constructor(data, quantidade, valor) {

        /* 
            Até o momento, a linguagem JavaScript não provê modificadores de acesso, como por
            exemplo private. Dessa forma, não temos como restringir que o desenvolvedor modifique
            o valor de uma variável da classe.
            Então, foi feita uma convenção de que, para indicar que uma variável não possa ser modificada
            diretamente, a variável será prefixada com underline. Dessa forma, indicamos que a variável só 
            possa ser modificada dentro da classe.
            Isso não faz com que o JavaScript bloqueie o acesso (tanto de leitura quanto de escrita) dessa
            variável, somente server como indicação. Além disso, como outra convenção, é a de criar métodos
            'get()' para os atributos
        */
        this._data = new Date(data.getTime()) // Estratégia de programação Defensiva
        this._quantidade = quantidade
        this._valor = valor


        /*
            Ao 'congelar' o objeto, isso torna as propriedes dessa classe 'imutáveis'.
            Entretanto, esse congelamento não é um 'deep freeze', mas sim um 'shallow freeze'.
            Isso quer dizer que o congelamento é superficial, ou seja, no caso de objetos (como no
            Date neste exemplo), a referência para esse objeto é imutável, mas como ela possui métodos
            de acesso que modificação seu comportamento (data.setDate() por exemplo), ao invocá-los, a 
            propriedade do objeto pode ser alterada!
            PS: Para isso, pode-se utilizar estratégias de 'Programação Defensiva' para evitar essas
            modificações indesejadas.
        */
        Object.freeze(this)
    }


    /*
        Uma abordagem interessante é a de utilizar a sintaxe 'get' para implementar os métodos de acesso
        para os atributos de uma classe.
        Ao implementarmos 'get quantidade()' ao invés de 'getQuantidade()' por exemplo, faz com que a invocação 
        desse método seja similar ao de um atributo e não de uma função:
            - 'getQuantidade() { return this._quantidade }':
                - instancia.getQuantidade()
            - 'get quantidade() { return this._quantidade }':
                - instancia.quantidade
        Dessa forma, o atributo fica "um pouco mais protegido" à tentativa de modificação, já que ao
        invocar essa função, ela é semelhante à invocação do acesso direto a uma variável da classe, e 
        não a uma função. Assim, caso o desenvolvedor tente realizar a operação:
            - instancia.quantidade = 1000
        isso não irá surtir nenhum efeito prático, pois "instancia.quantidade" remete à função get, 
        enquanto que o atributo _quantidade está 'protegido' pelo _ (mas, continua não impedindo do
        desenvolvedor acessar diretamente instancia._quantidade)

    */
    get volume() {
        return this._quantidade * this._valor
    }

    get data() {
        return new Date(this._data.getTime()) // Estratégia de programação Defensiva
    }
    
    get quantidade() {
        return this._quantidade
    }

    get valor() {
        return this._valor
    }
}