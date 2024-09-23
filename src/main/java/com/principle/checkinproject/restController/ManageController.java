package com.principle.checkinproject.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.principle.checkinproject.model.ClassRoom;
import com.principle.checkinproject.model.Student;
import com.principle.checkinproject.model.Subject;
import com.principle.checkinproject.model.Teacher;
import com.principle.checkinproject.service.SubjectManage;
import com.principle.checkinproject.service.TeacherManage;
import com.principle.checkinproject.service.ClassRoomManage;
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
    @Autowired
    private ClassRoomManage classRoomManage;

    // สร้างอาจารย์ใหม่
    @PostMapping("/teacher/add")
    public Teacher createTeacher(@RequestBody Teacher teacher){
        return teacherManage.addInstructor(teacher);
    }
    
    // ลบอาจารย์โดยใช้ ID  localhost:8080/api/manage/teacher/remove/{teacherId}
    @DeleteMapping("/teacher/remove/{teacherId}")
    public void removeTeacher(@PathVariable String teacherId){
        teacherManage.deleteTeacherById(teacherId);
    }

    // สร้างนักเรียนใหม่
    @PostMapping("/student/add")
    public Student createStudent(@RequestBody Student student){
        return studentManage.addStudent(student);
    }
    
    // ลบนักเรียนโดยใช้ ID
    @DeleteMapping("/student/remove/{studentId}")
    public void removeStudent(@PathVariable String studentId){
        studentManage.removeStudent(studentId);
    }

    // เพิ่มวิชาในห้องเรียนโดยใช้ ID ของอาจารย์
    @PostMapping("/classroom/{teacherId}/subject/add")//ใช้ได้
    public Subject classroomAddSubject(@PathVariable String teacherId, @RequestBody Subject subject){
        ClassRoom classRoom = teacherManage.getTeacherById(teacherId).getClassRoom();
        return classRoomManage.addSubject(classRoom,subject);
    }
    
    // ลบวิชาในห้องเรียนโดยใช้ ID ของอาจารย์และวิชา
    @DeleteMapping("/classroom/{teacherId}/subject/remove/{subjectId}")//ใช้ได้
    public void classroomRemoveSubject(@PathVariable String teacherId, @PathVariable String subjectId){
        ClassRoom classRoom = teacherManage.getTeacherById(teacherId).getClassRoom();
        Subject sbj = subjectManage.getSubjectById(subjectId);
        classRoomManage.removeSubject(classRoom,sbj);
    }
    
    // ลงทะเบียนนักเรียนในวิชาโดยใช้ ID ของวิชาและนักเรียน
    @PutMapping("/subeject/{subjectId}/student/register/{studentId}")
    public Student registerStudentToSubject(@PathVariable String subjectId, @PathVariable String studentId){
        Student student = studentManage.getStudentById(studentId);
        Subject subject = subjectManage.getSubjectById(subjectId);
        subjectManage.registerStudent(student, subject);
        return student;
    }

    // ยกเลิกการลงทะเบียนนักเรียนในวิชาโดยใช้ ID ของวิชาและนักเรียน
    @PutMapping("/subeject/{subjectId}/student/deregister/{studentId}")
    public Student deregisterStudentToSubject(@PathVariable String subjectId, @PathVariable String studentId){
        Student student = studentManage.getStudentById(studentId);
        Subject subject = subjectManage.getSubjectById(subjectId);
        subjectManage.deregisterStudent(student, subject);
        return student;
    }

}

