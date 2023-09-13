package com.example.aclass.teacher;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.aclass.Constants;
import com.example.aclass.Login.LoginResponse;
import com.example.aclass.R;
import com.example.aclass.teacher.response.GetCheckDetailResponse;
import com.example.aclass.teacher.response.GetUnFinishClassResponse;
import com.example.aclass.teacher.response.vo.CheckDetailAdapter;
import com.example.aclass.teacher.response.vo.RecordAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.List;
import androidx.core.view.ViewCompat;

public class TeacherCheckFragment extends Fragment {

    TextView blankTip;
    View rootView;

    public static TeacherCheckFragment newInstance(LoginResponse.UserData userData,int courseId) {
        TeacherCheckFragment fragment = new TeacherCheckFragment();
        Bundle args = new Bundle();
        args.putParcelable("userData", userData);
        args.putInt("courseId",courseId);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 使用 inflater.inflate() 方法创建并返回一个有效的 View 对象
        rootView = inflater.inflate(R.layout.fragment_teacher_check, container, false);
        //展示教师未结算的课程
        RecyclerView recyclerCheckDetail = rootView.findViewById(R.id.checkDetail);
        recyclerCheckDetail.setLayoutManager(new LinearLayoutManager(getContext()));

        //
        CheckDetailAdapter adapter = new CheckDetailAdapter(new ArrayList<>());
        recyclerCheckDetail.setAdapter(adapter);

        if (getArguments() != null) {
            LoginResponse.UserData userData = getArguments().getParcelable("userData");
            int courseId = getArguments().getInt("courseId");
            System.out.println("CheckFragment已获得"+userData.getEmail()+" "+courseId);
            checkDetails(courseId,Integer.parseInt(userData.getId()),recyclerCheckDetail);
        }
        return rootView;
    }

    private void checkDetails(int courseId ,int userId, RecyclerView recyclerView)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TeacherService teacherService = retrofit.create(TeacherService.class);
        Call<GetCheckDetailResponse> call = teacherService.getCheckDetail(courseId,userId);
        call.enqueue(new Callback<GetCheckDetailResponse>(){

            @Override
            public void onResponse(Call<GetCheckDetailResponse> call, Response<GetCheckDetailResponse> response) {
                if (response.isSuccessful()){
                    GetCheckDetailResponse getCheckDetailResponse = response.body();
                    if (getCheckDetailResponse != null && getCheckDetailResponse.getCode() == 200) {
                        System.out.println("当前签到详情请求数据为："+getCheckDetailResponse.getData());
                        List<GetCheckDetailResponse.Data> data = getCheckDetailResponse.getData();
                        for (GetCheckDetailResponse.Data element : data) {
                            System.out.println("学生："+element);
                        }
                        CheckDetailAdapter adapter = new  CheckDetailAdapter (data);
                        recyclerView.setAdapter(adapter);
                    }
                    else {
                        System.out.println("错误码:"+getCheckDetailResponse.getCode()+"原因:"+getCheckDetailResponse.getMessage());
                        blankTip = rootView.findViewById(R.id.blankTip);
                        blankTip.setText("点击课程签到详情获取~");
                        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) blankTip.getLayoutParams();
                        layoutParams.topMargin=600;//设置顶部外边距marign为400像素
                    }
                }
            }

            @Override
            public void onFailure(Call<GetCheckDetailResponse> call, Throwable t) {
                System.out.println("请求失败：" + t.getMessage());
            }
        });
    }

}
