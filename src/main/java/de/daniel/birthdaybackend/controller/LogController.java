package de.daniel.birthdaybackend.controller;

import de.daniel.birthdaybackend.controller.entity.Log;
import de.daniel.birthdaybackend.service.LogService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@AllArgsConstructor
@Data
public class LogController {

    LogService service;

    @GetMapping("/logs")
    public ResponseEntity<List<Log>> getLogs() {
        return ResponseEntity.ok(service.getLogs());
    }
}
