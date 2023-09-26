package ba.edu.ibu.webengineering.core.exceptions.auth;

import ba.edu.ibu.webengineering.common.constants.HttpCodes;
import ba.edu.ibu.webengineering.core.exceptions.GeneralException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class NoAccessException extends GeneralException {
    public NoAccessException() {
        super(HttpCodes.FORBIDDEN);
    }

    public NoAccessException(String message) {
        super(HttpCodes.FORBIDDEN, message);
    }
}
