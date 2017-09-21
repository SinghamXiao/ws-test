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

    private final IMocksControl mocksControl;

    private final T target;

    public TestBuilder(T target) {
        this.mocksControl = EasyMock.createControl();
        this.target = target;
    }

    public TestBuilder(T target, MockType mockType) {
        this.target = target;
        this.mocksControl = EasyMock.createControl(mockType);
    }

    public TestBuilder(Class<T> targetClass, Object... args) throws IllegalAccessException, InstantiationException {
        this(targetClass, MockType.DEFAULT, args);
    }

    public TestBuilder(Class<T> targetClass, MockType mockType, Object... args) throws IllegalAccessException, InstantiationException {
        this.mocksControl = EasyMock.createControl(mockType);

        if (args == null || args.length == 0) {
            this.target = targetClass.newInstance();
            return;
        }
        T tmp = null;
        for (Constructor<?> constructor : targetClass.getConstructors()) {
            try {
                tmp = (T) constructor.newInstance(args);
            } catch (InvocationTargetException | IllegalArgumentException e) {
                continue;
            }
        }
        if (tmp == null) {
            throw new InstantiationException("Constructor not found with " + args.length + " args.");
        }
        this.target = tmp;
    }

    public <M> TestBuilder<T> withMock(Class<M> mockComponentClass, MockRecorder<M> mockRecorder) {
        M mocker = mocksControl.createMock(mockComponentClass);
        mockRecorder.record(mocker);
        try {
            Field field = findFieldByClass(target.getClass(), mockComponentClass);
            field.setAccessible(true);
            setField(mocker, field);
        } catch (Exception e) {
            throw new IllegalArgumentException("Can not find field for class: " + mockComponentClass.getName());
        }
        return this;
    }

    public <M> TestBuilder<T> withMock(Class<M> mockComponentClass, String fieldName, MockRecorder<M> mockRecorder) {
        M mocker = mocksControl.createMock(mockComponentClass);
        mockRecorder.record(mocker);
        try {
            Field field = findFieldByName(target.getClass(), fieldName);
            field.setAccessible(true);
            setField(mocker, field);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid field name: " + fieldName);
        }
        return this;
    }

    public <M> TestBuilder<T> withMock(Class<M> mockComponentClass, String fieldName, String mapKey, MockRecorder<M> mockRecorder) {
        M mocker = mocksControl.createMock(mockComponentClass);
        mockRecorder.record(mocker);
        try {
            Field field = findFieldByName(target.getClass(), fieldName);
            field.setAccessible(true);
            if (!field.getGenericType().getTypeName().startsWith(Map.class.getName())) {
                throw new IllegalArgumentException("Field must be a Map: " + fieldName);
            }
            if (field.get(target) == null) {
                field.set(target, new LinkedHashMap<>());
            }
            ((Map) field.get(target)).put(mapKey, mocker);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid field name: " + fieldName);
        }
        return this;
    }

    public <V> TestBuilder<T> withField(String fieldName, V value) {
        try {
            Field field = findFieldByName(target.getClass(), fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid field name: " + fieldName);
        }
        return this;
    }

    public T build() {
        mocksControl.replay();
        return target;
    }

    public void verifyMocks() {
        mocksControl.verify();
    }

    private <M> void setField(M mocker, Field field) throws IllegalAccessException {
        if (field.getGenericType().getTypeName().startsWith(List.class.getName())) {
            if (field.get(target) == null) {
                field.set(target, new ArrayList<>());
            }
            ((List) field.get(target)).add(mocker);
        } else {
            field.set(target, mocker);
        }
    }

    private <M> Field findFieldByClass(Class clazz, Class<M> mockComponentClass) {
        for (Field field : clazz.getDeclaredFields()) {
            if (field.getGenericType().getTypeName().contains(mockComponentClass.getName())) {
                return field;
            }
        }
        if (clazz.getSuperclass() != null) {
            return findFieldByClass(clazz.getSuperclass(), mockComponentClass);
        }
        throw new IllegalArgumentException();
    }

    private Field findFieldByName(Class clazz, String fieldName) {
        for (Field field : clazz.getDeclaredFields()) {
            if (field.getName().equals(fieldName)) {
                return field;
            }
        }
        if (clazz.getSuperclass() != null) {
            return findFieldByName(clazz.getSuperclass(), fieldName);
        }
        throw new IllegalArgumentException();
    }

}
