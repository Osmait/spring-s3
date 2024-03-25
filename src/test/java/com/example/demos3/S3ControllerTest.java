package com.example.demos3;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class S3ControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dymDynamicPropertyRegistry) {
        dymDynamicPropertyRegistry.add("config.aws.s3.url", () -> "http://127.0.0.1:4566");
        dymDynamicPropertyRegistry.add("config.aws.bucket-name", () -> "saul-burgos-s3-bucket");
        dymDynamicPropertyRegistry.add("aws.s3.access-key", () -> "localstack");
        dymDynamicPropertyRegistry.add("aws.s3.secret-key", () -> "localstack");


    }

    @Test
    void upload() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "Java-Logo.png",
                MediaType.IMAGE_PNG_VALUE,
                getClass().getResourceAsStream("/static/Java-Logo.png")
        );
        mockMvc.perform(
                        multipart("/s3")    // Usar multipart() en lugar de post()
                                .file(file)     // Adjuntar el archivo
                                .contentType(MediaType.MULTIPART_FORM_DATA)
                )
                .andExpect(status().isOk());


    }
}