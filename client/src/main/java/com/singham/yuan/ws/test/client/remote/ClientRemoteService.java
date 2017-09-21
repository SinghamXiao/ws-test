package com.singham.yuan.ws.test.client.remote;

import com.singham.yuan.body.TestBody;
import com.singham.yuan.head.TestHead;
import com.singham.yuan.ws.test.client.factory.TestBodyFactory;
import com.singham.yuan.ws.test.client.factory.TestHeadFactory;
import com.sun.xml.bind.marshaller.NamespacePrefixMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.SoapMessage;

import javax.xml.bind.Marshaller;
import java.util.HashMap;
import java.util.Map;

@Service
public class ClientRemoteService {

    @Value("${remote.url}")
    private String remoteUrl;

    @Autowired
    @Qualifier("webService")
    private WebServiceTemplate webServiceTemplate;

    @Autowired
    @Qualifier("headerMarshaller")
    private Jaxb2Marshaller headerMarshaller;

    @Autowired
    @Qualifier("bodyMarshaller")
    private Jaxb2Marshaller bodyMarshaller;

    public void execute() {

        TestHead testHead = TestHeadFactory.newTestHead();

        TestBody testBody = TestBodyFactory.newTestBody();

        WebServiceMessageCallback requestCallback = message -> {
            SoapMessage soapMessage = (SoapMessage) message;

            Map<String, Object> properties = new HashMap<>();
            properties.put(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            properties.put(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

            NamespacePrefixMapper mapper = new NamespacePrefixMapper() {
                public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
                    if ("http://www.singham.yuan.com/head".equals(namespaceUri) && !requirePrefix)
                        return "";
                    if ("http://www.singham.yuan.com/body".equals(namespaceUri) && !requirePrefix)
                        return "";
                    return "ns";
                }
            };
            properties.put("com.sun.xml.bind.namespacePrefixMapper", mapper);
            headerMarshaller.setMarshallerProperties(properties);
            bodyMarshaller.setMarshallerProperties(properties);

            headerMarshaller.marshal(testHead, soapMessage.getSoapHeader().getResult());
            bodyMarshaller.marshal(testBody, soapMessage.getSoapBody().getPayloadResult());

/*            StringResult headerResult = new StringResult();
            headerMarshaller.marshal(testHead, headerResult);
            StringSource headerSource = new StringSource(headerResult.toString());
            transform(headerSource, soapMessage.getSoapHeader().getResult());

            StringResult bodyResult = new StringResult();
            bodyMarshaller.marshal(rq, bodyResult);
            StringSource bodySource = new StringSource(bodyResult.toString());
            transform(bodySource, soapMessage.getSoapBody().getPayloadResult());*/

        };

        WebServiceMessageCallback responseExtractor = message -> {

        };

        webServiceTemplate.sendAndReceive(remoteUrl, requestCallback, responseExtractor);
    }

}
