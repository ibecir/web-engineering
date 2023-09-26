package ba.edu.ibu.webengineering.core.exceptions.auth;

import ba.edu.ibu.webengineering.common.constants.HttpCodes;
import ba.edu.ibu.webengineering.core.exceptions.GeneralException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidParametersSentException extends GeneralException {
    public InvalidParametersSentException() {
        super(HttpCodes.BAD_REQUEST);
    }

    public InvalidParametersSentException(String message) {
        super(HttpCodes.BAD_REQUEST, message);
    }
}
