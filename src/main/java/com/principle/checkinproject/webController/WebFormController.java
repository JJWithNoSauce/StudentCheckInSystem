package com.principle.checkinproject.webController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.principle.checkinproject.model.Student;
import com.principle.checkinproject.model.Subject;
import com.principle.checkinproject.model.Teacher;
import com.principle.checkinproject.webService.webClientManageService;

@Controller
public class WebFormController {
    @Autowired
    webClientManageService webClientManageService;

        @GetMapping("/studentmanager/studentadd")
    public String showAddStudentForm(Model model) {
        return "studentadd";
    }

    @PostMapping("/student/add")
    public String addStudent(@RequestParam String name, @RequestParam String stdID, Model model) { 
    Student student = new Student();
    student.setName(name);
    student.setStdID(stdID); // Corrected to set stdID instead of name
    webClientManageService.createStudent(student).block();
    return "redirect:/students/manage";
}

    @GetMapping("/teachermanager/teacheradd")
    public String showAddTeacherForm(Model model) {
        return "teacheradd";
    }

    @PostMapping("/teacher/add")
    public String addTeacher(@RequestParam String name,@RequestParam String teacherID, Model model) {
        Teacher teacher = new Teacher();
        teacher.setName(name);
        teacher.setTeacherID(teacherID);
        webClientManageService.createTeacher(teacher).block();
        return "redirect:/teacher";
    }

    @GetMapping("/subject/{teacherId}/add")
    public String showAddSubjectForm(@PathVariable String teacherId, Model model) {
        System.out.println(teacherId);
        model.addAttribute("teacherId", teacherId);
        return "subjectadd";
    }

    @PostMapping("/subject/add")
    public String addSubject(@RequestParam String teacherId, @RequestParam String subjectId,
            @RequestParam String subjectName, @RequestParam String subjectTime, Model model) {
        Subject subject = new Subject();
        System.out.println(teacherId + "-------------------------add sub");
        subject.setSbjID(subjectId);
        subject.setName(subjectName);
        subject.setTime(subjectTime);
        try {
            webClientManageService.classroomAddSubject(teacherId, subject)
                    .doOnError(error -> model.addAttribute("error", "Failed to add subject."))
                    .block(); // Wait for the operation to complete
        } catch (Exception e) {
            model.addAttribute("error", "Failed to add subject.");
            return "subjectadd"; // Return to the form in case of error
        }
        return "redirect:/teacher/" + teacherId;
    }

    @GetMapping("/subject/{subjectId}/addstudent")
    public String showAddStudentToSubjectForm(@PathVariable String subjectId,Model model) {
        model.addAttribute("subjectId", subjectId);
        return "subjectstudentadd";
    }

    @GetMapping("/subject/studentadd")
    public String addStudenttoSubject(@RequestParam String subjectID,@RequestParam String stdID, Model model) {
        System.out.println("--------------+++" + subjectID);
        webClientManageService.registerStudentToSubject(subjectID, stdID).block();
        return "redirect:/students";
    }
}

