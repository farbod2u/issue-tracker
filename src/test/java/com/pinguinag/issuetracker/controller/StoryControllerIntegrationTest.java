package com.pinguinag.issuetracker.controller;

import com.pinguinag.issuetracker.entity.Developer;
import com.pinguinag.issuetracker.entity.Story;
import com.pinguinag.issuetracker.repository.DeveloperRepository;
import com.pinguinag.issuetracker.repository.StoryRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
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
        String url = "/stories/developer-stories/" + developerId + "/" + dateString;

        // when
        ResponseEntity<Story[]> response = testRestTemplate.getForEntity(url, Story[].class);
        List<Story> result = List.of(response.getBody());

        // then
        assertThat(result).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    void assignNewStoryToDevelopr() {
        //given
        Integer developerId = developer.getId();
        String url = "/stories/assign-story-developer/" + developerId;
        Story story = new Story(null, "t1", "", LocalDateTime.now(), null, null);

        HttpEntity<Story> request = new HttpEntity<>(story);

        //when
        ResponseEntity<Story> response = testRestTemplate.postForEntity(url, request, Story.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getIssueId()).isNotNull();
    }

    @Test
    void changeStoryDeveloper() {
        //given
        Developer developer2 = new Developer(null, "developer2");
        developerRepository.save(developer2);
        Integer developerId = developer2.getId();
        Long issueId = stories.get(0).getIssueId();
        String url = "/stories/change-story-developer/" + issueId + "/" + developerId;

        //when
        ResponseEntity<Story> response = testRestTemplate.exchange(url, HttpMethod.PUT, null, Story.class);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getDeveloper()).isEqualTo(developer2);
    }

    @Test
    void advanceStoryEstimate() {
        //given
        Long issueId = stories.get(0).getIssueId();
        String url = "/stories/advance-estimate/" + issueId;

        // when
        ResponseEntity<Story> response = testRestTemplate.getForEntity(url, Story.class);
        Story result = response.getBody();

        // then
        assertThat(result).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void decreaseStoryEstimate() {
        //given
        Long issueId = stories.get(0).getIssueId();
        String url = "/stories/decrease-estimate/" + issueId;

        // when
        ResponseEntity<Story> response = testRestTemplate.getForEntity(url, Story.class);
        Story result = response.getBody();

        // then
        assertThat(result).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}