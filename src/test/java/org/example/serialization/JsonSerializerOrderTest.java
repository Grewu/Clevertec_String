package org.example.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class JsonSerializerOrderTest {
    @Mock
    private JsonSerializer jsonSerializer;
    @Mock
    private ObjectMapper objectMapper;
}
