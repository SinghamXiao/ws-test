package com.singham.yuan.ws.test.server.interceptor;

import com.singham.yuan.ws.test.common.factory.TestHeadFactory;
import com.singham.yuan.ws.test.common.service.HandleNsPrefixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.SoapMessage;

@Component
public class HandleEndPointInterceptor implements EndpointInterceptor {

    @Autowired
    @Qualifier("serverHeadMarshaller")
    private Jaxb2Marshaller headMarshaller;

    @Autowired
    @Qualifier("serverHandleNsPrefixService")
    private HandleNsPrefixService handleNsPrefixService;

    @Override
    public boolean handleRequest(MessageContext messageContext, Object endpoint) throws Exception {
        return true;
    }

    @Override
    public boolean handleResponse(MessageContext messageContext, Object endpoint) throws Exception {
        SoapMessage soapMessage = (SoapMessage) messageContext.getResponse();
        headMarshaller.marshal(TestHeadFactory.newTestHead(), soapMessage.getSoapHeader().getResult());
        handleNsPrefixService.handleNsPrefix(soapMessage);
        return true;
    }

    @Override
    public boolean handleFault(MessageContext messageContext, Object endpoint) throws Exception {
        return true;
    }

    @Override
    public void afterCompletion(MessageContext messageContext, Object endpoint, Exception ex) throws Exception {

    }

}
