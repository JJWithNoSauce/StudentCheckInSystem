package com.principle.checkinproject.webController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.principle.checkinproject.webService.webClientManageService;

import reactor.core.publisher.Mono;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import com.principle.checkinproject.model.TeacherDTO;
import com.principle.checkinproject.model.Attendance;
import com.principle.checkinproject.model.CheckIn;
import com.principle.checkinproject.model.Student;
import com.principle.checkinproject.model.Subject;
import com.principle.checkinproject.model.Teacher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class webManageController {
    private static final Logger logger = LoggerFactory.getLogger(webManageController.class);

    @Autowired
    webClientManageService webClientManageService;

    @GetMapping("/teacher")
    public String getAllTeachers(Model model) {

        List<TeacherDTO> teachers = webClientManageService.getAllTeacher().block();
        model.addAttribute("teachers", teachers);
        return "teacherlist";
    }

    @GetMapping("/teacher/{id}")
    public String getTeacher(@PathVariable String id, Model model) {
        TeacherDTO teacher = webClientManageService.getTeacherById(id).block();
        List<Subject> subjects = webClientManageService.getAllSubjectByTeacher(id).block();
        model.addAttribute("teacher", teacher);
        model.addAttribute("subjects", subjects);
        return "teacher";
    }
    
    @GetMapping("/teachermanager/teacher/{id}")
    public String getAdminTeacher(@PathVariable String id, Model model) {
        TeacherDTO teacher = webClientManageService.getTeacherById(id).block();
        List<Subject> subjects = webClientManageService.getAllSubjectByTeacher(id).block();
        model.addAttribute("teacher", teacher);
        model.addAttribute("subjects", subjects);
        return "adminmanageteacher";
    }

    @GetMapping("/students/manage")
    public String Studentsmanage(Model model) {
        List<Student> students = webClientManageService.getAllStudents().block();
        model.addAttribute("students", students);
        return "studentmanager";
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
    public String showCheckin(@PathVariable String subjectId, @PathVariable String teacherId, Model model) {
        logger.info("Fetching attendance history for teacher {} and subject {}", teacherId, subjectId);
        System.out.println(teacherId + subjectId + "555555555555555555555555555555555555555555555555555555555555");

        try {
            TeacherDTO teacher = webClientManageService.getTeacherById(teacherId).block();
            Subject subject = webClientManageService.getSubject(subjectId).block();
            System.out.println(subject + "555555555555555555555555555555555566666666666666666666666666666");
            List<CheckIn> checkins = webClientManageService.getAllSubjectCheckIn(subjectId).block();

            if (teacher == null) {
                logger.error("Teacher not found for ID: {}", teacherId);
                model.addAttribute("error", "Teacher not found");
                return "error";
            }

            if (subject == null) {
                logger.error("Subject not found for ID: {}", subjectId);
                model.addAttribute("error", "Subject not found");
                return "error";
            }

            if (checkins == null) {
                logger.error("Failed to retrieve check-ins for subject: {}", subjectId);
                model.addAttribute("error", "Failed to retrieve check-ins");
                return "error";
            }

            if (checkins.isEmpty()) {
                logger.info("No check-ins available for subject: {}", subjectId);
                model.addAttribute("message", "No attendance records found for this subject");
                return "failedNoCheckinList";
            }

            model.addAttribute("subject", subject);
            model.addAttribute("teacher", teacher);
            model.addAttribute("checkin", checkins);

            return "attendanthistory";
        } catch (Exception e) {
            logger.error("Error occurred while fetching attendance history", e);
            model.addAttribute("error", "An unexpected error occurred: " + e.getMessage());
            return "error";
        }
    }

    // ... other methods remain unchanged ...

    @GetMapping("/attendanthistoryview/{teacherId}/{subjectId}/{checkinId}")
    public String showAttendantHistoryView(@PathVariable String subjectId, @PathVariable String teacherId,
            @PathVariable int checkinId, Model model) {
        TeacherDTO teacher = webClientManageService.getTeacherById(teacherId).block();
        Subject subject = webClientManageService.getSubject(subjectId).block();
        CheckIn checkin = webClientManageService.getSubjectCheckIn(subjectId, checkinId).block();
        List<Attendance> attendances = checkin.getAttendances();
        List<Student> students = webClientManageService.getSubjectStudents(subjectId).block();
        List<Student> studentsA = new ArrayList<Student>();
        
        for (Attendance attendance : attendances) {
            for (Student student : students) {
                if(student.getAttendance().contains(attendance)) {
                    studentsA.add(student);
                    System.out.println(student);
                }
            }
        }


        System.out.println(teacher);
        System.out.println(subject);
        System.out.println(checkin);
        System.out.println(attendances);

        if (subject == null || checkin == null || teacher == null || attendances == null) {
            System.out.println("Required data not found");
            return "redirect:/failedNoCheckinList";
        } else {
            model.addAttribute("subject", subject);
            model.addAttribute("checkin", checkin);
            model.addAttribute("teacher", teacher);
            model.addAttribute("attendant", attendances);
            model.addAttribute("students", studentsA);
        }
        return "attendanthistoryview";
    }

    @DeleteMapping("/teacher/remove")
    public void removeTeacher(@RequestParam String teacherId, Model model) {
        webClientManageService.classroomRemoveTeacher(teacherId).block();
    }

    @GetMapping("/subject/{teacherId}/{subjectId}/attendantform")
    public String showAttendantForm(@PathVariable String teacherId, @PathVariable String subjectId, Model model) {
        TeacherDTO teacher = webClientManageService.getTeacherById(teacherId).block();
        Subject subject = webClientManageService.getSubject(subjectId).block();

        List<Student> student = webClientManageService.getSubjectStudents(subjectId).block();
        System.out.println(student);

        if (student == null || student.isEmpty()) {
            System.out.println("No students found for this subject.");
            return "redirect:/failedNoStudentInSubject";
        }

        model.addAttribute("teacherId", teacherId); // Add this line
        model.addAttribute("teacher", teacher);
        model.addAttribute("subject", subject);
        model.addAttribute("student", student);

        return "subjectattendant";
    }

    @GetMapping("/failedNoStudentInSubject")
    public String failedNoStudentInSubject(Model model) {
        model.addAttribute("message",
                "No students enrolled for this subject. Please add some student into your class before continuing");
        return "failed";
    }

    @GetMapping("/failedNoCheckinList")
    public String failedNoCheckinList(Model model) {
        model.addAttribute("message",
                "This subject's attendance's never been registered , Or either teacher or subject were found. To access history, Please register at least one attendance");
        return "failed";
    }

    @GetMapping("/admin")
    public String go_admin() {
        return "admin";
    }

    @GetMapping("/teachermanager")
    public String go_teachermanager(Model model) {
        List<TeacherDTO> teachers = webClientManageService.getAllTeacher().block();
        model.addAttribute("teachers", teachers);
        return "teachermanager";
    }

    @GetMapping("/studentmanager")
    public String go_studentmanager(Model model) {
        List<Student> students = webClientManageService.getAllStudents().block();
        model.addAttribute("students", students);
        return "studentmanager";
    }

    @GetMapping("/subject/{subjectId}/manage")
    public String subjectStudentManage(@PathVariable String subjectId, Model model) {
        List<Student> students = webClientManageService.getSubjectStudents(subjectId).block();
        Subject sbj = webClientManageService.getSubject(subjectId).block();
        System.out.println("*-*-*-*-*-*-*-*-*--*-"+students);
        model.addAttribute("students", students);
        model.addAttribute("subject", sbj);
        return "subjectstudent";
    }

    @PostMapping("/student/delete/{stdID}")
    public String deleteStudent(@PathVariable String stdID) {
        webClientManageService.removeStudent(stdID);
        return "redirect:/students/manage";
    }

    // Inner class to hold combined student and attendance information
    public class AttendanceRecord {
        private Student student;
        private Attendance attendance;
        private int period;

        public AttendanceRecord(Student student, Attendance attendance, int period) {
            this.student = student;
            this.attendance = attendance;
            this.period = period;
        }

        // Getters
        public Student getStudent() {
            return student;
        }

        public Attendance getAttendance() {
            return attendance;
        }

        public int getPeriod() {
            return period;
        }
    }
}
