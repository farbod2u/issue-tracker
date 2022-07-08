package com.pinguinag.issuetracker.controller;

import com.pinguinag.issuetracker.entity.Bug;
import com.pinguinag.issuetracker.service.BugService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bugs")
@RequiredArgsConstructor
public class BugController {

    private final BugService bugService;

    @PostMapping("/assign-bug-developer/{developerId}")
    public ResponseEntity<Bug> assignNewBugToDeveloper(@RequestBody Bug entity, @PathVariable Integer developerId) {
        try {
            return new ResponseEntity<>(bugService.assignNewBugToDeveloper(entity, developerId), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.NOT_MODIFIED);
        }
    }

    @GetMapping("/change-developer/{issueId}/{developerId}")
    public ResponseEntity<Bug> changeBugDeveloper(@PathVariable Long issueId, @PathVariable Integer developerId) {
        try {
            return new ResponseEntity<>(bugService.changeBugDeveloper(issueId, developerId), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.NOT_MODIFIED);
        }
    }

    @GetMapping("/developer-bugs/{developerId}")
    public ResponseEntity<List<Bug>> getDeveloperBugs(@PathVariable Integer developerId) {
        try {
            List<Bug> developerBugs = bugService.getDeveloperBugs(developerId);
            return new ResponseEntity<>(developerBugs, HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.NOT_MODIFIED);
        }
    }

    @GetMapping("/change-priority/{issueId}/{newPriority}")
    public ResponseEntity<Bug> changePriority(@PathVariable Long issueId, @PathVariable Bug.PriorityType newPriority) {
        try {
            return new ResponseEntity<>(bugService.changePriority(issueId, newPriority), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.NOT_MODIFIED);
        }
    }

    @GetMapping("/advance-status/{issueId}")
    public ResponseEntity<Bug> advanceStatus(@PathVariable Long issueId) {
        try {
            return new ResponseEntity<>(bugService.advanceStatus(issueId), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.NOT_MODIFIED);
        }
    }

    @GetMapping("/decrease-status/{issueId}")
    public ResponseEntity<Bug> decreaseStatus(@PathVariable Long issueId) {
        try {
            return new ResponseEntity<>(bugService.decreaseStatus(issueId), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.NOT_MODIFIED);
        }
    }

}
