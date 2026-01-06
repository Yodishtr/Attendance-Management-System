package com.yodishtr.ams.services;

import com.yodishtr.ams.entities.Course;
import com.yodishtr.ams.repositories.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository){
        this.courseRepository = courseRepository;
    }

    @Transactional
    public Course createCourse(String courseCode, String courseName){
        if (courseCode == null || courseCode.isBlank() || courseRepository.existsByCourseCode(courseCode)){
            throw new IllegalArgumentException("Course code already exists");
        }
        if (courseName == null || courseName.isBlank() || courseRepository.existsByCourseName(courseName)){
            throw new IllegalArgumentException("Course name already exists");
        }
        Course newCourse = new Course(courseCode, courseName);
        return courseRepository.save(newCourse);
    }

    @Transactional
    public Course updateCourse(String courseCode, String courseName, Long courseId){
        Optional<Course> optCourseObject = courseRepository.findById(courseId);
        if (optCourseObject.isEmpty()){
            throw new IllegalArgumentException("Course id provided does not correspond to an existing course");
        }
        Course actualCourse = optCourseObject.get();
        if (courseCode != null && !courseCode.isBlank() && !courseCode.equals(actualCourse.getCourseCode())){
            if (!courseRepository.existsByCourseCode(courseCode)) {
                actualCourse.setCourseCode(courseCode);
            } else {
                throw new IllegalArgumentException("Course code already exists");
            }
        }
        if (courseName != null && !courseName.isBlank() && !courseName.equals(actualCourse.getCourseName())){
            if (!courseRepository.existsByCourseName(courseName)) {
                actualCourse.setCourseName(courseName);
            } else {
                throw new IllegalArgumentException("Course name already exists");
            }
        }
        return courseRepository.save(actualCourse);
    }

    @Transactional
    public void deleteCourseById(Long courseId){
        Optional<Course> courseOptObject = courseRepository.findById(courseId);
        if (courseOptObject.isEmpty()){
            throw new IllegalArgumentException("Course id provided does not correspond to an existing course");
        }
        Course currCourse = courseOptObject.get();
        courseRepository.delete(currCourse);
    }


    @Transactional
    public void deleteCourseByCourseCode(String courseCode){
        Optional<Course> courseOpt = courseRepository.findByCourseCode(courseCode);
        if (courseOpt.isEmpty()){
            throw new IllegalArgumentException("Course code provided does not correspond to an existing course");
        }
        Course currCourse = courseOpt.get();
        courseRepository.delete(currCourse);
    }

    @Transactional
    public Course getCourseWithEnrollmentById(Long courseId){
        Optional<Course> courseOpt = courseRepository.findWithEnrollmentsById(courseId);
        if (courseOpt.isEmpty()){
            throw new IllegalArgumentException("Course id provided does not correspond to an existing course");
        }
        Course currCourse = courseOpt.get();
        return currCourse;
    }

    @Transactional
    public Course getCourseWithEnrollmentByCourseCode(String courseCode){
        Optional<Course> courseOpt = courseRepository.findWithEnrollmentsByCourseCode(courseCode);
        if (courseOpt.isEmpty()){
            throw new IllegalArgumentException("Course code provided does not correspond to an existing course");
        }
        Course currCourse = courseOpt.get();
        return currCourse;
    }

    @Transactional(readOnly = true)
    public List<Course> getAllCourses(){
        return courseRepository.findAll();
    }


}
