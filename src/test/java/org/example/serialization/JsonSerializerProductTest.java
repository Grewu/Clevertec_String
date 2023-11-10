package org.example.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entity.Product;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JsonSerializerProductTest {
    @Mock
    private JsonSerializer jsonSerializer;
    @Mock
    private ObjectMapper objectMapper;

    @Test
    void serializer() throws JsonProcessingException, IllegalAccessException {
        Product product = TestDataProduct.builder().build().buildProduct();
        when(jsonSerializer.serializer(product)).thenReturn("Expected JSON String");
        when(objectMapper.writeValueAsString(product)).thenReturn("Expected JSON String");
        String actual = jsonSerializer.serializer(product);
        String expected = objectMapper.writeValueAsString(product);
        assertEquals(expected, actual);
    }

    @Test
    void serializerWithEmptyObject() throws IllegalAccessException {
        Product expected = TestDataProduct.builder().build().buildEmptyProduct();
        String actual = jsonSerializer.serializer(expected);
        assertNull(actual);
    }

    private static Stream<Arguments> productProvider() {
        return Stream.of(
                Arguments.of(
                        TestDataProduct.builder().build().buildProduct(),
                        "Expected Product"
                ),
                Arguments.of(
                        TestDataProduct.builder().build().setUuid(null).buildProduct(),
                        "Expected Product with null"
                ),
                Arguments.of(
                        TestDataProduct.builder().build().setName(null).buildProduct(),
                        "Expected Product with name null"
                ),
                Arguments.of(
                        TestDataProduct.builder().build().setPrice(null).buildProduct(),
                        "Expected Product with price null"
                )
        );
    }

    @ParameterizedTest
    @MethodSource("productProvider")
    void serializer(Product product, String expectedJson) throws IllegalAccessException {
        when(jsonSerializer.serializer(product)).thenReturn(expectedJson);
        String actual = jsonSerializer.serializer(product);
        assertEquals(expectedJson, actual);
    }

    @Test
    void serializerWithUUIDisNull() throws JsonProcessingException, IllegalAccessException {
        Product product = TestDataProduct.builder().build().setUuid(null).buildProduct();
        when(jsonSerializer.serializer(product)).thenReturn("Expected JSON String");
        when(objectMapper.writeValueAsString(product)).thenReturn("Expected JSON String");
        String actual = jsonSerializer.serializer(product);
        String expected = objectMapper.writeValueAsString(product);
        assertEquals(expected, actual);
    }

    @Test
    void serializerWithNameIsNull() throws JsonProcessingException, IllegalAccessException {
        Product product = TestDataProduct.builder().build().setName(null).buildProduct();
        when(jsonSerializer.serializer(product)).thenReturn("Expected JSON String");
        when(objectMapper.writeValueAsString(product)).thenReturn("Expected JSON String");
        String actual = jsonSerializer.serializer(product);
        String expected = objectMapper.writeValueAsString(product);
        assertEquals(expected, actual);
    }

    @Test
    void serializerWithPriceIsNull() throws JsonProcessingException, IllegalAccessException {
        Product product = TestDataProduct.builder().build().setPrice(null).buildProduct();
        when(jsonSerializer.serializer(product)).thenReturn("Expected JSON String");
        when(objectMapper.writeValueAsString(product)).thenReturn("Expected JSON String");
        String actual = jsonSerializer.serializer(product);
        String expected = objectMapper.writeValueAsString(product);
        assertEquals(expected, actual);
    }

    @Test
    void deSerializer() throws JsonProcessingException, IllegalAccessException, InvocationTargetException,
            NoSuchMethodException, InstantiationException {
        Product product = TestDataProduct.builder().build().buildProduct();
        String actual = jsonSerializer.serializer(product);
        String expected = objectMapper.writeValueAsString(product);
        Product productActual = jsonSerializer.deSerializer(actual, Product.class);
        Product productExpected = objectMapper.readValue(expected, Product.class);
        assertEquals(productExpected, productActual);
    }

    @Test
    void deSerializerWithUUIDisNull() throws JsonProcessingException, IllegalAccessException, InvocationTargetException,
            NoSuchMethodException, InstantiationException {
        Product product = TestDataProduct.builder().build().setUuid(null).buildProduct();
        String actual = jsonSerializer.serializer(product);
        String expected = objectMapper.writeValueAsString(product);
        Product productActual = jsonSerializer.deSerializer(actual, Product.class);
        Product productExpected = objectMapper.readValue(expected, Product.class);
        assertEquals(productExpected, productActual);
    }

    @Test
    void deSerializerWithNameIsNull() throws JsonProcessingException, IllegalAccessException, InvocationTargetException,
            NoSuchMethodException, InstantiationException {
        Product product = TestDataProduct.builder().build().setName(null).buildProduct();
        String actual = jsonSerializer.serializer(product);
        String expected = objectMapper.writeValueAsString(product);
        Product productActual = jsonSerializer.deSerializer(actual, Product.class);
        Product productExpected = objectMapper.readValue(expected, Product.class);
        assertEquals(productExpected, productActual);
    }

    @Test
    void deSerializerWithPriceIsNull() throws JsonProcessingException, IllegalAccessException, InvocationTargetException,
            NoSuchMethodException, InstantiationException {
        Product product = TestDataProduct.builder().build().setPrice(null).buildProduct();
        String actual = jsonSerializer.serializer(product);
        String expected = objectMapper.writeValueAsString(product);
        Product productActual = jsonSerializer.deSerializer(actual, Product.class);
        Product productExpected = objectMapper.readValue(expected, Product.class);
        assertEquals(productExpected, productActual);
    }

}