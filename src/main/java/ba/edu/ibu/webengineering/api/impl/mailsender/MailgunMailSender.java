package ba.edu.ibu.webengineering.api.impl.mailsender;

import ba.edu.ibu.webengineering.common.constants.MailgunConstants;
import ba.edu.ibu.webengineering.core.api.mailsender.MailSender;
import ba.edu.ibu.webengineering.core.model.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static ba.edu.ibu.webengineering.common.constants.MailgunConstants.*;

public class MailgunMailSender implements MailSender {
    private final RestTemplate restTemplate;
    private final String fromEmail;

    public MailgunMailSender(RestTemplate restTemplate, String fromEmail) {
        this.restTemplate = restTemplate;
        this.fromEmail = fromEmail;
    }

    @Override
    public String sendEmail(List<User> users, String from) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add(MailgunConstants.FROM, fromEmail);
        map.add(TO, "ibecir.isakovic@gmail.com");
        map.add(SUBJECT, "Some subject");
        map.add(BODY, "Some body");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        return restTemplate.postForEntity(MESSAGES_ENDPOINT, request, String.class).getBody();
    }
}
