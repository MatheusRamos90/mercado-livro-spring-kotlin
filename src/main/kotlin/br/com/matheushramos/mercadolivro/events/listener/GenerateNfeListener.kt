package br.com.matheushramos.mercadolivro.events.listener

import  br.com.matheushramos.mercadolivro.events.PurchaseEvent
import br.com.matheushramos.mercadolivro.services.PurchaseService
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import java.util.*

@Component
class GenerateNfeListener(
    private val purchaseService: PurchaseService
) {

    @Async
    @EventListener
    fun listenPurchase(purchaseEvent: PurchaseEvent) {
        println("--> Gerando NFE...")
        val nfeId = UUID.randomUUID().toString()
        val purchase = purchaseEvent.purchase.copy(nfe = nfeId)
        purchaseService.update(purchase)
    }

}