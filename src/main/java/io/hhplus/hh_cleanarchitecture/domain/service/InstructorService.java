package io.hhplus.hh_cleanarchitecture.domain.service;


import io.hhplus.hh_cleanarchitecture.common.exception.InstructorNotFoundException;
import io.hhplus.hh_cleanarchitecture.infrastructure.entity.Instructor;
import io.hhplus.hh_cleanarchitecture.infrastructure.repository.InstructorJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InstructorService {

    private final InstructorJpaRepository instructorJpaRepository;

    // 인스트럭터 조회
    public Instructor findById(long instructorId) {
        return instructorJpaRepository.findById(instructorId).orElseThrow(() -> new InstructorNotFoundException("강사가 존재하지 않습니다."));
    }
}
