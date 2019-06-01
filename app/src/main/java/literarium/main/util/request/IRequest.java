package com.example.com.literarium;

import org.w3c.dom.Document;

/**
 * defines the basic properties of a request.
 * all the requests must get their results in XML format.
 */
public interface IRequest {

    void send();

    Document getResult();
}
