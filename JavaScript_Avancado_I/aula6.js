// Funcao REDUCE
// reduce(callbackFunction, defaultValue)
let numeros = [1, 2, 3, 4, 5]

let somatorio = numeros.reduce((total, numero) => total + numero, 0)
let produtorio = numeros.reduce((total, numero) => total * numero, 1)

console.log(`Itens do array: [${numeros}]`)
console.log(`Valor do somatório: ${somatorio}`)
console.log(`Valor do produtório: ${produtorio}`)

// Funcao MAP
let funcionarios = [
    {
        "nome": "Douglas",
        "endereco" : "Rua da esquina, 123",
        "salario" : "4500"
    },
    {
        "nome": "Felipe",
        "endereco" : "Rua da virada, 456",
        "salario" : "5000"
    },
    {
        "nome": "Silvio",
        "endereco" : "Rua da aresta, 789",
        "salario" : "6000"
    }
];

let funcionariosEmHTML = funcionarios.map( f => `
    <tr>
        <td>${f.nome}</td>
        <td>${f.endereco}</td>
        <td>${f.salario}</td>
    </tr>
`)

console.log("\n\n*********** Ao utilizar MAP, será gerado um novo array com os elementos processados:")
console.log(funcionariosEmHTML)
console.log("\n********** Ao utilizar o JOIN, unimos os elementos do array em uma única string. Dessa forma, podemos inserí-lo facilmente como item de uma Template String para depois colocá-lo em um innerHTML:")
console.log(funcionariosEmHTML.join(''))
