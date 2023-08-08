package com.example.aclass.Login;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class LoginResponse  {
    @SerializedName("code")
    private int code;

    @SerializedName("msg")
    private String message;

    @SerializedName("data")
    private UserData userData;



    static public class UserData implements Parcelable{

        protected UserData(Parcel in) {
            id = in.readString();
            appKey = in.readString();
            userName = in.readString();
            roleId = in.readInt();
            realName = in.readString();
            idNumber = in.readString();
            collegeName = in.readString();
            gender = in.readByte() != 0;
            phone = in.readString();
            email = in.readString();
            avatar = in.readString();
            inSchoolTime = in.readString();
            createTime = in.readString();
            lastUpdateTime = in.readString();
        }

        public static final Creator<UserData> CREATOR = new Creator<UserData>() {
            @Override
            public UserData createFromParcel(Parcel in) {
                return new UserData(in);
            }

            @Override
            public UserData[] newArray(int size) {
                return new UserData[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }
        @Override
        public void writeToParcel(@NonNull Parcel dest, int i) {
            // 将 UserData 类的成员变量写入 Parcel 对象
            dest.writeString(id);
            dest.writeString(appKey);
            dest.writeString(userName);
            dest.writeInt(roleId);
            dest.writeString(realName);
            dest.writeString(idNumber);
            dest.writeString(collegeName);
            dest.writeByte((byte) (gender ? 1 : 0)); // 将 boolean 转换为 byte
            dest.writeString(phone);
            dest.writeString(email);
            dest.writeString(avatar);
            dest.writeString(inSchoolTime);
            dest.writeString(createTime);
            dest.writeString(lastUpdateTime);
        }

        @SerializedName("id")
        private String id;

        @SerializedName("appKey")
        private String appKey;

        @SerializedName("userName")
        private String userName;

        @SerializedName("roleId")
        private int roleId;

        @SerializedName("realName")
        private String realName;

        @SerializedName("idNumber")
        private String idNumber;

        @SerializedName("collegeName")
        private String collegeName;

        @SerializedName("gender")
        private boolean gender;

        @SerializedName("phone")
        private String phone;

        @SerializedName("email")
        private String email;

        @SerializedName("avatar")
        private String avatar;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAppKey() {
            return appKey;
        }

        public void setAppKey(String appKey) {
            this.appKey = appKey;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public int getRoleId() {
            return roleId;
        }

        public void setRoleId(int roleId) {
            this.roleId = roleId;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getIdNumber() {
            return idNumber;
        }

        public void setIdNumber(String idNumber) {
            this.idNumber = idNumber;
        }

        public String getCollegeName() {
            return collegeName;
        }

        public void setCollegeName(String collegeName) {
            this.collegeName = collegeName;
        }

        public boolean isGender() {
            return gender;
        }

        public void setGender(boolean gender) {
            this.gender = gender;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getInSchoolTime() {
            return inSchoolTime;
        }

        public void setInSchoolTime(String inSchoolTime) {
            this.inSchoolTime = inSchoolTime;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getLastUpdateTime() {
            return lastUpdateTime;
        }

        public void setLastUpdateTime(String lastUpdateTime) {
            this.lastUpdateTime = lastUpdateTime;
        }

        @SerializedName("inSchoolTime")
        private String inSchoolTime;

        @SerializedName("createTime")
        private String createTime;

        @SerializedName("lastUpdateTime")
        private String lastUpdateTime;
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

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }
}
