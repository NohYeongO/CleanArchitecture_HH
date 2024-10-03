package io.hhplus.hh_cleanarchitecture.common.exception;

import lombok.Getter;

@Getter
public class LectureException extends RuntimeException {
    private final int code;

    public LectureException(int code, String message) {
        super(message);
        this.code = code;
    }

}
