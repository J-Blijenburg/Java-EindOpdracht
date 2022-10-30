package com.example.javaeindopdracht.Exception;

public class UserNameException extends Exception{
    public UserNameException() {
        super("Username does not exist!");
    }
}
