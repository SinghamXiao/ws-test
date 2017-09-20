package com.singham.yuan.ws.test.client.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.ws.soap.server.endpoint.interceptor.SoapEnvelopeLoggingInterceptor;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

@Component
public class LogInterceptor extends SoapEnvelopeLoggingInterceptor implements ClientInterceptor {

    @Override
    public boolean handleRequest(MessageContext messageContext) throws WebServiceClientException {
        String message = getMessageContent(getSource(messageContext.getRequest()));
        System.out.println("Request: " + message);

        final SoapMessage request = (SoapMessage) messageContext.getRequest();
        DOMResult domResult = (DOMResult) request.getSoapHeader().getResult();
        Node domResultNode = domResult.getNode();

        NodeList childNodes = domResultNode.getChildNodes();
        removeNsPrefix(childNodes);

        Node firstChild = domResultNode.getFirstChild();
        if (firstChild != null) {
            removeNsPrefix(firstChild.getAttributes());
        }

/*        removeNsPrefix(((DOMSource) request.getSoapBody().getPayloadSource()).getNode());
        if (((DOMSource) request.getSoapBody().getPayloadSource()).getNode() != null) {
            removeNsPrefix(((DOMSource) request.getSoapBody().getPayloadSource()).getNode().getAttributes());
        }*/

        message = getMessageContent(getSource(messageContext.getRequest()));
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
            return String.format("Exception Name:%s Cause:%s Message:%s)",
                    e.getClass().getName(), e.getCause(), e.getMessage());
        }
    }

    private Transformer createNonIndentingTransformer() throws TransformerConfigurationException {
        Transformer transformer = createTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty(OutputKeys.INDENT, "no");
        return transformer;
    }

    private void removeNsPrefix(NamedNodeMap attributes) {
        if (attributes == null) {
            return;
        }
        final List<String> removeNodeNames = new ArrayList<>();
        for (int i = 0; i < attributes.getLength(); i++) {
            final String nodeName = attributes.item(i).getNodeName();
            if (nodeName.startsWith("xmlns:")) {
                removeNodeNames.add(nodeName);
            }
        }
        removeNodeNames.forEach(attributes::removeNamedItem);
    }

    private void removeNsPrefix(NodeList nodes) {
        for (int i = 0; i < nodes.getLength(); i++) {
            removeNsPrefix(nodes.item(i));
        }
    }

    private void removeNsPrefix(Node node) {
        if (node == null) {
            return;
        }
        if (node instanceof Element) {
            node.setPrefix(null);
            removeNsPrefix(node.getChildNodes());
        }
    }
}
