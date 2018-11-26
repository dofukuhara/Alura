class Aluno {

    constructor(matricula, nome) {
        this.matricula = matricula;
        this.nome = nome;
    }
}

class Prova {

    constructor(aluno, nota) {
        this.aluno = aluno;
        this.nota = nota;
    }
}

var avaliacoes = [
    new Prova(new Aluno(1, 'Luana'), 8),
    new Prova(new Aluno(2, 'Cássio'), 6),
    new Prova(new Aluno(3, 'Barney'), 9),
    new Prova(new Aluno(4, 'Bira'), 5)
];

// functions without using arrow function
var aprovados = avaliacoes
    .filter(function(prova) { return prova.nota >= 7; })
    .map(function(prova) { return prova.aluno.nome;});

console.log(aprovados)

// ... and makding use if arrow function
var novoTeste = avaliacoes
    .filter(prova => prova.nota >= 7)
    .map(prova => prova.aluno.nome)

console.log(novoTeste)


// Generating a data by parsing a string and adjusting MONTH as per Date() requirements
let dataString = "05-10-2018"
let dataObjReference = new Date(2018, 9, 5)
let dataObj = new Date(...dataString
    .split('-')
    .map((item,indice) => item = item - indice % 2)
    .reverse())

console.log("Data obj reference: " + dataObjReference)
console.log("Data obj parsed:    " + dataObj)
