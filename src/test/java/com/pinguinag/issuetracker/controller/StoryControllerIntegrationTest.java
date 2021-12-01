package com.pinguinag.issuetracker.controller;

import com.pinguinag.issuetracker.entity.Developer;
import com.pinguinag.issuetracker.entity.Story;
import com.pinguinag.issuetracker.repository.DeveloperRepository;
import com.pinguinag.issuetracker.repository.StoryRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StoryControllerIntegrationTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private DeveloperRepository developerRepository;

    @Autowired
    private StoryRepository storyRepository;

    private Developer developer = new Developer(null, "Saeed");

    private List<Story> stories = List.of(
            new Story(null, "t1", "", LocalDateTime.now(), developer, Story.EstimatedPointType.New),
            new Story(null, "t2", "", LocalDateTime.now(), developer, Story.EstimatedPointType.New),
            new Story(null, "t3", "", LocalDateTime.now(), developer, Story.EstimatedPointType.New)
    );

    private

    @BeforeEach
    void setUpAll() {
        developer = developerRepository.save(developer);
        stories = storyRepository.saveAll(stories);
    }

    @AfterEach
    void tearDownAll() {
        storyRepository.deleteAll();
        developerRepository.deleteAll();
    }

    @Test
    void getDeveloperStoriesForWeek() {
        //given
        Integer developerId = developer.getId();
        String dateString = "2021-12-02";
        String url = "/developer-stories/" + developerId + "/" + dateString;

        // when
        ResponseEntity<Story[]> response = testRestTemplate.getForEntity(url, Story[].class);
        List<Story> result = List.of(response.getBody());

        // then
        assertThat(result).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    void assignNewStoryToDevelopr() {
    }

    @Test
    void changeStoryDeveloper() {
    }

    @Test
    void advanceStoryEstimate() {
        //given
        String url = "/advance-estimate/" + stories.get(0).getIssueId();

        // when
        ResponseEntity<Story> response = testRestTemplate.getForEntity(url, Story.class);
        Story result = response.getBody();

        // then
        assertThat(result).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void decreaseStoryEstimate() {
    }
}