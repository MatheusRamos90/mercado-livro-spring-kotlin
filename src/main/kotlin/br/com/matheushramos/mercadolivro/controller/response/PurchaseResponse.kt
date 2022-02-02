package br.com.matheushramos.mercadolivro.controller.response

import br.com.matheushramos.mercadolivro.model.Book
import br.com.matheushramos.mercadolivro.model.Customer
import java.math.BigDecimal

data class PurchaseResponse(
    var id: Int? = null,
    var customer: Customer,
    var books: List<Book>,
    var nfe: String? = null,
    var price: BigDecimal,
    var createAt: String
)