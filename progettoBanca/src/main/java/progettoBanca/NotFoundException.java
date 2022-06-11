package progettoBanca;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

}  