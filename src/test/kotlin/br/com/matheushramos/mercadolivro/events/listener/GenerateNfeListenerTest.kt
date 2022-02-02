package br.com.matheushramos.mercadolivro.events.listener

import br.com.matheushramos.mercadolivro.events.PurchaseEvent
import br.com.matheushramos.mercadolivro.helper.buildPurchase
import br.com.matheushramos.mercadolivro.services.PurchaseService
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*

@ExtendWith(MockKExtension::class)
class GenerateNfeListenerTest {

    @MockK
    private lateinit var purchaseService: PurchaseService

    @InjectMockKs
    private lateinit var generateNfeListener: GenerateNfeListener


    @Test
    fun `should generate nfe`() {
        val purchase = buildPurchase(nfe = null)
        val fakeNfe = UUID.randomUUID()
        val purchaseExpected = purchase.copy(nfe = fakeNfe.toString())
        mockkStatic(UUID::class)

        every { UUID.randomUUID() } returns fakeNfe
        every { purchaseService.update(purchaseExpected) } just runs

        generateNfeListener.listenPurchase(PurchaseEvent(this, purchase))

        verify(exactly = 1) { purchaseService.update(purchaseExpected) }
    }

}