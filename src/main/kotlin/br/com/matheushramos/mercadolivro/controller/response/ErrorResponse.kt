package br.com.matheushramos.mercadolivro.controller.response

data class ErrorResponse(
    val code: Int,
    val message: String,
    val internalCode: String,
    val errors: List<FieldErrorResponse>?
) {}