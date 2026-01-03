package com.yodishtr.ams.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String courseCode;

    @Column(nullable  = false)
    private String courseName;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Enrollment> enrollments = new ArrayList<>();

    public Course(){}

    public Course(String courseCode, String courseName){
        this.courseCode = courseCode;
        this.courseName = courseName;
    }

    // Getters
    public Long getId() {
        return this.id;
    }

    public String getCourseCode(){
        return this.courseCode;
    }

    public String getCourseName(){
        return this.courseName;
    }

    public List<Enrollment> getEnrollments(){
        return this.enrollments;
    }

    // Setters
    public void setCourseCode(String courseCode){
        this.courseCode = courseCode;
    }

    public void setCourseName(String courseName){
        this.courseName = courseName;
    }

    public void addEnrollment(Enrollment e){
        this.enrollments.add(e);
        e.setCourse(this);
    }

    public void removeEnrollment(Enrollment e){
        this.enrollments.remove(e);
        e.setCourse(null);
    }
}
