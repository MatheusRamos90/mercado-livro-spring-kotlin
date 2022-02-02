package br.com.matheushramos.mercadolivro.controller

import br.com.matheushramos.mercadolivro.controller.request.PostPurchaseRequest
import br.com.matheushramos.mercadolivro.controller.response.PurchaseResponse
import br.com.matheushramos.mercadolivro.extensions.toResponse
import br.com.matheushramos.mercadolivro.mapper.PurchaseMapper
import br.com.matheushramos.mercadolivro.services.PurchaseService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("purchases")
class PurchaseController(
    private val purchaseService: PurchaseService,
    private val purchaseMapper: PurchaseMapper
) {

    @GetMapping
    fun findAll(): List<PurchaseResponse> = purchaseService.findAll().map { it.toResponse() }

    @GetMapping("customer-buyer/{id}")
    fun findByCustomerBuyer(@PathVariable id: String): PurchaseResponse = purchaseService.findByCustomerBuyer(id.toInt()).toResponse()

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody purchase: PostPurchaseRequest) = purchaseService.create(purchaseMapper.toModel(purchase))

}