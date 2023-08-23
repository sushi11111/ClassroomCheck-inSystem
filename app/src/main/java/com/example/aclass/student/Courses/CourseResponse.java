package com.example.aclass.student.Courses;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CourseResponse {
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("data")
    private CourseData data;

    public CourseResponse(int code, String msg, CourseData data) {
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

    public CourseData getData() {
        return data;
    }

    static public class CourseData {
        private List<Course> records;
        private int total;
        private int size;
        private int current;

        public List<Course> getRecords() {
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
