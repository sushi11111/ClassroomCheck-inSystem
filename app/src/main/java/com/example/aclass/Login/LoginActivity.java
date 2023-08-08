package com.example.aclass.Login;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aclass.Constants;
import com.example.aclass.R;
import com.example.aclass.Signup.SignupActivity;
import com.example.aclass.student.StudentMainActivity;
import com.example.aclass.teacher.TeacherCheckInActivity;
import com.example.aclass.teacher.TeacherMainActivity;


public class LoginActivity extends AppCompatActivity
        implements View.OnClickListener{

    private EditText etPwd;
    private EditText etAccount;

    private TextView goToSignUp;
    private CheckBox cbRememberPwd;
    private boolean bPwdSwitch = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //是否显示密码
        final ImageView ivPwdSwitch = findViewById(R.id.iv_pwd_switch);
        //密码框
        etPwd = findViewById(R.id.et_pwd);
        //账号框
        etAccount = findViewById(R.id.et_account);
        //是否记住登录
        cbRememberPwd = findViewById(R.id.cb_remember_pwd);

        //获取登录按钮
        Button btLogin = findViewById(R.id.bt_login);
        //登录按钮设置监听事件
        btLogin.setOnClickListener(this);

        //设置监听事件 是否显示密码
        ivPwdSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bPwdSwitch=!bPwdSwitch;
                if(bPwdSwitch){
                    ivPwdSwitch.setImageResource(R.drawable.baseline_visibility_24);
                    etPwd.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                else{
                    ivPwdSwitch.setImageResource(R.drawable.baseline_visibility_off_24);
                    etPwd.setInputType(
                            InputType.TYPE_TEXT_VARIATION_PASSWORD|InputType.TYPE_CLASS_TEXT
                    );
                    etPwd.setTypeface(Typeface.DEFAULT);
                }
            }
        });

        String spFileName = getResources()
                .getString(R.string.shared_preferences_file_name);
        String accountKey = getResources()
                .getString(R.string.login_account_name);
        String passwordKey = getResources()
                .getString(R.string.login_password);
        String rememberPasswordKey = getResources()
                .getString(R.string.login_remember_password);

        SharedPreferences spFile = getSharedPreferences(
                spFileName,MODE_PRIVATE);
        String account = spFile.getString(accountKey,null);
        String password = spFile.getString(passwordKey,null);
        Boolean rememberPassword = spFile.getBoolean(
                rememberPasswordKey,false);

        if(account!=null&& !TextUtils.isEmpty(account)){
            etAccount.setText(account);
        }
        if(password!=null&&!TextUtils.isEmpty(password)){
            etPwd.setText(password);
        }
        cbRememberPwd.setChecked(rememberPassword);

        //点击登录
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etAccount.getText().toString();
                String password = etPwd.getText().toString();

                //调用登录方法
                login(username,password);
            }
        });

        //点击去注册
        goToSignUp = findViewById(R.id.tv_sign_up);
        goToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转到注册页面
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

    }

    private void login(String username,String password){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LoginService apiService = retrofit.create(LoginService.class);

        Call<LoginResponse> call = apiService.login(username, password);
        call.enqueue(new Callback<LoginResponse>() {
                         @Override
                         public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                             if (response.isSuccessful()) {
                                 LoginResponse loginResponse = response.body();
                                 if (loginResponse != null && loginResponse.getCode() == 200) {
                                     // 登录成功，处理逻辑
                                     System.out.println("登录成功");
                                     //是老师
                                     if(loginResponse.getUserData().getRoleId()==1){
                                         Intent teacher_intent = new Intent();
                                         teacher_intent.setClass(LoginActivity.this, TeacherMainActivity.class);
                                         teacher_intent.putExtra("userData", loginResponse.getUserData());
                                         startActivity(teacher_intent);
                                     }else{
                                         Intent student_intent = new Intent();
                                         student_intent.setClass(LoginActivity.this, StudentMainActivity.class);
                                         student_intent.putExtra("userData", loginResponse.getUserData());
                                         startActivity(student_intent);
                                     }
                                 } else {
                                     //弹出错误提示框
                                     showLoginFailedDialog();
                                 }
                             } else {
                                 // 请求失败
                                 System.out.println("请求失败");
                             }
                         }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                System.out.println("请求失败：" + t.getMessage());
            }
            });
    }

    //登录失败的提示
    private void showLoginFailedDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("登录失败")
                .setMessage("用户名或密码错误，请重试。")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 点击确定按钮后的逻辑，例如关闭对话框或其他操作
                    }
                })
                .setCancelable(false) // 禁止点击对话框外部或返回键关闭对话框
                .show();
    }

    //按钮的时候
    @Override
    public void onClick(View view) {
        String spFileName = getResources()
                .getString(R.string.shared_preferences_file_name);
        String accountKey = getResources()
                .getString(R.string.login_account_name);
        String passwordKey = getResources()
                .getString(R.string.login_password);
        String rememberPasswordKey = getResources()
                .getString(R.string.login_remember_password);

        SharedPreferences spFile = getSharedPreferences(
                spFileName, Context.MODE_PRIVATE
        );
        SharedPreferences.Editor editor = spFile.edit();

        //检查是否被勾选
        //被勾选则将用户名密码存进SharedPreferences
        if(cbRememberPwd.isChecked()){
            String password = etPwd.getText().toString();
            String account = etAccount.getText().toString();

            editor.putString(accountKey,account);
            editor.putString(passwordKey,password);
            editor.putBoolean(rememberPasswordKey,true);
            editor.apply();
        }
        //清除掉之前存的
        else{
            editor.remove(accountKey);
            editor.remove(passwordKey);
            editor.remove(rememberPasswordKey);
            editor.apply();
        }
    }
}
