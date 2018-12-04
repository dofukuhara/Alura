package br.com.dofukuhara.financask.model

import java.math.BigDecimal
import java.util.*

class Transacao (val valor: BigDecimal,
                 val categoria : String = "undefined",
                 val tipo : Tipo,
                 val data : Calendar = Calendar.getInstance())
