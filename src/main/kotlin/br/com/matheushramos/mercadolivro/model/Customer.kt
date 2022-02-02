package br.com.matheushramos.mercadolivro.model

import br.com.matheushramos.mercadolivro.enums.EnumProfileRole
import br.com.matheushramos.mercadolivro.enums.EnumStatusCustomer
import javax.persistence.*

@Entity(name = "customer")
data class Customer(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column
    var name: String,

    @Column
    var email: String,

    @Column
    @Enumerated(EnumType.STRING)
    var status: EnumStatusCustomer,

    @Column
    var password: String,

    @Column
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "customer_roles", joinColumns = [JoinColumn(name = "customer_id")])
    @ElementCollection(targetClass = EnumProfileRole::class, fetch = FetchType.EAGER)
    var roles: Set<EnumProfileRole> = setOf()
)