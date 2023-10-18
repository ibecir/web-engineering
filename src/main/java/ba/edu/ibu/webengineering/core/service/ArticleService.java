package ba.edu.ibu.webengineering.core.service;

import ba.edu.ibu.webengineering.core.exceptions.repository.ResourceNotFoundException;
import ba.edu.ibu.webengineering.core.model.Article;
import ba.edu.ibu.webengineering.core.model.enums.ArticleType;
import ba.edu.ibu.webengineering.core.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> getAllArticles(){
        return articleRepository.findAll();
    }

    public Article createArticle(Article payload){
        return articleRepository.save(payload);
    }

    public void deleteArticleById(String articleId){
        articleRepository.deleteById(articleId);
    }

    public Article updateById(String articleId, Article payload){
        Optional<Article> article = articleRepository.findById(articleId);
        if(article.isEmpty())
            throw new ResourceNotFoundException("Unable to find entity with provided id");

        payload.setId(article.get().getId());
        return articleRepository.save(payload);
    }

    public List<Article> getAllArticlesByTitleAndType(String title, ArticleType articleType){
        return articleRepository.findAllByTitleAndArticleType(title, articleType);
    }
}
