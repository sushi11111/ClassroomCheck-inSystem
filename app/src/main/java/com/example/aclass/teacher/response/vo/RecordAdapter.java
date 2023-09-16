package com.example.aclass.teacher.response.vo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.aclass.Constants;
import com.example.aclass.Login.LoginResponse;
import com.example.aclass.R;
import com.example.aclass.student.Courses.Course;
import com.example.aclass.student.Courses.SelectService;
import com.example.aclass.student.Courses.ShortResponse;
import com.example.aclass.teacher.StartingCheckActivity;
import com.example.aclass.teacher.TeacherCheckFragment;
import com.example.aclass.teacher.TeacherService;
import com.example.aclass.teacher.response.DeleteClassResponse;
import com.example.aclass.teacher.response.GetUnFinishClassResponse;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.List;


public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder>{
    private List<GetUnFinishClassResponse.Record> records;
    LoginResponse.UserData userData;
    private FrameLayout fragmentContainer;
    private FragmentManager fragmentManager;
    private BottomNavigationView bottomNavigationView;
    View itemView;

    public RecordAdapter(List<GetUnFinishClassResponse.Record> records, LoginResponse.UserData userData, FragmentManager fragmentManager,BottomNavigationView bottomNavigationView) {
        this.records = records;
        this.userData = userData;
        this.fragmentManager = fragmentManager;
        this.bottomNavigationView = bottomNavigationView;
    }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate item layout
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_course_records, parent, false);
        return new ViewHolder(itemView);
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
                dialogDelete(holder);
            }
        });
        //发起签到按钮
        holder.startCheckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建一个Intent对象，指定当前活动为上下文，目标活动为要跳转的活动类
                Intent intent = new Intent(v.getContext(), StartingCheckActivity.class);
                // 添加需要传递的参数，例如：
                intent.putExtra("userData", userData);
                intent.putExtra("courseId", holder.courseId.getText());
                intent.putExtra("courseName",holder.courseName.getText());
                // 启动目标活动
                v.getContext().startActivity(intent);
            }
        });
        //签到详情按钮
        holder.checkDetailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //显示导航到签到页面
                bottomNavigationView.setSelectedItemId(R.id.menu_check_in);

                Fragment fragment = TeacherCheckFragment.newInstance(userData,Integer.parseInt(holder.courseId.getText().toString())); // 传递数据给 OtherFragment
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragment_container, fragment);
                transaction.commit();
                System.out.println("跳转签到详情...");

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
        TextView deleteCourseBtn;
        TextView startCheckBtn;
        TextView checkDetailBtn;
        FrameLayout fragmentContainer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            courseName = itemView.findViewById(R.id.courseName);
            collegeName = itemView.findViewById(R.id.collegeName);
            courseId = itemView.findViewById(R.id.courseId);
            coursePhoto = itemView.findViewById(R.id.coursePhoto);
            deleteCourseBtn = itemView.findViewById(R.id.deleteCourseBtn);
            startCheckBtn = itemView.findViewById(R.id.startCheckBtn);
            checkDetailBtn = itemView.findViewById(R.id.checkDetailBtn);
            // 初始化 fragmentContainer
            fragmentContainer = itemView.findViewById(R.id.fragment_container);
        }

    }

    private void dialogDelete(@NonNull ViewHolder holder) {
        AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext(),R.style.DialogButtonStyle);
        builder.setTitle("确认删除这门课吗？");
        builder.setMessage("是否要选择删除：" + holder.courseName.getText() +" 这门课？");

        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
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
                                for (int i = 0; i < records.size(); i++) {
                                    if(records.get(i).getCourseId().equals(holder.courseId.getText().toString())){
                                        records.remove(i);
                                    }
                                }
                                notifyDataSetChanged();
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
                dialogInterface.dismiss();
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog = builder.create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.purple_500));
                positiveButton.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), android.R.color.white));

                Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                negativeButton.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.purple_500));
                negativeButton.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), android.R.color.white));
            }
        });
        dialog.show();


    }
}
