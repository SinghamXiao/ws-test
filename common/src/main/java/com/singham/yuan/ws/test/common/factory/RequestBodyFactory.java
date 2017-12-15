package com.singham.yuan.ws.test.common.factory;

import com.singham.yuan.body.Info;
import com.singham.yuan.body.RequestBody;

public class RequestBodyFactory {

    public static RequestBody newRequestBody() {
        RequestBody testBody = new RequestBody();
        testBody.setLevel("Level");
        Info personInfo = new Info();
        personInfo.setName("Nanjing");
        personInfo.setAge(30);
        testBody.setInfo(personInfo);
        return testBody;
    }

}
