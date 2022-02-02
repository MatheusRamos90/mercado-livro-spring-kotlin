package br.com.matheushramos.mercadolivro.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("admin")
class AdminController {

    @GetMapping("reports")
    fun getReport(): String = "This is a Report. Only Admin can see it!"

}