package com.example.aclass.student.Courses;

public class Course {
    private String courseId;
    private String courseName;
    private String coursePhoto;
    private String collegeName;

    public Course(String courseId, String courseName, String coursePhoto, String collegeName) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.coursePhoto = coursePhoto;
        this.collegeName = collegeName;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCoursePhoto() {
        return coursePhoto;
    }

    public void setCoursePhoto(String coursePhoto) {
        this.coursePhoto = coursePhoto;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }
}
