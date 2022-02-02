package br.com.matheushramos.mercadolivro.services

import br.com.matheushramos.mercadolivro.exceptions.AuthenticationException
import br.com.matheushramos.mercadolivro.repository.CustomerRepository
import br.com.matheushramos.mercadolivro.security.UserCustomDetails
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsCustomService(
    private val customerRepository: CustomerRepository
) : UserDetailsService {

    override fun loadUserByUsername(id: String): UserDetails {
        val customer = customerRepository.findById(id.toInt())
            .orElseThrow { AuthenticationException("Usuário não encontrado", "M-999") }
        return UserCustomDetails(customer)
    }

}