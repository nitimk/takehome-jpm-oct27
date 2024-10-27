package com.example.demo.controller;

import com.example.demo.model.Message;
import com.example.demo.repository.MessageRepository;
import com.example.demo.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping
    public ResponseEntity<List<Message>> getAllMessages() {
        try {
            List<Message> messages = messageRepository.findAll();
            return ResponseEntity.ok(messages);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    // Add a message (for testing)
    @PostMapping
    public ResponseEntity<Message> addMessage(@RequestBody Message message) {
        try {
            if (message.getId() == null) {
                message.setId(UUID.randomUUID());
            }
            Message savedMessage = messageRepository.save(message);
            // Prepare the payload for Kafka publish
            String kafkaPublishUrl = "http://localhost:8080/api/kafka/publish";
            Map<String, String> kafkaMessage = new HashMap<>();
            kafkaMessage.put("message", "Message with text '" + savedMessage.getMessage() + "' was added to Cassandra");

            // Send the request to KafkaController
            ResponseEntity<String> kafkaResponse = restTemplate.postForEntity(kafkaPublishUrl, kafkaMessage, String.class);

            if (kafkaResponse.getStatusCode().is2xxSuccessful()) {
                return ResponseEntity.ok(savedMessage);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(savedMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    // Get by ID
    @GetMapping("/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable UUID id) {
        try {
            return messageRepository.findById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}