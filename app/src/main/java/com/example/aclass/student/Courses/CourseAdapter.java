package com.example.aclass.student.Courses;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.aclass.Constants;
import com.example.aclass.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder>{
    private List<Course> courseList;
    private List<Course> selectedList;
    Integer userId;
    View itemView;
    public CourseAdapter(List<Course> courseList,List<Course>selectedList,Integer userId) {
        this.courseList = courseList;
        this.selectedList = selectedList;
        this.userId = userId;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course, parent, false);
        return new CourseViewHolder(itemView);
    }

    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Course course = courseList.get(position);
        holder.bind(course);

        // 设置边框
        holder.itemView.setBackgroundResource(R.drawable.custom_border); // 替换为你的边框资源

        // 获取 Drawable 对象
        Drawable courseInDrawable = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.course_in);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL) // 替换为实际的基础 URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SelectService selectService = retrofit.create(SelectService.class);
        DeleteService deleteService = retrofit.create(DeleteService.class);

        // 设置点击事件
        holder.courseStatusPhotoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 获取 ImageView 当前显示的 Drawable 对象
                Drawable currentDrawable = holder.courseStatusPhotoImageView.getDrawable();

                if (currentDrawable != null && courseInDrawable != null) {
                    // 使用 getConstantState() 比较两个 Drawable 对象
                    if (currentDrawable.getConstantState().equals(courseInDrawable.getConstantState())) {
                        // 执行选课/退课的逻辑
                        showConfirmationDialog_in(course,selectService);
                    } else {
                        // 这里执行另一种逻辑
                        showConfirmationDialog_out(course,deleteService);
                    }
                }
            }
        });
    }

    private void showConfirmationDialog_in(Course course,SelectService selectService) {
        AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
        builder.setTitle("确认选课");
        builder.setMessage("是否要选择：" + course.getCourseName() + " 这门课？");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Integer courseId = Integer.parseInt(course.getCourseId());
                System.out.println("courseId = "+courseId);
                System.out.println("userId = "+userId);
                // 发送选课请求
                Call<ShortResponse>selectCourseCall = selectService.selectCourses(courseId,userId);
                selectCourseCall.enqueue(new Callback<ShortResponse>() {
                    @Override
                    public void onResponse(Call<ShortResponse> call, Response<ShortResponse> response) {
                        System.out.println(response.message());
                        if (response.isSuccessful()) {
                            selectedList.add(course);
                            // 数据改变
                            notifyDataSetChanged();
                        } else
                            System.out.println("选课失败");
                    }
                    @Override
                    public void onFailure(Call<ShortResponse> call, Throwable t) {
                        // 处理请求失败的情况
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
        dialog.show();
    }

    private void showConfirmationDialog_out(Course course,DeleteService deleteService) {
        AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
        builder.setTitle("确认退课");
        builder.setMessage("是否要退掉：" + course.getCourseName() + " 这门课？");

        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Integer courseId = Integer.parseInt(course.getCourseId());
                System.out.println("courseId = "+courseId);
                System.out.println("userId = "+userId);

                // 发送退课请求
                Call<ShortResponse>selectCourseCall = deleteService.deleteCourses(courseId,userId);
                selectCourseCall.enqueue(new Callback<ShortResponse>() {
                    @Override
                    public void onResponse(Call<ShortResponse> call, Response<ShortResponse> response) {
                        System.out.println(response.message());
                        if (response.isSuccessful()) {
                            selectedList.remove(course);
                            for (int j = 0; j < selectedList.size(); j++) {
                                if(selectedList.get(j).getCourseName().equals(course.getCourseName())){
                                    selectedList.remove(j);
                                }
                            }
                            // 数据改变
                            notifyDataSetChanged();
                        } else
                            System.out.println("退课失败");
                    }
                    @Override
                    public void onFailure(Call<ShortResponse> call, Throwable t) {
                        // 处理请求失败的情况
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
        dialog.show();
    }


    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public class CourseViewHolder extends RecyclerView.ViewHolder {
        private ImageView courseImageView;
        private ImageView courseStatusPhotoImageView;
        private TextView courseNameTextView;
        private TextView schoolNameTextView;
        private TextView courseStatusInfoTextView;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            courseImageView = itemView.findViewById(R.id.courseImageView);
            courseNameTextView = itemView.findViewById(R.id.courseNameTextView);
            schoolNameTextView = itemView.findViewById(R.id.schoolNameTextView);
            courseStatusInfoTextView = itemView.findViewById(R.id.courseStatusInfo);
            courseStatusPhotoImageView = itemView.findViewById(R.id.courseStatusPhoto);
        }

        public void bind(Course course) {
            // 使用 Glide 加载图片到 ImageView
            Glide.with(itemView.getContext())
                    .load(course.getCoursePhoto()) // 这里将图片 URL 传递给 Glide
                    .placeholder(R.drawable.blank) // 占位图
                    .into(courseImageView);

            //设置课程名和院校名
            courseNameTextView.setText(course.getCourseName());
            schoolNameTextView.setText(course.getCollegeName());

            //显示课程状态
//            if(selectedList.contains(course)){
//                courseStatusInfoTextView.setText("退课");
//                courseStatusPhotoImageView.setImageResource(R.drawable.course_out);
//            }else {
//                System.out.println(course.getCourseName());
//                System.out.println(selectedList.isEmpty());
//                System.out.println("selectedList = "+selectedList.get(0).getCourseName());
//                courseStatusInfoTextView.setText("选课");
//                courseStatusPhotoImageView.setImageResource(R.drawable.course_in);
//            }
            courseStatusInfoTextView.setText("选课");
            courseStatusPhotoImageView.setImageResource(R.drawable.course_in);

            if(!selectedList.isEmpty()){
                for (int i = 0; i < selectedList.size(); i++) {
                    if(selectedList.get(i).getCourseName().equals(course.getCourseName())){
                        courseStatusInfoTextView.setText("退课");
                        courseStatusPhotoImageView.setImageResource(R.drawable.course_out);
                    }
                }
            }
        }
    }
}
