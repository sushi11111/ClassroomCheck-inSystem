package com.example.aclass.student.CheckIn;

import com.example.aclass.student.Courses.Course;
import com.example.aclass.student.Courses.CourseResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CheckInResponse {
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("data")
    private CheckInCourseData data;

    public CheckInResponse(int code, String msg, CheckInCourseData data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public CheckInCourseData getData() {
        return data;
    }

    static public class CheckInCourseData {
        private List<CheckInCourse> records;
        private int total;
        private int size;
        private int current;

        public List<CheckInCourse> getRecords() {
            return records;
        }

        public int getTotal() {
            return total;
        }

        public int getSize() {
            return size;
        }

        public int getCurrent() {
            return current;
        }
    }
}
