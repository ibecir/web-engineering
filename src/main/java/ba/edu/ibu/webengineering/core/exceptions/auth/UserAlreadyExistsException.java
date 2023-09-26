package ba.edu.ibu.webengineering.core.exceptions.auth;

import ba.edu.ibu.webengineering.common.constants.HttpCodes;
import ba.edu.ibu.webengineering.core.exceptions.GeneralException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class UserAlreadyExistsException extends GeneralException {
    public UserAlreadyExistsException() {
        super(HttpCodes.CONFLICT);
    }

    public UserAlreadyExistsException(String message) {
        super(HttpCodes.CONFLICT, message);
    }
}
