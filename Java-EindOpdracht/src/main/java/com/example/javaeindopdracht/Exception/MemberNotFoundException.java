package com.example.javaeindopdracht.Exception;

public class MemberNotFoundException extends Exception {
    public MemberNotFoundException() {
        super("Member not found in this library");
    }
}
