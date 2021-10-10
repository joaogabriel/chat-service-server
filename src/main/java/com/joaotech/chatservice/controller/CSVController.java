package com.joaotech.chatservice.controller;

import com.joaotech.chatservice.service.CSVService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/csv")
@AllArgsConstructor
public class CSVController {

    private final CSVService csvService;

    @GetMapping
    public ResponseEntity<String> generate() {

        try {

            csvService.generate();

            return ResponseEntity.ok("generated");

        } catch (Throwable exception) {

            exception.printStackTrace();

            return ResponseEntity.ok("error, check logs");

        }

    }

}
