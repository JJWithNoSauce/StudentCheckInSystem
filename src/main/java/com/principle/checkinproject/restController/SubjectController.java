package com.principle.checkinproject.restController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.principle.checkinproject.model.Attendance;
import com.principle.checkinproject.model.CheckIn;
import com.principle.checkinproject.model.Student;
import com.principle.checkinproject.model.Subject;
import com.principle.checkinproject.service.SubjectManage;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {
    @Autowired
    private SubjectManage subjectManage;

    @GetMapping
    private List<Subject> getAllSubject(){
       return subjectManage.getAllSubjects();
    }

    @GetMapping("/{subjectId}")
    private Subject getSubject(@PathVariable String subjectId){
       return subjectManage.getSubjectById(subjectId);
    }

    @PostMapping("/{subjectId}/checking")
    public CheckIn checking(@PathVariable String sbjId,@RequestBody List<Attendance> attendances) {
        CheckIn check = subjectManage.checkInStudent(sbjId, attendances);
        
        return check;
    }

    @GetMapping("/{subjectId}/students")
    public List<Student> getSubjectStudents(@PathVariable String subjectId){
        return subjectManage.getAllStudentsInSubject(subjectId);
    }

    @GetMapping("/{subjectId}/checkins")
    public List<CheckIn> getAllSubjectCheckIn(@PathVariable String subjectId){
        return subjectManage.getAllCheckInInSubject(subjectId);
    }

    @GetMapping("/{subjectId}/checkins/{period}")
    public CheckIn getSubjectCheckIn(@PathVariable String subjectId, @PathVariable int period){
        return subjectManage.getCheckInInSubject(subjectId, period);
    }
}
