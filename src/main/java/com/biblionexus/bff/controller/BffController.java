package com.biblionexus.bff.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class BffController {

    private final SnsClient snsClient;

    @Value("${aws.sns.topic.arn}")
    private String snsTopicArn;

    @Value("${api.secret:HolaMundo123}")
    private String apiSecret;

    public BffController(SnsClient snsClient) {
        this.snsClient = snsClient;
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/reports")
    public ResponseEntity<Map<String, Object>> generarInforme(
            @RequestHeader(value = "x-api-secret", required = false) String headerSecret,
            @RequestBody(required = false) Map<String, Object> body) {

        Map<String, Object> response = new HashMap<>();

        if (headerSecret == null || !headerSecret.equals(apiSecret)) {
            response.put("ok", false);
            response.put("error", "Acceso no autorizado: API_SECRET inválido");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        try {
            String username = body != null && body.containsKey("username") ? body.get("username").toString() : "AdminUser";
            String userId = body != null && body.containsKey("userId") ? body.get("userId").toString() : "usr-001";

            String payload = String.format(
                "{\"type\": \"informe\", \"username\": \"%s\", \"userId\": \"%s\", \"createdAt\": \"%s\"}",
                username, userId, java.time.Instant.now().toString()
            );

            PublishRequest request = PublishRequest.builder()
                    .message(payload)
                    .topicArn(snsTopicArn)
                    .build();

            PublishResponse result = snsClient.publish(request);

            response.put("ok", true);
            response.put("messageId", result.messageId());
            response.put("detail", "Evento de informe publicado en SNS");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("ok", false);
            response.put("error", e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
// prueba   