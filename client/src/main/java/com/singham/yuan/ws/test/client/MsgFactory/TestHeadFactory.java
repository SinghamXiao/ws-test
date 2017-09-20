package com.singham.yuan.ws.test.client.MsgFactory;

import com.yuan.singham.header.Authentication;
import com.yuan.singham.header.Info;
import com.yuan.singham.header.TestHead;

public class TestHeadFactory {

    public static TestHead newTestHead() {
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
        return testHead;
    }

}
