package com.principle.checkinproject.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
public class Student {
    @Id
    @Column(name="stdID")
    private String stdID;
    
    @Column(name="name")
    private String name;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Attendance> attendance;
    
    // @ManyToMany
    // @JoinTable(
    //     name = "student_subject",
    //     joinColumns = @JoinColumn(name = "stdID"),
    //     inverseJoinColumns = @JoinColumn(name = "sbjID")
    // )
    // private List<Subject> subject;
    
    @ManyToMany(mappedBy = "students", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Subject> subject;
    
    public Student(){
        this.subject = new ArrayList<Subject>();
        this.attendance = new ArrayList<Attendance>();
    }

    public String getStdID(){
        return stdID;
    }

    public void setStdID(String stdID) {
        this.stdID = stdID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Attendance> getAttendance() {
        return attendance;
    }

    public void setAttendance(List<Attendance> attendance) {
        this.attendance = attendance;
    }

    public List<Subject> getSubject() {
        return subject;
    }

    public void setSubject(List<Subject> subject) {
        this.subject = subject;
    }
}
