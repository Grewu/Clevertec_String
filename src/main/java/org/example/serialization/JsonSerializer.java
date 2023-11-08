package org.example.serialization;

import java.lang.reflect.InvocationTargetException;

public interface JsonSerializer {
    String serializer(Object object);

    <T> T deSerializer(String jsonString, Class<T> clazz);
}
