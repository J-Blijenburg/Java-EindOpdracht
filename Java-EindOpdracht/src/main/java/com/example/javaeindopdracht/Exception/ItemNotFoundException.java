package com.example.javaeindopdracht.Exception;

public class ItemNotFoundException extends Exception{
    public ItemNotFoundException() {
        super("Item not found in this library");
    }
}
