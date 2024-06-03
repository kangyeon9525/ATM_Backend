package com.example.ATM_Backend.ai.controller;

import com.example.ATM_Backend.jwt.JwtTokenProvider;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

@RestController
@RequiredArgsConstructor
public class FlaskController {

    private final String flaskUrl = "http://127.0.0.1:5000/get";
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/flask-api")
    public ResponseEntity<String> getFlaskResponse(@RequestHeader("Authorization") String token) {
        try {
            // JWT 토큰에서 사용자 이름 추출
            String userPK = jwtTokenProvider.getUserPK(token.substring(7)); // "Bearer "를 제거한 토큰에서 사용자명 추출

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(0, new org.springframework.http.converter.StringHttpMessageConverter(StandardCharsets.UTF_8));

            // Flask 서버에 요청을 보낼 때 현재 사용자의 이름을 포함하여 요청
            String response = restTemplate.getForObject(flaskUrl + "?username=" + userPK, String.class);
            System.out.println("Received from Flask: " + response);  // 로깅

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response);

            String advice = root.path("advice").asText();
            String result = String.format("%s", advice);

            return new ResponseEntity<>(result, HttpStatus.OK);


        } catch (Exception e) {
            e.printStackTrace();  // 오류가 발생한 경우 스택 트레이스를 출력하여 문제 파악
            return new ResponseEntity<>("Flask API 응답 처리 중 오류가 발생했습니다: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}