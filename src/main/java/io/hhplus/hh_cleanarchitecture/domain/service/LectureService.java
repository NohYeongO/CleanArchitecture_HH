package io.hhplus.hh_cleanarchitecture.domain.service;

import io.hhplus.hh_cleanarchitecture.common.exception.LectureException;
import io.hhplus.hh_cleanarchitecture.infrastructure.entity.Lecture;
import io.hhplus.hh_cleanarchitecture.infrastructure.repository.LectureJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LectureService {

    private final LectureJpaRepository lectureJpaRepository;

    // lecture List 조회
    public List<Lecture> lectureList(){
        LocalDate currentDate = LocalDate.now();
        List<Lecture> lectures = lectureJpaRepository.findByLectureDateGreaterThanEqualAndCapacityLessThan(currentDate, 30);
        if(lectures.isEmpty()){
            throw new LectureException(404, "조회결과가 없습니다.");
        }
        return lectures;
    }

    // Lecture 조회
    @Transactional
    public Lecture findById(long lectureId){
        return lectureJpaRepository.findByLectureIdAndCapacityLessThan(lectureId, 30)
                .orElseThrow(() -> new LectureException(404, "마감된 특강입니다."));
    }

    // 신청수 증가
    public void updateCapacity(Lecture lecture) {
        if (lecture.getCapacity() > 30) {
            throw new LectureException(409, "강의 정원이 초과되었습니다.");
        }
        lecture.setCapacity(lecture.getCapacity() + 1);
    }

}
