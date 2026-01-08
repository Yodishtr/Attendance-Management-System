package com.yodishtr.ams.controllers;

import com.yodishtr.ams.services.CourseService;
import com.yodishtr.ams.services.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;
    private final CourseService courseService;
    public StudentController(StudentService studentService, CourseService courseService){
        this.studentService = studentService;
        this.courseService = courseService;

    }

    @GetMapping
    public String allStudents(Model model){
        model.addAttribute("students", studentService.getAllStudents());
        return "student/list";
    }

    @GetMapping("/{studentId}")
    public String studentDetails(@PathVariable Long studentId, Model model){
        model.addAttribute("student", studentService.getStudentById(studentId));
        model.addAttribute("courses", courseService.getAllCourses());
        return "student/detail";
    }


}
