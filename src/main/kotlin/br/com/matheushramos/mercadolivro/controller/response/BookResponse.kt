package br.com.matheushramos.mercadolivro.controller.response

import br.com.matheushramos.mercadolivro.enums.EnumStatusBook
import br.com.matheushramos.mercadolivro.model.Customer
import java.math.BigDecimal

data class BookResponse(
    var id: Int? = null,
    var name: String,
    var price: BigDecimal,
    var customer: Customer? = null,
    var status: EnumStatusBook? = null
)