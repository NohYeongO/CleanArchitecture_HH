package io.hhplus.hh_cleanarchitecture.application.response;


import io.hhplus.hh_cleanarchitecture.domain.dto.LectureDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter @AllArgsConstructor
public class ReservationResponse {
    private LocalDate date;
    private List<LectureDto> lectures;
}
