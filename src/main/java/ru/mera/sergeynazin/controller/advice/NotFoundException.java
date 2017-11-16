package ru.mera.sergeynazin.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {


    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }

    public NotFoundException(final String name) {
        super("Entity with name " + name + " NOT found !");
    }

    public NotFoundException(final Serializable id) {
        super("Entity with ID " + id + " NOT found !");
    }

    public static NotFoundException throwNew(final String name) {
        return new NotFoundException(name);
    }

    public static NotFoundException throwNew(final Serializable id) {
        return new NotFoundException(id);
    }
}
