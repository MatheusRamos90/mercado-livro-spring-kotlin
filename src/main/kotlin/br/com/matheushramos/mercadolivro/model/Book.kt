package br.com.matheushramos.mercadolivro.model

import br.com.matheushramos.mercadolivro.exceptions.BadRequestException
import br.com.matheushramos.mercadolivro.enums.EnumErrors
import br.com.matheushramos.mercadolivro.enums.EnumStatusBook
import java.math.BigDecimal
import javax.persistence.*

@Entity(name = "book")
data class Book(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column
    var name: String,

    @Column
    var price: BigDecimal,

    @ManyToOne
    @JoinColumn(name = "customer_id")
    var customer: Customer? = null
) {
    @Column
    @Enumerated(EnumType.STRING)
    var status: EnumStatusBook? = null
        set(value) {
            if (field == EnumStatusBook.DELETADO || field == EnumStatusBook.CANCELADO) {
                throw BadRequestException(EnumErrors.M102.message.format(field), EnumErrors.M102.code)
            }

            field = value
        }

    constructor(
        id: Int? = null,
        name: String,
        price: BigDecimal,
        customer: Customer? = null,
        status: EnumStatusBook?
    ) : this(id, name, price, customer) {
        this.status = status
    }
}