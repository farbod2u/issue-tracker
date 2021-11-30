package com.pinguinag.issuetracker.service;

import com.pinguinag.issuetracker.entity.Story;
import com.pinguinag.issuetracker.repository.StoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoryService {

    private final StoryRepository storyRepository;

    public List<Story> getAll(){
        return storyRepository.findAll();
    }

    public Story get(Long id){
        return storyRepository.findById(id).get();
    }

    public Story save(Story entity){
        return storyRepository.save(entity);
    }
}
