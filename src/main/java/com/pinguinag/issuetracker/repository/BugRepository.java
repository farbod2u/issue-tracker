package com.pinguinag.issuetracker.repository;

import com.pinguinag.issuetracker.entity.Bug;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BugRepository extends JpaRepository<Bug, Long> {
}
