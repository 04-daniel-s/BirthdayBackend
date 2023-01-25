package de.daniel.birthdaybackend.service;

import de.daniel.birthdaybackend.controller.entity.Log;
import de.daniel.birthdaybackend.repository.LogRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class LogService {
    LogRepository repository;

    public List<Log> getLogs() {
        List<Log> logs = new ArrayList<>();
        repository.findAll().forEach(v -> logs.add(v));
        logs.sort((a,b)->Math.round(b.getDate().getTime()-a.getDate().getTime()));
        return logs;
    }
}
