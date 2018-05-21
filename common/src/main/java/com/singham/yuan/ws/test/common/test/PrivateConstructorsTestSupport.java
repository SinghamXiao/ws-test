package com.singham.yuan.ws.test.common.test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class PrivateConstructorsTestSupport {

    public static void testPrivateConstructor(Class<?> aClass) {
        try {
            Constructor<?> constructor = aClass.getDeclaredConstructor();
            System.out.println(constructor.getName());
            constructor.setAccessible(true);
            constructor.newInstance();
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
            System.out.println(e.toString());
        }
    }

}
