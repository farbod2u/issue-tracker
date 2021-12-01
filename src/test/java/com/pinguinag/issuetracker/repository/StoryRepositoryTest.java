package com.pinguinag.issuetracker.repository;

import com.pinguinag.issuetracker.entity.Developer;
import com.pinguinag.issuetracker.entity.Story;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class StoryRepositoryTest {

    @Autowired
    StoryRepository storyRepository;

    @Autowired
    DeveloperRepository developerRepository;

    @AfterEach
    void teardown() {
        storyRepository.deleteAll();
        developerRepository.deleteAll();
    }

    @Test
    void getDeveloperStories() {
        //given
        Developer developer = new Developer(null, "Saeed");
        developer = developerRepository.save(developer);
        Integer developerId = developer.getId();

        List<Story> expected = new ArrayList<>();
        expected.add(storyRepository.save(new Story(null, "t1", "",
                LocalDateTime.now(), developer, Story.EstimatedPointType.New)));
        expected.add(storyRepository.save(new Story(null, "t2", "",
                LocalDateTime.now(), developer, Story.EstimatedPointType.New)));
        expected.add(storyRepository.save(new Story(null, "t3", "",
                LocalDateTime.now(), developer, Story.EstimatedPointType.New)));

        //when
        List<Story> result = storyRepository.getDeveloperStories(developerId);

        //then
        assertThat(result).isNotNull()
                .isEqualTo(expected);


    }
}