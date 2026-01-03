package com.yodishtr.ams.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "enrollments",
        uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "course_id"})
)
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private LocalDateTime enrollmentDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(nullable = false)
    private boolean active = true;

    // Setters
    public void setStudent(Student student){
        this.student = student;
    }

    public void setCourse(Course course){
        this.course = course;
    }

    public void setEnrollmentDate(LocalDateTime enrollmentDate){
        if (enrollmentDate == null){
            throw new IllegalArgumentException("Enrollment date cannot be null");
        }
        this.enrollmentDate = enrollmentDate;
    }

    public void setActive(boolean active){
        this.active = active;
    }

    // Getters
    public Long getId(){
        return this.id;
    }

    public LocalDateTime getEnrollmentDate(){
        return this.enrollmentDate;
    }

    public Student getStudent(){
        return this.student;
    }

    public Course getCourse(){
        return this.course;
    }

    public boolean isActive(){
        return this.active;
    }

}
