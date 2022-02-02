package br.com.matheushramos.mercadolivro.controller.request

import br.com.matheushramos.mercadolivro.validation.EmailAvailable
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

data class PostCustomerRequest(

    @field:NotEmpty(message = "O nome deve ser preenchido")
    var name: String,

    @field:Email(message = "O e-mail deve ser válido")
    @EmailAvailable(message = "Este e-mail já existe")
    var email: String,

    @field:NotEmpty(message = "A senha deve ser preenchida")
    var password: String

) {
}