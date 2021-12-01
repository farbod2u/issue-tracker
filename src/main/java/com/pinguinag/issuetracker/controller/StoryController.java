package com.pinguinag.issuetracker.controller;

import com.pinguinag.issuetracker.entity.Story;
import com.pinguinag.issuetracker.service.StoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stories")
public class StoryController {

    private final StoryService storyService;

    @GetMapping("/developer-stories/{developerId}/{dateString}")
    public ResponseEntity<List<Story>> getDeveloperStoriesForWeek(@PathVariable Integer developerId,

                                                                  @PathVariable String dateString) {
        try {
            List<Story> res = storyService.getDeveloperStoriesForWeek(developerId, dateString);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/today")
    public LocalDateTime getToday() {
        return LocalDateTime.now();
    }

    @PostMapping("/assign-story-developer/{developerId}")
    public ResponseEntity<Story> assignNewStoryToDevelopr(@RequestBody Story entity, @PathVariable Integer developerId) {
        try {
            Story res = storyService.assignNewStoryToDeveloper(entity, developerId);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/change-story-developer/{issueId}/{developerId}")
    public ResponseEntity<Story> changeStoryDeveloper(@PathVariable Long issueId, @PathVariable Integer developerId) {
        try {
            Story res = storyService.changeStoryDeveloper(issueId, developerId);
            return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/advance-estimate/{issueId}")
    public ResponseEntity<Story> advanceStoryEstimate(@PathVariable("issueId") Long issueId) {
        try {
            return new ResponseEntity<>(storyService.advanceStoryEstimate(issueId), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/decrease-estimate/{issueId}")
    public ResponseEntity<Story> decreaseStoryEstimate(@PathVariable("issueId") Long issueId) {
        try {
            return new ResponseEntity<>(storyService.decreaseStoryEstimate(issueId), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

}
