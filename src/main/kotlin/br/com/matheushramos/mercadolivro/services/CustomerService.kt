package br.com.matheushramos.mercadolivro.services

import br.com.matheushramos.mercadolivro.enums.EnumErrors
import br.com.matheushramos.mercadolivro.enums.EnumProfileRole
import br.com.matheushramos.mercadolivro.enums.EnumStatusCustomer
import br.com.matheushramos.mercadolivro.exceptions.NotFoundException
import br.com.matheushramos.mercadolivro.model.Customer
import br.com.matheushramos.mercadolivro.repository.CustomerRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class CustomerService(
    private val customerRepository: CustomerRepository,
    private val bookService: BookService,
    private val bCrypt: BCryptPasswordEncoder
) {

    fun findAll(name: String?): List<Customer> {
        name?.let {
            return customerRepository.findByNameContaining(it)
        }

        return customerRepository.findAll().toList()
    }

    fun findById(id: Int): Customer = customerRepository.findById(id).orElseThrow{ NotFoundException(EnumErrors.M101.message.format(id), EnumErrors.M101.code) }

    fun create(customer: Customer) {
        val customerCopy = customer.copy(
            roles = setOf(EnumProfileRole.CUSTOMER),
            password = bCrypt.encode(customer.password)
        )
        customerRepository.save(customerCopy)
    }

    fun update(customer: Customer) {
        if (!customerRepository.existsById(customer.id!!)) {
            throw NotFoundException(EnumErrors.M101.message.format(customer.id), EnumErrors.M101.code)
        }
        customerRepository.save(customer)
    }

    fun delete(id: String) {
        val customer = findById(id.toInt())
        bookService.deleteByCustomer(customer)

        customer.status = EnumStatusCustomer.INATIVO

        customerRepository.save(customer)
    }

    fun emailAvailable(email: String): Boolean {
        return !customerRepository.existsByEmail(email)
    }

}