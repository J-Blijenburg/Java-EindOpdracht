package com.example.javaeindopdracht.Exception;

public class ItemNotLendOutException extends Exception{
    public ItemNotLendOutException() {
        super("Item is not been lend out");
    }
}
