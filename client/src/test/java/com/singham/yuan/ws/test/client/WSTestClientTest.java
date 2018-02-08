package com.singham.yuan.ws.test.client;

import com.singham.yuan.head.Authentication;
import com.singham.yuan.head.Info;
import com.singham.yuan.head.TestHead;
import com.singham.yuan.ws.test.client.interceptor.LogClientInterceptor;
import org.junit.Test;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.xml.transform.StringResult;
import org.springframework.xml.transform.StringSource;
import org.springframework.xml.transform.TransformerHelper;
import org.springframework.xml.transform.TransformerObjectSupport;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class WSTestClientTest extends TransformerObjectSupport {

    private static final String url = "http://localhost:8081";

    public static void main(String[] args) {

        WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
        webServiceTemplate.setInterceptors(new ClientInterceptor[]{new LogClientInterceptor()});

        Jaxb2Marshaller headerMarshaller = new Jaxb2Marshaller();
        headerMarshaller.setClassesToBeBound(TestHead.class);
        Map<String, Object> properties = new HashMap<>();
//        properties.put(Marshaller.JAXB_ENCODING, "UTF-8");
//        properties.put(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
//        properties.put(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

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

            StringResult headerResult = new StringResult();
            headerMarshaller.marshal(testHead, headerResult);
            StringSource headerSource = new StringSource(headerResult.toString());
            StringWriter writer = new StringWriter();
            Transformer transformer = new TransformerHelper().createTransformer();
//            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
//            transformer.setOutputProperty(OutputKeys.INDENT, "no");
            transformer.transform(headerSource, new StreamResult(writer));
            System.out.println();
            System.out.println(writer.toString());


            try {
                JAXBContext context = JAXBContext.newInstance(testHead.getClass());
                Marshaller marshaller = context.createMarshaller();
//                marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");// //编码格式
//                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);// 是否格式化生成的xml串
//                marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false);// 是否省略xm头声明信息
                writer = new StringWriter();
                marshaller.marshal(testHead, writer);
                System.out.println(writer.toString());
            } catch (JAXBException e) {
                e.printStackTrace();
            }
/*
            StringResult bodyResult = new StringResult();
            bodyMarshaller.marshal(rq, bodyResult);
            StringSource bodySource = new StringSource(bodyResult.toString());
            transform(bodySource, soapMessage.getSoapBody().getPayloadResult());*/

        };

        WebServiceMessageCallback responseExtractor = message -> {

        };

        webServiceTemplate.sendAndReceive(url, requestCallback, responseExtractor);
    }

    @Test
    public void testExecute() {

    }
}
