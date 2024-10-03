package io.hhplus.hh_cleanarchitecture.infrastructure.repository;


import io.hhplus.hh_cleanarchitecture.infrastructure.entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface LectureJpaRepository extends JpaRepository<Lecture, Long> {

    List<Lecture> findByLectureDateGreaterThanEqualAndCapacityLessThan(LocalDate currentDate, int capacity);

    Optional<Lecture> findByLectureIdAndCapacityLessThan(Long lectureId, int capacity);
}
