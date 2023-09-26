package ba.edu.ibu.webengineering.core.api.auth;

import ba.edu.ibu.webengineering.core.model.User;

public interface AuthorizationApi {
    String generateToken(User user);

    User decodeUser(String token);
}