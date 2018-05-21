package com.singham.yuan.ws.test.common.test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class PrivateConstructorsTestSupport {

    public static void testPrivateConstructors(Constructor<?>[] constructors) {
        for (Constructor<?> constructor : constructors) {
            try {
                System.out.println(constructor.getName());
                constructor.setAccessible(true);
                constructor.newInstance();
            } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
                System.out.println(e.toString());
            }
        }
    }

}
