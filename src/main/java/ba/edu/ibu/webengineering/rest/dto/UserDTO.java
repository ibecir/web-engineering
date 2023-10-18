package ba.edu.ibu.webengineering.rest.dto;

import ba.edu.ibu.webengineering.core.model.enums.UserType;

public class UserDTO {
    private String id;
    private String name;
    private String username;
    private UserType userType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
