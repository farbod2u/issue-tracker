package com.pinguinag.issuetracker.repository;

import com.pinguinag.issuetracker.entity.Developer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DeveloperRepository extends JpaRepository<Developer, Integer> {
    @Query("from Developer where name = ?1")
    Developer findByName(String name);
}
