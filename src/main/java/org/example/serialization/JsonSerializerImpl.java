package org.example.serialization;

import org.example.entity.Customer;
import org.example.entity.Order;
import org.example.entity.Product;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;


public class JsonSerializerImpl implements JsonSerializer {
    @Override
    public String serializer(Object object) throws IllegalAccessException {
        LinkedHashMap<String, Object> jsonElementsMap = getStringObjectLinkedHashMap(object);
        return getString(jsonElementsMap);
    }

    @Override
    public <T> T deSerializer(String jsonString, Class<T> clazz) throws NoSuchMethodException, InvocationTargetException,
            InstantiationException, IllegalAccessException {
        LinkedHashMap<String, Object> jsonElementsMap = parseJson(jsonString);
        Constructor<T> constructor = clazz.getConstructor();
        T object = constructor.newInstance();

        for (Field field : clazz.getDeclaredFields()) {
            String fieldName = field.getName();
            String setterName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            try {
                setFieldValue(clazz, field, setterName, jsonElementsMap, fieldName, object);
            } catch (IllegalAccessException | InvocationTargetException e) {
               throw new RuntimeException();
            }
        }

        return object;
    }

    private static <T> void setFieldValue(Class<T> clazz, Field field, String setterName, LinkedHashMap<String, Object> jsonElementsMap, String fieldName, T object) throws IllegalAccessException, InvocationTargetException {
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            if (method.getName().equals(setterName)) {
                Object fieldValue = jsonElementsMap.get(fieldName);
                if (fieldValue != null) {
                    if (field.getType() == UUID.class && fieldValue instanceof String) {
                        UUID uuid = UUID.fromString((String) fieldValue);
                        method.invoke(object, uuid);
                    } else if (field.getType() == Double.class) {
                        if (fieldValue instanceof Double) {
                            method.invoke(object, fieldValue);
                        } else if (fieldValue instanceof String) {
                            Double price = Double.parseDouble((String) fieldValue);
                            method.invoke(object, price);
                        }
                    } else if (field.getType() == String.class) {

                        if (fieldValue instanceof String) {
                            String name = (String) fieldValue;
                            method.invoke(object, name);
                        }
                    }
                }
            }
        }
    }

    private LinkedHashMap<String, Object> parseJson(String jsonString) {
        LinkedHashMap<String, Object> jsonMap = new LinkedHashMap<>();
        jsonString = jsonString.trim();
        if (jsonString.startsWith("{") && jsonString.endsWith("}")) {
            String substring = jsonString.substring(1, jsonString.length() - 1);
            String[] keyValues = substring.split(",");
            for (String pair : keyValues) {
                String[] strings = pair.split(":");
                if (strings.length == 2) {
                    String key = strings[0].trim().replace("\"", "");
                    String value = strings[1].trim().replace("\"", "");
                    jsonMap.put(key, value);
                }
            }
        }
        return jsonMap;
    }

    private String getString(LinkedHashMap<String, Object> jsonElementsMap) {
        StringBuilder jsonString = new StringBuilder("{");

        boolean firstEntry = true;
        for (Map.Entry<String, Object> entry : jsonElementsMap.entrySet()) {
            if (!firstEntry) {
                jsonString.append(",");
            }
            jsonString.append("\"").append(entry.getKey()).append("\":");

            if (entry.getValue() instanceof String || entry.getValue() instanceof Character
                    || entry.getValue().getClass().isEnum()) {
                jsonString.append("\"").append(entry.getValue()).append("\"");
            } else if (entry.getValue() instanceof List<?> list) {
                jsonString.append("[");
                for (int i = 0; i < list.size(); i++) {
                    if (i > 0) {
                        jsonString.append(",");
                    }
                    jsonString.append(list.get(i));
                }
                jsonString.append("]");
            } else {
                jsonString.append(entry.getValue());
            }

            firstEntry = false;
        }

        jsonString.append("}");

        return jsonString.toString();
    }

    private LinkedHashMap<String, Object> getStringObjectLinkedHashMap(Object object) throws IllegalAccessException {
        Class<?> clazz = object.getClass();
        LinkedHashMap<String, Object> jsonElementsMap = new LinkedHashMap<>();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fileName = field.getName();
            Object fieldValue = field.get(object);

            if (fieldValue == null) {
                jsonElementsMap.put(fileName, null);
            } else if (field.getType().equals(String.class) || field.getType().equals(Integer.class)
                    || field.getType().equals(Double.class) || field.getType().equals(Float.class)
                    || field.getType().equals(Long.class) || field.getType().equals(Boolean.class)
                    || field.getType().isEnum() || field.getType().isArray()) {
                jsonElementsMap.put(fileName, fieldValue);
            } else if (field.getType().equals(UUID.class)) {
                jsonElementsMap.put(fileName, fieldValue.toString());
            } else if (field.getType().equals(Order.class) || field.getType().equals(Product.class) || field.getType().equals(Customer.class)) {
                String recursive = serializer(fieldValue);
                jsonElementsMap.put(fileName, recursive);
            } else if (fieldValue instanceof List<?> list) {
                List<String> listJson = new ArrayList<>();
                for (Object item : list) {
                    String itemsJson = serializer(item);
                    listJson.add(itemsJson);
                }
                jsonElementsMap.put(fileName, listJson);
            } else if (fieldValue instanceof Map<?, ?> map) {
                List<String> listJson = new ArrayList<>();
                for (Object key : map.keySet()) {
                    Object item = map.get(key);
                    String itemJson = serializer(item);
                    listJson.add(itemJson);
                }
                jsonElementsMap.put(fileName, listJson);
            } else if (fieldValue instanceof Set<?> set) {
                List<String> listJson = new ArrayList<>();
                for (Object key : set) {
                    String itemJson = serializer(key);
                    listJson.add(itemJson);
                }
                jsonElementsMap.put(fileName, listJson);
            } else {
                jsonElementsMap.put(fileName, fieldValue.toString());
            }
        }
        return jsonElementsMap;
    }
}
