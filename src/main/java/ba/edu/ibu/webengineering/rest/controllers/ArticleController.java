package ba.edu.ibu.webengineering.rest.controllers;

import ba.edu.ibu.webengineering.core.model.Article;
import ba.edu.ibu.webengineering.core.model.enums.ArticleType;
import ba.edu.ibu.webengineering.core.service.ArticleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/articles")
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/")
    public ResponseEntity<List<Article>> getAll(){
        List<Article> articles = articleService.getAllArticles();
        return ResponseEntity.ok(articles);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/filter")
    public ResponseEntity<List<Article>> filter(@RequestParam("title") String title, @RequestParam("type") ArticleType articleType){
        List<Article> articles = articleService.getAllArticlesByTitleAndType(title, articleType);
        return ResponseEntity.ok(articles);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/")
    public ResponseEntity<Article> createArticle(@RequestBody Article payload){
        return ResponseEntity.ok(articleService.createArticle(payload));
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/delete/{articleId}")
    public ResponseEntity deleteArticleById(@PathVariable String articleId){
        articleService.deleteArticleById(articleId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/update/{articleId}")
    public ResponseEntity<Article> updateById(@RequestBody Article updatePayload, @PathVariable String articleId){
        return ResponseEntity.ok(articleService.updateById(articleId, updatePayload));
    }


}
