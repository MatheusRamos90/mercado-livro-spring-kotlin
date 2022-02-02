package br.com.matheushramos.mercadolivro.controller.request

import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

data class PutCustomerRequest(

    @field:NotEmpty(message = "O nome deve ser preenchido")
    var name: String,

    @field:Email(message = "O email deve ser válido")
    var email: String
) {
}