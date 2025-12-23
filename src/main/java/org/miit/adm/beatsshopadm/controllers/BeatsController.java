package org.miit.adm.beatsshopadm.controllers;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/beats")
public class BeatsController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Beats 2 service healthy");
    }

    @GetMapping("/boss-check")
    public ResponseEntity<String> bossCheck() {
        return ResponseEntity.ok("Boss 2 approved: service running");
    }

    @PostMapping("/buy/{beatId}")
    public ResponseEntity<String> buyBeat(@PathVariable String beatId) {
        // Отправляем в RabbitMQ
        rabbitTemplate.convertAndSend("beats.queue",
                "Purchased beat: " + beatId);
        return ResponseEntity.ok("Beat " + beatId + " purchased! Check RabbitMQ");
    }

    @GetMapping("/catalog")
    public ResponseEntity<List<String>> catalog() {
        return ResponseEntity.ok(Arrays.asList(
                "trap-drumkit-01", "808-pack-pro", "melodic-loops-vol2"
        ));
    }
}
