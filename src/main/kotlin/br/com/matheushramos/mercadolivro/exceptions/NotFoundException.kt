package br.com.matheushramos.mercadolivro.exceptions

class NotFoundException(override val message: String, val code: String): Exception() {}