package com.holdme.holdmeapi_restfull.exception;

public class RoleNotFoundExeption extends RuntimeException{
    public RoleNotFoundExeption() {
        super("Role not found for the user");
    }

    public RoleNotFoundExeption(String message) {
        super(message);
    }
}
