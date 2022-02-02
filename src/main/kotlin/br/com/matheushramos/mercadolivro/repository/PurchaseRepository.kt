package br.com.matheushramos.mercadolivro.repository

import br.com.matheushramos.mercadolivro.model.Customer
import br.com.matheushramos.mercadolivro.model.Purchase
import org.springframework.data.jpa.repository.JpaRepository

interface PurchaseRepository : JpaRepository<Purchase, Int> {

    fun findByCustomer(customer: Customer?): Purchase

}