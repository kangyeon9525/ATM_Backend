package com.example.ATM_Backend.ai.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

@RestController
public class FlaskController {

    private final String flaskUrl = "http://127.0.0.1:5000/get";

    @GetMapping("/flask-api")
    public ResponseEntity<String> getFlaskResponse() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            // Ensure RestTemplate uses UTF-8 encoding
            restTemplate.getMessageConverters().add(0, new org.springframework.http.converter.StringHttpMessageConverter(StandardCharsets.UTF_8));

            String response = restTemplate.getForObject(flaskUrl, String.class);

            // JSON 응답 파싱
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response);

            // predict_time 추출
            double predictTime = root.path("predict_time").asDouble();

            // advice 추출
            String advice = root.path("advice").asText();

            // 사람이 이해할 수 있는 형식으로 변환
            String result = String.format("예상 사용 시간: %.1f 시간\n조언: %s", predictTime, advice);

            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("Error processing Flask API response", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
