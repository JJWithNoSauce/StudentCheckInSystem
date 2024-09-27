package com.principle.checkinproject.webService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import com.principle.checkinproject.model.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class webClientManageService {
    private static final Logger logger = LoggerFactory.getLogger(webClientManageService.class);

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

    public Mono<Void> removeStudent(String stdID) {
        return webClient.delete()
                .uri("/api/students/manage/remove/{stdID}", stdID)
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
                        logger.error("Error parsing subject JSON: {}", e.getMessage());
                        return Mono.empty();
                    }
                })
                .onErrorResume(e -> {
                    logger.error("Error fetching subject: {}", e.getMessage());
                    return Mono.empty();
                });
    }

    public Mono<CheckIn> checking(String sbjId, List<Attendance> attendances,List<Student> students) {
        logger.info("Checking attendances for subject: {}", sbjId);
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("attendances", attendances);
        requestBody.put("students", students);

        return webClient.post()
                .uri("/subjects/{id}/checking", sbjId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(CheckIn.class)
                .doOnNext(checkIn -> logger.info("Received CheckIn: {}", checkIn))
                .doOnError(error -> {
                    if (error instanceof WebClientResponseException) {
                        WebClientResponseException wcre = (WebClientResponseException) error;
                        logger.error("Error during checking: Status: {}, Body: {}", wcre.getStatusCode(), wcre.getResponseBodyAsString());
                    } else {
                        logger.error("Error during checking: ", error);
                    }
                })
                .onErrorResume(error -> {
                    if (error instanceof WebClientResponseException) {
                        WebClientResponseException wcre = (WebClientResponseException) error;
                        return Mono.error(new RuntimeException("Error during checking: Status: " + wcre.getStatusCode() + ", Body: " + wcre.getResponseBodyAsString()));
                    } else {
                        return Mono.error(error);
                    }
                });
    }

    public Mono<List<CheckIn>> getAllSubjectCheckIn(String subjectId) {
        return webClient.get()
                .uri("/subjects/{id}/checkins", subjectId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<CheckIn>>() {})
                .doOnError(error -> logger.error("Error fetching check-ins for subject {}: {}", subjectId, error.getMessage()));
    }

    public Mono<List<Student>> getSubjectStudents(String subjectId) {
        return webClient.get()
                .uri("/subjects/{id}/students", subjectId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Student.class)
                .collectList()
                .doOnError(error -> logger.error("Error fetching students for subject {}: {}", subjectId, error.getMessage()));
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
