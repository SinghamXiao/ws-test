package com.singham.yuan.ws.test.common.test;

import com.singham.yuan.ws.test.common.mock.TestBuilder;
import org.junit.After;
import org.junit.Before;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class TestBuilderSupport<T> {

    protected TestBuilder<T> builder;

    private Class<T> entityClass;

    public TestBuilderSupport() {
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        entityClass = (Class) params[0];
    }

    @Before
    public void setUp() {
        builder = new TestBuilder<T>(entityClass);
    }

    @After
    public void tearDown() {
        builder.verifyMocks();
    }

}
