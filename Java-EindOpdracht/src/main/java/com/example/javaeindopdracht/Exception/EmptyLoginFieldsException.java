package com.example.javaeindopdracht.Exception;

public class EmptyLoginFieldsException extends Exception{
    public EmptyLoginFieldsException() {
        super("Please, Enter a Username and Password.");
    }
}
