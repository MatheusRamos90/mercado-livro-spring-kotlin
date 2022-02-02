package br.com.matheushramos.mercadolivro.controller.request

import com.fasterxml.jackson.annotation.JsonAlias
import java.math.BigDecimal

data class PutBookRequest(
    var id: Int,
    var name: String,
    var price: BigDecimal,

    @JsonAlias("customer_id")
    var customerId: Int
) {
}