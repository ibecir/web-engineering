package ba.edu.ibu.webengineering.rest.configuration;

import ba.edu.ibu.webengineering.api.impl.mailsender.MailgunMailSender;
import ba.edu.ibu.webengineering.core.api.mailsender.MailSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class MailgunConfiguration {
    @Value("${email.mailgun.from-email}")
    private String fromEmail;
    @Value("${email.mailgun.username}")
    private String username;
    @Value("${email.mailgun.password}")
    private String password;

    @Bean
    public MailSender mailgunMailSender(RestTemplate restTemplate) {
        return new MailgunMailSender(restTemplate, fromEmail);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.uriTemplateHandler(new DefaultUriBuilderFactory("https://api.eu.mailgun.net/v3/sandboxe07bbd31526745b3a36d99843ba35ff6.mailgun.org")).basicAuthentication(username, password).build();
    }
}
