package com.example.com.literarium;

import org.w3c.dom.Document;

import java.net.Socket;

/**
 * TODO: don't know if it's useful.
 */
public class XmlRequest implements IRequest {

    private Document toSend;

    private String ip;

    private int port;

    private Socket s;

    public XmlRequest(Document toSend, String ip, int port) {
        this.toSend = toSend;
        this.ip = ip;
        this.port = port;
    }

    @Override
    public void send() {}

    @Override
    public Document getResult() {
        return null;
    }
}
