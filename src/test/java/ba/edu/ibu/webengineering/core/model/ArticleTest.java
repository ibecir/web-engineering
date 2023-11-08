package ba.edu.ibu.webengineering.core.model;

import com.jayway.jsonpath.JsonPath;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


class ArticleTest {

    // JUnit
    @Test
    void shouldCreateNewArticle() {
        Article article = new Article("https://klix.ba", "Some title");

        Assertions.assertEquals("Some title", article.getTitle());
    }

    @Test
    void shouldCrateNewArticleWithAssetJ() {
        Article article = new Article("https://klix.ba", "Some title");

        assertThat(article.getTitle()).startsWith("S").endsWith("e").contains("ti").isEqualTo("Some title").isEqualToIgnoringCase("Some title");
    }

    @Test
    void shouldCrateNewArticleWithHamcrest() {
        Article article = new Article("https://klix.ba", "Some title");
        Article articleTwo = new Article("https://klix.ba", "Some title");

        MatcherAssert.assertThat(article, Matchers.equalTo(articleTwo));
    }

    @Test
    void shouldCompareJson() throws JSONException {
        String data = getJson();
        String expected = """
                {
                   "articles":[
                      {
                         "thumbnailUrl":"https://klix.ba",
                         "title":"First Article"
                      },
                      {
                         "thumbnailUrl":"https://facebook.ba",
                         "title":"Second Article"
                      }
                   ]
                }
                """;
        JSONAssert.assertEquals(expected, data, false);
    }

    @Test
    void shouldCompareJsonPath() {
        String json = """
                {
                   "articles":[
                      {
                         "thumbnailUrl":"https://klix.ba",
                         "title":"First Article"
                      },
                      {
                         "thumbnailUrl":"https://facebook.ba",
                         "title":"Second Article"
                      }
                   ]
                }
                """;
        Integer length = JsonPath.read(json, "$.articles.length()");
        String name = JsonPath.read(json, "$.articles[0].title");
        Assertions.assertEquals(name, "First Article");
        Assertions.assertEquals(2, length);
    }

    private String getJson() {
        return """
                {
                   "articles":[
                      {
                         "thumbnailUrl":"https://klix.ba",
                         "title":"First Article"
                      },
                      {
                         "thumbnailUrl":"https://facebook.ba",
                         "title":"Second Article"
                      }
                   ]
                }
                """;
    }
}