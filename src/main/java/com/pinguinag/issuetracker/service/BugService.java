package com.pinguinag.issuetracker.service;

import com.pinguinag.issuetracker.entity.Bug;
import com.pinguinag.issuetracker.entity.Developer;
import com.pinguinag.issuetracker.repository.BugRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BugService {

    private final BugRepository bugRepository;
    private final DeveloperService developerService;

    public Bug assignNewBugToDeveloper(Bug entity, Integer developerId) throws Exception {
        Developer developer = developerService.get(developerId);
        entity.setDeveloper(developer);
        return bugRepository.save(entity);
    }

    public Bug changeBugDeveloper(Long issueId, Integer developerId) throws Exception {
        Bug bug = this.get(issueId);
        Developer developer = developerService.get(developerId);
        bug.setDeveloper(developer);
        return bugRepository.save(bug);
    }

    public List<Bug> getDeveloperBugs(Integer developerId) {
        return bugRepository.getDeveloperBugs(developerId);
    }

    public Bug get(Long id) throws Exception {
        Optional<Bug> res = bugRepository.findById(id);
        if (res.isPresent())
            return res.get();
        else
            throw new Exception("Bug not found");
    }

    @Transactional(rollbackOn = Exception.class)
    public Bug changePriority(Long issueId, Bug.PriorityType newPriority) throws Exception {
        Bug bug = this.get(issueId);
        bug.setPriority(newPriority);
        return bug;
    }

    @Transactional(rollbackOn = Exception.class)
    public Bug advanceStatus(Long issueId) throws Exception {
        Bug bug = this.get(issueId);
        switch (bug.getStatus()) {
            case New:
                bug.setStatus(Bug.StatusType.Verified);
                break;
            case Verified:
                bug.setStatus(Bug.StatusType.Resolved);
                break;
        }
        return bug;
    }

    @Transactional(rollbackOn = Exception.class)
    public Bug decreaseStatus(Long issueId) throws Exception {
        Bug bug = this.get(issueId);
        switch (bug.getStatus()) {
            case Verified:
                bug.setStatus(Bug.StatusType.New);
                break;
            case Resolved:
                bug.setStatus(Bug.StatusType.Verified);
                break;
        }
        return bug;
    }

}
