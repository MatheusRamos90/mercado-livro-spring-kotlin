package br.com.matheushramos.mercadolivro.enums

enum class EnumErrors(val code: String, val message: String) {
    M000("M-000", "Access denied"),
    M001("M-001", "Invalid request"),
    M002("M-002", "Book [%s] is not available to purchase"),
    M101("M-101", "Customer [%s] not exists"),
    M102("M-102", "The field status can't change for [%s]"),
    M201("M-201", "Book [%s] not exists"),
    M998("M-998", "Invalid token"),
    M999("M-999", "Fail in authentication")
}