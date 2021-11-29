package com.pinguinag.issuetracker.repository;

import com.pinguinag.issuetracker.entity.Story;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoryRepository extends JpaRepository<Story, Long> {
}
