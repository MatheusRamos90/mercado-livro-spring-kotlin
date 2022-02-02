package br.com.matheushramos.mercadolivro.mapper

import br.com.matheushramos.mercadolivro.controller.request.PostPurchaseRequest
import br.com.matheushramos.mercadolivro.model.Purchase
import br.com.matheushramos.mercadolivro.services.BookService
import br.com.matheushramos.mercadolivro.services.CustomerService
import org.springframework.stereotype.Component

@Component
class PurchaseMapper(
    private val customerService: CustomerService,
    private val bookService: BookService
) {

    fun toModel(purchase: PostPurchaseRequest): Purchase {
        val customer = customerService.findById(purchase.customerId)
        val books = bookService.findAllByIds(purchase.bookIds)

        return Purchase(
            customer = customer,
            books = books.toMutableList(),
            price = books.sumOf { it.price }
        )
    }

}