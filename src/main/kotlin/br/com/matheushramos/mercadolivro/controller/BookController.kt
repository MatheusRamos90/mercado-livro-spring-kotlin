package br.com.matheushramos.mercadolivro.controller

import br.com.matheushramos.mercadolivro.controller.request.PostBookRequest
import br.com.matheushramos.mercadolivro.controller.request.PutBookRequest
import br.com.matheushramos.mercadolivro.controller.response.BookResponse
import br.com.matheushramos.mercadolivro.controller.response.PageResponse
import br.com.matheushramos.mercadolivro.extensions.toBook
import br.com.matheushramos.mercadolivro.extensions.toResponse
import br.com.matheushramos.mercadolivro.extensions.toResponseList
import br.com.matheushramos.mercadolivro.services.BookService
import br.com.matheushramos.mercadolivro.services.CustomerService
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("books")
class BookController(
    private val bookService: BookService,
    private val customerService: CustomerService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody @Valid body : PostBookRequest) {
        val customer = customerService.findById(body.customerId)
        bookService.create(body.toBook(customer))
    }

    @GetMapping
    fun findAll(@PageableDefault(page = 0, size = 10) pageable: Pageable): PageResponse<BookResponse>
        = bookService.findAll(pageable).map { it.toResponse() }.toResponseList()

    @GetMapping("saled")
    fun findAllByStatusVendido(@PageableDefault(page = 0, size = 10) pageable: Pageable): PageResponse<BookResponse>
        = bookService.findAllByStatusVendido(pageable).map { it.toResponse() }.toResponseList()

    @GetMapping("/actives")
    fun findByBooksActives(@PageableDefault(page = 0, size = 10) pageable: Pageable): PageResponse<BookResponse>
        = bookService.findByBooksActives(pageable).map { it.toResponse() }.toResponseList()

    @GetMapping("/{id}")
    fun findById(@PathVariable id: String): BookResponse = bookService.findById(id.toInt()).toResponse()

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun update(@PathVariable id: String, @RequestBody body : PutBookRequest) {
        val book = bookService.findById(id.toInt())
        bookService.update(body.toBook(book))
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: String) = bookService.setDeleteBook(id.toInt())

}