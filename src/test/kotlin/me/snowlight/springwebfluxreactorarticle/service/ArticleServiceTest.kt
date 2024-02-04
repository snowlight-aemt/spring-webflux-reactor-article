package me.snowlight.springwebfluxreactorarticle.service

import me.snowlight.springwebfluxreactorarticle.modal.Article
import me.snowlight.springwebfluxreactorarticle.modal.ArticleRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ArticleServiceTest(
    @Autowired private val service: ArticleService,
    @Autowired private val repository: ArticleRepository,
) {
    @Test
    fun createAndGet() {
        val prevCnt = repository.count().block() ?: 0
        val article = service.create(ReqCreate(title = "title create", body = "body create", authorId = 1234)).block()!!
        val currCnt = repository.count().block() ?: 0
        Assertions.assertEquals(prevCnt + 1, currCnt)

        val readArticle = service.get(article.id).block()
        assertEquals(article, readArticle)
        assertNotNull(article.createdAt)
        assertNotNull(article.updatedAt)
    }

    @Test
    fun getAll() {
        service.create(ReqCreate(title = "title 1", body = "body create", authorId = 1234)).block()!!
        service.create(ReqCreate(title = "title 2", body = "body create", authorId = 1234)).block()!!
        service.create(ReqCreate(title = "title 2", body = "body create", authorId = 1234)).block()!!

        Assertions.assertEquals(3, service.getAll().collectList().block()!!.size)
        Assertions.assertEquals(1, service.getAll("1").collectList().block()!!.size)
    }

    @Test
    fun update() {
        val article = service.create(
                ReqCreate(title = "title 1", body = "body create", authorId = 1234)
            ).block()!!
        val articleUpdated = service.update(article.id, ReqUpdate(title = "1234")).block()!!

        Assertions.assertEquals("1234", articleUpdated.title)
    }

    @Test
    fun delete() {
        val prevCnt = repository.count().block()
        val article = service.create(
            ReqCreate(title = "title 1", body = "body create", authorId = 1234)
        ).block()!!

        service.delete(article.id).block()

        val currCnt = repository.count().block()

        Assertions.assertEquals(prevCnt, currCnt)
    }
}