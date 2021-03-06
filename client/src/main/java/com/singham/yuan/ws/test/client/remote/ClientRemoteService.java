package com.singham.yuan.ws.test.client.remote;

import com.singham.yuan.body.Error;
import com.singham.yuan.body.TestBody;
import com.singham.yuan.head.TestHead;
import com.singham.yuan.ws.test.client.model.db.User;
import com.singham.yuan.ws.test.client.repository.UserRepository;
import com.singham.yuan.ws.test.common.factory.TestBodyFactory;
import com.singham.yuan.ws.test.common.factory.TestHeadFactory;
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
    @Qualifier("clientHeadMarshaller")
    private Jaxb2Marshaller headMarshaller;

    @Autowired
    @Qualifier("clientBodyMarshaller")
    private Jaxb2Marshaller bodyMarshaller;

    @Autowired
    @Qualifier("clientFaultMarshaller")
    private Jaxb2Marshaller faultMarshaller;

    @Autowired
    private UserRepository userRepository;

    public void execute() {

        TestHead testHead = TestHeadFactory.newTestHead();

        TestBody testBody = TestBodyFactory.newTestBody();

        WebServiceMessageCallback requestCallback = message -> {
            SoapMessage soapMessage = (SoapMessage) message;
//            headMarshaller.marshal(testHead, soapMessage.getSoapHeader().getResult());
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


        try {
            userRepository.save(new User("Lucy"));
            webServiceTemplate.sendAndReceive(remoteUrl, requestCallback, responseExtractor);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }

//        RequestBody requestBody = RequestBodyFactory.newRequestBody();
//        WebServiceMessageCallback requestCallback2 = message -> {
//            SoapMessage soapMessage = (SoapMessage) message;
//            headMarshaller.marshal(testHead, soapMessage.getSoapHeader().getResult());
//            bodyMarshaller.marshal(requestBody, soapMessage.getSoapBody().getPayloadResult());
//        };
//
//        WebServiceMessageExtractor<ResponseBody> responseExtractor2 = message -> {
//            SoapMessage soapMessage = (SoapMessage) message;
//            if (null != soapMessage.getSoapBody().getFault()) {
//                Fault fault = (Fault) ((JAXBElement) faultMarshaller.unmarshal(soapMessage.getSoapBody().getFault().getSource())).getValue();
//                LOGGER.error(fault.getFaultstring());
//                return null;
//            }
//
//            ResponseBody rs = (ResponseBody) bodyMarshaller.unmarshal(soapMessage.getSoapBody().getPayloadSource());
//            ErrorInfo error = rs.getError();
//            if (null != error) {
//                LOGGER.error(error.getMessage());
//                return null;
//            }
//
//            return rs;
//        };
//
//        try {
//            webServiceTemplate.sendAndReceive(remoteUrl, requestCallback2, responseExtractor2);
//        } catch (exception e) {
//            LOGGER.error(e.getMessage());
//        }
//
//
//        WebServiceMessageCallback requestCallback3 = message -> {
//            SoapMessage soapMessage = (SoapMessage) message;
//            SoapHeader soapHeader = ((SoapMessage) message).getSoapHeader();
//
//            String requestIdentifier = "<RequestIdentifier>" + "ID" + "</RequestIdentifier>";
//            String credentials = "<Credentials Company=\"" + "xx" + "\" Agency=\"" + "xxxx" + "\" Password=\"" + "xxxxxxxx" + "\" />";
//            String system = "<System>" + "?" + "</System>";
//
//            Transformer transformer = TransformerFactory.newInstance().newTransformer();
//            transformer.transform(new StringSource(requestIdentifier), soapHeader.getResult());
//            transformer.transform(new StringSource(credentials), soapHeader.getResult());
//            transformer.transform(new StringSource(system), soapHeader.getResult());
//
//            bodyMarshaller.marshal(requestBody, soapMessage.getSoapBody().getPayloadResult());
//        };
//
//        try {
//            webServiceTemplate.sendAndReceive(remoteUrl, requestCallback3, responseExtractor2);
//        } catch (exception e) {
//            LOGGER.error(e.getMessage());
//        }
    }

}
