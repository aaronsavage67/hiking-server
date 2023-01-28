package org.aaron.savage.hiking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_GATEWAY, reason = "failed to send email")
public class FailedToSendEmailException extends RuntimeException {
}
