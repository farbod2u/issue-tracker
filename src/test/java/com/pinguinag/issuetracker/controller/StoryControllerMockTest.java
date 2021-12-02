package com.pinguinag.issuetracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pinguinag.issuetracker.entity.Developer;
import com.pinguinag.issuetracker.entity.Story;
import com.pinguinag.issuetracker.service.StoryService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Delayed;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StoryController.class)
class StoryControllerMockTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StoryService storyService;

    @SneakyThrows
    @Test
    void getDeveloperStoriesForWeek() {
        // given
        var developer = new Developer(1, "developer1");
        var developerId = developer.getId();
        String dateString = "2021-12-02";
        String url = "/stories/developer-stories/" + developerId + "/" + dateString;

        var stories = List.of(
                new Story(1L, "title1", "", LocalDateTime.now(), developer, Story.EstimatedPointType.Completed),
                new Story(1L, "title1", "", LocalDateTime.now(), developer, Story.EstimatedPointType.Estimated),
                new Story(1L, "title1", "", LocalDateTime.now(), developer, Story.EstimatedPointType.New)
        );
        given(storyService.getDeveloperStoriesForWeek(developerId, dateString)).willReturn(stories);

        // when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andExpect(status().isOk())
                .andReturn();

        String actualResult = mvcResult.getResponse().getContentAsString();
        String expectedResult = objectMapper.writeValueAsString(stories);

        // then
        assertThat(actualResult).isEqualTo(expectedResult);
    }


    @SneakyThrows
    @Test
    void assignNewStoryToDevelopr() {
        // given
        var developer = new Developer(1, "developer1");
        var developerId = developer.getId();
        String url = "/stories/assign-story-developer/" + developerId;

        var story = new Story(1L, "t1", "", LocalDateTime.now(), developer, Story.EstimatedPointType.Estimated);
        given(storyService.assignNewStoryToDeveloper(story, developerId)).willReturn(story);

        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(story)))
                .andExpect(status().isCreated())
                .andReturn();

        String result = mvcResult.getResponse().getContentAsString();
        String expected = objectMapper.writeValueAsString(story);

        //then
        assertThat(result).isEqualTo(expected);
    }

    @SneakyThrows
    @Test
    void assignNewStoryToDevelopr_withException() {
        // given
        var developer = new Developer(1, "developer1");
        var developerId = developer.getId();
        String url = "/stories/assign-story-developer/" + developerId;

        var story = new Story(1L, "t1", "", LocalDateTime.now(), developer, Story.EstimatedPointType.Estimated);
        given(storyService.assignNewStoryToDeveloper(any(), anyInt())).willThrow(new Exception());

        //when
        // then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(story)))
                .andExpect(status().isNotFound())
                .andReturn();
    }


    @SneakyThrows
    @Test
    void changeStoryDeveloper() {
        //given
        Developer developer = new Developer(1, "saeed");
        Story story = new Story(1L, "", "", LocalDateTime.now(), developer, Story.EstimatedPointType.Estimated);
        Long issueId = story.getIssueId();
        Integer developerId = developer.getId();
        String url = "/stories/change-story-developer/" + issueId + "/" + developerId;

        given(storyService.changeStoryDeveloper(issueId, developerId)).willReturn(story);

        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(url))
                .andExpect(status().isAccepted())
                .andReturn();

        String result = mvcResult.getResponse().getContentAsString();
        String expected = objectMapper.writeValueAsString(story);

        //then
        assertThat(result).isEqualTo(expected);
    }

    @SneakyThrows
    @Test
    void advanceStoryEstimate() {
        //given
        Story storyIn = new Story(1L, "", "", LocalDateTime.now(), null, Story.EstimatedPointType.Estimated);
        Story storyOut = new Story(1L, "", "", LocalDateTime.now(), null, Story.EstimatedPointType.Completed);
        Long issueId = storyIn.getIssueId();
        String url = "/stories/advance-estimate/" + issueId;

        given(storyService.advanceStoryEstimate(issueId)).willReturn(storyOut);

        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andExpect(status().isOk())
                .andReturn();

        String result = mvcResult.getResponse().getContentAsString();
        String expected = objectMapper.writeValueAsString(storyOut);

        //then
        assertThat(result).isEqualTo(expected);
    }

    @SneakyThrows
    @Test
    void decreaseStoryEstimate() {
        //given
        Story storyIn = new Story(1L, "", "", LocalDateTime.now(), null, Story.EstimatedPointType.Estimated);
        Story storyOut = new Story(1L, "", "", LocalDateTime.now(), null, Story.EstimatedPointType.New);
        Long issueId = storyIn.getIssueId();
        String url = "/stories/decrease-estimate/" + issueId;

        given(storyService.decreaseStoryEstimate(issueId)).willReturn(storyOut);

        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andExpect(status().isOk())
                .andReturn();

        String result = mvcResult.getResponse().getContentAsString();
        String expected = objectMapper.writeValueAsString(storyOut);

        //then
        assertThat(result).isEqualTo(expected);
    }
}