package com.singham.yuan.ws.test.common.mock;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.easymock.MockType;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TestBuilder<T> {

    private IMocksControl mocksControl;

    private T target;

    public TestBuilder(T target) {
        this.mocksControl = EasyMock.createControl();
        this.target = target;
    }

    public TestBuilder(T target, MockType mockType) {
        this.target = target;
        this.mocksControl = EasyMock.createControl(mockType);
    }

    public TestBuilder(Class<T> targetClass, Object... args) {
        this(targetClass, MockType.DEFAULT, args);
    }

    public TestBuilder(Class<T> targetClass, MockType mockType, Object... args) {
        try {
            this.mocksControl = EasyMock.createControl(mockType);
            if (args != null && args.length != 0) {
                T tmp = null;
                Constructor[] var5 = targetClass.getConstructors();
                for (Constructor constructor : var5) {
                    try {
                        tmp = (T) constructor.newInstance(args);
                    } catch (IllegalArgumentException | InvocationTargetException var10) {
                    }
                }

                if (tmp == null) {
                    throw new InstantiationException("Constructor not found with " + args.length + " args.");
                } else {
                    this.target = tmp;
                }
            } else {
                this.target = targetClass.newInstance();
            }
        } catch (Exception var11) {
            throw new RuntimeException(var11);
        }
    }

    public <M> TestBuilder<T> withMock(Class<M> mockComponentClass, MockRecorder<M> mockRecorder) {
        M mocker = this.mocksControl.createMock(mockComponentClass);
        mockRecorder.record(mocker);

        try {
            Field field = this.findFieldByClass(this.target.getClass(), mockComponentClass);
            field.setAccessible(true);
            setField(mocker, field);

            return this;
        } catch (Exception var5) {
            throw new IllegalArgumentException("Can not find field for class: " + mockComponentClass.getName());
        }
    }

    private <M> void setField(M mocker, Field field) throws IllegalAccessException {
        if (field.getGenericType().getTypeName().startsWith(List.class.getName())) {
            if (field.get(this.target) == null) {
                field.set(this.target, new ArrayList());
            }

            ((List) field.get(this.target)).add(mocker);
        } else {
            field.set(this.target, mocker);
        }
    }

    public <M> TestBuilder<T> withMock(Class<M> mockComponentClass, String fieldName, MockRecorder<M> mockRecorder) {
        M mocker = this.mocksControl.createMock(mockComponentClass);
        mockRecorder.record(mocker);

        try {
            Field field = this.findFieldByName(this.target.getClass(), fieldName);
            field.setAccessible(true);
            setField((M) mocker, field);

            return this;
        } catch (Exception var6) {
            throw new IllegalArgumentException("Invalid field name: " + fieldName);
        }
    }

    public <M> TestBuilder<T> withMock(Class<M> mockComponentClass, String fieldName, String mapKey, MockRecorder<M> mockRecorder) {
        M mocker = this.mocksControl.createMock(mockComponentClass);
        mockRecorder.record(mocker);

        try {
            Field field = this.findFieldByName(this.target.getClass(), fieldName);
            field.setAccessible(true);
            if (!field.getGenericType().getTypeName().startsWith(Map.class.getName())) {
                throw new IllegalArgumentException("Field must be a Map: " + fieldName);
            } else {
                if (field.get(this.target) == null) {
                    field.set(this.target, new LinkedHashMap());
                }

                ((Map) field.get(this.target)).put(mapKey, mocker);
                return this;
            }
        } catch (Exception var7) {
            throw new IllegalArgumentException("Invalid field name: " + fieldName);
        }
    }

    public <V> TestBuilder<T> withField(String fieldName, V value) {
        try {
            Field field = this.findFieldByName(this.target.getClass(), fieldName);
            field.setAccessible(true);
            field.set(this.target, value);
            return this;
        } catch (Exception var4) {
            throw new IllegalArgumentException("Invalid field name: " + fieldName);
        }
    }

    public T build() {
        this.mocksControl.replay();
        return this.target;
    }

    public void verifyMocks() {
        this.mocksControl.verify();
    }

    private <M> Field findFieldByClass(Class clazz, Class<M> mockComponentClass) {
        Field[] var3 = clazz.getDeclaredFields();

        for (Field field : var3) {
            if (field.getGenericType().getTypeName().contains(mockComponentClass.getName())) {
                return field;
            }
        }

        if (clazz.getSuperclass() != null) {
            return this.findFieldByClass(clazz.getSuperclass(), mockComponentClass);
        } else {
            throw new IllegalArgumentException();
        }
    }

    private Field findFieldByName(Class clazz, String fieldName) {
        Field[] var3 = clazz.getDeclaredFields();

        for (Field field : var3) {
            if (field.getName().equals(fieldName)) {
                return field;
            }
        }

        if (clazz.getSuperclass() != null) {
            return this.findFieldByName(clazz.getSuperclass(), fieldName);
        } else {
            throw new IllegalArgumentException();
        }
    }

}
