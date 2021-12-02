package com.pinguinag.issuetracker.repository;

import com.pinguinag.issuetracker.entity.Bug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BugRepository extends JpaRepository<Bug, Long> {
    @Query("select b from Bug as b where b.developer.id = ?1 ")
    List<Bug> getDeveloperBugs(Integer developerId);
}
