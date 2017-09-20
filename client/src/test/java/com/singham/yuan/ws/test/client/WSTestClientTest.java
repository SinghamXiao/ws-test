package com.singham.yuan.ws.test.client;

import com.singham.yuan.ws.test.client.interceptor.RequestInterceptor;
import com.sun.xml.internal.bind.marshaller.NamespacePrefixMapper;
import com.yuan.singham.header.Authentication;
import com.yuan.singham.header.Info;
import com.yuan.singham.header.TestHead;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.xml.transform.TransformerObjectSupport;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.util.HashMap;
import java.util.Map;

public class WSTestClientTest extends TransformerObjectSupport {

    private static final String url = "http://localhost:8081";

    public static void main(String[] args) throws JAXBException {

        WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
        webServiceTemplate.setInterceptors(new ClientInterceptor[]{new RequestInterceptor()});

        Jaxb2Marshaller headerMarshaller = new Jaxb2Marshaller();
        headerMarshaller.setClassesToBeBound(TestHead.class);
        Map<String, Object> properties = new HashMap<>();
        properties.put(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        properties.put(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

        NamespacePrefixMapper mapper = new NamespacePrefixMapper() {
            public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
                if ("http://www.singham.yuan.com/header".equals(namespaceUri) && !requirePrefix)
                    return "";
                if ("http://www.singham.yuan.com/body".equals(namespaceUri) && !requirePrefix)
                    return "";
                return "ns";
            }
        };
        properties.put("com.sun.xml.internal.bind.namespacePrefixMapper", mapper);


        headerMarshaller.setMarshallerProperties(properties);

        TestHead testHead = new TestHead();
        Info info = new Info();
        info.setRequestID("RequestID");
        info.setSourceID("SourceID");
        info.setDestinationID("DestinationID");
        Authentication authentication = new Authentication();
        authentication.setUsername("username");
        authentication.setPassword("password");
        info.setAuthentication(authentication);
        testHead.setInfo(info);
        testHead.setName("name");
        testHead.setVersion("version");

        WebServiceMessageCallback requestCallback = message -> {
            SoapMessage soapMessage = (SoapMessage) message;

            headerMarshaller.marshal(testHead, soapMessage.getSoapHeader().getResult());

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

        webServiceTemplate.sendAndReceive(url, requestCallback, responseExtractor);
    }
}
