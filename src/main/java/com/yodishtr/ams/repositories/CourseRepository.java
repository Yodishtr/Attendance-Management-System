package com.yodishtr.ams.repositories;

import com.yodishtr.ams.entities.Course;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {

    Optional<Course> findByCourseCode(String courseCode);
    boolean existsByCourseCode(String courseCode);
    Optional<Course> findByCourseName(String courseName);
    boolean existsByCourseName(String courseName);
    Optional<Course> findById(Long courseId);

    @EntityGraph(attributePaths = {"enrollments", "enrollments.student"})
    Optional<Course> findWithEnrollmentsById(Long course_id);

    @EntityGraph(attributePaths = {"enrollments", "enrollments.student"})
    Optional<Course> findWithEnrollmentsByCourseCode(String courseCode);
}
