package ru.mera.sergeynazin.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class CreatingAlreadyExistentException extends RuntimeException {

    private Object foundExistent;

    public HttpStatus getStatus() {
        return HttpStatus.UNPROCESSABLE_ENTITY;
    }

    public Object getFoundExistent() {
        return foundExistent;
    }

    public CreatingAlreadyExistentException(final Object foundExistent) {
        super("Entity ALREADY exist! Check unique parameters!");
        this.foundExistent = foundExistent;
    }

    public CreatingAlreadyExistentException(final String name, final Object foundExistent) {
        super("Entity with name " + name + " ALREADY exist !");
        this.foundExistent = foundExistent;
    }

    public CreatingAlreadyExistentException(final Serializable id, final Object foundExistent) {
        super("Entity with id " + id + " ALREADY exist !");
        this.foundExistent = foundExistent;
    }

    public CreatingAlreadyExistentException(final String name) {
        super("Entity with name " + name + " ALREADY exist !");
    }

    public CreatingAlreadyExistentException(final Serializable id) {
        super("Entity with id " + id + " ALREADY exist !");
    }

    public static CreatingAlreadyExistentException throwNew(final String name) {
        return new CreatingAlreadyExistentException(name);
    }

    public static CreatingAlreadyExistentException throwNew(final Serializable id) {
        return new CreatingAlreadyExistentException(id);
    }

    public static CreatingAlreadyExistentException throwNew(final Object foundExistent) {
        return new CreatingAlreadyExistentException(foundExistent);
    }

    public static CreatingAlreadyExistentException throwNew(final String name, final Object foundExistent) {
        return new CreatingAlreadyExistentException(name, foundExistent);
    }

    public static CreatingAlreadyExistentException throwNew(final Serializable id, final Object foundExistent) {
        return new CreatingAlreadyExistentException(id, foundExistent);
    }
}