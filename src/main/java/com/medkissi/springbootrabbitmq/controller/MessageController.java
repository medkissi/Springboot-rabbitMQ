package com.medkissi.springbootrabbitmq.controller;

import com.medkissi.springbootrabbitmq.dto.User;
import com.medkissi.springbootrabbitmq.publisher.RabbitMQProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class MessageController {
    private RabbitMQProducer producer;
    public MessageController(RabbitMQProducer rabbitMQProducer){
       this.producer = rabbitMQProducer;

    }

    @GetMapping("/publish")
    public ResponseEntity<String> sendMessage(@RequestParam("message") String message){
        producer.sendMessage(message);
        return ResponseEntity.ok("Message published");

    }
}
