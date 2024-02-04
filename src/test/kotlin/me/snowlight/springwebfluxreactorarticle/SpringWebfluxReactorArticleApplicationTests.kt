package me.snowlight.springwebfluxreactorarticle

import me.snowlight.springwebfluxreactorarticle.modal.Article
import me.snowlight.springwebfluxreactorarticle.modal.ArticleRepository
import mu.KotlinLogging
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

private val logger = KotlinLogging.logger {}

// LEARN 스텝 배리 파이어 - StepVerifier ? --> 코루틴을 가지위한 길이기 때문이 자세한 내용은 생략한다.
@SpringBootTest
class SpringWebfluxReactorArticleApplicationTests(
    @Autowired private val articleRepository: ArticleRepository,
) {
    @Test
    fun contextLoads() {
        val count = articleRepository.count().block() ?: 0
        articleRepository.save(Article(title = "title")).block()
        val articles = articleRepository.findAll().collectList().block()
        articles?.forEach { logger.debug { it } }

        val currCount = articleRepository.count().block() ?: 0
        Assertions.assertEquals(count + 1, currCount)
    }
}