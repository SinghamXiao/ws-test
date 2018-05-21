package com.singham.yuan.ws.test.common.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JsonMapperUtilTest {

    private String jsonString = "{\"id\":1,\"name\":\"DerbySoft\"}";

    @Test
    public void toJsonString() {
        JsonInnerTest jsonInnerTest = new JsonInnerTest();
        jsonInnerTest.setId(1);
        jsonInnerTest.setName("DerbySoft");
        String jsonString = JsonMapperUtil.toJsonString(jsonInnerTest);
        System.out.println(jsonString);
        assertEquals(this.jsonString, jsonString);
    }

    @Test
    public void parseToObject1() {
        JsonInnerTest jsonInnerTest = JsonMapperUtil.parseToObject(jsonString, JsonInnerTest.class);
        String jsonString = JsonMapperUtil.toJsonString(jsonInnerTest);
        System.out.println(jsonString);
        assertEquals(1, jsonInnerTest.getId().intValue());
        assertEquals("DerbySoft", jsonInnerTest.getName());
    }

    @Test
    public void parseToObject2() {
        JsonTest jsonTest = JsonMapperUtil.parseToObject(jsonString, JsonTest.class);
        String jsonString = JsonMapperUtil.toJsonString(jsonTest);
        System.out.println(jsonString);
        assertEquals(1, jsonTest.getId().intValue());
        assertEquals("DerbySoft", jsonTest.getName());
    }

    static class JsonInnerTest {

        private Integer id;

        private String name;

//        public JsonInnerTest() {
//        }
//
//        @JsonCreator
//        public JsonInnerTest(@JsonProperty("id") Integer id, @JsonProperty("name") String name) {
//            this.id = id;
//            this.name = name;
//        }
//
//        public JsonInnerTest(Integer id, String name) {
//            this.id = id;
//            this.name = name;
//        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

}