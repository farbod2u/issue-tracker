package com.pinguinag.issuetracker.service;

import com.pinguinag.issuetracker.entity.Developer;
import com.pinguinag.issuetracker.entity.Story;
import com.pinguinag.issuetracker.repository.StoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoryService {

    private final StoryRepository storyRepository;
    private final DeveloperService developerService;

    private String DATE_FORMAT = "yyyy-MM-dd";

    public List<Story> getDeveloperStoriesForWeek(Integer developerId, String dateString) throws Exception {
        int week = getWeekOfYear(dateString);

        return storyRepository.getDeveloperStories(developerId)
                .stream()
                .filter(p -> {
                    try {
                        return getWeekOfYear(getFormattedDate(p.getCreationDate())) == week;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return false;
                    }
                })
                .collect(Collectors.toList());
    }

    private String getFormattedDate(LocalDateTime date) throws Exception {
        if (date == null)
            throw new Exception("Date must be not Null");
        DateTimeFormatter f = DateTimeFormatter.ofPattern(DATE_FORMAT);
        return date.format(f);
    }

    private int getWeekOfYear(String dateString) throws Exception {
        SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
        Date date = null;
        try {
            date = df.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new Exception("correct date format is yyyy-MM-dd");
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.WEEK_OF_YEAR);
    }

    public List<Story> getAll() {
        return storyRepository.findAll();
    }

    public Story get(Long id) throws Exception {
        Optional<Story> res = storyRepository.findById(id);
        if (res.isPresent())
            return res.get();
        else
            throw new Exception("Story not found.");
    }

    public Story assignNewStoryToDevelopr(Story entity, Integer developerId) throws Exception {
        Developer developer = developerService.get(developerId);

        if (canAssignToDeveloper(entity, developerId)) {
            entity.setEstimatedPoint(Story.EstimatedPointType.New);
            entity.setDeveloper(developer);
            return storyRepository.save(entity);
        } else
            throw new Exception("Story not assigned, developer has 10 stories in the week");
    }

    private boolean canAssignToDeveloper(Story entity, Integer developerId) throws Exception {
        var developerStories = this.getDeveloperStoriesForWeek(developerId, getFormattedDate(entity.getCreationDate()));
        return (developerStories.size() < 10);
    }

    @Transactional
    public Story changeStoryDeveloper(Long issueId, Integer developerId) throws Exception {
        Story entity = this.get(issueId);
        Developer developer = developerService.get(developerId);

        if (canAssignToDeveloper(entity, developerId)) {
            entity.setDeveloper(developer);
            return entity;
        } else
            throw new Exception("Story developer dose not change, developer has 10 stories in the week");
    }
}
