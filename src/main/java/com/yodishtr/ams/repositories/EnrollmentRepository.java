package com.yodishtr.ams.repositories;

import com.yodishtr.ams.entities.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    List<Enrollment> findByActiveFalse();
    List<Enrollment> findByActiveTrue();

    List<Enrollment> findByStudentIdAndActiveTrue(Long studentId);
    List<Enrollment> findByStudentIdAndActiveFalse(Long studentId);

    List<Enrollment> findByCourseIdAndActiveTrue(Long courseId);
    List<Enrollment> findByCourseIdAndActiveFalse(Long courseId);

    Optional<Enrollment> findByStudentIdAndCourseId(Long studentId, Long courseId);

    @Query("""
            select e
            from Enrollment e
            join fetch e.student
            join fetch e.course
            where e.id = :id
            """)
    Optional<Enrollment> findByIdWithStudentAndCourse(@Param("id") Long id);

    @Query("""
            select e
            from Enrollment e
            join fetch e.course
            where e.student.id = :studentId and e.active = true
            """)
    Optional<Enrollment> findActiveByStudentIdWithCourse(@Param("student_id") Long studentId);

}
