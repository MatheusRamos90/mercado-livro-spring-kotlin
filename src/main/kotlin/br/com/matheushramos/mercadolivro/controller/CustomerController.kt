package br.com.matheushramos.mercadolivro.controller

import br.com.matheushramos.mercadolivro.controller.request.PostCustomerRequest
import br.com.matheushramos.mercadolivro.controller.request.PutCustomerRequest
import br.com.matheushramos.mercadolivro.controller.response.CustomerResponse
import br.com.matheushramos.mercadolivro.extensions.toCustomer
import br.com.matheushramos.mercadolivro.extensions.toResponse
import br.com.matheushramos.mercadolivro.security.UserCanOnlyAccessTheirOwnResource
import br.com.matheushramos.mercadolivro.services.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("customers")
class CustomerController(
    private val customerService: CustomerService
) {

    @GetMapping
    fun findAll(@RequestParam name: String?): List<CustomerResponse> = customerService.findAll(name).map { it.toResponse() }

    @GetMapping("/{id}")
    @UserCanOnlyAccessTheirOwnResource
    fun getCustomerById(@PathVariable id: Int): CustomerResponse = customerService.findById(id).toResponse()

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody @Valid body: PostCustomerRequest) = customerService.create(body.toCustomer())

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @UserCanOnlyAccessTheirOwnResource
    fun update(@PathVariable id: Int, @RequestBody @Valid body: PutCustomerRequest) {
        val customerSaved = customerService.findById(id)
        customerService.update(body.toCustomer(customerSaved))
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: String) = customerService.delete(id)

}