/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.students.util;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.xml.sax.SAXException;

/**
 *
 * @author pushi_000
 */
public class XMLUtils {

    public static void write(OutputStream os, Object object) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(object.getClass());
        Marshaller m = jc.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        m.marshal(object, os);
    }

    public static <T> T read(Class<T> clazz, InputStream is, String pathToSchema) throws SAXException, JAXBException {
        JAXBContext jc = JAXBContext.newInstance(clazz);
        Unmarshaller um = jc.createUnmarshaller();
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
//        Schema schema = schemaFactory.newSchema(new File(pathToSchema));
//        um.setSchema(schema);
        return (T) um.unmarshal(is);
    }
}
