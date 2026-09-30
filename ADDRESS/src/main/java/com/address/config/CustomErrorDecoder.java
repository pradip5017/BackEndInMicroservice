package com.address.config;



import com.address.exception.CustomeException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.core.type.TypeReference;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class CustomErrorDecoder implements ErrorDecoder {

    private final JsonMapper objectMapper = JsonMapper.builder()
            .findAndAddModules()
            .build();

    @Override
    public Exception decode(String methodKey, Response response) {
        HttpStatus status = HttpStatus.resolve(response.status());
        if (status == null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        if (response.body() == null) {
            return new CustomeException("Employee service returned an empty error response.", status);
        }

        try (InputStream inputStream = response.body().asInputStream()) {
            Map<String, String> errorResponse = objectMapper.readValue(
                    inputStream,
                    new TypeReference<Map<String, String>>() {}
            );
            String message = errorResponse.get("message");
            if (message == null || message.isBlank()) {
                message = "Employee service request failed.";
            }
            return new CustomeException(message, status);
        } catch (IOException e) {
            return new CustomeException("Employee service returned an unreadable error response.", status);
        }
    }
}
