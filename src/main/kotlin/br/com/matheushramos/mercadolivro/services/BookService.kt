package br.com.matheushramos.mercadolivro.services

import br.com.matheushramos.mercadolivro.enums.EnumErrors
import br.com.matheushramos.mercadolivro.enums.EnumStatusBook
import br.com.matheushramos.mercadolivro.exceptions.NotFoundException
import br.com.matheushramos.mercadolivro.model.Book
import br.com.matheushramos.mercadolivro.model.Customer
import br.com.matheushramos.mercadolivro.repository.BookRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class BookService(
    private val bookRepository: BookRepository
) {
    fun create(book: Book) = bookRepository.save(book)

    fun findAll(pageable: Pageable): Page<Book> = bookRepository.findAll(pageable)

    fun findAllByStatusVendido(pageable: Pageable): Page<Book> = bookRepository.findByStatus(EnumStatusBook.VENDIDO, pageable)

    fun findByBooksActives(pageable: Pageable): Page<Book> = bookRepository.findByStatus(EnumStatusBook.ATIVO, pageable)

    fun findById(id: Int): Book = bookRepository.findById(id).orElseThrow{ NotFoundException(EnumErrors.M201.message.format(id), EnumErrors.M201.code) }

    fun update(book: Book) = bookRepository.save(book)

    fun setDeleteBook(id: Int) {
        val book = findById(id)
        book.status = EnumStatusBook.CANCELADO
        update(book)
    }

    fun deleteByCustomer(customer: Customer) {
        val books = bookRepository.findByCustomer(customer)
        for (book in books) {
            book.status = EnumStatusBook.DELETADO
        }
        bookRepository.saveAll(books)
    }

    fun findAllByIds(bookIds: Set<Int>): List<Book> = bookRepository.findAllById(bookIds).toList()

    fun purchase(books: MutableList<Book>) {
        books.map {
            it.status = EnumStatusBook.VENDIDO
        }
        bookRepository.saveAll(books)
    }

}