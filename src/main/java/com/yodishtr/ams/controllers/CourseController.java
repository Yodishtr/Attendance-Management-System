package com.yodishtr.ams.controllers;

import com.yodishtr.ams.services.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;
    public CourseController(CourseService courseService){
        this.courseService = courseService;
    }

    @GetMapping
    public String allCourses(Model model){
        model.addAttribute("courses", courseService.getAllCourses());
        return "course/list";
    }

    @GetMapping("/{courseId}")
    public String courseDetails(@PathVariable Long courseId, Model model){
        model.addAttribute("course", courseService.getCourseWithEnrollmentById(courseId));
        return "course/details";
    }


}
