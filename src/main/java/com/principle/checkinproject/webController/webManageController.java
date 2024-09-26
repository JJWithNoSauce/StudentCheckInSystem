package com.principle.checkinproject.webController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.principle.checkinproject.webService.webClientManageService;

import java.util.List;

import com.principle.checkinproject.model.Teacher;
import com.principle.checkinproject.model.CheckIn;
import com.principle.checkinproject.model.Student;
import com.principle.checkinproject.model.Subject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class webManageController {
    @Autowired
    webClientManageService webClientManageService;

    @GetMapping("/teacher")
    public String getAllTeachers(Model model) {

        List<Teacher> teachers = webClientManageService.getAllTeacher().block();
        model.addAttribute("teachers", teachers);
        return "teacherlist";
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


    @GetMapping("/subjects")
    public String getAllSubjects(Model model) {
        webClientManageService.getAllSubject()
                .doOnError(error -> model.addAttribute("error", "Failed to load subjects."))
                .subscribe(subjects -> model.addAttribute("subjects", subjects));
        return "subjectlist";
    }


    @DeleteMapping("/subject/remove")
    public void removeSubject(@RequestParam String teacherId, @RequestParam String subjectId, Model model) {
        webClientManageService.classroomRemoveSubject(teacherId, subjectId).block();
    }

    @GetMapping("/attendanthistory/{teacherId}/{subjectId}")
    public String shotCheckin(@PathVariable String subjectId, @PathVariable String teacherId, Model model) {
        Teacher teacher = webClientManageService.getTeacherById(teacherId).block();
        Subject subject = webClientManageService.getSubject(subjectId).block();
        List<CheckIn> checkin = webClientManageService.getAllSubjectCheckIn(subjectId).block();
        System.out.println(teacher);
        System.out.println(subject);
        System.out.println(checkin);
        
        if (subject == null || checkin == null || teacher == null) {
            // If the list is null or empty, log and add a message to the model
            System.out.println("either subject , or list of check in were found");
            return "redirect:/failedNoCheckinList";
        }
        else{
            model.addAttribute("subject", subject);
            model.addAttribute("checkin", checkin);
            model.addAttribute("teacher", teacher);
        }
        return "attendanthistory";
    }

    @DeleteMapping("/teacher/remove")
    public void removeTeacher(@RequestParam String teacherId, Model model) {
        webClientManageService.classroomRemoveTeacher(teacherId).block();
    }

    @GetMapping("/subject/{teacherId}/{subjectId}/attendantform")
    public String showAttendantForm(@PathVariable String teacherId, @PathVariable String subjectId, Model model) {
        Teacher teacher = webClientManageService.getTeacherById(teacherId).block();
        Subject subject = webClientManageService.getSubject(subjectId).block();

        // Get the list of students and handle the case where the list is null or empty
        List<Student> students = webClientManageService.getSubjectStudents(subject.getSbjID()).block();
        System.out.println(students);

        if (students == null || students.isEmpty()) {
            // If the list is null or empty, log and add a message to the model
            System.out.println("No students found for this subject.");
            return "redirect:/failedNoStudentInSubject";
        } else {
            model.addAttribute("students", students);
        }

        // Pass the teacher and subject data to the model
        model.addAttribute("teacher", teacher);
        model.addAttribute("subject", subject);
        
        return "subjectattendant";
    }


    //error handler
    @GetMapping("/failedNoStudentInSubject")
    public String failedNoStudentInSubject(Model model) {
        model.addAttribute("message", "No students enrolled for this subject. Please add some student into your class before continuing");
        return "failed";
    }

    @GetMapping("/failedNoCheckinList")
    public String failedNoCheckinList(Model model) {
        model.addAttribute("message", "This subject's attendance's never been registered , Or either teacher or subject were found. To access history, Please register at least one attendance");
        return "failed";
    }
    /*

    @GetMapping("/admin/{tEmp}")
    public String getMethodName(@PathVariable String tEmp) {
        return "teachermanager";
    }                                                                   
    
    */
    
    @GetMapping("/admin")
    public String go_admin() {
        return "admin";
    }
    
    @GetMapping("/teachermanager")
    public String go_teachermanager(Model model) {
        List<Teacher> teachers = webClientManageService.getAllTeacher().block();
        model.addAttribute("teachers", teachers);
        return "teachermanager";
    }
    
    @GetMapping("/studentmanager")
    public String go_studentmanager() {
        return "studentmanager";
    }


}

