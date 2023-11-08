package ba.edu.ibu.webengineering.core.repository;

import ba.edu.ibu.webengineering.core.model.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ArticleRepositoryTest {

    @Autowired
    ArticleRepository articleRepository;

    @Test
    public void myTest() throws Exception {
        List<Article> articles = articleRepository.findAll();
        articles.forEach(a -> {
            System.out.println(a.getTitle());
        });
        assertEquals(1, articles.size());
    }

}