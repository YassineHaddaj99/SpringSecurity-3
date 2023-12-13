package com.example.docsWebService.service;


import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class DocSenderService {

   private final RestTemplate restTemplate;


    public String uploadFile(MultipartFile file, MultiValueMap<String, Object> additionalFormParams) {
        String url = "http://localhost:8081/receiver/store";
        MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File not provided. Please provide a valid file.");
        }

        try {
            parts.add("file", new ByteArrayResource(file.getBytes()) {
                @Override
                public String getFilename() {
                    return file.getOriginalFilename();
                }
            });
        } catch (IOException e) {
            throw new RuntimeException("Failed to read the file data.", e);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(parts, headers);
           restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

            return "File uploaded successfully";

    }
}
