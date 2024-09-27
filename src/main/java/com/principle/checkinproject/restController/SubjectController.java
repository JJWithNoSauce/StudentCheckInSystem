package com.principle.checkinproject.restController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
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
    public ResponseEntity<Subject> getSubject(@PathVariable String subjectId){
       Subject subject = subjectManage.getSubjectById(subjectId);
       if (subject == null) {
           return ResponseEntity.notFound().build();
       }
       return ResponseEntity.ok(subject);
    }

    @SuppressWarnings("unchecked")
    @PostMapping("/{subjectId}/checking")
    public CheckIn checking(@PathVariable String subjectId, @RequestBody Map<String, Object> requestBody) {
  ObjectMapper mapper = new ObjectMapper();

    // Convert the list of attendances
    List<LinkedHashMap<String, Object>> attendanceMaps = (List<LinkedHashMap<String, Object>>) requestBody.get("attendances");
    List<Attendance> attendances = attendanceMaps.stream()
            .map(map -> mapper.convertValue(map, Attendance.class))
            .collect(Collectors.toList());

    // Convert the list of students
    List<LinkedHashMap<String, Object>> studentMaps = (List<LinkedHashMap<String, Object>>) requestBody.get("students");
    List<Student> students = studentMaps.stream()
            .map(map -> mapper.convertValue(map, Student.class))
            .collect(Collectors.toList());

    // Assign students to attendances
    for (int i = 0; i < attendances.size(); i++) {
        attendances.get(i).setStudent(students.get(i));
        System.out.println(attendances.get(i)+"1 2121212121");
    }

        return subjectManage.checkInStudent(subjectId, attendances);

    }

    @GetMapping("/{subjectId}/students")
    public ResponseEntity<List<Student>> getSubjectStudents(@PathVariable String subjectId){
        try {
            List<Student> students = subjectManage.getAllStudentsInSubject(subjectId);
            return ResponseEntity.ok(students);
        } catch (Exception e) {
            logger.error("Error retrieving students for subject {}: {}", subjectId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
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
            if (checkIn == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(checkIn);
        } catch (RuntimeException e) {
            logger.error("Error retrieving check-in for subject {} and period {}: {}", subjectId, period, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(null);
        }
    }
}
