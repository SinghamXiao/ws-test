package com.singham.yuan.ws.test.common.factory;

import com.singham.yuan.body.ResponseBody;
import com.singham.yuan.body.ResponseInfo;

public class ResponseBodyFactory {

    public static ResponseBody newResponseBody() {
        ResponseBody responseBody = new ResponseBody();
        responseBody.setLevel("Level");
        ResponseInfo responseInfo = new ResponseInfo();
        responseInfo.setBirthday("2010-10-01");
        responseInfo.setCity("Nanjing");
        responseInfo.setCountry("CHN");
        responseInfo.setAge(10);
        responseBody.setResponseInfo(responseInfo);
        return responseBody;
    }

}
