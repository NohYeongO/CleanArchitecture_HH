package io.hhplus.hh_cleanarchitecture.domain.service;


import io.hhplus.hh_cleanarchitecture.common.exception.LectureException;
import io.hhplus.hh_cleanarchitecture.infrastructure.entity.Lecture;
import io.hhplus.hh_cleanarchitecture.infrastructure.entity.Reservation;
import io.hhplus.hh_cleanarchitecture.infrastructure.repository.ReservationJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ReservationServiceTest {

    ReservationService reservationService;

    @Mock
    ReservationJpaRepository reservationJpaRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        reservationService = new ReservationService(reservationJpaRepository);
    }

    @DisplayName("신청할 특강이 존재하지 않을 경우")
    @Test
    void registerLectureTest() {
        Lecture lecture = null;
        assertThrows(LectureException.class, () ->
                reservationService.registerLecture(lecture, 1L));

    }

    @DisplayName("userId로 특강 신청 여부 확인")
    @Test
    void lectureReservedByUserTest() {
        long userId = 1L;

        when(reservationJpaRepository.existsByUserIdAndLectureLectureDateGreaterThanEqual(userId, LocalDate.now())).thenReturn(true);
        assertTrue(reservationService.lectureReservedByUser(userId));
    }

    @DisplayName("해당 강의에 대한 신청이 존재할 경우 테스트")
    @Test
    void isUserReservedLectureTrueTest() {
        long userId = 1L;
        long lectureId = 1L;

        when(reservationJpaRepository.existsByUserIdAndLecture_LectureId(userId, lectureId)).thenReturn(true);
        assertThrows(LectureException.class, () ->
                reservationService.isUserReservedLecture(userId, lectureId));
    }

    @DisplayName("해당 강의에 대한 신청이 존재하지 않을 경우 테스트")
    @Test
    void isUserReservedLectureFalseTest() {
        long userId = 1L;
        long lectureId = 1L;

        when(reservationJpaRepository.existsByUserIdAndLecture_LectureId(userId, lectureId)).thenReturn(false);
        assertDoesNotThrow(() -> reservationService.isUserReservedLecture(userId, lectureId));
    }

    @DisplayName("특강 신청 내역이 없을경우")
    @Test
    void reservationNotFound(){
        long userId = 1L;
        when(reservationJpaRepository.findByUserId(userId)).thenReturn(List.of());
        assertThrows(LectureException.class, () -> reservationService.getUserReservations(userId));
    }

    @DisplayName("특강 신청 내역이 있을 경우")
    @Test
    void reservationFound(){
        long userId = 1L;
        Reservation reservation = new Reservation();
        reservation.setUserId(userId);
        reservation.setReservationId(1);

        when(reservationJpaRepository.findByUserId(userId)).thenReturn(List.of(reservation));
        assertDoesNotThrow(() -> assertIterableEquals(List.of(reservation), reservationService.getUserReservations(userId)));
    }


}