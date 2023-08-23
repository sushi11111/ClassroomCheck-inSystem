package com.example.aclass.student.CheckIn;

public class CheckInCourse {
    private String userSignId;
    private String courseName;
    private String courseAddr;
    private String createTime;

    public CheckInCourse(String userSignId, String courseName, String courseAddr, String createTime) {
        this.userSignId = userSignId;
        this.courseName = courseName;
        this.courseAddr = courseAddr;
        this.createTime = createTime;
    }

    public String getUserSignId() {
        return userSignId;
    }

    public void setUserSignId(String userSignId) {
        this.userSignId = userSignId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseAddr() {
        return courseAddr;
    }

    public void setCourseAddr(String courseAddr) {
        this.courseAddr = courseAddr;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
