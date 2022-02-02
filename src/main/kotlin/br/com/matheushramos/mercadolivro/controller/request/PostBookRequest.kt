package br.com.matheushramos.mercadolivro.controller.request

import com.fasterxml.jackson.annotation.JsonAlias
import java.math.BigDecimal
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class PostBookRequest(

    @field:NotEmpty(message = "O nome deve ser preenchido")
    var name: String,

    @field:NotNull(message = "O preço deve ser preenchido")
    var price: BigDecimal,

    @JsonAlias("customer_id")
    var customerId: Int
) {
}