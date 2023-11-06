package org.example.serialization;

import org.example.entity.Order;
import org.example.entity.Product;

import java.lang.reflect.Field;
import java.util.*;


public class JsonSerializerImpl implements JsonSerializer {
    public String serializer(Object object)  {
        LinkedHashMap<String, Object> jsonElementsMap = null;
        try {
            jsonElementsMap = getStringObjectLinkedHashMap(object);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return getString(jsonElementsMap);
    }

    private  String getString(LinkedHashMap<String, Object> jsonElementsMap) {
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

    private  LinkedHashMap<String, Object> getStringObjectLinkedHashMap(Object object) throws IllegalAccessException {
        Class<?> clazz = object.getClass();
        LinkedHashMap<String, Object> jsonElementsMap = new LinkedHashMap<>();

        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object fieldValue = field.get(object);

            if (fieldValue == null) {
                jsonElementsMap.put(fieldName, null);
            } else if (field.getType().equals(String.class) || field.getType().equals(Character.class)
                    || field.getType().equals(Byte.class) ||
                    field.getType().equals(Float.class) || field.getType().equals(Double.class)
                    || field.getType().equals(Boolean.class) || field.getType().isEnum()) {
                jsonElementsMap.put(fieldName, fieldValue);
            } else if (field.getType().equals(Integer.class) || field.getType().equals(Long.class)
                    || field.getType().isEnum()) {
                jsonElementsMap.put(fieldName, fieldValue);
            } else if (field.getType().equals(UUID.class)) {
                jsonElementsMap.put(fieldName, fieldValue.toString());
            } else if (field.getType().equals(Order.class) || field.getType().equals(Product.class)) {
                String recusrive = serializer(fieldValue);
                jsonElementsMap.put(fieldName, recusrive);
            } else if (fieldValue instanceof List<?> list) {
                List<String> listJson = new ArrayList<>();
                for (Object item : list) {
                    String itemJson = serializer(item);
                    listJson.add(itemJson);
                }
                jsonElementsMap.put(fieldName, listJson);
            } else if (fieldValue instanceof Map<?, ?> map) {
                List<String> listJson = new ArrayList<>();
                for (Object key : map.keySet()) {
                    Object item = map.get(key);
                    String itemJson = serializer(item);
                    listJson.add(itemJson);
                }
                jsonElementsMap.put(fieldName, listJson);
            } else {
                jsonElementsMap.put(fieldName, fieldValue.toString());
            }
        }
        return jsonElementsMap;
    }
}
