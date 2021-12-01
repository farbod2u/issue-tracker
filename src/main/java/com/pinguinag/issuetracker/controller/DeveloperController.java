package com.pinguinag.issuetracker.controller;

import com.pinguinag.issuetracker.entity.Developer;
import com.pinguinag.issuetracker.service.DeveloperService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/developers")
public class DeveloperController {

    private final DeveloperService developerService;

    /***/
    @GetMapping
    public ResponseEntity<List<Developer>> getAll() {
        try {
            return new ResponseEntity<>(developerService.getAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Developer> get(@PathVariable Integer id) {
        try {
            return new ResponseEntity<>(developerService.get(id), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Developer> save(@RequestBody Developer entity) {
        try {
            return new ResponseEntity<>(developerService.save(entity), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping
    public ResponseEntity<Developer> update(@RequestBody Developer entity) {
        try {
            return new ResponseEntity<>(developerService.update(entity), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
