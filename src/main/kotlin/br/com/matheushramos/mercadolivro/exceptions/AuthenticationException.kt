package br.com.matheushramos.mercadolivro.exceptions

class AuthenticationException(override val message: String, val code: String) : Exception()
