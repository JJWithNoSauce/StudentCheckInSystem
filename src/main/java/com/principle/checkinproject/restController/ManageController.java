package com.principle.checkinproject.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.principle.checkinproject.model.Student;
import com.principle.checkinproject.model.Subject;
import com.principle.checkinproject.model.Teacher;
import com.principle.checkinproject.service.SubjectManage;
import com.principle.checkinproject.service.TeacherManage;
import com.principle.checkinproject.service.StudentManage;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/manage")
public class ManageController {
    @Autowired
    private TeacherManage teacherManage;
    @Autowired
    private SubjectManage subjectManage;
    @Autowired
    private StudentManage studentManage;

    @PostMapping("/teacher/add")
    public Teacher createTeacher(@RequestBody Teacher teacher){
        return teacherManage.addInstructor(teacher);
    }
    
    @DeleteMapping("/teacher/remove/{teacherId}")
    public void removeTeacher(@PathVariable String teacherId){
        teacherManage.deleteTeacherById(teacherId);
    }

    @PostMapping("/student/add")
    public Student createStudent(@RequestBody Student student){
        return studentManage.addStudent(student);
    }
    
    @DeleteMapping("/student/remove/{studentId}")
    public void removeStudent(@PathVariable String studentId){
        studentManage.removeStudent(studentId);
    }

    @PostMapping("/classroom/{teacherId}/subject/add")
    public Subject classroomAddSubject(@PathVariable String techerId, @RequestBody Subject subject){
        return subjectManage.createSubject(subject);
    }
    
    @DeleteMapping("/classroom/{teacherId}/subject/remove/{subjectId}")
    public void classroomRemoveSubject(@PathVariable String techerId, @PathVariable String subjectId){
        subjectManage.deleteSubject(subjectId);
    }
    
    @PutMapping("/subeject/{subjectId}/student/register/{studentId}")
    public Student registerStudentToSubject(@PathVariable String subjectId, @PathVariable String studentId){
        Student student = studentManage.getStudentById(studentId).orElse(null);
        Subject subject = subjectManage.getSubjectById(subjectId);
        subjectManage.registerStudent(student, subject);
        return student;
    }

    @DeleteMapping("/subeject/{subjectId}/student/deregister/{studentId}")
    public Student deregisterStudentToSubject(@PathVariable String subjectId, @PathVariable String studentId){
        Student student = studentManage.getStudentById(studentId).orElse(null);
        Subject subject = subjectManage.getSubjectById(subjectId);
        subjectManage.deregisterStudent(student, subject);
        return student;
    }

}

