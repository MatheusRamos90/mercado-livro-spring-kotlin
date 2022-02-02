package br.com.matheushramos.mercadolivro.exceptions

class BadRequestException(override val message: String, val code: String): Exception() {}