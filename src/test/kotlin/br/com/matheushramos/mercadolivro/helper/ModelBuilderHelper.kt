package br.com.matheushramos.mercadolivro.helper

import br.com.matheushramos.mercadolivro.enums.EnumProfileRole
import br.com.matheushramos.mercadolivro.enums.EnumStatusCustomer
import br.com.matheushramos.mercadolivro.model.Book
import br.com.matheushramos.mercadolivro.model.Customer
import br.com.matheushramos.mercadolivro.model.Purchase
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

fun buildCustomer(
    id: Int? = null,
    name: String = "customer name",
    email: String = "${UUID.randomUUID()}@gmail.com",
    password: String = "password"
) = Customer(
    id = id,
    name = name,
    email = email,
    status = EnumStatusCustomer.ATIVO,
    password = password,
    roles = setOf(EnumProfileRole.CUSTOMER)
)

fun buildPurchase(
    id: Int? = null,
    customer: Customer = buildCustomer(),
    books: MutableList<Book> = mutableListOf(),
    nfe: String? = UUID.randomUUID().toString(),
    price: BigDecimal = BigDecimal.TEN
) = Purchase(
    id = id,
    customer = customer,
    books = books,
    nfe = nfe,
    price = price,
    createAt = LocalDateTime.now()
)

fun buildBook(
    id: Int? = null,
    name: String = "book name",
    price: BigDecimal = BigDecimal.TEN,
    customer: Customer = buildCustomer()
) = Book(
    id = id,
    name = name,
    price = price,
    customer = customer
)