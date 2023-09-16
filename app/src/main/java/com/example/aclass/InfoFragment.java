package com.example.aclass;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.aclass.Login.LoginActivity;
import com.example.aclass.Login.LoginResponse;
import com.example.aclass.Login.LoginService;
import com.example.aclass.student.StudentMainActivity;
import com.example.aclass.teacher.TeacherMainActivity;
import com.example.aclass.teacher.TeacherService;
import com.example.aclass.teacher.request.ChangeUserRequest;
import com.example.aclass.teacher.response.ChangeUserResponse;
import com.example.aclass.teacher.response.GetClassListResponse;
import com.example.aclass.teacher.response.GetFinishClassResponse;
import com.example.aclass.teacher.response.GetUnFinishClassResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
//我的信息
public class InfoFragment extends Fragment {

    TextView userName;
    TextView role;
    TextView realName;
    TextView idNumber;
    TextView collegeName;
    TextView gender;
    TextView phone;
    TextView email;

    private EditText userNameEditText;
    private EditText realNameEditText;
    private EditText idNumberEditText;
    private EditText collegeNameEditText;
    private RadioGroup genderRadioGroup;
    private EditText phoneEditText;
    private EditText emailEditText;
    private LoginResponse.UserData userData=null;

    View rootView;

    public static InfoFragment newInstance(LoginResponse.UserData userData) {
        InfoFragment fragment = new InfoFragment();
        Bundle args = new Bundle();
        args.putParcelable("userData", userData);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 使用 inflater.inflate() 方法创建并返回一个有效的 View 对象
        rootView = inflater.inflate(R.layout.fragment_information, container, false);
        //控件绑定
        ImageView avatar = rootView.findViewById(R.id.avatar);
        userName = rootView.findViewById(R.id.userName);
        role = rootView.findViewById(R.id.role);
        realName = rootView.findViewById(R.id.realName);
        idNumber = rootView.findViewById(R.id.idNumber);
        collegeName = rootView.findViewById(R.id.collegeName);
        gender = rootView.findViewById(R.id.gender);
        phone = rootView.findViewById(R.id.phone);
        email = rootView.findViewById(R.id.email);

        // 获取传递的数据
        if (getArguments() != null) {
            userData = getArguments().getParcelable("userData");
            System.out.println("InfoFragment已获得" + userData.getEmail());
            // 在布局中显示数据
            String avatarUrl = userData.getAvatar();
            Glide.with(this)
                    .load(avatarUrl)
                    .apply(RequestOptions.circleCropTransform())
                    .into(avatar);
            userName.setText(userData.getUserName());

            if (userData.isGender()) {
                gender.setText("男");
            } else {
                gender.setText("女");
            }

            if (userData.getRealName() == null) {
                realName.setText("未填写");
            } else {
                realName.setText(userData.getRealName());
            }

            if (userData.getIdNumber() == null) {
                idNumber.setText("未填写");
            } else {
                idNumber.setText(userData.getIdNumber());
            }

            if (userData.getCollegeName() == null) {
                collegeName.setText("未填写");
            } else {
                collegeName.setText(userData.getCollegeName());
            }

            if (userData.getRoleId() == 1) {
                role.setText("教师");
            } else {
                role.setText("学生");
            }

            if (userData.getPhone() == null) {
                phone.setText("未填写");
            } else {
                phone.setText(userData.getPhone());
            }

            if (userData.getEmail() == null) {
                email.setText("未填写");
            } else {
                email.setText(userData.getEmail());
            }


            //设置修改按钮的点击事件
            ImageView imageView = rootView.findViewById(R.id.modify_info);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 创建一个AlertDialog.Builder
                    AlertDialog.Builder builder = new AlertDialog.Builder(rootView.getContext());
                    builder.setTitle("输入新的个人信息");

                    // 创建并设置自定义布局
                    View customLayout = LayoutInflater.from(rootView.getContext()).inflate(R.layout.modify_info_dialog, null);
                    builder.setView(customLayout);

                    // 获取所有EditText和RadioGroup的引用
                    userNameEditText = customLayout.findViewById(R.id.modify_userName);
                    realNameEditText = customLayout.findViewById(R.id.modify_realName);
                    idNumberEditText = customLayout.findViewById(R.id.modify_idNumber);
                    collegeNameEditText = customLayout.findViewById(R.id.modify_collegeName);
                    genderRadioGroup = customLayout.findViewById(R.id.modify_gender);
                    phoneEditText = customLayout.findViewById(R.id.modify_phone);
                    emailEditText = customLayout.findViewById(R.id.modify_email);

                    userNameEditText.setText(userName.getText());
                    realNameEditText.setText(realName.getText());
                    idNumberEditText.setText(idNumber.getText());
                    collegeNameEditText.setText(collegeName.getText());
                    phoneEditText.setText(phone.getText());
                    emailEditText.setText(email.getText());

                    // 确定修改后的逻辑
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //获取输入框的信息
                            String userName = userNameEditText.getText().toString();
                            String realName = realNameEditText.getText().toString();
                            String idNumber = idNumberEditText.getText().toString();
                            String collegeName = collegeNameEditText.getText().toString();

                            int selectedGenderId = genderRadioGroup.getCheckedRadioButtonId();
                            boolean gender = true;
                            if (selectedGenderId == R.id.btnWomen) {
                                gender = false;
                            }
                            String phone = phoneEditText.getText().toString();
                            String email = emailEditText.getText().toString();


                            //发送修改的请求
                            //如果是空字符串要...
                            ChangeUserRequest changeUserRequest = new ChangeUserRequest(collegeName,email, gender,Long.parseLong(userData.getId()),Long.parseLong(idNumber),phone,realName,userName );
                            changeUserInfo(changeUserRequest,v);
                            // 重新登陆
                            dialog.dismiss();
                            logout(v);
                        }
                    });
                    // 取消修改后的逻辑
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
                    // 创建并显示对话框
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });
        }

        return rootView;
    }

    private void changeUserInfo(ChangeUserRequest changeUserRequest,View v)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TeacherService teacherService = retrofit.create(TeacherService.class);

        Call<ChangeUserResponse> call = teacherService.changeUser(changeUserRequest);
        call.enqueue(new Callback<ChangeUserResponse>() {
            @Override
            public void onResponse(Call<ChangeUserResponse> call, Response<ChangeUserResponse> response) {
                if (response.isSuccessful()) {
                    ChangeUserResponse changeUserResponse = response.body();
                    if (changeUserResponse != null && changeUserResponse.getCode() == 200) {
                        // 登录成功，处理逻辑
                        System.out.println("用户修改信息成功...");
//                        userName.setText(userNameEditText.getText());
//                        realName.setText(realNameEditText.getText());
//                        idNumber.setText(idNumberEditText.getText());
//                        collegeName.setText(collegeNameEditText.getText());
//                        phone.setText(phoneEditText.getText());
//                        email.setText(emailEditText.getText());
//                        if(genderRadioGroup.getCheckedRadioButtonId()==R.id.btnWomen)
//                        {
//                            gender.setText("女");
//                        }
//                        else {
//                            gender.setText("男");
//                        }


                    } else {
                        //弹出错误提示框
                        System.out.println("修改失败");

                    }
                } else {
                    // 请求失败
                    System.out.println("请求失败");
                }
            }

            @Override
            public void onFailure(Call<ChangeUserResponse> call, Throwable t) {
                System.out.println("请求失败：" + t.getMessage());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

    }
    private void logout(View v)
    {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(rootView.getContext());
        builder.setTitle("TIP");
        builder.setMessage("修改信息成功，请重新登录");

        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(userData.getRoleId()==1){
                    Intent teacher_intent = new Intent();
                    teacher_intent.setClass(v.getContext(), LoginActivity.class);
                    startActivity(teacher_intent);
                }else{
                    Intent student_intent = new Intent();
                    student_intent.setClass(v.getContext(), LoginActivity.class);
                    startActivity(student_intent);
                }
                dialogInterface.dismiss();
            }
        });

        android.app.AlertDialog dialog = builder.create();

        dialog.show();
    }
//    private void getAllCourse(int current ,int size)
//    {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(Constants.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        TeacherService teacherService = retrofit.create(TeacherService.class);
//        Call<GetClassListResponse> call = teacherService.getClass(current ,size);
//        call.enqueue(new Callback<GetClassListResponse>(){
//
//            @Override
//            public void onResponse(Call<GetClassListResponse> call, Response<GetClassListResponse> response) {
//                if (response.isSuccessful()){
//                    GetClassListResponse getClassListResponse = response.body();
//                    if (getClassListResponse != null && getClassListResponse.getCode() == 200) {
//                        System.out.println("课程请求数据为："+getClassListResponse.getData());
//                    }
//                    else {
//                        System.out.println("错误码:"+getClassListResponse.getCode()+"原因:"+getClassListResponse.getMessage());
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<GetClassListResponse> call, Throwable t) {
//                System.out.println("请求失败：" + t.getMessage());
//            }
//        });
//    }

}
