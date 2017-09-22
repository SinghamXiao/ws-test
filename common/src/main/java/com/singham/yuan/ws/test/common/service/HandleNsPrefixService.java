package com.singham.yuan.ws.test.common.service;

import org.springframework.ws.context.MessageContext;
import org.springframework.ws.soap.SoapMessage;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import java.util.ArrayList;
import java.util.List;

public class HandleNsPrefixService {

    private static final String XMLNS = "xmlns:";

    public void handleNsPrefix(MessageContext messageContext) {
        final SoapMessage request = (SoapMessage) messageContext.getRequest();

        Node domResultNode = ((DOMResult) request.getSoapHeader().getResult()).getNode();

        NodeList childNodes = domResultNode.getChildNodes();
        removeNsPrefix(childNodes);

        Node firstChild = domResultNode.getFirstChild();
        if (firstChild != null) {
            removeNsPrefix(firstChild.getAttributes());
        }

        Node node = ((DOMSource) request.getSoapBody().getPayloadSource()).getNode();
        removeNsPrefix(node);
        if (node != null) {
            removeNsPrefix(node.getAttributes());
        }
    }

    private void removeNsPrefix(NamedNodeMap attributes) {
        if (attributes == null) {
            return;
        }

        final List<String> removeNodeNames = new ArrayList<>();
        for (int i = 0; i < attributes.getLength(); i++) {
            final String nodeName = attributes.item(i).getNodeName();
            if (nodeName.startsWith(XMLNS)) {
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
