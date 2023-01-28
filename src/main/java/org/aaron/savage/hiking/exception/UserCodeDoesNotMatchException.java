package org.aaron.savage.hiking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "user code does not match")
public class UserCodeDoesNotMatchException extends RuntimeException {
}
