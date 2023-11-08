package ba.edu.ibu.webengineering.rest.controllers;

import ba.edu.ibu.webengineering.core.model.Article;
import ba.edu.ibu.webengineering.core.model.enums.ArticleType;
import ba.edu.ibu.webengineering.core.service.ArticleService;
import ba.edu.ibu.webengineering.core.service.JwtService;
import ba.edu.ibu.webengineering.core.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(ArticleController.class)
class ArticleControllerTest {

    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    ArticleService articleService;

    @MockBean
    JwtService jwtService;

    @MockBean
    UserService userService;

    @Test
    void shouldReturnAllArticles() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/articles/").contentType(MediaType.APPLICATION_JSON)).andReturn();

        String response = result.getResponse().getContentAsString();
        System.out.println("---------" + response);
    }

    @Test
    void shouldCreateNewArticle() throws Exception {
        Article article = new Article("https://thumbnail.com", "My article");
        article.setArticleType(ArticleType.SHORT_ARTICLE);

        when(articleService.createArticle(any(Article.class))).thenReturn(article);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/articles/").content(objectMapper.writeValueAsString(article)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        String response = result.getResponse().getContentAsString();
        assertEquals(article.getTitle(), JsonPath.read(response, "$.title"));
        System.out.println(article.getTitle() + " <----> " + JsonPath.read(response, "$.title"));
    }
}