package com.example.javaeindopdracht.Exception;

public class DidNotLendException extends Exception{
    public DidNotLendException() {
        super("Member didn't lend this item");
    }
}
