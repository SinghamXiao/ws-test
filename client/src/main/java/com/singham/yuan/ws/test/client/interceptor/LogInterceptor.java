package com.singham.yuan.ws.test.client.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.soap.server.endpoint.interceptor.SoapEnvelopeLoggingInterceptor;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;


@Component
public class LogInterceptor extends SoapEnvelopeLoggingInterceptor implements ClientInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogInterceptor.class);

    @Override
    public boolean handleRequest(MessageContext messageContext) throws WebServiceClientException {
        String message = getMessageContent(getSource(messageContext.getRequest()));
        LOGGER.info("Request: " + message);
        System.out.println("Request: " + message);
        return false;
    }

    @Override
    public boolean handleResponse(MessageContext messageContext) throws WebServiceClientException {
        return true;
    }

    @Override
    public boolean handleFault(MessageContext messageContext) throws WebServiceClientException {
        return true;
    }

    @Override
    public void afterCompletion(MessageContext messageContext, Exception ex) throws WebServiceClientException {

    }

    private String getMessageContent(Source source) {
        if (source == null) {
            return "";
        }

        try {
            Transformer transformer = createNonIndentingTransformer();
            StringWriter writer = new StringWriter();
            transformer.transform(source, new StreamResult(writer));
            return writer.toString();
        } catch (Exception e) {
            return String.format("Exception Name:%s Cause:%s Message:%s)", e.getClass().getName(), e.getCause(), e.getMessage());
        }
    }

    private Transformer createNonIndentingTransformer() throws TransformerConfigurationException {
        Transformer transformer = createTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty(OutputKeys.INDENT, "no");
        return transformer;
    }

}
