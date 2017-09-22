package com.singham.yuan.ws.test.client.interceptor;

import com.singham.yuan.ws.test.common.service.HandleNsPrefixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.soap.SoapMessage;


@Component
public class HandleClientInterceptor implements ClientInterceptor {

    @Autowired
    @Qualifier("clientHandleNsPrefixService")
    private HandleNsPrefixService handleNsPrefixService;

    @Override
    public boolean handleRequest(MessageContext messageContext) throws WebServiceClientException {
        SoapMessage soapMessage = (SoapMessage) messageContext.getRequest();
        handleNsPrefixService.handleNsPrefix(soapMessage);
        return true;
    }

    @Override
    public boolean handleResponse(MessageContext messageContext) throws WebServiceClientException {
        SoapMessage soapMessage = (SoapMessage) messageContext.getResponse();
        handleNsPrefixService.handleNsPrefix(soapMessage);
        return true;
    }

    @Override
    public boolean handleFault(MessageContext messageContext) throws WebServiceClientException {
        return true;
    }

    @Override
    public void afterCompletion(MessageContext messageContext, Exception ex) throws WebServiceClientException {

    }

}
