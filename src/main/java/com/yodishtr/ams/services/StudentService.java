package com.yodishtr.ams.services;

import com.yodishtr.ams.entities.Student;
import com.yodishtr.ams.repositories.StudentRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    @Transactional
    public Student createStudent(String fullName, Integer age, String email, Integer phone, String address){
        if (fullName == null || fullName.isBlank()){
            throw new IllegalArgumentException("Full name cannot be null or empty");
        }
        if (age == null || age < 0){
            throw new IllegalArgumentException("Age cannot be null or negative");
        }
        if (email == null || email.isBlank()){
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if (studentRepository.existsByEmail(email)){
            throw new IllegalArgumentException("Email already exists");
        }
        Student newStudent = new Student(fullName, age, email, phone, address);
        return studentRepository.save(newStudent);
    }

    @Transactional
    public Student updateStudent(String fullName, Integer age, String email, Integer phone, String address,
                                 Long student_id){
        Optional<Student> originalStudent = studentRepository.findById(student_id);
        if (!originalStudent.isPresent()){
            throw new IllegalArgumentException("Student not found");
        }
        Student currStudent = originalStudent.get();
        if (fullName != null && !fullName.equals(currStudent.getFullName()) && !fullName.isBlank()){
            currStudent.setFullname(fullName);
        }
        if (age != null && age != currStudent.getAge() && age != 0){
            currStudent.setAge(age);
        }
        if (email != null && !email.equals(currStudent.getEmail()) && !email.isBlank()){
            if (!studentRepository.existsByEmail(email)) {
                currStudent.setEmail(email);
            }
        }
        if (phone != null && !phone.equals(currStudent.getPhone())){
            currStudent.setPhone(phone);
        }
        if (address != null && !address.equals(currStudent.getAddress()) && !address.isBlank()){
            currStudent.setAddress(address);
        }
        return studentRepository.save(currStudent);
    }

    @Transactional
    public void deleteStudent(Long student_id){
        Optional<Student> originalStudent = studentRepository.findById(student_id);
        if (!originalStudent.isPresent()){
            throw new IllegalArgumentException("Student not found");
        }
        Student currStudent = originalStudent.get();
        studentRepository.delete(currStudent);
    }

    @Transactional
    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

    @Transactional
    public Student getStudentById(Long student_id){
        // returns student data with their enrollment and courses
        Optional<Student> originalStudent = studentRepository.findByIdWithEnrollmentsAndCourse(student_id);
        if (!originalStudent.isPresent()){
            throw new IllegalArgumentException("Student not found");
        }
        Student currStudent = originalStudent.get();
        return currStudent;
    }

    @Transactional
    public List<Student> getAllStudentsWithSameName(String fullName){
        List<Student> studentList = studentRepository.findByFullName(fullName);
        return studentList;
    }


}
