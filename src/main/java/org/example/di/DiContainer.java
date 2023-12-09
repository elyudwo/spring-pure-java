package org.example.di;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import java.util.stream.Collectors;

public class DiContainer {

    private Set<Object> beans;

    public DiContainer(Set<Class<?>> beans) {
        this.beans = createBeans(beans);
        this.beans.forEach(this::setFields);
    }

    private Set<Object> createBeans(Set<Class<?>> beans) {
        return beans.stream()
                .map(this::createInstance)
                .collect(Collectors.toSet());
    }

    private Object createInstance(Class<?> aClass) {
        try {
            Constructor<?> constructor = aClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException();
        }
    }

    private void setFields(Object aClass) {
        Field[] fields = aClass.getClass().getDeclaredFields();

        for(Field field : fields) {
            try {
                setField(aClass, field);
            } catch (IllegalAccessException e) {
                throw new RuntimeException();
            }
        }
    }

    private void setField(Object bean, Field field) throws IllegalAccessException {
        field.setAccessible(true);
        Class<?> fieldType = field.getType();

        for(Object o : beans) {
            if(fieldType.isAssignableFrom(o.getClass())) {
                /**
                 * bean 객체에 o를 주입한다.
                 * ex) bean 객체가 HelloController 이고 Field 객체가 HelloService 일 경우
                 *
                 * DI 컨테이너에 없는 객체를 주입해 줄 수 없기 때문에
                 * DI 컨테이너 내부를 순회하면서 HelloService 를 찾은 뒤  HelloController 에 HelloService 객체가 주입되는 것
                 */
                field.set(bean, o);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> aClass) {
        for(Object o : beans) {
            if(aClass.isAssignableFrom(o.getClass())) {
                return (T) o;
            }
        }

        throw new RuntimeException("못찾겠어");
    }

}