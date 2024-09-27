package com.principle.checkinproject.model;

import java.util.List;
import java.util.stream.Collectors;

public class TeacherDTO {
    private String teacherID;
    private String name;
    private Long classRoomId;
    private List<String> subjectIds;

    public TeacherDTO() {}

    public TeacherDTO(Teacher teacher) {
        this.teacherID = teacher.getTeacherID();
        this.name = teacher.getName();
        if (teacher.getClassRoom() != null) {
            this.classRoomId = teacher.getClassRoom().getId();
            this.subjectIds = teacher.getClassRoom().getSubjects().stream()
                .map(Subject::getSbjID)
                .collect(Collectors.toList());
        }
    }

    public String getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(String teacherID) {
        this.teacherID = teacherID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getClassRoomId() {
        return classRoomId;
    }

    public void setClassRoomId(Long classRoomId) {
        this.classRoomId = classRoomId;
    }

    public List<String> getSubjectIds() {
        return subjectIds;
    }

    public void setSubjectIds(List<String> subjectIds) {
        this.subjectIds = subjectIds;
    }
}