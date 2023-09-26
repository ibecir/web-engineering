package ba.edu.ibu.webengineering.core.exceptions.auth;

import ba.edu.ibu.webengineering.common.constants.HttpCodes;
import ba.edu.ibu.webengineering.core.exceptions.GeneralException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends GeneralException {
    public UnauthorizedException() {
        super(HttpCodes.UNAUTHORIZED);
    }

    public UnauthorizedException(String message) {
        super(HttpCodes.UNAUTHORIZED, message);
    }
}
