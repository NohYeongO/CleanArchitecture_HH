package io.hhplus.hh_cleanarchitecture.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@AllArgsConstructor
@Builder
public class ReservationDto {

    private long reservationId;
    private long userId;
    private String lectureName;
    private String instructorName;
    private LocalDate lectureDate;
}
