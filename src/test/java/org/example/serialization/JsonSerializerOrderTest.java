package org.example.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entity.Order;
import org.example.util.TestDataProduct;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.InvocationTargetException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JsonSerializerOrderTest {
    @Mock
    private JsonSerializer jsonSerializer;
    @Mock
    private ObjectMapper objectMapper;

    @Test
    void serializer() throws JsonProcessingException, IllegalAccessException {
        Order order = TestDataProduct.builder().build().buildOrder();
        when(jsonSerializer.serializer(order)).thenReturn("Expected JSON String");
        when(objectMapper.writeValueAsString(order)).thenReturn("Expected JSON String");
        String actual = jsonSerializer.serializer(order);
        String expected = objectMapper.writeValueAsString(order);
        assertEquals(expected, actual);
    }

    @Test
    void serializerWithEmptyObject() throws IllegalAccessException {
        Order expected = TestDataProduct.builder().build().buildOrder();
        String actual = jsonSerializer.serializer(expected);
        assertNull(actual);
    }

    private static Stream<Arguments> orderProvider() {
        return Stream.of(
                Arguments.of(
                        TestDataProduct.builder().build().buildOrder(),
                        "Expected Order"
                ),
                Arguments.of(
                        TestDataProduct.builder().build().setUuid(null).buildOrder(),
                        "Expected Order with null"
                ),
                Arguments.of(
                        TestDataProduct.builder().build().setFirstName(null).buildOrder(),
                        "Expected Order with firstName null"
                ),
                Arguments.of(
                        TestDataProduct.builder().build().setLastName(null).buildOrder(),
                        "Expected Order with lastName null"
                ),
                Arguments.of(
                        TestDataProduct.builder().build().setDateBirth(null).buildOrder(),
                        "Expected Order with dateBirth null"
                ),
                Arguments.of(
                        TestDataProduct.builder().build().setOrders(null).buildOrder(),
                        "Expected Order with orders null"
                )
        );
    }

    @ParameterizedTest
    @MethodSource("orderProvider")
    void serializer(Order product, String expectedJson) throws IllegalAccessException {
        when(jsonSerializer.serializer(product)).thenReturn(expectedJson);
        String actual = jsonSerializer.serializer(product);
        assertEquals(expectedJson, actual);
    }

    @Test
    void serializerWithUUIDisNull() throws JsonProcessingException, IllegalAccessException {
        Order order = TestDataProduct.builder().build().setUuid(null).buildOrder();
        when(jsonSerializer.serializer(order)).thenReturn("Expected JSON String");
        when(objectMapper.writeValueAsString(order)).thenReturn("Expected JSON String");
        String actual = jsonSerializer.serializer(order);
        String expected = objectMapper.writeValueAsString(order);
        assertEquals(expected, actual);
    }

    @Test
    void serializerWithFirstNameIsNull() throws JsonProcessingException, IllegalAccessException {
        Order order = TestDataProduct.builder().build().setFirstName(null).buildOrder();
        when(jsonSerializer.serializer(order)).thenReturn("Expected JSON String");
        when(objectMapper.writeValueAsString(order)).thenReturn("Expected JSON String");
        String actual = jsonSerializer.serializer(order);
        String expected = objectMapper.writeValueAsString(order);
        assertEquals(expected, actual);
    }

    @Test
    void serializerWithLastNameIsNull() throws JsonProcessingException, IllegalAccessException {
        Order order = TestDataProduct.builder().build().setLastName(null).buildOrder();
        when(jsonSerializer.serializer(order)).thenReturn("Expected JSON String");
        when(objectMapper.writeValueAsString(order)).thenReturn("Expected JSON String");
        String actual = jsonSerializer.serializer(order);
        String expected = objectMapper.writeValueAsString(order);
        assertEquals(expected, actual);
    }

    @Test
    void serializerWithDateBirthIsNull() throws JsonProcessingException, IllegalAccessException {
        Order order = TestDataProduct.builder().build().setDateBirth(null).buildOrder();
        when(jsonSerializer.serializer(order)).thenReturn("Expected JSON String");
        when(objectMapper.writeValueAsString(order)).thenReturn("Expected JSON String");
        String actual = jsonSerializer.serializer(order);
        String expected = objectMapper.writeValueAsString(order);
        assertEquals(expected, actual);
    }

    @Test
    void serializerWithOrdersIsNull() throws JsonProcessingException, IllegalAccessException {
        Order order = TestDataProduct.builder().build().setOrders(null).buildOrder();
        when(jsonSerializer.serializer(order)).thenReturn("Expected JSON String");
        when(objectMapper.writeValueAsString(order)).thenReturn("Expected JSON String");
        String actual = jsonSerializer.serializer(order);
        String expected = objectMapper.writeValueAsString(order);
        assertEquals(expected, actual);
    }


    @Test
    void deSerializer() throws JsonProcessingException, IllegalAccessException, InvocationTargetException,
            NoSuchMethodException, InstantiationException {
        Order order = TestDataProduct.builder().build().buildOrder();
        String actual = jsonSerializer.serializer(order);
        String expected = objectMapper.writeValueAsString(order);
        Order productActual = jsonSerializer.deSerializer(actual, Order.class);
        Order productExpected = objectMapper.readValue(expected, Order.class);
        assertEquals(productExpected, productActual);
    }

    @Test
    void deSerializerWithEmptyObject() throws IllegalAccessException, InvocationTargetException,
            NoSuchMethodException, InstantiationException {
        Order expected = TestDataProduct.builder().build().buildOrder();
        String actual = jsonSerializer.serializer(expected);
        Order order = jsonSerializer.deSerializer(actual, Order.class);
        assertNull(order);
    }

    @Test
    void deSerializerWithUUIDisNull() throws JsonProcessingException, IllegalAccessException, InvocationTargetException,
            NoSuchMethodException, InstantiationException {
        Order order = TestDataProduct.builder().build().setUuid(null).buildOrder();
        String actual = jsonSerializer.serializer(order);
        String expected = objectMapper.writeValueAsString(order);
        Order productActual = jsonSerializer.deSerializer(actual, Order.class);
        Order productExpected = objectMapper.readValue(expected, Order.class);
        assertEquals(productExpected, productActual);
    }

    @Test
    void deSerializerWithFirstNameIsNull() throws JsonProcessingException, IllegalAccessException, InvocationTargetException,
            NoSuchMethodException, InstantiationException {
        Order order = TestDataProduct.builder().build().setFirstName(null).buildOrder();
        String actual = jsonSerializer.serializer(order);
        String expected = objectMapper.writeValueAsString(order);
        Order productActual = jsonSerializer.deSerializer(actual, Order.class);
        Order productExpected = objectMapper.readValue(expected, Order.class);
        assertEquals(productExpected, productActual);
    }

    @Test
    void deSerializerWithLastNameIsNull() throws JsonProcessingException, IllegalAccessException, InvocationTargetException,
            NoSuchMethodException, InstantiationException {
        Order order = TestDataProduct.builder().build().setLastName(null).buildOrder();
        String actual = jsonSerializer.serializer(order);
        String expected = objectMapper.writeValueAsString(order);
        Order productActual = jsonSerializer.deSerializer(actual, Order.class);
        Order productExpected = objectMapper.readValue(expected, Order.class);
        assertEquals(productExpected, productActual);
    }

    @Test
    void deSerializerWithDateBirthIsNull() throws JsonProcessingException, IllegalAccessException, InvocationTargetException,
            NoSuchMethodException, InstantiationException {
        Order order = TestDataProduct.builder().build().setDateBirth(null).buildOrder();
        String actual = jsonSerializer.serializer(order);
        String expected = objectMapper.writeValueAsString(order);
        Order productActual = jsonSerializer.deSerializer(actual, Order.class);
        Order productExpected = objectMapper.readValue(expected, Order.class);
        assertEquals(productExpected, productActual);
    }

    @Test
    void deSerializerWithOrdersIsNull() throws JsonProcessingException, IllegalAccessException, InvocationTargetException,
            NoSuchMethodException, InstantiationException {
        Order order = TestDataProduct.builder().build().setOrders(null).buildOrder();
        String actual = jsonSerializer.serializer(order);
        String expected = objectMapper.writeValueAsString(order);
        Order productActual = jsonSerializer.deSerializer(actual, Order.class);
        Order productExpected = objectMapper.readValue(expected, Order.class);
        assertEquals(productExpected, productActual);
    }

}
