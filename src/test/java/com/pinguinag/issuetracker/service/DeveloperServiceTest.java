package com.pinguinag.issuetracker.service;

import com.pinguinag.issuetracker.entity.Developer;
import com.pinguinag.issuetracker.repository.DeveloperRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeveloperServiceTest {

    @Mock
    private DeveloperRepository developerRepository;
    @InjectMocks
    private DeveloperService underTest;
    @Captor
    ArgumentCaptor<Developer> developerArgumentCaptor;

    /*
    * getAll() work correct
    * */
    @SneakyThrows
    @Test
    void getAll() {
        //given
        given(developerRepository.findAll()).willReturn(List.of(new Developer()));

        //when
        underTest.getAll();

        //then
        verify(developerRepository).findAll();
    }

    /*
     * getAll() don't find any entity
     * */
    @SneakyThrows
    @Test
    void getAll2() {
        //given
        given(developerRepository.findAll()).willReturn(List.of());

        //when


        //then
        assertThatThrownBy(() -> underTest.getAll())
                .isInstanceOf(Exception.class)
                .hasMessageContaining("developers is empty");
    }

    /*
    * get() find entity
    * */
    @SneakyThrows
    @Test
    void get() {
        //given
        given(developerRepository.findById(anyInt())).willReturn(Optional.of(new Developer()));

        //when
        underTest.get(anyInt());

        //then
        verify(developerRepository).findById(anyInt());
    }

    /*
    * get() don't find
    * */
    @Test
    void get2() {
        //given
        given(developerRepository.findById(anyInt())).willReturn(Optional.empty());

        //when

        //then
        assertThatThrownBy(() -> underTest.get(anyInt()))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("developer not found");
    }

    @Test
    void getByName() {
        //given
        given(developerRepository.findByName(anyString())).willReturn(new Developer());

        //when
        underTest.getByName(anyString());

        //then
        verify(developerRepository).findByName(anyString());
    }

    @Test
    void save() {
        //given
        Developer developer = new Developer(0, "Saeed");
        given(developerRepository.save(any())).willReturn(developer);

        //when
        underTest.save(developer);

        //then
        verify(developerRepository).save(developerArgumentCaptor.capture());
        Developer capturedDeveloper = developerArgumentCaptor.getValue();
        assertThat(capturedDeveloper).isEqualTo(developer);

    }

    /*
    * update() work correct
    * */
    @SneakyThrows
    @Test
    void update() {
        //given
        Developer developer = new Developer(0, "Saeed");
        given(developerRepository.findById(anyInt())).willReturn(Optional.of(developer));

        //when
        Developer result = underTest.update(developer);

        //then
        assertThat(result).isEqualTo(developer);

    }

    /*
     * update() don't work correct
     * */
    @Test
    void update2() {
        //given
        given(developerRepository.findById(anyInt())).willReturn(Optional.empty());

        //when


        //then
        assertThatThrownBy(() -> underTest.update(new Developer(0, "")))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("developer not found");

    }
}