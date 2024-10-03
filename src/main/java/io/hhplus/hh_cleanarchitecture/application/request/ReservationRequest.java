package io.hhplus.hh_cleanarchitecture.application.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class ReservationRequest {
    private long userId;
    private long lectureId;
}
