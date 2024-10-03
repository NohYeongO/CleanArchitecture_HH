package io.hhplus.hh_cleanarchitecture.domain.service;


import io.hhplus.hh_cleanarchitecture.common.exception.InstructorNotFoundException;
import io.hhplus.hh_cleanarchitecture.infrastructure.entity.Instructor;
import io.hhplus.hh_cleanarchitecture.infrastructure.repository.InstructorJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


class InstructorServiceTest {

    InstructorService instructorService;

    @Mock
    InstructorJpaRepository instructorJpaRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        instructorService = new InstructorService(instructorJpaRepository);
    }

    @DisplayName("인스트럭터가 조회될경우 테스트")
    @Test
    void instructorFound() {
        long instructorId = 1L;
        Instructor instructor = new Instructor();
        instructor.setInstructorId(instructorId);
        instructor.setInstructorName("test");

        when(instructorJpaRepository.findById(instructorId)).thenReturn(Optional.of(instructor));
        assert (instructorService.findById(instructorId).equals(instructor));
    }


    @DisplayName("인스트럭터가 조회되지 않을경우 테스트")
    @Test
    void instructorNotFound(){
        long instructorId = 1L;

        when(instructorJpaRepository.findById(instructorId)).thenReturn(Optional.empty());
        assertThrows(InstructorNotFoundException.class, () -> instructorService.findById(instructorId));
    }

}