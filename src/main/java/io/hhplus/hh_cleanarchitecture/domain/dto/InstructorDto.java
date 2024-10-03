package io.hhplus.hh_cleanarchitecture.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@Builder
public class InstructorDto {

    private long instructorId;
    private String instructorName;

}
