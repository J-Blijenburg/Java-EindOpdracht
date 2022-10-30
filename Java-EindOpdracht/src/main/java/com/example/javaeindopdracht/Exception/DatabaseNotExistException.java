package com.example.javaeindopdracht.Exception;

import java.io.File;

public class DatabaseNotExistException extends Exception{
    public DatabaseNotExistException(File file) {
        super("Database file " + file.getName() + " does not exist!");
    }
}
