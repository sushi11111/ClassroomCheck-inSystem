package com.example.aclass.teacher;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.example.aclass.Constants;
import com.example.aclass.Login.LoginResponse;
import com.example.aclass.R;
import com.example.aclass.teacher.request.AddClassRequest;
import com.example.aclass.teacher.response.AddClassResponse;
import com.example.aclass.teacher.response.CheckingResponse;
import com.example.aclass.teacher.response.UploadImageResponse;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Url;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class AddCourseActivity extends AppCompatActivity {

    // 请求码，用于在结果中识别选择图片的操作
    static final int REQUEST_SELECT_IMAGE = 1;

    private boolean checkPermission() {
        int readStoragePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int writeStoragePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return readStoragePermission == PackageManager.PERMISSION_GRANTED &&
                writeStoragePermission == PackageManager.PERMISSION_GRANTED;
    }
    private static final int MY_PERMISSIONS_REQUEST = 1001;

    EditText collegeNameAdd;
    EditText courseNameAdd;
    EditText introduceAdd;
    EditText startTimeAdd;
    EditText learnTimeAdd;
    Button photoBtnAdd;
    ImageView photoAdd;
    Button courseBtnAdd;
    LoginResponse.UserData userData;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置布局文件
        setContentView(R.layout.activity_add_course);
        System.out.println("进入教师增加课程页面");
        //绑定页面组件
        collegeNameAdd = findViewById(R.id.collegeNameAdd);
        courseNameAdd = findViewById(R.id.courseNameAdd);
        introduceAdd = findViewById(R.id.introduceAdd);
        startTimeAdd = findViewById(R.id.startTimeAdd);
        learnTimeAdd = findViewById(R.id.learnTimeAdd);
        photoBtnAdd = findViewById(R.id.photoBtnAdd);
        photoAdd = findViewById(R.id.photoAdd);
        courseBtnAdd = findViewById(R.id.courseBtnAdd);
        // 获取传递的参数值
        Intent intent = getIntent();
        userData = intent.getParcelableExtra("userData");
        System.out.println("获得参数:"+userData);

        courseBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 处理 courseBtnAdd 的点击事件
                // ...
                addCourse();
            }
        });

        photoBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 检查是否已经获取了所需权限
                if (checkPermission()) {
                    // 已经获得权限，执行选择图片的逻辑
                    selectImage();
                } else {
                    // 申请权限
                    requestPermission();
                }
            }
        });
    }

    private void addCourse()
    {
        System.out.println("点击");
        String collegeName = collegeNameAdd.getText().toString();
        String courseName = courseNameAdd.getText().toString();
        String url = "https://guet-lab.oss-cn-hangzhou.aliyuncs.com/api/2023/08/09/4bdf1f89-85a8-4d6c-9268-ff226c791e11.png";
        String introduce = introduceAdd.getText().toString();
        int startTime = Integer.parseInt(startTimeAdd.getText().toString());
        int learnTime = Integer.parseInt(learnTimeAdd.getText().toString());
        AddClassRequest addClassRequest = new AddClassRequest(collegeName,courseName,url,learnTime,introduce,userData.getRealName(),startTime,
                Integer.parseInt(userData.getId()),userData.getUserName());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TeacherService teacherService = retrofit.create(TeacherService.class);
        System.out.println("获取"+addClassRequest);
        Call<AddClassResponse> call = teacherService.addClass(addClassRequest);
        call.enqueue(new Callback<AddClassResponse>() {
            @Override
            public void onResponse(Call<AddClassResponse> call, Response<AddClassResponse> response) {
                if (response.isSuccessful()) {
                    AddClassResponse addClassResponse = response.body();
                    if (addClassResponse != null && addClassResponse.getCode() == 200) {
                        // 注册成功，处理逻辑
                        System.out.println("教师增加课程成功");
                    } else {
                        // 注册失败 处理逻辑 服务器内部错误？？
                        System.out.println("教师增加课程失败  "+addClassResponse.getCode()+addClassResponse.getMessage());
                    }
                } else {
                    // 请求失败，处理逻辑
                    System.out.println("请求失败");
                }
            }

            @Override
            public void onFailure(Call<AddClassResponse> call, Throwable t) {
                // 网络错误，处理逻辑
                System.out.println("请求失败：" + t.getMessage());
            }
        });

    }
     private void uploadImage(Uri imageUri)
     {
         Retrofit retrofit = new Retrofit.Builder()
                 .baseUrl(Constants.BASE_URL)
                 .addConverterFactory(GsonConverterFactory.create())
                 .build();

         TeacherService teacherService = retrofit.create(TeacherService.class);
         System.out.println("开始上传图片");
         // 根据URI获取图片文件的真实路径
         String filePath = getRealPathFromURI(imageUri);
         // 创建要上传的图片文件
         File imageFile = new File(filePath);
// 创建请求体部分，传入图片文件和对应的键名
         RequestBody requestBody = new MultipartBody.Builder()
                 .setType(MultipartBody.FORM)
                 .addFormDataPart("image", imageFile.getName(), RequestBody.create(MediaType.parse("image/*"), imageFile))
                 .build();
         MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", imageFile.getName(), requestBody);
         Call<UploadImageResponse> call = teacherService.uploadImage(imagePart);
         call.enqueue(new Callback<UploadImageResponse>() {
             @Override
             public void onResponse(Call<UploadImageResponse> call, Response<UploadImageResponse> response) {
                 if (response.isSuccessful()) {
                     UploadImageResponse uploadImageResponse = response.body();
                     if (uploadImageResponse != null && uploadImageResponse.getCode() == 200) {
                         // 注册成功，处理逻辑
                         System.out.println("教师上传图片成功");
                     } else {
                         // 注册失败 处理逻辑 服务器内部错误？？
                         System.out.println("教师上传失败  "+uploadImageResponse.getCode()+uploadImageResponse.getMessage());
                     }
                 } else {
                     // 请求失败，处理逻辑
                     try {
                         Log.e("Request Failure", response.errorBody().string());
                     } catch (IOException e) {
                         e.printStackTrace();
                     }
                     System.out.println("请求失败");
                 }
             }

             @Override
             public void onFailure(Call<UploadImageResponse> call, Throwable t) {
                 // 网络错误，处理逻辑
                 System.out.println("请求失败：" + t.getMessage());
             }
         });
     }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_SELECT_IMAGE && resultCode == RESULT_OK) {
            if (data != null) {
                // 获取返回的图像数据
                Uri imageUri = data.getData();
                // 执行上传图片的逻辑
                uploadImage(imageUri);
            }
        }
    }
    public String getRealPathFromURI(Uri uri) {
        String filePath = null;
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            if (cursor.moveToFirst()) {
                filePath = cursor.getString(columnIndex);
            }
            cursor.close();
        }
        return filePath;
    }
    private void requestPermission() {
        // 声明所需的权限数组
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        // 请求权限
        ActivityCompat.requestPermissions(this, permissions,MY_PERMISSIONS_REQUEST);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST) {
            // 检查请求结果
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 权限已授予，执行选择图片的逻辑
                selectImage();
            } else {
                // 权限被拒绝，处理逻辑（例如显示一个提示信息）
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void selectImage() {
        // 创建 ACTION_GET_CONTENT 意图，指定类型为 "image/*"
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        System.out.println("准备进入图库...");
        // 启动意图，等待用户选择图片
        startActivityForResult(Intent.createChooser(intent, "Select Image"), REQUEST_SELECT_IMAGE);
        System.out.println("选择完成...");
    }
}
