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
    public List<Developer> getAll() throws Exception {
        return developerService.getAll();
    }

    @GetMapping("/{id}")
    public Developer get(@PathVariable Integer id) throws Exception {
        return developerService.get(id);
    }

    @PostMapping
    public Developer save(@RequestBody Developer entity) {
        return developerService.save(entity);
    }

    @PutMapping
    public Developer update(@RequestBody Developer entity) throws Exception {
        return developerService.update(entity);
    }
}
