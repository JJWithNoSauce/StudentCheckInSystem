package com.principle.checkinproject.restController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.principle.checkinproject.model.Student;
import com.principle.checkinproject.service.StudentManage;




@RestController
@RequestMapping("/api/students")
public class StudentController {
    @Autowired
    private StudentManage studentManage;

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentManage.getAllStudents();
        return ResponseEntity.ok(students);
    }



    @GetMapping("/{studentId}")
    public Student getstudentById(@PathVariable String studentId){
        return studentManage.getStudentById(studentId);
    }

    
    
}
