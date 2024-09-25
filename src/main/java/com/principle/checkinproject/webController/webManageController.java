package com.principle.checkinproject.webController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.result.view.Rendering;

import com.principle.checkinproject.webService.webClientManageService;

import reactor.core.publisher.Mono;
import java.util.List;

import com.principle.checkinproject.model.Teacher;
import com.principle.checkinproject.model.Student;
import com.principle.checkinproject.model.Subject;

@Controller
public class webManageController {
    @Autowired
    webClientManageService webClientManageService;

    @GetMapping("/teacher")
    public String getAllTeachers(Model model) {

        webClientManageService.getAllTeacher()
                .doOnError(error -> model.addAttribute("error", "Failed to load teachers."))
                .subscribe(teachers -> model.addAttribute("teachers", teachers));
        return "teacherlist";
    }

    @PostMapping("/teacher/add")
    public String addTeacher(@RequestParam String name, Model model) {
        Teacher teacher = new Teacher();
        teacher.setName(name);
        webClientManageService.createTeacher(teacher)
                .doOnError(error -> model.addAttribute("error", "Failed to add teacher."))
                .subscribe();
        return "redirect:/teacher";
    }

    @GetMapping("/teacher/{id}")
    public String getTeacher(@PathVariable String id, Model model) {
        Teacher teacher = webClientManageService.getTeacherById(id).block();
        List<Subject> subjects = webClientManageService.getAllSubjectByTeacher(id).block();
        model.addAttribute("teacher", teacher);
        model.addAttribute("subjects", subjects);
        return "teacher";
    }

    @PostMapping("/teacher/update")
    public String updateTeacher(@RequestParam String id, @RequestParam String name, Model model) {
        Teacher teacher = new Teacher();
        teacher.setTeacherID(id);
        teacher.setName(name);
        webClientManageService.updateTeacher(teacher)
                .doOnError(error -> model.addAttribute("error", "Failed to update teacher."))
                .subscribe();
        return "redirect:/teacher";
    }

    @PostMapping("/teacher/delete")
    public String deleteTeacher(@RequestParam String id, Model model) {
        webClientManageService.deleteTeacher(id)
                .doOnError(error -> model.addAttribute("error", "Failed to delete teacher."))
                .subscribe();
        return "redirect:/teacher";
    }

    @GetMapping("/students")
    public String getAllStudents(Model model) {
        webClientManageService.getAllStudents()
                .doOnError(error -> model.addAttribute("error", "Failed to load students."))
                .subscribe(students -> model.addAttribute("students", students));
        return "studentlist";
    }

    @PostMapping("/student/add")
    public String addStudent(@RequestParam String name, Model model) {
        Student student = new Student();
        student.setName(name);
        webClientManageService.createStudent(student)
                .doOnError(error -> model.addAttribute("error", "Failed to add student."))
                .subscribe();
        return "redirect:/students";
    }

    @GetMapping("/subjects")
    public String getAllSubjects(Model model) {
        webClientManageService.getAllSubject()
                .doOnError(error -> model.addAttribute("error", "Failed to load subjects."))
                .subscribe(subjects -> model.addAttribute("subjects", subjects));
        return "subjectlist";
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
        webClientManageService.classroomAddSubject(teacherId, subject)
                .doOnError(error -> model.addAttribute("error", "Failed to add subject."))
                .subscribe();
        return "redirect:/teacher/" + teacherId;
    }
}
