package io.hhplus.hh_cleanarchitecture.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name= "INSTRUCTOR")
public class Instructor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="INSTRUCTOR_ID")
    private long instructorId;

    @Column(name="INSTRUCTOR_NAME")
    private String instructorName;

    @OneToMany(mappedBy = "instructor", fetch = FetchType.LAZY)
    private List<Lecture> lectures;

}
