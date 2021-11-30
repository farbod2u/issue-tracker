package com.pinguinag.issuetracker.repository;

import com.pinguinag.issuetracker.entity.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StoryRepository extends JpaRepository<Story, Long> {
    @Query("select s from Story as s where s.developer.id = ?1")
    List<Story> getDeveloperStories(Integer developerId);
}
