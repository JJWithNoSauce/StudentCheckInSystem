package com.principle.checkinproject.restController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.principle.checkinproject.repository.StudentRespository;
import com.principle.checkinproject.repository.SubjectRepository;
import com.principle.checkinproject.service.SubjectManage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {
    private static final Logger logger = LoggerFactory.getLogger(SubjectController.class);

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired 
    private StudentRespository studentRespository;
    
    @Autowired
    private SubjectManage subjectManage;

    @GetMapping
    public ResponseEntity<List<Subject>> getAllSubject(){
       return ResponseEntity.ok(subjectManage.getAllSubjects());
    }

    @GetMapping("/{subjectId}")
    public Subject getSubject(@PathVariable String subjectId){
       Subject subject = subjectManage.getSubjectById(subjectId);
       return subject;
    }

    @PostMapping("/{subjectId}/checking")
    public CheckIn checking(@PathVariable String subjectId, @RequestBody List<Attendance> attendances) {
        logger.info("Received checking request for subject: {}", subjectId);
        System.out.println("9+9+6+85+54+598595    "+attendances.get(0).getStudent().getStdID());
        CheckIn check = subjectManage.checkInStudent(subjectId, attendances);
        return check;
    }

    @GetMapping("/{subjectId}/students")
    public List<Student> getSubjectStudents(@PathVariable String subjectId){
            List<Student> students = subjectManage.getAllStudentsInSubject(subjectId);
            return students;
    }

    @GetMapping("/{subjectId}/checkins")
    public ResponseEntity<?> getAllSubjectCheckIn(@PathVariable String subjectId){
        logger.info("Fetching all check-ins for subject: {}", subjectId);
        try {
            List<CheckIn> checkIns = subjectManage.getAllCheckInInSubject(subjectId);
            if (checkIns == null || checkIns.isEmpty()) {
                logger.info("No check-ins found for subject: {}", subjectId);
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(checkIns);
        } catch (RuntimeException e) {
            logger.error("Error retrieving check-ins for subject {}: {}", subjectId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error retrieving check-ins: " + e.getMessage());
        }
    }

    @GetMapping("/{subjectId}/checkins/{period}")
    public ResponseEntity<CheckIn> getSubjectCheckIn(@PathVariable String subjectId, @PathVariable int period){
        try {
            CheckIn checkIn = subjectManage.getCheckInInSubject(subjectId, period);
            return ResponseEntity.ok(checkIn);
        } catch (RuntimeException e) {
            logger.error("Error retrieving check-in for subject {} and period {}: {}", subjectId, period, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(null);
        }
    }
}
