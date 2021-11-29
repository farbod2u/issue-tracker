package com.pinguinag.issuetracker.service;

import com.pinguinag.issuetracker.entity.Developer;
import com.pinguinag.issuetracker.repository.DeveloperRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeveloperService {

    private final DeveloperRepository developerRepository;

    public List<Developer> getAll(){
        return developerRepository.findAll();
    }

    public Developer get(Integer id){
        return developerRepository.findById(id).get();
    }

    public Developer getByName(String name){
        return developerRepository.findByName(name);
    }
}
