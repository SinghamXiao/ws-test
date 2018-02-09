package com.singham.yuan.ws.test.common.service;

import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.xml.transform.TransformerHelper;
import org.springframework.xml.transform.TransformerObjectSupport;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

public class TransformMessageService extends TransformerObjectSupport {

    public String getMessageContent(WebServiceMessage webServiceMessage) {
        Source source = getSource(webServiceMessage);
        if (source == null) {
            return "";
        }

        try {
            StringWriter writer = new StringWriter();
            new TransformerHelper().transform(source, new StreamResult(writer));
            return writer.toString();
        } catch (Exception e) {
            return String.format("Exception Name:%s Cause:%s Message:%s)", e.getClass().getName(), e.getCause(), e.getMessage());
        }
    }

    private Source getSource(WebServiceMessage message) {
        if (message instanceof SoapMessage) {
            SoapMessage soapMessage = (SoapMessage) message;
            return soapMessage.getEnvelope().getSource();
        }
        return null;
    }

}
