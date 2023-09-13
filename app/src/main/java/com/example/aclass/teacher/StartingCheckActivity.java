package com.example.aclass.teacher;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.example.aclass.Constants;
import com.example.aclass.Login.LoginResponse;
import com.example.aclass.R;
import com.example.aclass.Signup.SignupRequest;
import com.example.aclass.Signup.SignupResponse;
import com.example.aclass.Signup.SignupService;
import com.example.aclass.teacher.request.CheckingRequest;
import com.example.aclass.teacher.response.CheckingResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StartingCheckActivity extends AppCompatActivity {
    EditText checkTime;
    EditText classAddress;
    EditText checkCode;
    EditText stuNum;
    Button startCheckBtn;
    String courseId;
    String courseName;
    LoginResponse.UserData userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting_check);
        checkTime = findViewById(R.id.checkTime);
        classAddress = findViewById(R.id.classAddress);
        checkCode = findViewById(R.id.checkCode);
        stuNum = findViewById(R.id.stuNum);
        startCheckBtn = findViewById(R.id.startCheckBtn);
        System.out.println("跳转到发起签到页面...");
        // 获取传递的参数值
        Intent intent = getIntent();
        userData = intent.getParcelableExtra("userData");
//        int courseId = intent.getIntExtra("courseId",0);
        courseId = intent.getStringExtra("courseId");
        courseName = intent.getStringExtra("courseName");
        System.out.println("签到页面获得数据:"+userData+courseId+courseName);

        // 发起签到
        startCheckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               dialogAdd();
            }
        });
    }
    private void dialogAdd() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("TIP");
        builder.setMessage("发起签到成功!");

        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                addCheck();
                dialogInterface.dismiss();
                finish();
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
    private void addCheck()
    {
        // 在这里执行按钮点击后的操作
        // 可以获取输入框的值、处理逻辑等
        String checkTimeValue = checkTime.getText().toString();
        String classAddressValue = classAddress.getText().toString();
        String checkCodeValue = checkCode.getText().toString();
        String stuNumValue = stuNum.getText().toString();

        // TODO: 在这里添加您的逻辑代码
        CheckingRequest checkingRequest = new CheckingRequest(Integer.parseInt(checkTimeValue),classAddressValue,Integer.parseInt(courseId),
                courseName,checkCodeValue,stuNumValue,Integer.parseInt(userData.getId()));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TeacherService teacherService = retrofit.create(TeacherService.class);
        System.out.println("获取"+checkingRequest);
        Call<CheckingResponse> call = teacherService.startChecking(checkingRequest);
        call.enqueue(new Callback<CheckingResponse>() {
            @Override
            public void onResponse(Call<CheckingResponse> call, Response<CheckingResponse> response) {
                if (response.isSuccessful()) {
                    CheckingResponse checkingResponse = response.body();
                    if (checkingResponse != null && checkingResponse.getCode() == 200) {
                        // 注册成功，处理逻辑
                        System.out.println("教师发起成功");
                    } else {
                        // 注册失败 处理逻辑 服务器内部错误？？
                        System.out.println("教师发起失败  "+checkingResponse.getCode()+checkingResponse.getMessage());
                    }
                } else {
                    // 请求失败，处理逻辑
                    System.out.println("请求失败");
                }
            }

            @Override
            public void onFailure(Call<CheckingResponse> call, Throwable t) {
                // 网络错误，处理逻辑
                System.out.println("请求失败：" + t.getMessage());
            }
        });
    }
}
