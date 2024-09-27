package com.principle.checkinproject.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class CheckIn {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private int classPeriod;

	@OneToMany(mappedBy = "checkIn", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("checkIn-attendance")
    private List<Attendance> attendances;

    @ManyToOne
	@JoinColumn(name = "subject", nullable = false, referencedColumnName = "sbjID")	
	@JsonBackReference
	private Subject subject;
    
    public CheckIn(){
        this.attendances = new ArrayList<Attendance>();
    }

	public CheckIn(List<Attendance> attendances, Subject subject){
		this.attendances = attendances;
		this.subject = subject;
		this.classPeriod = subject.getCheckIns().size() + 1;
	}

    public List<Attendance> viewHistory(){
        return attendances;
    }

	public List<Attendance> getAttendances() {
		return attendances;
	}

	public void setAttendances(List<Attendance> attendances) {
		this.attendances = attendances;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public Long getId(){
		return id;
	}

	public void setId(Long id){
		this.id = id;
	}

    public int getClassPeriod() {
        return classPeriod;
    }

    public void setClassPeriod(int classPeriod) {
        this.classPeriod = classPeriod;
    }
}
