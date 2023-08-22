package com.example.aclass.teacher.response.vo;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.aclass.Constants;
import com.example.aclass.Login.LoginResponse;
import com.example.aclass.R;
import com.example.aclass.teacher.StartingCheckActivity;
import com.example.aclass.teacher.TeacherCheckFragment;
import com.example.aclass.teacher.TeacherService;
import com.example.aclass.teacher.response.DeleteClassResponse;
import com.example.aclass.teacher.response.GetUnFinishClassResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.List;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder>{
    private List<GetUnFinishClassResponse.Record> records;
    LoginResponse.UserData userData;

    public RecordAdapter(List<GetUnFinishClassResponse.Record> records, LoginResponse.UserData userData) {
        this.records = records;
        this.userData = userData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate item layout
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_course_records, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GetUnFinishClassResponse.Record record = records.get(position);
        holder.courseName.setText(record.getCourseName());
        holder.courseId.setText(record.getCourseId());
        holder.collegeName.setText(record.getCollegeName());
        // 使用Glide加载图片
        Glide.with(holder.coursePhoto.getContext())
                .load(record.getCoursePhoto())
                .into(holder.coursePhoto);
        System.out.println(holder.courseName.getText());
        System.out.println(holder.collegeName.getText());

        //删除课程按钮
        holder.deleteCourseBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Constants.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                TeacherService teacherService = retrofit.create(TeacherService.class);
                Call<DeleteClassResponse> call = teacherService.deleteClass(Integer.parseInt(holder.courseId.getText().toString()),Integer.parseInt(userData.getId()));
                call.enqueue(new Callback<DeleteClassResponse>(){

                    @Override
                    public void onResponse(Call<DeleteClassResponse> call, Response<DeleteClassResponse> response) {
                        if (response.isSuccessful()){
                            DeleteClassResponse deleteClassResponse = response.body();
                            if (deleteClassResponse != null && deleteClassResponse.getCode() == 200) {
                                System.out.println("删除成功!："+deleteClassResponse.getData());
                            }
                            else {
                                System.out.println("错误码:"+deleteClassResponse.getCode()+"原因:"+deleteClassResponse.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<DeleteClassResponse> call, Throwable t) {
                        System.out.println("请求失败：" + t.getMessage());
                    }
                });
            }
        });
        //发起签到按钮
        holder.startCheckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建一个Intent对象，指定当前活动为上下文，目标活动为要跳转的活动类
                Intent intent = new Intent(v.getContext(), StartingCheckActivity.class);

                // 添加需要传递的参数，例如：
//                intent.putExtra("key", value);

                // 启动目标活动
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return records.size();
    }
    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView courseName;
        TextView collegeName;
        TextView courseId;
        ImageView coursePhoto;
        Button deleteCourseBtn;
        Button startCheckBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            courseName = itemView.findViewById(R.id.courseName);
            collegeName = itemView.findViewById(R.id.collegeName);
            courseId = itemView.findViewById(R.id.courseId);
            coursePhoto = itemView.findViewById(R.id.coursePhoto);
            deleteCourseBtn = itemView.findViewById(R.id.deleteCourseBtn);
            startCheckBtn = itemView.findViewById(R.id.startCheckBtn);
        }

    }
}
