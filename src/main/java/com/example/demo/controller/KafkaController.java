package com.example.demo.controller;

import com.example.demo.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class KafkaController {

    @Autowired
    private KafkaProducerService producerService;

    @PostMapping("/api/kafka/publish")
    public ResponseEntity<String> sendMessageToKafkaTopic(@RequestBody Map<String, String> payload) {
        String message = payload.get("message");
        
        if (message == null || message.isEmpty()) {
            return ResponseEntity.badRequest().body("Message cannot be empty");
        }

        try {
            producerService.sendMessage(message);
            return ResponseEntity.ok("Message sent to Kafka topic successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to send message to Kafka topic: " + e.getMessage());
        }
    }
}