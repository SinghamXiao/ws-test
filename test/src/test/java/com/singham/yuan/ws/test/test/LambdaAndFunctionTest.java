package com.singham.yuan.ws.test.test;

import org.junit.Test;

import java.util.function.Function;

public class LambdaAndFunctionTest {

    @Test
    public void testLambda() {
        System.out.println(test("Hello", s -> test1((String) s, "World")));
        System.out.println(test("Hello", s -> test1((String) s, "World2")));
        System.out.println(test("Hello2", s -> test2((String) s, "World1", "!")));
        System.out.println(test("Hello2", s -> test2((String) s, "World2", "?")));
    }

    private String test2(String s1, String s2, String s3) {
        System.out.println(s1);
        System.out.println(s2);
        return s1 + " - " + s2 + " - " + s3;
    }

    private String test1(String s1, String s2) {
        System.out.println(s1);
        System.out.println(s2);
        return s1 + " - " + s2;
    }

    private String test(String s, TestLambda testLambda) {
        return (String) testLambda.doWithRequest(s);
    }

    @FunctionalInterface
    interface TestLambda {
        Object doWithRequest(Object rq);
    }

    @Test
    public void testFunction() {
        System.out.println(testFunction("Hello", s -> s + " World!"));
        System.out.println(testFunction("Hello2", s -> s + " World!"));
    }

    private String testFunction(String test, Function<String, String> function) {
        return function.apply(test);
    }

}