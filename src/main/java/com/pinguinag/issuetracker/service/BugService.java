package com.pinguinag.issuetracker.service;

import com.pinguinag.issuetracker.entity.Bug;
import com.pinguinag.issuetracker.repository.BugRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BugService {

    private final BugRepository bugRepository;

    public List<Bug> getAll(){
        return bugRepository.findAll();
    }

    public Bug get(Long id){
        return bugRepository.findById(id).get();
    }

    public Bug save(Bug entity){
        return bugRepository.save(entity);
    }

}
