package com.yodishtr.ams.services;

import com.yodishtr.ams.entities.Course;
import com.yodishtr.ams.entities.Enrollment;
import com.yodishtr.ams.entities.Student;
import com.yodishtr.ams.repositories.CourseRepository;
import com.yodishtr.ams.repositories.EnrollmentRepository;
import com.yodishtr.ams.repositories.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository,
                             StudentRepository studentRepository,
                             CourseRepository courseRepository){
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @Transactional
    public Enrollment createEnrollment(Long studentId, Long courseId){
        if (!studentRepository.existsById(studentId)){
            throw new IllegalArgumentException("Student with id " + studentId + " does not exist");
        }
        if (!courseRepository.existsById(courseId)){
            throw new IllegalArgumentException("Course with id " + courseId + " does not exist");
        }
        Optional<Enrollment> potentialEnrollment = enrollmentRepository.findByStudentIdAndCourseId(studentId, courseId);
        if (potentialEnrollment.isPresent()){
            Enrollment currEnrollment = potentialEnrollment.get();
            if (!currEnrollment.isActive()){
                throw new IllegalArgumentException("Enrollment with id " + currEnrollment.getId() +
                        " is already active");
            } else {
                currEnrollment.setActive(true);
                return enrollmentRepository.save(currEnrollment);
            }
        }
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        Student currStudent = optionalStudent.get();
        Course currCourse = optionalCourse.get();
        Enrollment newEnrollment = new Enrollment(currStudent, currCourse);
        return enrollmentRepository.save(newEnrollment);
    }

    @Transactional
    public Enrollment deactivateEnrollment(Long enrollmentId){
        Optional<Enrollment> optionalEnrollment = enrollmentRepository.findById(enrollmentId);
        if (optionalEnrollment.isEmpty()){
            throw new IllegalArgumentException("Enrollment does not exist");
        }
        Enrollment currEnrollment = optionalEnrollment.get();
        currEnrollment.setActive(false);
        return enrollmentRepository.save(currEnrollment);
    }

    @Transactional(readOnly = true)
    public Enrollment getEnrollment(Long enrollmentId){
        Optional<Enrollment> optionalEnrollment = enrollmentRepository.findById(enrollmentId);
        if (optionalEnrollment.isEmpty()){
            throw new IllegalArgumentException("Enrollment does not exist");
        }
        return optionalEnrollment.get();
    }

    @Transactional
    public List<Enrollment> getStudentEnrollments(Long studentId){
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        if (optionalStudent.isEmpty()){
            throw new IllegalArgumentException("Student does not exist");
        }
        Student currStudent = optionalStudent.get();
        return currStudent.getEnrollments();
    }

    @Transactional
    public List<Enrollment> getCourseEnrollments(Long courseId){
        Optional<Course> optionalCourse = courseRepository.findWithEnrollmentsById(courseId);
        if (optionalCourse.isEmpty()){
            throw new IllegalArgumentException("Course does not exist");
        }
        Course currCourse = optionalCourse.get();
        return currCourse.getEnrollments();
    }
}
