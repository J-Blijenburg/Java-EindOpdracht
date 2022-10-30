package com.example.javaeindopdracht.Exception;

public class ItemNotAvailableException extends Exception{
    public ItemNotAvailableException() {
        super("Item is not available now");
    }
}
