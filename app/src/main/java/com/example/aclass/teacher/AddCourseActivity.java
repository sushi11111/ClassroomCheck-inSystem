package com.example.aclass.teacher;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.example.aclass.Constants;
import com.example.aclass.Login.LoginActivity;
import com.example.aclass.Login.LoginResponse;
import com.example.aclass.R;
import com.example.aclass.teacher.request.AddClassRequest;
import com.example.aclass.teacher.response.AddClassResponse;
import com.example.aclass.teacher.response.CheckingResponse;
import com.example.aclass.teacher.response.DeleteClassResponse;
import com.example.aclass.teacher.response.UploadImageResponse;
import com.example.aclass.teacher.response.vo.RecordAdapter;
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
import java.io.InputStream;
import java.util.UUID;

import static android.app.PendingIntent.getActivity;

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

//        // 创建一个GradientDrawable对象，用于定义按钮的背景
//        GradientDrawable gradientDrawable = new GradientDrawable();
//
//        // 设置圆角半径，这里使用50dp作为示例，您可以根据需要进行调整
//        gradientDrawable.setCornerRadius(54);
//
//        // 将GradientDrawable对象设置为按钮的背景
//        photoBtnAdd.setBackground(gradientDrawable);
//        courseBtnAdd.setBackground(gradientDrawable);


        courseBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 处理 courseBtnAdd 的点击事件
                // ...
                dialogAdd(v);
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
    private void dialogAdd(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setTitle("TIP");
        builder.setMessage("确认要添加该课程吗？");

        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                System.out.println("开始增加...");
                addCourse(dialog,v);
                System.out.println("结束增加...");
//                Intent teacher_intent = new Intent();
//                teacher_intent.putExtra("userData", userData);
//                teacher_intent.setClass(v.getContext(), TeacherMainActivity.class);
//                startActivity(teacher_intent);
            }
        });
        // 取消修改后的逻辑
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.purple_500));
                positiveButton.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), android.R.color.white));

                Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                negativeButton.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.purple_500));
                negativeButton.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), android.R.color.white));
            }
        });
        dialog.show();


    }

    private void addCourse(DialogInterface dialogInterface,View v )
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
                        dialogInterface.dismiss();
                        addSuccess(v);
                    } else {
                        // 注册失败 处理逻辑 服务器内部错误？？
                        System.out.println("教师增加课程失败  "+addClassResponse.getCode()+addClassResponse.getMessage());
                        dialogInterface.dismiss();
                        addFailure(addClassResponse.getMessage(),v);
                    }
                } else {
                    // 请求失败，处理逻辑
                    System.out.println("请求失败...");
                    dialogInterface.dismiss();
                    addFailure(response.message(),v);
                }
            }

            @Override
            public void onFailure(Call<AddClassResponse> call, Throwable t) {
                // 网络错误，处理逻辑
                System.out.println("请求失败：" + t.getMessage());
            }
        });
    }
    private void addFailure(String message,View v )
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setTitle("TIP");
        builder.setMessage("添加失败！原因："+message);
        System.out.println("失败对话...");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void addSuccess(View v )
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setTitle("TIP");
        builder.setMessage("添加成功!即将返回主页");
        System.out.println("成功对话...");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                Intent teacher_intent = new Intent();
                teacher_intent.putExtra("userData", userData);
                teacher_intent.setClass(v.getContext(), TeacherMainActivity.class);
                startActivity(teacher_intent);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
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
        // 设置边界参数
        String boundary = UUID.randomUUID().toString();
        MediaType mediaType = MediaType.parse("multipart/form-data");
// 创建请求体部分，传入图片文件和对应的键名
//        RequestBody requestBody = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart("image", imageFile.getName(), RequestBody.create(MediaType.parse("image/*"), imageFile))
//                .build();
        // 创建请求体部分
        RequestBody requestBody = RequestBody.create(mediaType, imageFile);
        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image/*", imageFile.getName(), requestBody);
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
                    System.out.println(response.body());
                    try {
                        Log.e("Request Failure", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("请求失败!!");
                }
            }

            @Override
            public void onFailure(Call<UploadImageResponse> call, Throwable t) {
                // 网络错误，处理逻辑
                System.out.println("请求失败：" + t.getMessage());
            }
        });
    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    private Bitmap getBitmapFromUri(Uri uri) {
        try {
            // 通过 ContentResolver 获取输入流
            InputStream inputStream = getContentResolver().openInputStream(uri);

            // 将输入流解码为 Bitmap 对象
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

            // 关闭输入流
            if (inputStream != null) {
                inputStream.close();
            }

            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    private void showPreview(Uri imageUri) {
        // 在这里编写显示预览或其他处理的代码
        // 这里只是一个示例，你可以根据具体需求进行实现

        // 获取图像的 Bitmap 对象
        Bitmap bitmap = getBitmapFromUri(imageUri);

        if (bitmap != null) {
            // 将 Bitmap 显示到 ImageView 或其他视图中
            photoAdd.setImageBitmap(bitmap);
        } else {
            // 图像加载失败，给出提示信息
            showToast("Failed to load image");
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_SELECT_IMAGE && resultCode == RESULT_OK) {
            if (data != null) {
                // 获取返回的图像数据
                Uri imageUri = data.getData();

                // 在上传图片之前可以进行一些额外的操作，例如显示预览或做其他处理
                showPreview(imageUri);

                // 执行上传图片的逻辑
                uploadImage(imageUri);
            } else {
                // 用户没有选择有效的图片，进行相应的处理
                showToast("No valid image selected");
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

