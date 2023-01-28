package org.aaron.savage.hiking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "duplicate entry")
public class DuplicateEntryException extends RuntimeException {
}
