package br.com.matheushramos.mercadolivro.controller.request

data class LoginRequest(
    var email: String,
    var password: String
)