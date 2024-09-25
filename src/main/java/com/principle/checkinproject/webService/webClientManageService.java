package com.principle.checkinproject.webService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.principle.checkinproject.model.*;
import reactor.core.publisher.Mono;
import java.util.List;

@Service
public class webClientManageService {
    @Autowired
    private final WebClient webClient;

    public webClientManageService(WebClient webClient){
        this.webClient = webClient;
    }

    // ManageController APIs
    public Mono<Teacher> createTeacher(Teacher teacher) {
        return webClient.post()
                .uri("/teacher")
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

    public Mono<Teacher> updateTeacher(Teacher teacher) {
        return webClient.put()
                .uri("/teacher/{id}", teacher.getTeacherID())
                .bodyValue(teacher)
                .retrieve()
                .bodyToMono(Teacher.class);
    }

    public Mono<Teacher> getTeacherById(String teacherId) {
        return webClient.get()
                .uri("/teacher/{id}", teacherId)
                .retrieve()
                .bodyToMono(Teacher.class);
    }

    public Mono<List<Teacher>> getAllTeacher() {
        return webClient.get()
                .uri("/teacher")
                .retrieve()
                .bodyToFlux(Teacher.class)
                .collectList();
    }

    public Mono<List<Subject>> getAllSubjectByTeacher(String teacherId) {
        return webClient.get()
                .uri("/teacher/subjects/{teacherId}", teacherId)
                .retrieve()
                .bodyToFlux(Subject.class)
                .collectList();
    }

    // Additional methods for students and subjects
    public Mono<Student> createStudent(Student student) {
        return webClient.post()
                .uri("/manage/student")
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
                .uri("/manage/teacher/{teacherId}/subject/{subjectId}", teacherId, subjectId)
                .retrieve()
                .bodyToMono(Void.class);
    }

    public Mono<Student> registerStudentToSubject(String subjectId, String studentId) {
        return webClient.post()
                .uri("/manage/subject/{subjectId}/student/{studentId}", subjectId, studentId)
                .retrieve()
                .bodyToMono(Student.class);
    }

    public Mono<Student> deregisterStudentToSubject(String subjectId, String studentId) {
        return webClient.delete()
                .uri("/manage/subject/{subjectId}/student/{studentId}", subjectId, studentId)
                .retrieve()
                .bodyToMono(Student.class);
    }

    // SubjectController APIs
    public Mono<List<Subject>> getAllSubject() {
        return webClient.get()
                .uri("/subject")
                .retrieve()
                .bodyToFlux(Subject.class)
                .collectList();
    }

    public Mono<Subject> getSubject(String subjectId) {
        return webClient.get()
                .uri("/subject/{id}", subjectId)
                .retrieve()
                .bodyToMono(Subject.class);
    }

    public Mono<CheckIn> checking(String sbjId, List<Attendance> attendances) {
        return webClient.post()
                .uri("/subject/{id}/checkin", sbjId)
                .bodyValue(attendances)
                .retrieve()
                .bodyToMono(CheckIn.class);
    }

    public Mono<List<Student>> getSubjectStudents(String subjectId) {
        return webClient.get()
                .uri("/subject/{id}/students", subjectId)
                .retrieve()
                .bodyToFlux(Student.class)
                .collectList();
    }

    public Mono<List<CheckIn>> getAllSubjectCheckIn(String subjectId) {
        return webClient.get()
                .uri("/subject/{id}/checkins", subjectId)
                .retrieve()
                .bodyToFlux(CheckIn.class)
                .collectList();
    }

    public Mono<CheckIn> getSubjectCheckIn(String subjectId, int period) {
        return webClient.get()
                .uri("/subject/{id}/checkin/{period}", subjectId, period)
                .retrieve()
                .bodyToMono(CheckIn.class);
    }

    // Added methods for students
    public Mono<List<Student>> getAllStudents() {
        return webClient.get()
                .uri("/students")
                .retrieve()
                .bodyToFlux(Student.class)
                .collectList();
    }

    // Define deleteTeacher method
    public Mono<Void> deleteTeacher(String teacherId) {
        return webClient.delete()
                .uri("/teacher/{id}", teacherId)
                .retrieve()
                .bodyToMono(Void.class);
    }
}

