package com.principle.checkinproject.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Subject {
    @Id
    @Column(name="sbjID")
    private String sbjID;
    @Column(name="name")
    private String name;
    @Column(name="time")
    private String time;

    @OneToMany(mappedBy = "subject")
    private List<CheckIn> checkIns;

    @OneToMany(mappedBy = "subject")
    private List<Student> students;

    @ManyToOne
    @JsonIgnore // This will exclude the classRoom attribute from JSON serialization
    @JoinColumn(name = "classID")
    private ClassRoom classRoom;

    public Subject(){}

    public String getSbjID(){
        return this.sbjID;
    }

    public Student getStudent(String stdID){
        for (Student student : students) {
            if(student.getStdID() == stdID) 
                return student;
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<CheckIn> getCheckIns() {
        return checkIns;
    }

    public void setCheckIns(List<CheckIn> checkIns) {
        this.checkIns = checkIns;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public ClassRoom getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(ClassRoom classRoom) {
        this.classRoom = classRoom;
    }

    public void setSbjID(String sbjID) {
        this.sbjID = sbjID;
    }
}

