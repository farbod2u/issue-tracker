package com.pinguinag.issuetracker.service;

import com.pinguinag.issuetracker.entity.Developer;
import com.pinguinag.issuetracker.entity.Story;
import com.pinguinag.issuetracker.repository.StoryRepository;
import lombok.SneakyThrows;
import org.apache.tomcat.jni.Local;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StoryServiceTest {

    @Mock
    StoryRepository storyRepository;
    @Mock
    DeveloperService developerService;

    @InjectMocks
    StoryService underTest;

    @SneakyThrows
    @Test
    void getDeveloperStoriesForWeek() {
        //given
        Developer developer = new Developer(2, "d2");
        var stories = List.of(
                new Story(1L, "t1", "des1", LocalDateTime.now(), developer, Story.EstimatedPointType.New),
                new Story(2L, "t2", "des2", LocalDateTime.now(), developer, Story.EstimatedPointType.New),
                new Story(3L, "t3", "des3", LocalDateTime.now(), developer, Story.EstimatedPointType.New)
        );
        given(storyRepository.getDeveloperStories(anyInt())).willReturn(stories);

        //when
        underTest.getDeveloperStoriesForWeek(anyInt(), "2021-12-02");

        //then
        verify(storyRepository).getDeveloperStories(anyInt());
    }

    @SneakyThrows
    @Test
    void assignNewStoryToDeveloper() {
        //given
        int developerId = 1;
        var developer = new Developer(developerId, "saeed");
        var story = new Story(10L, "title", "", LocalDateTime.now(), developer, Story.EstimatedPointType.New);
        given(developerService.get(anyInt())).willReturn(developer);
        given(storyRepository.save(story)).willReturn(story);

        var stories = List.of(
                new Story(1L, "t1", "des1", LocalDateTime.now(), developer, Story.EstimatedPointType.New),
                new Story(2L, "t2", "des2", LocalDateTime.now(), developer, Story.EstimatedPointType.New),
                new Story(3L, "t3", "des3", LocalDateTime.now(), developer, Story.EstimatedPointType.New)
        );
        given(storyRepository.getDeveloperStories(anyInt())).willReturn(stories);

        //when
        underTest.assignNewStoryToDeveloper(story, developerId);

        //then
        verify(storyRepository).save(story);

    }

    @SneakyThrows
    @Test
    void assignNewStoryToDeveloper_notAssign() {
        //given
        int developerId = 1;
        var developer = new Developer(developerId, "saeed");
        var story = new Story(10L, "title", "", LocalDateTime.now(), developer, Story.EstimatedPointType.New);
        given(developerService.get(anyInt())).willReturn(developer);

        var stories = List.of(
                new Story(1L, "t1", "des1", LocalDateTime.now(), null, Story.EstimatedPointType.New),
                new Story(2L, "t2", "des2", LocalDateTime.now(), null, Story.EstimatedPointType.New),
                new Story(1L, "t1", "des1", LocalDateTime.now(), null, Story.EstimatedPointType.New),
                new Story(2L, "t2", "des2", LocalDateTime.now(), null, Story.EstimatedPointType.New),
                new Story(1L, "t1", "des1", LocalDateTime.now(), null, Story.EstimatedPointType.New),
                new Story(2L, "t2", "des2", LocalDateTime.now(), null, Story.EstimatedPointType.New),
                new Story(1L, "t1", "des1", LocalDateTime.now(), null, Story.EstimatedPointType.New),
                new Story(2L, "t2", "des2", LocalDateTime.now(), null, Story.EstimatedPointType.New),
                new Story(1L, "t1", "des1", LocalDateTime.now(), null, Story.EstimatedPointType.New),
                new Story(2L, "t2", "des2", LocalDateTime.now(), null, Story.EstimatedPointType.New)
        );
        given(storyRepository.getDeveloperStories(anyInt())).willReturn(stories);

        //when


        //then
        assertThatThrownBy(() -> underTest.assignNewStoryToDeveloper(story, developerId))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("Story not assigned, developer has 10 stories in the week");

    }

    @SneakyThrows
    @Test
    void changeStoryDeveloper() {
        //given
        int developerId = 1;
        var developer = new Developer(developerId, "saeed");
        long issueId = 10L;
        var story = new Story(issueId, "title", "", LocalDateTime.now(), developer, Story.EstimatedPointType.New);

        given(storyRepository.findById(issueId)).willReturn(Optional.of(story));
        given(developerService.get(developerId)).willReturn(developer);

        var stories = List.of(
                new Story(1L, "t1", "des1", LocalDateTime.now(), null, Story.EstimatedPointType.New),
                new Story(2L, "t2", "des2", LocalDateTime.now(), null, Story.EstimatedPointType.New),
                new Story(1L, "t1", "des1", LocalDateTime.now(), null, Story.EstimatedPointType.New)
        );
        given(storyRepository.getDeveloperStories(anyInt())).willReturn(stories);

        //when
        Story result = underTest.changeStoryDeveloper(issueId, developerId);

        //then
        assertThat(result).isEqualTo(story);
    }

    @SneakyThrows
    @Test
    void changeStoryDeveloper_dosenotChange() {
        //given
        int developerId = 1;
        var developer = new Developer(developerId, "saeed");
        long issueId = 10L;
        var story = new Story(issueId, "title", "", LocalDateTime.now(), developer, Story.EstimatedPointType.New);

        given(storyRepository.findById(anyLong())).willReturn(Optional.of(story));
        given(developerService.get(developerId)).willReturn(developer);

        var stories = List.of(
                new Story(1L, "t1", "des1", LocalDateTime.now(), null, Story.EstimatedPointType.New),
                new Story(2L, "t2", "des2", LocalDateTime.now(), null, Story.EstimatedPointType.New),
                new Story(1L, "t1", "des1", LocalDateTime.now(), null, Story.EstimatedPointType.New),
                new Story(2L, "t2", "des2", LocalDateTime.now(), null, Story.EstimatedPointType.New),
                new Story(1L, "t1", "des1", LocalDateTime.now(), null, Story.EstimatedPointType.New),
                new Story(2L, "t2", "des2", LocalDateTime.now(), null, Story.EstimatedPointType.New),
                new Story(1L, "t1", "des1", LocalDateTime.now(), null, Story.EstimatedPointType.New),
                new Story(2L, "t2", "des2", LocalDateTime.now(), null, Story.EstimatedPointType.New),
                new Story(1L, "t1", "des1", LocalDateTime.now(), null, Story.EstimatedPointType.New),
                new Story(2L, "t2", "des2", LocalDateTime.now(), null, Story.EstimatedPointType.New)
        );
        given(storyRepository.getDeveloperStories(anyInt())).willReturn(stories);


        //when

        //then
        assertThatThrownBy(() -> underTest.changeStoryDeveloper(issueId, developerId))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("dose not change");
    }

    @SneakyThrows
    @Test
    void advanceStoryEstimate() {
        //given
        long issueId = 10L;
        var story = new Story(issueId, "title", "", LocalDateTime.now(), null, Story.EstimatedPointType.New);
        given(storyRepository.findById(anyLong())).willReturn(Optional.of(story));

        //when
        Story result = underTest.advanceStoryEstimate(issueId);

        //then
        assertThat(result).isEqualTo(story);
    }

    @SneakyThrows
    @Test
    void decreaseStoryEstimate() {
        //given
        long issueId = 10L;
        var story = new Story(issueId, "title", "", LocalDateTime.now(), null, Story.EstimatedPointType.Completed);
        given(storyRepository.findById(anyLong())).willReturn(Optional.of(story));

        //when
        Story result = underTest.decreaseStoryEstimate(issueId);

        //then
        assertThat(result).isEqualTo(story);
    }
}