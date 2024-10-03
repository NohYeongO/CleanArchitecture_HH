package io.hhplus.hh_cleanarchitecture.infrastructure.repository;


import io.hhplus.hh_cleanarchitecture.infrastructure.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationJpaRepository extends JpaRepository<Reservation, Long> {

    boolean existsByUserIdAndLectureLectureDateGreaterThanEqual(Long userId, LocalDate now);

    boolean existsByUserIdAndLecture_LectureId(long userId, long lectureId);

    List<Reservation> findByUserId(long userId);
}
