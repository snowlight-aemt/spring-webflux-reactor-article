package me.snowlight.springwebfluxreactorarticle.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class HelloController {
    @GetMapping("/hello")
    fun hello(): Mono<String> {
        return Mono.just("Hello, world")
    }

    @GetMapping("/")
    fun index(): String {
        return "main hello !"
    }
}