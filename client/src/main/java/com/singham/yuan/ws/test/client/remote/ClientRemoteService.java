package com.singham.yuan.ws.test.client.remote;

import com.singham.yuan.body.Error;
import com.singham.yuan.body.TestBody;
import com.singham.yuan.head.TestHead;
import com.singham.yuan.ws.test.client.factory.TestBodyFactory;
import com.singham.yuan.ws.test.client.factory.TestHeadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceMessageExtractor;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.SoapMessage;
import org.xmlsoap.schemas.soap.envelope.Fault;

import javax.xml.bind.JAXBElement;

@Service
public class ClientRemoteService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientRemoteService.class);

    @Value("${remote.url}")
    private String remoteUrl;

    @Autowired
    @Qualifier("webServiceTemplate")
    private WebServiceTemplate webServiceTemplate;

    @Autowired
    @Qualifier("headerMarshaller")
    private Jaxb2Marshaller headerMarshaller;

    @Autowired
    @Qualifier("bodyMarshaller")
    private Jaxb2Marshaller bodyMarshaller;

    @Autowired
    @Qualifier("faultMarshaller")
    private Jaxb2Marshaller faultMarshaller;

    public void execute() {

        TestHead testHead = TestHeadFactory.newTestHead();

        TestBody testBody = TestBodyFactory.newTestBody();

        WebServiceMessageCallback requestCallback = message -> {
            SoapMessage soapMessage = (SoapMessage) message;
            headerMarshaller.marshal(testHead, soapMessage.getSoapHeader().getResult());
            bodyMarshaller.marshal(testBody, soapMessage.getSoapBody().getPayloadResult());
        };

        WebServiceMessageExtractor<TestBody> responseExtractor = message -> {
            SoapMessage soapMessage = (SoapMessage) message;
            if (null != soapMessage.getSoapBody().getFault()) {
                Fault fault = (Fault) ((JAXBElement) faultMarshaller.unmarshal(soapMessage.getSoapBody().getFault().getSource())).getValue();
                LOGGER.error(fault.getFaultstring());
                return null;
            }

            TestBody rs = (TestBody) bodyMarshaller.unmarshal(soapMessage.getSoapBody().getPayloadSource());
            Error error = rs.getError();
            if (null != error) {
                LOGGER.error(error.getDetail());
                return null;
            }

            return rs;
        };

        webServiceTemplate.sendAndReceive(remoteUrl, requestCallback, responseExtractor);
    }

}
