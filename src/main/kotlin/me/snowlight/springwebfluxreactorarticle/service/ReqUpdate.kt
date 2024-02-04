package me.snowlight.springwebfluxreactorarticle.service

import me.snowlight.springwebfluxreactorarticle.modal.Article

data class ReqUpdate (
    val title: String? = null,
    var body: String? = null,
    var authorId: Long? = null,
)
