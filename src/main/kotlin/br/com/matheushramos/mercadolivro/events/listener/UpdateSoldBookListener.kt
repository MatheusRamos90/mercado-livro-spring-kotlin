package br.com.matheushramos.mercadolivro.events.listener

import br.com.matheushramos.mercadolivro.events.PurchaseEvent
import br.com.matheushramos.mercadolivro.services.BookService
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class UpdateSoldBookListener(
    private val bookService: BookService
) {

    @Async
    @EventListener
    fun listenPurchase(purchase: PurchaseEvent) {
        println("--> Atualizando status do livros")
        bookService.purchase(purchase.purchase.books)
    }

}