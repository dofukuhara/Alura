class DateHelper {

    constructor() {
        throw new Error('DataHelper não pode ser instanciada!')
    }

    static dataParaTexto(data) {

        /*
        return data.getDate() + '/'
            + (data.getMonth() + 1) + '/'
            +  data.getFullYear()
        */
        // Instead of using 'string concat' to build a string as in the example above,
        // JavaScript provide us 'Template String'. By making use of ` (backstick char) and
        // ${}, JS will interpolate the content of the variable in ${} into the result string

        // Note that we can use functions return as variable, as well as perform math operations
        return `${data.getDate()}/${data.getMonth() + 1}/${data.getFullYear()}`
    }

    static textoParaData(texto) {
        /* 
            As we are using a 'texto' String parameter to be used in Date() constructor, there is a 
            chance that this value may be in a wrong format, that would result in a non-desired value.
            So, for this case, we can use the "Fail Fast" strategy, which consists of checking the 
            input first and, if it is not in the right format, throw an exception informing it... 
            To do so, regex can be used to accomplish this verification
        */
        if (! /^\d{4}-\d{2}-\d{2}$/.test(texto))
            throw new Error('A data deve estar no formato aaaa-mm-dd')

        return new Date(
            ...texto
                .split('-')
                .map((item, indice) => item - indice % 2))
    }
}