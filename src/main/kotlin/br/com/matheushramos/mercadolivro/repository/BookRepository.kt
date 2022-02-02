package br.com.matheushramos.mercadolivro.repository

import br.com.matheushramos.mercadolivro.enums.EnumStatusBook
import br.com.matheushramos.mercadolivro.model.Book
import br.com.matheushramos.mercadolivro.model.Customer
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface BookRepository : JpaRepository<Book, Int> {

    fun findByStatus(status: EnumStatusBook, pageable: Pageable) : Page<Book>
    fun findByCustomer(customer: Customer) : List<Book>

}