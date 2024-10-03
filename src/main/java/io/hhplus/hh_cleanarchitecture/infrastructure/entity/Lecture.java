package io.hhplus.hh_cleanarchitecture.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity @Getter @Setter
@Table(name = "LECTURE")
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LECTURE_ID")
    private long lectureId;

    @Column(name = "LECTURE_NAME", nullable = false)
    private String lectureName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="INSTRUCTOR_ID")
    private Instructor instructor;


    @Column(name = "LECTURE_DATE", nullable = false)
    private LocalDate lectureDate;

    @Column(name="CAPACITY", nullable = false)
    private int capacity;


}
