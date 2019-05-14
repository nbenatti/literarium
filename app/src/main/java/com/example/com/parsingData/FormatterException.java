package com.example.com.dataAcquisition;

public class FormatterException extends RuntimeException {

    public FormatterException() {
        this("");
    }

    public FormatterException(String msg) {
        super(msg);
    }

}
