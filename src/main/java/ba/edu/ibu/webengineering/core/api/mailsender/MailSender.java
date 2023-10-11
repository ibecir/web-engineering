package ba.edu.ibu.webengineering.core.api.mailsender;

import ba.edu.ibu.webengineering.core.model.User;

import java.util.List;

public interface MailSender {
    public void sendEmail(List<User> users);
}
