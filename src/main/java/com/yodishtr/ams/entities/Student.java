package com.yodishtr.ams.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;
    @Column(nullable = false)
    private int age;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Enrollment> enrollments = new ArrayList<>();

    private String email;
    private Integer phone;
    private String address;

    protected Student(){}

    public Student(String fullname, int age, String email, Integer phone, String address){
        this.fullName = fullname;
        this.age = age;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public void addEnrollment(Enrollment e){
        e.setStudent(this);
        this.enrollments.add(e);
    }

    public void removeEnrollment(Enrollment e){
        this.enrollments.remove(e);
        e.setStudent(null);
    }

    // Getters
    public Long getId(){
        return this.id;
    }

    public String getFullName(){
        return this.fullName;
    }

    public int getAge(){
        return this.age;
    }

    public String getEmail(){
        return this.email;
    }

    public Integer getPhone(){
        return this.phone;
    }

    public String getAddress(){
        return this.address;
    }

    public List<Enrollment> getEnrollments(){
        return this.enrollments;
    }

    // Setters
    public void setFullname(String fullname){
        this.fullName = fullname;
    }

    public void setAge(int age){
        this.age = age;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setPhone(Integer phone){
        this.phone = phone;
    }

    public void setAddress(String address){
        this.address = address;
    }

}
