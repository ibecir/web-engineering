package ba.edu.ibu.webengineering.core.exceptions.general;

import ba.edu.ibu.webengineering.common.constants.HttpCodes;
import ba.edu.ibu.webengineering.core.exceptions.GeneralException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends GeneralException {
    public BadRequestException() {
        super(HttpCodes.BAD_REQUEST);
    }

    public BadRequestException(String message) {
        super(HttpCodes.BAD_REQUEST, message);
    }
}
