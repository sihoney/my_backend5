package org.example.member.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicatePhoneException extends RuntimeException{
    public DuplicatePhoneException(String message) {
        super(message);
    }
}
