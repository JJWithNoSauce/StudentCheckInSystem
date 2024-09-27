package com.principle.checkinproject.webService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.principle.checkinproject.model.*;
import reactor.core.publisher.Mono;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class webClientManageService {
    @Autowired
    private final WebClient webClient;

    private final ObjectMapper objectMapper;

    public webClientManageService(WebClient webClient, ObjectMapper objectMapper) {
        this.webClient = webClient;
        this.objectMapper = objectMapper;
    }

    public Mono<TeacherDTO> getTeacherById(String teacherId) {
        return webClient.get()
                .uri("/teacher/{id}", teacherId)
                .retrieve()
                .bodyToMono(TeacherDTO.class);
    }

    public Mono<List<TeacherDTO>> getAllTeacher() {
        return webClient.get()
                .uri("/teacher")
                .retrieve()
                .bodyToFlux(TeacherDTO.class)
                .collectList();
    }

    public Mono<List<Subject>> getAllSubjectByTeacher(String teacherId) {
        return webClient.get()
                .uri("/teacher/subjects/{teacherId}", teacherId)
                .retrieve()
                .bodyToFlux(Subject.class)
                .collectList();
    }

    public Mono<Student> getStudentById(String studentId) {
        return webClient.get()
                .uri("/students/{id}", studentId)
                .retrieve()
                .bodyToMono(Student.class);
    }

    public Mono<Teacher> createTeacher(Teacher teacher) {
        return webClient.post()
                .uri("/manage/teacher/add")
                .bodyValue(teacher)
                .retrieve()
                .bodyToMono(Teacher.class);
    }

    public Mono<Void> removeTeacher(String teacherId) {
        return webClient.delete()
                .uri("/teacher/{id}", teacherId)
                .retrieve()
                .bodyToMono(Void.class);
    }

    public Mono<Student> createStudent(Student student) {
        return webClient.post()
                .uri("/manage/student/add")
                .bodyValue(student)
                .retrieve()
                .bodyToMono(Student.class);
    }

    public Mono<Void> removeStudent(String studentId) {
        return webClient.delete()
                .uri("/manage/student/{id}", studentId)
                .retrieve()
                .bodyToMono(Void.class);
    }

    public Mono<Subject> classroomAddSubject(String teacherId, Subject subject) {
        return webClient.post()
                .uri("/manage/classroom/{id}/subject/add", teacherId)
                .bodyValue(subject)
                .retrieve()
                .bodyToMono(Subject.class);
    }

    public Mono<Void> classroomRemoveSubject(String teacherId, String subjectId) {
        return webClient.delete()
                .uri("/manage/classroom/{teacherId}/subject/remove/{subjectId}", teacherId, subjectId)
                .retrieve()
                .bodyToMono(Void.class);
    }

    public Mono<Void> classroomRemoveTeacher(String teacherId) {
        return webClient.delete()
                .uri("/manage/teacher/remove/{teacherId}", teacherId)
                .retrieve()
                .bodyToMono(Void.class);
    }

    public Mono<Student> registerStudentToSubject(String subjectId, String studentId) {
        return webClient.put()
                .uri("/subjects/{subjectId}/students/{studentId}", subjectId, studentId)
                .retrieve()
                .bodyToMono(Student.class);
    }

    public Mono<Student> deregisterStudentToSubject(String subjectId, String studentId) {
        return webClient.put()
                .uri("/manage/subeject/{subjectId}/student/deregister/{studentId}", subjectId, studentId)
                .retrieve()
                .bodyToMono(Student.class);
    }

    public Mono<List<Subject>> getAllSubject() {
        return webClient.get()
                .uri("/subject")
                .retrieve()
                .bodyToFlux(Subject.class)
                .collectList();
    }

    public Mono<Subject> getSubject(String subjectId) {
        return webClient.get()
                .uri("/subjects/{id}", subjectId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(jsonString -> {
                    try {
                        Subject subject = objectMapper.readValue(jsonString, Subject.class);
                        return Mono.just(subject);
                    } catch (JsonProcessingException e) {
                        System.err.println("Error parsing subject JSON: " + e.getMessage());
                        return Mono.empty();
                    }
                })
                .onErrorResume(e -> {
                    System.err.println("Error fetching subject: " + e.getMessage());
                    return Mono.empty();
                });
    }

    public Mono<CheckIn> checking(String sbjId, List<Attendance> attendances) {
        return webClient.post()
                .uri("/subjects/{id}/checking", sbjId)
                .bodyValue(attendances)
                .retrieve()
                .bodyToMono(CheckIn.class);
    }

    public Mono<List<Student>> getSubjectStudents(String subjectId) {
        return webClient.get()
                .uri("/subjects/{id}/students", subjectId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(new ParameterizedTypeReference<Student>() {})
                .collectList()
                ;
    }

    public Mono<List<CheckIn>> getAllSubjectCheckIn(String subjectId) {
        return webClient.get()
                .uri("/subjects/{id}/checkins", subjectId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<CheckIn>>() {})
                .onErrorResume(e -> {
                    System.err.println("Error fetching check-ins: " + e.getMessage());
                    return Mono.empty();
                });
    }

    public Mono<CheckIn> getSubjectCheckIn(String subjectId, int period) {
        return webClient.get()
                .uri("/subject/{id}/checkin/{period}", subjectId, period)
                .retrieve()
                .bodyToMono(CheckIn.class);
    }

    public Mono<List<Student>> getAllStudents() {
        return webClient.get()
                .uri("/students")
                .retrieve()
                .bodyToFlux(Student.class)
                .collectList();
    }

    public Mono<Void> deleteTeacher(String teacherId) {
        return webClient.delete()
                .uri("/teacher/{id}", teacherId)
                .retrieve()
                .bodyToMono(Void.class);
    }
}
