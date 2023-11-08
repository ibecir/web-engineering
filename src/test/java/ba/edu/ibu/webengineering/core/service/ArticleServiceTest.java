package ba.edu.ibu.webengineering.core.service;

import ba.edu.ibu.webengineering.core.model.Article;
import ba.edu.ibu.webengineering.core.model.enums.ArticleType;
import ba.edu.ibu.webengineering.core.repository.ArticleRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
@SpringBootTest
class ArticleServiceTest {
    @Mock
    ArticleRepository articleRepository;

    @Test
    void shouldReturnArticleWhenCreated() {
        Article article = new Article("https://spring.io", "Spring article title");
        article.setArticleType(ArticleType.LONG_ARTICLE);

        when(articleRepository.save(any(Article.class))).thenReturn(article);

        Article savedArticle = articleRepository.save(article);
        assertThat(article.getTitle()).isSameAs(savedArticle.getTitle());
        assertNotNull(savedArticle.getTitle());
        System.out.println(savedArticle.getId());
    }

}