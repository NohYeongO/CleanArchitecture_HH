package io.hhplus.hh_cleanarchitecture.application.controller;


import io.hhplus.hh_cleanarchitecture.application.facade.LectureManagementFacade;
import io.hhplus.hh_cleanarchitecture.application.request.ReservationRequest;
import io.hhplus.hh_cleanarchitecture.application.response.ReservationResponse;
import io.hhplus.hh_cleanarchitecture.domain.dto.ReservationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/lecture")
@RequiredArgsConstructor
public class LectureReservationController {

    private final LectureManagementFacade lectureManagementFacade;


    /**
     *  특강 신청 API
     */
    @PostMapping("/reservations")
    public ResponseEntity<ReservationDto> registerLecture(@RequestBody ReservationRequest reservation) {
        return ResponseEntity.ok(lectureManagementFacade.registerLecture(reservation));
    }


    /**
     * 특강 신청 여부 조회 API
     */
    @GetMapping("/reservation/{userId}")
    public ResponseEntity<Boolean> lectureReservedByUser(@PathVariable long userId) {
        return ResponseEntity.ok(lectureManagementFacade.lectureReservedByUser(userId));
    }


    /**
     * 날짜별로 현재 신청 가능한 특강 조회하는 API
     */
    @GetMapping("/reservation/date")
    public ResponseEntity<List<ReservationResponse>> lectureGroupedByDate(){
        return ResponseEntity.ok(lectureManagementFacade.lectureGroupedByDate());
    }


    /**
     * userId로 특강 신청완료된 목록 조회 API
     */
    @GetMapping("/reservation/{userId}/reserved")
    public ResponseEntity<List<ReservationDto>> getUserReservations(@PathVariable long userId) {
        return ResponseEntity.ok(lectureManagementFacade.getUserReservations(userId));
    }



}
