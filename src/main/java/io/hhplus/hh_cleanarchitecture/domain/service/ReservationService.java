package io.hhplus.hh_cleanarchitecture.domain.service;


import io.hhplus.hh_cleanarchitecture.common.exception.LectureException;
import io.hhplus.hh_cleanarchitecture.infrastructure.entity.Lecture;
import io.hhplus.hh_cleanarchitecture.infrastructure.entity.Reservation;
import io.hhplus.hh_cleanarchitecture.infrastructure.repository.ReservationJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationJpaRepository reservationJpaRepository;

    // 특강 신청
    @Transactional
    public Reservation registerLecture(Lecture lecture, long userId) {

        if(lecture == null) throw new LectureException(404, "현재 특강을 신청할 수 없습니다.");

        Reservation reservation = new Reservation();
        reservation.setLecture(lecture);
        reservation.setUserId(userId);

        return reservationJpaRepository.save(reservation);
    }

    // 해당 강의에 대한 신청이 존재하는지 확인
    public void isUserReservedLecture(long userId, long lectureId) {
        if(reservationJpaRepository.existsByUserIdAndLecture_LectureId(userId, lectureId)){
            throw new LectureException(409, "해당 강의에 대한 신청이 존재합니다.");
        }
    }

    // 해당 유저에 대한 특강 신청 여부
    public boolean lectureReservedByUser(long userId) {
       return reservationJpaRepository.existsByUserIdAndLectureLectureDateGreaterThanEqual(userId, LocalDate.now());
    }

    // 특강 신청 목록 조회
    public List<Reservation> getUserReservations(long userId){
        List<Reservation> list = reservationJpaRepository.findByUserId(userId);
        if(list.isEmpty()){
            throw new LectureException(404, "신청완료된 특강이 없습니다.");
        }
        return list;
    }
}
