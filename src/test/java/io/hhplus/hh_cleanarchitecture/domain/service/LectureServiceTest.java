package io.hhplus.hh_cleanarchitecture.domain.service;


import io.hhplus.hh_cleanarchitecture.common.exception.LectureException;
import io.hhplus.hh_cleanarchitecture.infrastructure.entity.Lecture;
import io.hhplus.hh_cleanarchitecture.infrastructure.repository.LectureJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class LectureServiceTest {

    LectureService lectureService;

    @Mock
    LectureJpaRepository lectureJpaRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        lectureService = new LectureService(lectureJpaRepository);
    }


    @DisplayName("조회한 lecture List가 조회되는지 테스트")
    @Test
    void lectureListFound(){
        List<Lecture> lectures = new ArrayList<>();

        Lecture lectureA = new Lecture();
        lectureA.setLectureId(1);
        lectureA.setLectureName("test1");
        Lecture lectureB = new Lecture();
        lectureB.setLectureId(2);
        lectureB.setLectureName("test2");

        lectures.add(lectureA);
        lectures.add(lectureB);

        when(lectureJpaRepository.findByLectureDateGreaterThanEqualAndCapacityLessThan(LocalDate.now(), 30)).thenReturn(lectures);

        assertDoesNotThrow(() ->  assertIterableEquals(lectures, lectureService.lectureList()));
    }

    @DisplayName("조회된 Lecture가 없을 경우 테스트")
    @Test
    void lectureListNotFound(){
        when(lectureJpaRepository.findByLectureDateGreaterThanEqualAndCapacityLessThan(LocalDate.now(), 30)).thenReturn(List.of());
        assertThrows(LectureException.class, () -> lectureService.lectureList());
    }

}