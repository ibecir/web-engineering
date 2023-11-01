package ba.edu.ibu.webengineering.rest.dto;

import ba.edu.ibu.webengineering.core.model.enums.UserType;

public class RegisterResponseDTO {
    private String email;
    private String username;
    private UserType userType;

    private Exception exception;

    public RegisterResponseDTO(String email, String username, UserType userType) {
        this.email = email;
        this.username = username;
        this.userType = userType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
