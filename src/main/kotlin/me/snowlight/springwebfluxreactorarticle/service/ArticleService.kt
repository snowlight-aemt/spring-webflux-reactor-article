package me.snowlight.springwebfluxreactorarticle.service

import me.snowlight.springwebfluxreactorarticle.exception.NoArticleException
import me.snowlight.springwebfluxreactorarticle.modal.Article
import me.snowlight.springwebfluxreactorarticle.modal.ArticleRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@Service
class ArticleService(
    private val articleRepository: ArticleRepository,
) {

    // LEARN 실행을 서비스 단계에서 하지 않고 넘긴다.
    @Transactional
    fun create(request: ReqCreate): Mono<Article> {
        return articleRepository.save(request.toArticle())
    }

    // LEARN reactor 는 null 이면 doNext 하지 않는다 따라서, null 처리를 해야 한다. `switchIfEmpty`
    fun get(id: Long): Mono<Article> {
        return articleRepository.findById(id).switchIfEmpty { throw NoArticleException("id: $id") }
    }

    fun getAll(title: String? = null): Flux<Article> {
        return if (title.isNullOrEmpty()) {
            articleRepository.findAll()
        } else {
            articleRepository.findAllByTitleContains(title)
        }
    }

    // LEARN `flatMap` 를 사용하는 이유는 r2dbc 의 비동기를 살리기 위해서.
    fun update(id: Long, request: ReqUpdate): Mono<Article> {
        return articleRepository.findById(id)
            .switchIfEmpty { throw NoArticleException("id: $id") }
            .flatMap { article ->
                request.title?.let { article.title = it }
                request.body?.let { article.body = it }
                request.authorId?.let { article.authorId = it }

                articleRepository.save(article) // 비동기
            }
    }

    fun delete(id: Long): Mono<Void> {
        return articleRepository.deleteById(id)
    }
}