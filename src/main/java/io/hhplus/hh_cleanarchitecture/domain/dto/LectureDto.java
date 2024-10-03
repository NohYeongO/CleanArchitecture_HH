package io.hhplus.hh_cleanarchitecture.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@AllArgsConstructor
@Builder
public class LectureDto {

        private long lectureId;
        private String lectureName;
        private long instructorId;
        private String instructorName;
        private LocalDate lectureDate;
        private int capacity;


}
