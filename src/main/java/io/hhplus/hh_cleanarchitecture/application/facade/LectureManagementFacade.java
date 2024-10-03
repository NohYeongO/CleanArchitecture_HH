package io.hhplus.hh_cleanarchitecture.application.facade;


import io.hhplus.hh_cleanarchitecture.application.request.ReservationRequest;
import io.hhplus.hh_cleanarchitecture.application.response.ReservationResponse;
import io.hhplus.hh_cleanarchitecture.domain.dto.LectureDto;
import io.hhplus.hh_cleanarchitecture.domain.dto.ReservationDto;
import io.hhplus.hh_cleanarchitecture.domain.service.InstructorService;
import io.hhplus.hh_cleanarchitecture.domain.service.LectureService;
import io.hhplus.hh_cleanarchitecture.domain.service.ReservationService;
import io.hhplus.hh_cleanarchitecture.infrastructure.entity.Instructor;
import io.hhplus.hh_cleanarchitecture.infrastructure.entity.Lecture;
import io.hhplus.hh_cleanarchitecture.infrastructure.entity.Reservation;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class LectureManagementFacade {

    private final LectureService lectureService;
    private final ReservationService reservationService;
    private final InstructorService instructorService;

    // 날짜별 강의 조회
    public List<ReservationResponse> lectureGroupedByDate(){
        List<Lecture> lectures = lectureService.lectureList();

        return lectures.stream().map(lecture ->{
            Instructor instructor = instructorService.findById(lecture.getInstructor().getInstructorId());

            return LectureDto.builder()
                    .lectureId(lecture.getLectureId())
                    .lectureName(lecture.getLectureName())
                    .lectureDate(lecture.getLectureDate())
                    .instructorName(instructor.getInstructorName())
                    .capacity(lecture.getCapacity())
                    .build();
        }).collect(Collectors.groupingBy(LectureDto::getLectureDate))
                .entrySet()
                .stream()
                .map(entry -> new ReservationResponse(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    // 강의 신청
    @Transactional
    public ReservationDto registerLecture(ReservationRequest request) {

        // 해당 강의에 대한 신청이 존재하는지 확인
        reservationService.isUserReservedLecture(request.getUserId(), request.getLectureId());

        // 해당 강의 조회
        Lecture lecture = lectureService.findById(request.getLectureId());

        // 저장
        Reservation reservation = reservationService.registerLecture(lecture, request.getUserId());

        lectureService.updateCapacity(lecture);

        return ReservationDto.builder().reservationId(reservation.getReservationId())
                .lectureName(reservation.getLecture().getLectureName())
                .lectureDate(reservation.getLecture().getLectureDate())
                .build();
    }

    // 특강 신청 여부
    public Boolean lectureReservedByUser(long userId) {
        return reservationService.lectureReservedByUser(userId);
    }

    // 특강 신청 목록 조회
    public List<ReservationDto> getUserReservations(long userId) {
        List<Reservation> reservations = reservationService.getUserReservations(userId);

        return  reservations.stream().map(reservation ->{
            return ReservationDto.builder()
                    .reservationId(reservation.getReservationId())
                    .instructorName(reservation.getLecture().getInstructor().getInstructorName())
                    .lectureName(reservation.getLecture().getLectureName())
                    .lectureDate(reservation.getLecture().getLectureDate())
                    .userId(reservation.getUserId())
                    .build();
        }).collect(Collectors.toList());
    }
}
