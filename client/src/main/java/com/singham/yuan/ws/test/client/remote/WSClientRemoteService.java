package com.singham.yuan.ws.test.client.remote;

import com.singham.yuan.ws.test.client.MsgFactory.TestHeadFactory;
import com.yuan.singham.header.TestHead;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.xml.transform.TransformerObjectSupport;

@Service
public class WSClientRemoteService extends TransformerObjectSupport {

    @Value("${remote.url}")
    private String remoteUrl;

    @Autowired
    private WebServiceTemplate webServiceTemplate;

    @Autowired
    private Jaxb2Marshaller headerMarshaller;

    public void excute() {

        TestHead testHead = TestHeadFactory.newTestHead();

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

        webServiceTemplate.sendAndReceive(remoteUrl, requestCallback, responseExtractor);
    }

}
