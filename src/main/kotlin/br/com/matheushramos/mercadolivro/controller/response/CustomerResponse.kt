package br.com.matheushramos.mercadolivro.controller.response

import br.com.matheushramos.mercadolivro.enums.EnumStatusCustomer

data class CustomerResponse(
    var id: Int? = null,
    var name: String,
    var email: String,
    var status: EnumStatusCustomer? = null
)