package com.example.com.geoLocalization;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public interface XMLSerializable {

    /**
     * XML -> object
     * @param doc the XML documetn representing the object.
     */
    public void decode(Document doc);

    /**
     * object -> XML
     * @return content of the object encoded in XML.
     */
    public Element encode();
}
