package com.pinguinag.issuetracker.service;

import com.pinguinag.issuetracker.entity.Developer;
import com.pinguinag.issuetracker.repository.DeveloperRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

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

    public Developer save(Developer entity){
        return developerRepository.save(entity);
    }

    @Transactional(rollbackOn = Exception.class)
    public Developer update(Developer entity) throws Exception {
        Optional<Developer> byId = developerRepository.findById(entity.getId());

        if(byId.isPresent()){
            Developer originalEntity = byId.get();
            originalEntity.setName(entity.getName());
            return originalEntity;
        }
        else
            throw new Exception("Entity not found");
    }
    
}
