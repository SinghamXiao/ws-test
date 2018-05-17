package com.singham.yuan.ws.test.test;

import org.junit.Test;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;

public class NewTest {

    @Test
    public void name() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
//        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
//        mapper.setVisibility(PropertyAccessor.IS_GETTER, JsonAutoDetect.Visibility.NONE);
//        mapper.setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
//        mapper.setVisibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.NONE);
        mapper.setVisibility(new VisibilityChecker.Std(
                JsonAutoDetect.Visibility.NONE,
                JsonAutoDetect.Visibility.NONE,
                JsonAutoDetect.Visibility.NONE,
                JsonAutoDetect.Visibility.NONE,
                JsonAutoDetect.Visibility.ANY));


        String json = mapper.writeValueAsString(new TestTT(1, "Json"));
        System.out.println(json);

        json = mapper.writeValueAsString(new TestTT(1, ""));
        System.out.println(json);

        json = mapper.writeValueAsString(new TestTT(1, null));
        System.out.println(json);

        TestTT value = new TestTT();
        value.setPOS("pos");
        json = mapper.writeValueAsString(value);
        System.out.println(json);

    }

    private class TestTT {

        @JsonProperty("ID")
        private int iD;

        @JsonProperty("POS")
        private String pOS;

        public TestTT() {
        }

        public TestTT(int iD, String pOS) {
            this.iD = iD;
            this.pOS = pOS;
        }

        public int getID() {
            return iD;
        }

        public void setID(int iD) {
            this.iD = iD;
        }

        public String getPOS() {
            return pOS;
        }

        public void setPOS(String pos) {
            this.pOS = pos;
        }
    }
}