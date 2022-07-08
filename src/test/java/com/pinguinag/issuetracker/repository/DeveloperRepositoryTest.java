package com.pinguinag.issuetracker.repository;

import com.pinguinag.issuetracker.entity.Developer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Description;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class DeveloperRepositoryTest {

    @Autowired
    private DeveloperRepository developerRepository;

    @AfterEach
    void tearDown() {
        developerRepository.deleteAll();
    }

    /*
        test if findByName() work correctly
     */
    @Test
    void findByName() {
        //given
        String name = "Saeed";
        Developer developer = new Developer(null, name);
        developerRepository.save(developer);

        //when
        Developer result = developerRepository.findByName(name);

        //then
        assertThat(result.getName()).isEqualTo(name);
    }

    /*
        test if findByName() don't find
    */
    @Test
    void findByName2() {
        //given
        String name = "Saeed";

        //when
        Developer result = developerRepository.findByName(name);

        //then
        assertThat(result).isNull();
    }


}