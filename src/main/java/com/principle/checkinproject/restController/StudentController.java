package com.principle.checkinproject.restController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.principle.checkinproject.model.Student;
import com.principle.checkinproject.service.StudentManage;




@RestController
@RequestMapping("/api/students/")
public class StudentController {
    @Autowired
    private StudentManage studentManage;

    @GetMapping
    public List<Student> getAllStudents(){
        return studentManage.getAllStudents();
    }



    @GetMapping("/{studentId}")
    public Student getstudentById(@PathVariable String studentId){
        return studentManage.getStudentById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentId));
    }

    
    
}
