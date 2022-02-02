package br.com.matheushramos.mercadolivro.controller

import br.com.matheushramos.mercadolivro.controller.request.PostCustomerRequest
import br.com.matheushramos.mercadolivro.controller.request.PutCustomerRequest
import br.com.matheushramos.mercadolivro.enums.EnumStatusCustomer
import br.com.matheushramos.mercadolivro.helper.buildCustomer
import br.com.matheushramos.mercadolivro.repository.CustomerRepository
import br.com.matheushramos.mercadolivro.security.UserCustomDetails
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration
@ActiveProfiles("test")
@WithMockUser
class CustomerControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var customerRepository: CustomerRepository

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @BeforeEach
    fun setupBeforeEach() {
        customerRepository.deleteAll()
    }

    @AfterEach
    fun setupAfterEach() {
        customerRepository.deleteAll()
    }

    @Test
    fun `should return all customer when get all`() {
        val customer1 = customerRepository.save(buildCustomer())
        val customer2 = customerRepository.save(buildCustomer())

        mockMvc.perform(get("/customers"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].id").value(customer1.id))
            .andExpect(jsonPath("$[0].name").value(customer1.name))
            .andExpect(jsonPath("$[0].email").value(customer1.email))
            .andExpect(jsonPath("$[0].status").value(customer1.status.name))
            .andExpect(jsonPath("$[1].id").value(customer2.id))
            .andExpect(jsonPath("$[1].name").value(customer2.name))
            .andExpect(jsonPath("$[1].email").value(customer2.email))
            .andExpect(jsonPath("$[1].status").value(customer2.status.name))
    }

    @Test
    fun `should filter all customers by name when get all`() {
        val customer1 = customerRepository.save(buildCustomer(name = "Marcos"))
        customerRepository.save(buildCustomer(name = "Gustavo"))

        mockMvc.perform(get("/customers?name=Mar"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.length()").value(1))
            .andExpect(jsonPath("$[0].id").value(customer1.id))
            .andExpect(jsonPath("$[0].name").value(customer1.name))
            .andExpect(jsonPath("$[0].email").value(customer1.email))
            .andExpect(jsonPath("$[0].status").value(customer1.status.name))
    }

    @Test
    fun `should create customer`() {
        val request = PostCustomerRequest("fake name", "fakename@email.com", "123456")

        mockMvc.perform(post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated)

        val customers = customerRepository.findAll().toList()
        assertEquals(1, customers.size)
        assertEquals(request.name, customers[0].name)
        assertEquals(request.email, customers[0].email)
    }

    @Test
    fun `should throw error when create customer has invalid information`() {
        val request = PostCustomerRequest("", "fakename@email.com", "123456")

        mockMvc.perform(post("/customers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isUnprocessableEntity)
            .andExpect(jsonPath("$.code").value(422))
            .andExpect(jsonPath("$.message").value("Invalid request"))
            .andExpect(jsonPath("$.internalCode").value("M-001"))
    }

    @Test
    fun `should get user by id when user has the same id`() {
        val customer = customerRepository.save(buildCustomer())

        mockMvc.perform(get("/customers/${customer.id}").with(user(UserCustomDetails(customer))))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(customer.id))
            .andExpect(jsonPath("$.name").value(customer.name))
            .andExpect(jsonPath("$.email").value(customer.email))
            .andExpect(jsonPath("$.status").value(customer.status.name))
    }

    @Test
    fun `should return forbidden when user has different id`() {
        val customer = customerRepository.save(buildCustomer())

        mockMvc.perform(get("/customers/0").with(user(UserCustomDetails(customer))))
            .andExpect(status().isForbidden)
            .andExpect(jsonPath("$.code").value(403))
            .andExpect(jsonPath("$.message").value("Access denied"))
            .andExpect(jsonPath("$.internalCode").value("M-000"))
    }

    @Test
    @WithMockUser(roles= ["ADMIN"])
    fun `should get user by id when user is admin`() {
        val customer = customerRepository.save(buildCustomer())

        mockMvc.perform(get("/customers/${customer.id}").with(user(UserCustomDetails(customer))))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(customer.id))
            .andExpect(jsonPath("$.name").value(customer.name))
            .andExpect(jsonPath("$.email").value(customer.email))
            .andExpect(jsonPath("$.status").value(customer.status.name))
    }

    @Test
    @WithMockUser(roles= ["ADMIN"])
    fun `should update customer`() {
        val customer = customerRepository.save(buildCustomer())
        val request = PostCustomerRequest("fake name", "fakename@email.com", "123456")

        mockMvc.perform(put("/customers/${customer.id}")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isNoContent)

        val customers = customerRepository.findAll().toList()
        assertEquals(1, customers.size)
        assertEquals(request.name, customers[0].name)
        assertEquals(request.email, customers[0].email)
    }

    @Test
    @WithMockUser(roles= ["ADMIN"])
    fun `should throw error when update customer has invalid information`() {
        val customer = customerRepository.save(buildCustomer())
        val request = PutCustomerRequest("", "fakename@email.com")

        mockMvc.perform(put("/customers/${customer.id}")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isUnprocessableEntity)
            .andExpect(jsonPath("$.code").value(422))
            .andExpect(jsonPath("$.message").value("Invalid request"))
            .andExpect(jsonPath("$.internalCode").value("M-001"))
    }

    @Test
    @WithMockUser(roles= ["ADMIN"])
    fun `should return not found when update customer not exists`() {
        val request = PutCustomerRequest("Matheus", "fakename@email.com")

        mockMvc.perform(put("/customers/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$.code").value(404))
            .andExpect(jsonPath("$.message").value("Customer [1] not exists"))
            .andExpect(jsonPath("$.internalCode").value("M-101"))
    }

    @Test
    fun `should delete customer`() {
        val customer = customerRepository.save(buildCustomer())

        mockMvc.perform(delete("/customers/${customer.id}"))
            .andExpect(status().isNoContent)

        val customerExpected = customerRepository.findById(customer.id!!)
        assertEquals(customer.id, customerExpected.get().id)
        assertEquals(EnumStatusCustomer.INATIVO, customerExpected.get().status)
    }

    @Test
    fun `should return not found when delete customer that not exists`() {
        mockMvc.perform(delete("/customers/1"))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$.code").value(404))
            .andExpect(jsonPath("$.message").value("Customer [1] not exists"))
            .andExpect(jsonPath("$.internalCode").value("M-101"))
    }
}