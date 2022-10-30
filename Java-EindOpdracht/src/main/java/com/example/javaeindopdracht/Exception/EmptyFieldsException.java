package com.example.javaeindopdracht.Exception;

public class EmptyFieldsException extends Exception {
    public EmptyFieldsException() {
        super("Please, fill out all the fields");
    }
}
