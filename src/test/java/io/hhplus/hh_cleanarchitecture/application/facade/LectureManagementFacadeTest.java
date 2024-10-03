package io.hhplus.hh_cleanarchitecture.application.facade;


import io.hhplus.hh_cleanarchitecture.application.request.ReservationRequest;
import io.hhplus.hh_cleanarchitecture.application.response.ReservationResponse;
import io.hhplus.hh_cleanarchitecture.domain.dto.ReservationDto;
import io.hhplus.hh_cleanarchitecture.domain.service.InstructorService;
import io.hhplus.hh_cleanarchitecture.domain.service.LectureService;
import io.hhplus.hh_cleanarchitecture.domain.service.ReservationService;
import io.hhplus.hh_cleanarchitecture.infrastructure.entity.Instructor;
import io.hhplus.hh_cleanarchitecture.infrastructure.entity.Lecture;
import io.hhplus.hh_cleanarchitecture.infrastructure.entity.Reservation;
import io.hhplus.hh_cleanarchitecture.infrastructure.repository.InstructorJpaRepository;
import io.hhplus.hh_cleanarchitecture.infrastructure.repository.LectureJpaRepository;
import io.hhplus.hh_cleanarchitecture.infrastructure.repository.ReservationJpaRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class LectureManagementFacadeTest {
    private static final Logger log = LoggerFactory.getLogger(LectureManagementFacadeTest.class);
    @Autowired
    private LectureManagementFacade lectureManagementFacade;

    @Autowired
    private LectureService lectureService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private InstructorService instructorService;

    @Autowired
    private LectureJpaRepository lectureJpaRepository;

    @Autowired
    private InstructorJpaRepository instructorJpaRepository;
    @Autowired
    private ReservationJpaRepository reservationJpaRepository;

    @DisplayName("날짜별로 묶어서 조회가 되는지 확인하는 테스트")
    @Test
    void lectureGroupedByDateTest(){

        // Given: 테스트 데이터 준비
        Instructor instructor = new Instructor();
        instructor.setInstructorName("강사 1");
        instructor = instructorJpaRepository.save(instructor);

        Lecture lecture1 = new Lecture();
        lecture1.setLectureName("강의 1");
        lecture1.setLectureDate(LocalDate.of(2024, 10, 21));
        lecture1.setInstructor(instructor);
        lecture1.setCapacity(0);
        lectureJpaRepository.save(lecture1);

        Lecture lecture2 = new Lecture();
        lecture2.setLectureName("강의 2");
        lecture2.setLectureDate(LocalDate.of(2024, 10, 21));
        lecture2.setInstructor(instructor);
        lecture2.setCapacity(0);
        lectureJpaRepository.save(lecture2);

        Lecture lecture3 = new Lecture();
        lecture3.setLectureName("강의 3");
        lecture3.setLectureDate(LocalDate.of(2024, 10, 22));
        lecture3.setInstructor(instructor);
        lecture3.setCapacity(0);
        lectureJpaRepository.save(lecture3);

        // When = 강의 목록 조회
        List<ReservationResponse> result = lectureManagementFacade.lectureGroupedByDate();

        // THEN
        // 결과가 null이 아닌지 검증
        assertNotNull(result);
        // 결과가 비어있지 않은지 검증
        assertFalse(result.isEmpty());

        ReservationResponse group1 = result.stream()
                .filter(group -> group.getDate().equals(LocalDate.of(2024, 10, 21)))
                .findFirst().orElse(null);
        assertNotNull(group1); // 해당 날짜에 대한 그룹이 있는지 검증
        assertEquals(2, group1.getLectures().size()); // 10.21의 강의 개수가 2개인지 검증

        ReservationResponse group2 = result.stream()
                .filter(group -> group.getDate().equals(LocalDate.of(2024, 10, 22)))
                .findFirst().orElse(null);
        assertNotNull(group2); // 해당 날짜에 대한 그룹이 있는지 검증
        assertEquals(1, group2.getLectures().size()); // 10.22의 강의 개수가 1개인지 검증

    }

    @DisplayName("강의 신청이 등록되는지 테스트")
    @Test
    public void registerLectureTest() {
        // Given: 테스트 데이터 준비
        Instructor instructor = new Instructor();
        instructor.setInstructorName("강사 1");
        instructor = instructorJpaRepository.save(instructor);

        Lecture lecture = new Lecture();
        lecture.setLectureName("강의 1");
        lecture.setLectureDate(LocalDate.of(2024, 10, 21));
        lecture.setInstructor(instructor);
        lecture.setCapacity(0);
        lectureJpaRepository.save(lecture);

        ReservationRequest request = new ReservationRequest(1, lecture.getLectureId());

        // When: 강의 신청
        ReservationDto result = lectureManagementFacade.registerLecture(request);

        // Then: 결과 검증
        assertNotNull(result);
        assertEquals("강의 1", result.getLectureName()); // 강의명이 정확한지 검증
    }

    @DisplayName("특정 사용자가 강의를 신청했는지 여부를 확인하는 테스트")
    @Test
    public void lectureReservedByUserTest() {
        // Given: 테스트 데이터 준비
        Instructor instructor = new Instructor();
        instructor.setInstructorName("강사 1");
        instructor = instructorJpaRepository.save(instructor);

        Lecture lecture = new Lecture();
        lecture.setLectureName("강의 1");
        lecture.setLectureDate(LocalDate.of(2024, 10, 21));
        lecture.setInstructor(instructor);
        lecture.setCapacity(30);
        lectureJpaRepository.save(lecture);

        // 강의 신청
        Reservation reservation = new Reservation();
        reservation.setLecture(lecture);
        reservation.setUserId(1L);
        reservationJpaRepository.save(reservation);

        // When: 특정 사용자가 강의를 신청했는지 확인
        boolean isReserved = lectureManagementFacade.lectureReservedByUser(1);

        // Then: 결과 검증
        assertTrue(isReserved); // 강의 신청이 되었는지 확인
    }

    @DisplayName("특정 사용자의 예약 목록을 조회하는 테스트")
    @Test
    public void getUserReservationsTest() {
        // Given: 테스트 데이터 준비
        Instructor instructor = new Instructor();
        instructor.setInstructorName("강사 1");
        instructor = instructorJpaRepository.save(instructor);

        Lecture lecture1 = new Lecture();
        lecture1.setLectureName("강의 1");
        lecture1.setLectureDate(LocalDate.of(2024, 10, 21));
        lecture1.setInstructor(instructor);
        lecture1.setCapacity(0);
        lectureJpaRepository.save(lecture1);

        Lecture lecture2 = new Lecture();
        lecture2.setLectureName("강의 2");
        lecture2.setLectureDate(LocalDate.of(2024, 10, 22));
        lecture2.setInstructor(instructor);
        lecture2.setCapacity(0);
        lectureJpaRepository.save(lecture2);

        // 강의 신청
        Reservation reservation1 = new Reservation();
        reservation1.setLecture(lecture1);
        reservation1.setUserId(100);
        reservationJpaRepository.save(reservation1);

        Reservation reservation2 = new Reservation();
        reservation2.setLecture(lecture2);
        reservation2.setUserId(100);
        reservationJpaRepository.save(reservation2);

        // When: 사용자의 예약 목록 조회
        List<ReservationDto> result = lectureManagementFacade.getUserReservations(100);

        // Then: 결과 검증
        assertNotNull(result); // 결과가 null이 아닌지 검증
        assertEquals(2, result.size()); // 강의 개수가 정확한지 검증
    }

}