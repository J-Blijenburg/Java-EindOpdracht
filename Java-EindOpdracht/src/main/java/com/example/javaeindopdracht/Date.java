package com.example.javaeindopdracht;

import com.example.javaeindopdracht.Exception.DatePickerException;
import javafx.scene.control.DatePicker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Date {
    public LocalDate checkDate(DatePicker dateTimePicker) throws DatePickerException {
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            return LocalDate.parse(dateTimePicker.getEditor().getText(), formatter);
        }catch(Exception ex){
            throw new DatePickerException();
        }
    }
}
