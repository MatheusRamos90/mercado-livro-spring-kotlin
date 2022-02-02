package br.com.matheushramos.mercadolivro.extensions

import br.com.matheushramos.mercadolivro.controller.request.PostBookRequest
import br.com.matheushramos.mercadolivro.controller.request.PostCustomerRequest
import br.com.matheushramos.mercadolivro.controller.request.PutBookRequest
import br.com.matheushramos.mercadolivro.controller.request.PutCustomerRequest
import br.com.matheushramos.mercadolivro.controller.response.BookResponse
import br.com.matheushramos.mercadolivro.controller.response.CustomerResponse
import br.com.matheushramos.mercadolivro.controller.response.PageResponse
import br.com.matheushramos.mercadolivro.controller.response.PurchaseResponse
import br.com.matheushramos.mercadolivro.enums.EnumStatusBook
import br.com.matheushramos.mercadolivro.enums.EnumStatusCustomer
import br.com.matheushramos.mercadolivro.model.Book
import br.com.matheushramos.mercadolivro.model.Customer
import br.com.matheushramos.mercadolivro.model.Purchase
import org.springframework.data.domain.Page

fun PostCustomerRequest.toCustomer(): Customer {
    return Customer(
        name = this.name,
        email = this.email,
        status = EnumStatusCustomer.ATIVO,
        password = this.password)
}

fun PutCustomerRequest.toCustomer(previousValue: Customer): Customer {
    return Customer(
        id = previousValue.id,
        name = this.name,
        email = this.email,
        status = previousValue.status,
        password = previousValue.password
    )
}

fun PostBookRequest.toBook(customer: Customer): Book {
    return Book(
        name = this.name,
        price = this.price,
        status = EnumStatusBook.ATIVO,
        customer = customer
    )
}

fun PutBookRequest.toBook(previousValue: Book) : Book {
    return Book(
        id = previousValue.id,
        name = this.name ?: previousValue.name,
        price = this.price ?: previousValue.price,
        status = previousValue.status,
        customer = previousValue.customer
    )
}

fun Customer.toResponse(): CustomerResponse {
    return CustomerResponse(
        id = this.id,
        name = this.name,
        email = this.email,
        status = this.status
    )
}

fun Book.toResponse(): BookResponse {
    return BookResponse(
        id = this.id,
        name = this.name,
        price = this.price,
        customer = this.customer,
        status = this.status
    )
}

fun Purchase.toResponse(): PurchaseResponse {
    return PurchaseResponse(
        id = this.id,
        customer = this.customer,
        books = this.books,
        nfe = this.nfe,
        price = this.price,
        createAt = this.createAt.toString()
    )
}

fun <T> Page<T>.toResponseList(): PageResponse<T> {
    return PageResponse(
        this.content,
        this.number,
        this.totalElements,
        this.totalPages
    )
}