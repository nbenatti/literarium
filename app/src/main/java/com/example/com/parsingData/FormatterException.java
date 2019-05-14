package com.example.com.parsingData;

public class FormatterException extends RuntimeException {

    public FormatterException() {
        this("");
    }

    public FormatterException(String msg) {
        super(msg);
    }

}
