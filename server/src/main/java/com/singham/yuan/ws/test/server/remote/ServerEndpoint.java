package com.singham.yuan.ws.test.server.remote;

import com.singham.yuan.body.RequestBody;
import com.singham.yuan.body.ResponseBody;
import com.singham.yuan.body.TestBody;
import com.singham.yuan.ws.test.common.factory.ResponseBodyFactory;
import com.singham.yuan.ws.test.server.model.Student;
import com.singham.yuan.ws.test.server.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.ws.soap.SOAPBinding;

@Endpoint(value = SOAPBinding.SOAP12HTTP_BINDING)
public class ServerEndpoint {

    private static final String NAMESPACE_URI = "http://www.yuan.singham.com/body";

    @Autowired
    private StudentRepository studentRepository;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "TestBody")
    @ResponsePayload
    public TestBody execute(@RequestPayload TestBody request) {
        studentRepository.save(new Student(1L, "Lucy"));
        return request;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "RequestBody")
    @ResponsePayload
    public ResponseBody execute(@RequestPayload RequestBody request) {
        return ResponseBodyFactory.newResponseBody();
    }
}
