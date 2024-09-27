package com.principle.checkinproject.webController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.principle.checkinproject.model.Attendance;
import com.principle.checkinproject.model.CheckIn;
import com.principle.checkinproject.model.Student;
import com.principle.checkinproject.model.Subject;
import com.principle.checkinproject.model.Teacher;
import com.principle.checkinproject.webService.webClientManageService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

@Controller
public class WebFormController {
    private static final Logger logger = LoggerFactory.getLogger(WebFormController.class);

    @Autowired
    webClientManageService webClientManageService;

    @PostMapping("/submitattendance")
    public String submitAttendance(@RequestParam String teacherId,
            @RequestParam String subjectId,
            @RequestParam Map<String, String> formData,
            Model model) {
        // Fetch the subject
        System.out.println("+5+5+5+5+5+5+5+5+5+5+5"+subjectId);
        Subject subject = webClientManageService.getSubject(subjectId).block();
        if (subject == null) {
            model.addAttribute("error", "Subject not found");
            return "failed";
        }

        List<Attendance> attendances = new ArrayList<>();

        // Process form data
                for (Map.Entry<String, String> entry : formData.entrySet()) {
                    if (entry.getKey().startsWith("attendanceStatus_")) {
                        String studentId = entry.getKey().substring("attendanceStatus_".length());
                        String status = entry.getValue();
                        String note = formData.get("note_" + studentId);

                // Create Attendance object
                                Attendance attendance = new Attendance();
                attendance.setStudent(webClientManageService.getStudentById(studentId).block());
                                attendance.setStatus(status);
                                attendance.setNote(note);

                attendances.add(attendance);
            }
        }
        
        // Call the checking API to save the attendance data
        webClientManageService.checking(subjectId, attendances).block();

        return "redirect:/teacher/" + teacherId;
    }

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
    public String addTeacher(@RequestParam String name, @RequestParam String teacherID, Model model) {
        Teacher teacher = new Teacher();
        teacher.setName(name);
        teacher.setTeacherID(teacherID);
        webClientManageService.createTeacher(teacher).block();
        return "redirect:/teachermanager";
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
    public String showAddStudentToSubjectForm(@PathVariable String subjectId, Model model) {
        model.addAttribute("subjectId", subjectId);
        return "subjectstudentadd";
    }

    @GetMapping("/subject/studentadd")
    public String addStudenttoSubject(@RequestParam String subjectID, @RequestParam String stdID, Model model) {
        webClientManageService.registerStudentToSubject(subjectID, stdID).block();
        return "redirect:/subject/" + subjectID + "/manage";
    }

    @GetMapping("/subject/studentremove")
    public String removeStudenttoSubject(@RequestParam String subjectID, @RequestParam String stdID, Model model) {
        webClientManageService.deregisterStudentToSubject(subjectID, stdID).block();
        return "redirect:/subject/" + subjectID + "/manage";
    }
}
