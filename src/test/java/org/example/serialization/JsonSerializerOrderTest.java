package org.example.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entity.Order;
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
                        TestDataProduct.builder().build().setProductsList(null).buildOrder(),
                        "Expected Order with products null"
                ),
                Arguments.of(
                        TestDataProduct.builder().build().setOffsetDateTime(null).buildOrder(),
                        "Expected Order with OffsetDateTime null"
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
        Order product = TestDataProduct.builder().build().setUuid(null).buildOrder();
        when(jsonSerializer.serializer(product)).thenReturn("Expected JSON String");
        when(objectMapper.writeValueAsString(product)).thenReturn("Expected JSON String");
        String actual = jsonSerializer.serializer(product);
        String expected = objectMapper.writeValueAsString(product);
        assertEquals(expected, actual);
    }

    @Test
    void serializerWithProductsIsNull() throws JsonProcessingException, IllegalAccessException {
        Order product = TestDataProduct.builder().build().setProducts(null).buildOrder();
        when(jsonSerializer.serializer(product)).thenReturn("Expected JSON String");
        when(objectMapper.writeValueAsString(product)).thenReturn("Expected JSON String");
        String actual = jsonSerializer.serializer(product);
        String expected = objectMapper.writeValueAsString(product);
        assertEquals(expected, actual);
    }

    @Test
    void serializerWithCreateDateIsNull() throws JsonProcessingException, IllegalAccessException {
        Order product = TestDataProduct.builder().build().setOffsetDateTime(null).buildOrder();
        when(jsonSerializer.serializer(product)).thenReturn("Expected JSON String");
        when(objectMapper.writeValueAsString(product)).thenReturn("Expected JSON String");
        String actual = jsonSerializer.serializer(product);
        String expected = objectMapper.writeValueAsString(product);
        assertEquals(expected, actual);
    }

    @Test
    void deSerializer() throws JsonProcessingException, IllegalAccessException, InvocationTargetException,
            NoSuchMethodException, InstantiationException {
        Order product = TestDataProduct.builder().build().buildOrder();
        String actual = jsonSerializer.serializer(product);
        String expected = objectMapper.writeValueAsString(product);
        Order productActual = jsonSerializer.deSerializer(actual, Order.class);
        Order productExpected = objectMapper.readValue(expected, Order.class);
        assertEquals(productExpected, productActual);
    }

    @Test
    void deSerializerWithUUIDisNull() throws JsonProcessingException, IllegalAccessException, InvocationTargetException,
            NoSuchMethodException, InstantiationException {
        Order product = TestDataProduct.builder().build().setUuid(null).buildOrder();
        String actual = jsonSerializer.serializer(product);
        String expected = objectMapper.writeValueAsString(product);
        Order productActual = jsonSerializer.deSerializer(actual, Order.class);
        Order productExpected = objectMapper.readValue(expected, Order.class);
        assertEquals(productExpected, productActual);
    }

    @Test
    void deSerializerWithProductsIsNull() throws JsonProcessingException, IllegalAccessException, InvocationTargetException,
            NoSuchMethodException, InstantiationException {
        Order product = TestDataProduct.builder().build().setProducts(null).buildOrder();
        String actual = jsonSerializer.serializer(product);
        String expected = objectMapper.writeValueAsString(product);
        Order productActual = jsonSerializer.deSerializer(actual, Order.class);
        Order productExpected = objectMapper.readValue(expected, Order.class);
        assertEquals(productExpected, productActual);
    }

    @Test
    void deSerializerWithCreateDateIsNull() throws JsonProcessingException, IllegalAccessException, InvocationTargetException,
            NoSuchMethodException, InstantiationException {
        Order product = TestDataProduct.builder().build().setOffsetDateTime(null).buildOrder();
        String actual = jsonSerializer.serializer(product);
        String expected = objectMapper.writeValueAsString(product);
        Order productActual = jsonSerializer.deSerializer(actual, Order.class);
        Order productExpected = objectMapper.readValue(expected, Order.class);
        assertEquals(productExpected, productActual);
    }

}
