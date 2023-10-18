package ba.edu.ibu.webengineering.core.repository;

import ba.edu.ibu.webengineering.core.model.Article;
import ba.edu.ibu.webengineering.core.model.enums.ArticleType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends MongoRepository<Article, String> {
    List<Article> findAllByTitleAndArticleType(String title, ArticleType articleType);
}
