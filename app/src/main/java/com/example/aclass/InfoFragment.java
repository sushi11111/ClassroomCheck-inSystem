package com.example.aclass;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.aclass.Login.LoginResponse;
import com.example.aclass.teacher.TeacherService;
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
        View rootView = inflater.inflate(R.layout.fragment_information, container, false);
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
            LoginResponse.UserData userData = getArguments().getParcelable("userData");
            System.out.println("InfoFragment已获得"+userData.getEmail());
            // 在布局中显示数据
            String avatarUrl = userData.getAvatar();
            Glide.with(this)
                    .load(avatarUrl)
                    .into(avatar);
            userName.setText(userData.getUserName());
            if(userData.isGender()){
                role.setText("男");
            } else {
                role.setText("女");
            }
            realName.setText(userData.getRealName());
            idNumber.setText(userData.getIdNumber());
            collegeName.setText(userData.getCollegeName());
            if(userData.getRoleId()==1){
                role.setText("教师");
            } else {
                role.setText("学生");
            }
            phone.setText(userData.getPhone());
            email.setText(userData.getEmail());
        }

        return rootView;
    }

    private void getAllCourse(int current ,int size)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TeacherService teacherService = retrofit.create(TeacherService.class);
        Call<GetClassListResponse> call = teacherService.getClass(current ,size);
        call.enqueue(new Callback<GetClassListResponse>(){

            @Override
            public void onResponse(Call<GetClassListResponse> call, Response<GetClassListResponse> response) {
                if (response.isSuccessful()){
                    GetClassListResponse getClassListResponse = response.body();
                    if (getClassListResponse != null && getClassListResponse.getCode() == 200) {
                        System.out.println("课程请求数据为："+getClassListResponse.getData());
                    }
                    else {
                        System.out.println("错误码:"+getClassListResponse.getCode()+"原因:"+getClassListResponse.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<GetClassListResponse> call, Throwable t) {
                System.out.println("请求失败：" + t.getMessage());
            }
        });
    }

}
