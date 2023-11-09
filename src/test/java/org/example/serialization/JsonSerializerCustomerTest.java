package org.example.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entity.Customer;
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
public class JsonSerializerCustomerTest {
    @Mock
    private JsonSerializer jsonSerializer;
    @Mock
    private ObjectMapper objectMapper;

    @Test
    void serializer() throws JsonProcessingException, IllegalAccessException {
        Customer customer = TestDataProduct.builder().build().buidCustomer();
        when(jsonSerializer.serializer(customer)).thenReturn("Expected JSON String");
        when(objectMapper.writeValueAsString(customer)).thenReturn("Expected JSON String");
        String actual = jsonSerializer.serializer(customer);
        String expected = objectMapper.writeValueAsString(customer);
        assertEquals(expected, actual);
    }

    @Test
    void serializerWithEmptyObject() throws IllegalAccessException {
        Customer expected = TestDataProduct.builder().build().buidCustomer();
        String actual = jsonSerializer.serializer(expected);
        assertNull(actual);
    }

    private static Stream<Arguments> customerProvider() {
        return Stream.of(
                Arguments.of(
                        TestDataProduct.builder().build().buidCustomer(),
                        "Expected Customer"
                ),
                Arguments.of(
                        TestDataProduct.builder().build().setUuid(null).buidCustomer(),
                        "Expected Customer with null"
                ),
                Arguments.of(
                        TestDataProduct.builder().build().setProductsList(null).buidCustomer(),
                        "Expected Customer with products null"
                ),
                Arguments.of(
                        TestDataProduct.builder().build().setOffsetDateTime(null).buidCustomer(),
                        "Expected Customer with OffsetDateTime null"
                )
        );
    }

    @ParameterizedTest
    @MethodSource("customerProvider")
    void serializer(Customer customer, String expectedJson) throws IllegalAccessException {
        when(jsonSerializer.serializer(customer)).thenReturn(expectedJson);
        String actual = jsonSerializer.serializer(customer);
        assertEquals(expectedJson, actual);
    }

    @Test
    void serializerWithUUIDisNull() throws JsonProcessingException, IllegalAccessException {
        Customer customer = TestDataProduct.builder().build().setUuid(null).buidCustomer();
        when(jsonSerializer.serializer(customer)).thenReturn("Expected JSON String");
        when(objectMapper.writeValueAsString(customer)).thenReturn("Expected JSON String");
        String actual = jsonSerializer.serializer(customer);
        String expected = objectMapper.writeValueAsString(customer);
        assertEquals(expected, actual);
    }

    @Test
    void serializerWithProductsIsNull() throws JsonProcessingException, IllegalAccessException {
        Customer customer = TestDataProduct.builder().build().setProducts(null).buidCustomer();
        when(jsonSerializer.serializer(customer)).thenReturn("Expected JSON String");
        when(objectMapper.writeValueAsString(customer)).thenReturn("Expected JSON String");
        String actual = jsonSerializer.serializer(customer);
        String expected = objectMapper.writeValueAsString(customer);
        assertEquals(expected, actual);
    }

    @Test
    void serializerWithCreateDateIsNull() throws JsonProcessingException, IllegalAccessException {
        Customer customer = TestDataProduct.builder().build().setOffsetDateTime(null).buidCustomer();
        when(jsonSerializer.serializer(customer)).thenReturn("Expected JSON String");
        when(objectMapper.writeValueAsString(customer)).thenReturn("Expected JSON String");
        String actual = jsonSerializer.serializer(customer);
        String expected = objectMapper.writeValueAsString(customer);
        assertEquals(expected, actual);
    }

    @Test
    void deSerializer() throws JsonProcessingException, IllegalAccessException, InvocationTargetException,
            NoSuchMethodException, InstantiationException {
        Customer customer = TestDataProduct.builder().build().buidCustomer();
        String actual = jsonSerializer.serializer(customer);
        String expected = objectMapper.writeValueAsString(customer);
        Customer productActual = jsonSerializer.deSerializer(actual, Customer.class);
        Customer productExpected = objectMapper.readValue(expected, Customer.class);
        assertEquals(productExpected, productActual);
    }

    @Test
    void deSerializerWithEmptyObject() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException {
        Customer expected = TestDataProduct.builder().build().buidCustomer();
        String actual = jsonSerializer.serializer(expected);
        Customer customer = jsonSerializer.deSerializer(actual, Customer.class);
        assertNull(customer);
    }

    @Test
    void deSerializerWithUUIDisNull() throws JsonProcessingException, IllegalAccessException, InvocationTargetException,
            NoSuchMethodException, InstantiationException {
        Customer customer = TestDataProduct.builder().build().setUuid(null).buidCustomer();
        String actual = jsonSerializer.serializer(customer);
        String expected = objectMapper.writeValueAsString(customer);
        Customer productActual = jsonSerializer.deSerializer(actual, Customer.class);
        Customer productExpected = objectMapper.readValue(expected, Customer.class);
        assertEquals(productExpected, productActual);
    }

    @Test
    void deSerializerWithProductsIsNull() throws JsonProcessingException, IllegalAccessException, InvocationTargetException,
            NoSuchMethodException, InstantiationException {
        Customer customer = TestDataProduct.builder().build().setProducts(null).buidCustomer();
        String actual = jsonSerializer.serializer(customer);
        String expected = objectMapper.writeValueAsString(customer);
        Customer productActual = jsonSerializer.deSerializer(actual, Customer.class);
        Customer productExpected = objectMapper.readValue(expected, Customer.class);
        assertEquals(productExpected, productActual);
    }

    @Test
    void deSerializerWithCreateDateIsNull() throws JsonProcessingException, IllegalAccessException, InvocationTargetException,
            NoSuchMethodException, InstantiationException {
        Customer customer = TestDataProduct.builder().build().setOffsetDateTime(null).buidCustomer();
        String actual = jsonSerializer.serializer(customer);
        String expected = objectMapper.writeValueAsString(customer);
        Customer productActual = jsonSerializer.deSerializer(actual, Customer.class);
        Customer productExpected = objectMapper.readValue(expected, Customer.class);
        assertEquals(productExpected, productActual);
    }
}
