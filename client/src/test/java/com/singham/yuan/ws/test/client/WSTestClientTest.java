package com.singham.yuan.ws.test.client;

import org.junit.Test;
import org.springframework.ws.soap.saaj.SaajSoapMessage;
import org.springframework.xml.transform.TransformerObjectSupport;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPMessage;
import java.math.BigDecimal;

public class WSTestClientTest extends TransformerObjectSupport {

    private static final String url = "http://localhost:8081";

    public static void main(String[] args) {

        try {
            MessageFactory messageFactory = MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL);
            SOAPMessage saajMessage = messageFactory.createMessage();
            SaajSoapMessage saajSoapMessage = new SaajSoapMessage(saajMessage, true, messageFactory);
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();

            System.out.println(-12345678.0);
            System.out.println(12345678.0);
            //整数位为0,当小数位以0开始连续出现大于等于3时开始以科学计数法显示
            System.out.println(0.0001);
            System.out.println(-0.0001);
        } catch (Exception e) {
            e.printStackTrace();
        }
//
//        WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
//        webServiceTemplate.setInterceptors(new ClientInterceptor[]{new LogClientInterceptor()});
//
//        Jaxb2Marshaller headerMarshaller = new Jaxb2Marshaller();
//        headerMarshaller.setClassesToBeBound(TestHead.class);
//        Map<String, Object> properties = new HashMap<>();
////        properties.put(Marshaller.JAXB_ENCODING, "UTF-8");
////        properties.put(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
////        properties.put(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
//
//        headerMarshaller.setMarshallerProperties(properties);
//
//        TestHead testHead = new TestHead();
//        Info info = new Info();
//        info.setRequestID("RequestID");
//        info.setSourceID("SourceID");
//        info.setDestinationID("DestinationID");
//        Authentication authentication = new Authentication();
//        authentication.setUsername("username");
//        authentication.setPassword("password");
//        info.setAuthentication(authentication);
//        testHead.setInfo(info);
//        testHead.setName("name");
//        testHead.setVersion("version");
//
//        WebServiceMessageCallback requestCallback = message -> {
//            SoapMessage soapMessage = (SoapMessage) message;
//
//            headerMarshaller.marshal(testHead, soapMessage.getSoapHeader().getResult());
//
//            StringWriter writer = new StringWriter();
//            Transformer transformer = new TransformerHelper().createTransformer();
////            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
////            transformer.setOutputProperty(OutputKeys.INDENT, "no");
//            Source source = soapMessage.getEnvelope().getSource();
//            transformer.transform(source, new StreamResult(writer));
//            System.out.println();
//            System.out.println(writer.toString());
//
//
////            try {
////                JAXBContext context = JAXBContext.newInstance(testHead.getClass());
////                Marshaller marshaller = context.createMarshaller();
////                marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");// //编码格式
////                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);// 是否格式化生成的xml串
////                marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false);// 是否省略xm头声明信息
////                writer = new StringWriter();
////                marshaller.marshal(testHead, writer);
////                System.out.println(writer.toString());
////            } catch (JAXBException e) {
////                e.printStackTrace();
////            }
///*
//            StringResult bodyResult = new StringResult();
//            bodyMarshaller.marshal(rq, bodyResult);
//            StringSource bodySource = new StringSource(bodyResult.toString());
//            transform(bodySource, soapMessage.getSoapBody().getPayloadResult());*/
//
//        };
//
//        WebServiceMessageCallback responseExtractor = message -> {
//
//        };
//
//        webServiceTemplate.sendAndReceive(url, requestCallback, responseExtractor);

        System.out.println();
    }

    @Test
    public void testExecute() {
        User lily = new User("Lily");
        Student student = new Student("1", lily);
        System.out.println(lily.getName());
    }
}
