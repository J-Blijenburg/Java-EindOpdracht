package com.example.javaeindopdracht.Exception;

public class DatePickerException extends Exception {
    public DatePickerException(){
        super("Please, enter a valid date. For Example: 11-02-2000");
    }
}
