package io.hhplus.hh_cleanarchitecture.infrastructure.repository;

import io.hhplus.hh_cleanarchitecture.infrastructure.entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

;

@Repository
public interface InstructorJpaRepository extends JpaRepository<Instructor, Long> {

}
