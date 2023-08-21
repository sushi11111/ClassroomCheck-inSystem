package com.example.aclass.teacher.response;

import com.google.gson.annotations.SerializedName;
import org.json.JSONObject;

import java.util.List;

public class GetUnFinishClassResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("msg")
    private String message;

    @SerializedName("data")
    private
    Data data;

    public GetUnFinishClassResponse(int code, String message,
                                    Data data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }



    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(
            Data data) {
        this.data = data;
    }

    public class Record {
        private String courseId;
        private String courseName;
        private String coursePhoto;
        private String collegeName;

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
    public class Data {
        private List<Record> records;
        private int total;
        private int size;
        private int current;

        public List<Record> getRecords() {
            return records;
        }

        public void setRecords(List<Record> records) {
            this.records = records;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getCurrent() {
            return current;
        }

        public void setCurrent(int current) {
            this.current = current;
        }
    }
}
