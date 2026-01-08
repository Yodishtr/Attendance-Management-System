package com.yodishtr.ams.controllers;

import com.yodishtr.ams.services.EnrollmentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;
    public EnrollmentController(EnrollmentService enrollmentService){
        this.enrollmentService = enrollmentService;
    }

    @PostMapping
    public String enroll(@RequestParam Long studentId, @RequestParam Long courseId, RedirectAttributes redirectAttributes){
        try {
            enrollmentService.createEnrollment(studentId, courseId);
            redirectAttributes.addFlashAttribute("successMessage", "Enrollment Successful");
        } catch (Exception e){
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/students/" + studentId;
    }

    @PostMapping("/{enrollmentId}/deactivate")
    public String deactivateEnrollment(@PathVariable Long enrollmentId, @RequestParam Long studentId, RedirectAttributes redirectAttributes){
        try {
            enrollmentService.deactivateEnrollment(enrollmentId);
            redirectAttributes.addFlashAttribute("successMessage", "Enrollment deactivated");
        } catch (IllegalArgumentException e){
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/students/" + studentId;
    }
}
