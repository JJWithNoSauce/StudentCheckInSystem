package com.principle.checkinproject.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
    @JsonManagedReference
    @JsonIgnore
    private List<CheckIn> checkIns;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "student_subject",
        joinColumns = @JoinColumn(name = "sbjID"),
        inverseJoinColumns = @JoinColumn(name = "stdID")
    )
    @JsonIgnore
    @JsonManagedReference
    private List<Student> students;

    @ManyToOne
    @JoinColumn(name = "classID")
    @JsonIgnore
    private ClassRoom classRoom;

    public Subject(){}

    public String getSbjID(){
        return this.sbjID;
    }

    public Student getStudent(String stdID){
        for (Student student : students) {
            if(student.getStdID().equals(stdID)) 
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
