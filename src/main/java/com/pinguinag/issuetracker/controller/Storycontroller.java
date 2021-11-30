package com.pinguinag.issuetracker.controller;

import com.pinguinag.issuetracker.entity.Story;
import com.pinguinag.issuetracker.service.StoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stories")
public class Storycontroller {

    private final StoryService storyService;

    @GetMapping("/developer-stories/{developerId}/{dateString}")
    public List<Story> getDeveloperStoriesForWeek(@PathVariable Integer developerId,
                                                  @PathVariable String dateString) throws Exception {
        return storyService.getDeveloperStoriesForWeek(developerId, dateString);
    }

    @PostMapping("/assign-story-developer/{developerId}")
    public Story assignNewStoryToDevelopr(@RequestBody Story entity, @PathVariable Integer developerId) throws Exception {
        return storyService.assignNewStoryToDevelopr(entity, developerId);
    }

    @PutMapping("/change-story-developer/{issueId}/{developerId}")
    public Story changeStoryDeveloper(Long issueId, Integer developerId) throws Exception {
        return storyService.changeStoryDeveloper(issueId, developerId);
    }
}
