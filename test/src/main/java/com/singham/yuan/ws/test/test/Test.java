package com.singham.yuan.ws.test.test;

public class Test {

    public static void main(String[] args) {
        for (int i = 0; i < args.length; i++) {
            System.out.println(args[i]);
        }

        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        System.out.println(loader);
        System.out.println(loader.getParent());
        System.out.println(loader.getParent().getParent());

        StackTraceElement[] stackTrace = new RuntimeException().getStackTrace();
        System.out.println();
    }


}