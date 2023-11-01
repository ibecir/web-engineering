package ba.edu.ibu.webengineering.rest.dto;

public class LoginResponseDTO {
    private String jwt;
    private int status;

    public LoginResponseDTO(String jwt, int status) {
        this.jwt = jwt;
        this.status = status;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
