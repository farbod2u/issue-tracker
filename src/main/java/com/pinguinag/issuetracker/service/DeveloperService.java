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

    public List<Developer> getAll() throws RuntimeException {
        List<Developer> res = developerRepository.findAll();
        if (res != null && res.size() > 0)
            return res;
        else
            throw new RuntimeException("developers is empty");
    }

    public Developer get(Integer id) {
        Optional<Developer> res = developerRepository.findById(id);
        if (res.isPresent())
            return res.get();
        else
            throw new RuntimeException("developer not found");
    }

    public Developer getByName(String name) {
        return developerRepository.findByName(name);
    }

    public Developer save(Developer entity) {
        return developerRepository.save(entity);
    }

    @Transactional(rollbackOn = Exception.class)
    public Developer update(Developer entity) {
        Developer originalEntity = this.get(entity.getId());
        originalEntity.setName(entity.getName());
        return originalEntity;
    }

}
