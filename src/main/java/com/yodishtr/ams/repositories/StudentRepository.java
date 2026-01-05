package com.yodishtr.ams.repositories;

import com.yodishtr.ams.entities.Student;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByEmail(String email);
    Optional<Student> findByPhone(Integer phone);
    Optional<Student> findByAddress(String address);
    List<Student> findByFullName(String fullname);
    List<Student> findByAge(Integer age);
    boolean existsByFullName(@Param("fullname")String fullname);

    @Query("""
            select s 
            from Student s
            where s.phone = :phone and s.email = :email_add
            """)
    List<Student> findByEmailAndPhone(@Param("email_add")String email, @Param("phone") Integer phone);

    @EntityGraph(attributePaths = {"enrollments"})
    @Query("""
            select s
            from Student s
            where s.fullName = :fullName and s.age = :age and s.phone = :phone and s.email = :email and s.address = :address
            """)
    Optional<Student> completeSearch(@Param("fullname") String fullname, @Param("age") Integer age,
                                     @Param("phone") Integer phone, @Param("email") String email,
                                     @Param("address") String address);

    @EntityGraph(attributePaths = {"enrollments"})
    Optional<Student> findById(Long student_id);

    @EntityGraph(attributePaths = {"enrollments", "enrollments.course"})
    @Query("""
            select s
            from Student s
            where s.id = :student_id
            """)
    Optional<Student> findByIdWithEnrollmentsAndCourse(@Param("student_id") Long student_id);
}
