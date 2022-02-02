package br.com.matheushramos.mercadolivro.services

import br.com.matheushramos.mercadolivro.enums.EnumErrors
import br.com.matheushramos.mercadolivro.enums.EnumStatusBook
import br.com.matheushramos.mercadolivro.events.PurchaseEvent
import br.com.matheushramos.mercadolivro.exceptions.NotFoundException
import br.com.matheushramos.mercadolivro.model.Purchase
import br.com.matheushramos.mercadolivro.repository.CustomerRepository
import br.com.matheushramos.mercadolivro.repository.PurchaseRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class PurchaseService(
    private val purchaseRepository: PurchaseRepository,
    private val bookService: BookService,
    private val customerRepository: CustomerRepository,
    private val applicationEventPublisher: ApplicationEventPublisher
) {
    fun findAll(): List<Purchase> = purchaseRepository.findAll().toList()

    fun create(purchase: Purchase) {
        purchase.books
            .filter { EnumStatusBook.ATIVO != bookService.findById(it.id!!.toInt()).status }
            .map { throw NotFoundException(EnumErrors.M002.message.format(it.id), EnumErrors.M002.code) }

        purchaseRepository.save(purchase)

        println("--> Enviando evento de criação de compra efetuada")
        applicationEventPublisher.publishEvent(PurchaseEvent(this, purchase))
        println("--> Finalizando evento de criação de compra")
    }

    fun update(purchase: Purchase) {
        purchaseRepository.save(purchase)
    }

    fun findByCustomerBuyer(customerId: Int): Purchase {
        val customer = customerRepository.findById(customerId).orElseThrow{ NotFoundException(EnumErrors.M101.message.format(customerId), EnumErrors.M101.code) }
        return purchaseRepository.findByCustomer(customer)
    }
}