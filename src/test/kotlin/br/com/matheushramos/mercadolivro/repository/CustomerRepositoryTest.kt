package br.com.matheushramos.mercadolivro.repository

import br.com.matheushramos.mercadolivro.helper.buildCustomer
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockKExtension::class)
class CustomerRepositoryTest {

    @Autowired
    private lateinit var customerRepository: CustomerRepository

    @BeforeEach
    fun setup() = customerRepository.deleteAll()

    @Test
    fun `should return name containing`() {
        val marcosCustomer = customerRepository.save(buildCustomer(name = "Marcos"))
        val matheusCustomer = customerRepository.save(buildCustomer(name = "Matheus"))
        customerRepository.save(buildCustomer(name = "Alex"))

        val customers = customerRepository.findByNameContaining("Ma")

        assertEquals(listOf(marcosCustomer, matheusCustomer), customers)
    }

    @Nested
    inner class `exists by email` {
        @Test
        fun `should return true when email exists`() {
            val email = "email@gmail.com"
            customerRepository.save(buildCustomer(email = email))

            val customerExists = customerRepository.existsByEmail(email)

            assertTrue(customerExists)
        }

        @Test
        fun `should return false when email do not exists`() {
            val email = "emailemail@gmail.com"

            val customerExists = customerRepository.existsByEmail(email)

            assertFalse(customerExists)
        }
    }

    @Nested
    inner class `find by email` {
        @Test
        fun `should return customer when email exists`() {
            val email = "findemail@gmail.com"
            val customer = customerRepository.save(buildCustomer(email = email))

            val customerResult = customerRepository.findByEmail(email)

            assertNotNull(customerResult)
            assertEquals(customer, customerResult)
        }
    }

}