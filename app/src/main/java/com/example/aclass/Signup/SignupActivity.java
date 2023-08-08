package com.example.aclass.Signup;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aclass.Constants;
import com.example.aclass.Login.LoginActivity;
import com.example.aclass.Login.LoginResponse;
import com.example.aclass.Login.LoginService;
import com.example.aclass.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignupActivity extends AppCompatActivity
        implements View.OnClickListener{

    private EditText new_username_et;
    private EditText new_password_et;

    private RadioGroup radioGroup;
    private TextView goTo_Login;
    private Button signup_btn;
    //身份代码 老师1/学生0
    private String roleId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        roleId = "0";

        //多选框 选择老师还是学生
        radioGroup = findViewById(R.id.radioGroup);
        //对多选框添加状态变化监听器
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                // 获取选中的 RadioButton
                RadioButton selectedRadioButton = findViewById(i);

                // 根据选中的 RadioButton 做相应的操作
                if (selectedRadioButton != null) {
                    String selectedOption = selectedRadioButton.getText().toString();

                    if (selectedOption.equals("学生")) {
                        // 是学生
                        roleId = "0";
                    } else if (selectedOption.equals("老师")) {
                        // 是老师
                        roleId = "1";
                    }
            }
        }});


        new_username_et = findViewById(R.id.new_username);
        new_password_et = findViewById(R.id.new_password);

        //点击注册按钮 开始注册
        signup_btn = findViewById(R.id.bt_signup);
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取账号密码的字符串
                String new_username = new_username_et.getText().toString();
                String new_password = new_password_et.getText().toString();

                System.out.println("获取"+new_username+new_password+roleId);
                //调用注册方法
                sign_up(new_username,roleId,new_password);
            }
        });

        //注册完返回登录
        goTo_Login = findViewById(R.id.goback_login);
        goTo_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    //注册的方法
    private void sign_up(String username,String roleId,String password) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SignupService signupService = retrofit.create(SignupService.class);
        System.out.println("获取"+username+password+roleId);
        SignupRequest request = new SignupRequest(password, username, roleId);

        Call<SignupResponse> call = signupService.registerUser(request);
        System.out.println("你看看:"+call);
        call.enqueue(new Callback<SignupResponse>() {
            @Override
            public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
                if (response.isSuccessful()) {
                    SignupResponse signupResponse = response.body();
                    if (signupResponse != null && signupResponse.getCode() == 200) {
                        // 注册成功，处理逻辑
                        System.out.println("注册成功");
                    } else {
                        // 注册失败 处理逻辑 服务器内部错误？？
                        System.out.println("注册失败  "+signupResponse.getCode()+signupResponse.getMessage());
                    }
                } else {
                    // 请求失败，处理逻辑
                    System.out.println("请求失败");
                }
            }

            @Override
            public void onFailure(Call<SignupResponse> call, Throwable t) {
                // 网络错误，处理逻辑
                System.out.println("请求失败：" + t.getMessage());
            }
        });
    }



    @Override
    public void onClick(View view) {

    }
}
