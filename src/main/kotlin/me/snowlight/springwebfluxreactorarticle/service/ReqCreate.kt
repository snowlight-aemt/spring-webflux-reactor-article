package me.snowlight.springwebfluxreactorarticle.service

import me.snowlight.springwebfluxreactorarticle.modal.Article

data class ReqCreate(
    val title: String,
    var body: String? = null,
    var authorId: Long? = null,
) {
    fun toArticle(): Article {
        return Article(
            title = this.title,
            body = this.body,
            authorId = this.authorId,
        )
    }
}
