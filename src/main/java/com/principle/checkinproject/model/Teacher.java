package com.principle.checkinproject.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;

@Entity
public class Teacher extends AInstructor{
	@Column(name="teacherID")
    private String teacherID;

    @Column(name = "name")
    private String name;

    @OneToOne(mappedBy = "teacher")
    @JoinColumn(name = "classroom_id")
    private ClassRoom classRoom;

    public Teacher() {}

    public Teacher(String teacherID, String name) {
        this.teacherID = teacherID;
        this.name = name;
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

    public ClassRoom getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(ClassRoom classRoom) {
        if (this.classRoom != null && this.classRoom != classRoom) {
            this.classRoom.setTeacher(null);
        }
        this.classRoom = classRoom;
        if (classRoom != null && classRoom.getTeacher() != this) {
            classRoom.setTeacher(this);
        }
    }
}
