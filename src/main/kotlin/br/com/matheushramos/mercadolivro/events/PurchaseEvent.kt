package br.com.matheushramos.mercadolivro.events

import br.com.matheushramos.mercadolivro.model.Purchase
import org.springframework.context.ApplicationEvent

class PurchaseEvent(
    source: Any,
    var purchase: Purchase
) : ApplicationEvent(source)