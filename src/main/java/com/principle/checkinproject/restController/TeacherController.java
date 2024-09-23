package com.principle.checkinproject.restController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.principle.checkinproject.model.Subject;
import com.principle.checkinproject.model.Teacher;
import com.principle.checkinproject.service.TeacherManage;



@RestController
@RequestMapping("/api/teachers/")
public class TeacherController {
    @Autowired
    private TeacherManage teacherManage;

    @GetMapping
    public List<Teacher> getAllTeacher(){
        return teacherManage.getAllTeachers();
    }

    @GetMapping("subjects/{teacherId}")
    private List<Subject> getAllSubject(@PathVariable String teacherId){
       Teacher teacher = teacherManage.getTeacherById(teacherId);
       return teacher.getClassRoom().getSubjects();
    }

    @GetMapping("/{teacherId}")
    public Teacher getTeacherById(@PathVariable String teacherId){
        return teacherManage.getTeacherById(teacherId);
    }

    
    
}
