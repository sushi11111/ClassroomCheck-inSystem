package com.example.aclass.student.CheckIn;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aclass.Constants;
import com.example.aclass.R;
import com.example.aclass.student.Courses.Course;
import com.example.aclass.student.Courses.SelectService;
import com.example.aclass.student.Courses.ShortResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StudentCheckInAdapter extends RecyclerView.Adapter<StudentCheckInAdapter.CheckInCourseViewHolder>{
    private List<CheckInCourse> yesCourseList;
    private List<CheckInCourse> noCourseList;

    private List<Course> selectCourseList;
    private Integer userId;

    View itemView;
    public StudentCheckInAdapter(List<CheckInCourse> yesCourseList, List<CheckInCourse> noCourseList,
                                 List<Course> selectCourseList, Integer userId) {
        this.yesCourseList = yesCourseList;
        this.noCourseList = noCourseList;
        this.selectCourseList = selectCourseList;
        this.userId = userId;
    }

    @NonNull
    @Override
    public CheckInCourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student_check_in, parent, false);
        return new CheckInCourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckInCourseViewHolder holder, int position) {
        // 设置边框
        holder.itemView.setBackgroundResource(R.drawable.custom_border); // 替换为你的边框资源

        CheckInCourse course = null;

        if (position < noCourseList.size()) {
            // 当 position 小于 noCourseList.size() 时，显示未签到的课程
            course = noCourseList.get(position);
            //调用bind1
            holder.bind1(course);
        } else {
            // 当 position 大于等于 noCourseList.size() 时，显示已签到的课程
            int adjustedPosition = position - noCourseList.size();
            if (adjustedPosition < yesCourseList.size()) {
                course = yesCourseList.get(adjustedPosition);
                //调用bind2
                holder.bind2(course);
            }
        }

        // 获取userSignId
        Integer userSignId = Integer.parseInt(course.getUserSignId());

        // 获取 Drawable 对象
        Drawable courseInDrawable = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.no);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 获取 ImageView 当前显示的 Drawable 对象
                Drawable currentDrawable = holder.status_pho.getDrawable();
                if(currentDrawable!=null&& courseInDrawable!=null){
                    if(currentDrawable.getConstantState().equals(courseInDrawable.getConstantState())){
                        // 创建一个AlertDialog.Builder
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                        builder.setTitle("请输入签到码");

                        // 创建并设置自定义布局
                        View customLayout = LayoutInflater.from(view.getContext()).inflate(R.layout.dialog_sign_in, null);
                        builder.setView(customLayout);

                        // 获取签到码输入框
                        EditText signInCodeEditText = customLayout.findViewById(R.id.signInCodeEditText);

                        // 设置确定按钮
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // 获取输入框中的签到码
                                String signInCode = signInCodeEditText.getText().toString();
                                System.out.println(signInCode);
                                sendCheckInRequest(Integer.parseInt(signInCode),userId,userSignId,view);
                            }
                        });
                        // 设置取消按钮
                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                        // 创建并显示对话框
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }


                    else {
                        // 创建一个AlertDialog.Builder
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                        builder.setMessage("你已签到，请勿重复签到！")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // 这里可以添加点击确定按钮后的操作
                                        dialog.dismiss(); // 关闭对话框
                                    }
                                });
                        // 创建并显示对话框
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                }
            }
        });
    }

    public void sendCheckInRequest(Integer signInCode,Integer userId,Integer userSignId,View view){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL) // 替换为实际的基础 URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CheckInService checkInService = retrofit.create(CheckInService.class);
        Call<ShortResponse> call = checkInService.StudentCheckIn(new CheckInRequest(signInCode,userId,userSignId));
        call.enqueue(new Callback<ShortResponse>() {
            @Override
            public void onResponse(Call<ShortResponse> call, Response<ShortResponse> response) {
                if (response.isSuccessful()){
                    // 将下面的消息弹出提醒用户
                    String message=null;
                    if(response.body().getCode()!=200){
                        message = "签到失败，";
                    }else {
                        String courseName = null;
                        String courseAddr = null;
                        String createTime = null;
                        //通知适配器数据已改变
                        for (int i = 0; i < noCourseList.size(); i++) {
                            if(Integer.parseInt(noCourseList.get(i).getUserSignId())==userSignId){
                                courseName = noCourseList.get(i).getCourseName();
                                courseAddr = noCourseList.get(i).getCourseAddr();
                                createTime = noCourseList.get(i).getCreateTime();
                                noCourseList.remove(i);
                            }
                        }
                        yesCourseList.add(new CheckInCourse(Integer.toString(userSignId),courseName,courseAddr,createTime));
                        notifyDataSetChanged();
                    }
                    // 创建一个AlertDialog.Builder
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    if(response.body().getCode()!=200){
                        builder.setMessage(message+response.body().getMsg())
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // 这里可以添加点击确定按钮后的操作
                                        dialog.dismiss(); // 关闭对话框
                                    }
                                });
                    }
                    else {
                        builder.setMessage("签到成功！")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // 这里可以添加点击确定按钮后的操作
                                        dialog.dismiss(); // 关闭对话框
                                    }
                                });
                    }
                    // 创建并显示对话框
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }else {
                    System.out.println("签到失败");
                }
            }

            @Override
            public void onFailure(Call<ShortResponse> call, Throwable t) {

            }
        });
    }



    @Override
    public int getItemCount() {
        return yesCourseList.size() + noCourseList.size();
    }

    class CheckInCourseViewHolder extends RecyclerView.ViewHolder {
        TextView courseNameTextView;
        TextView courseAddrTextView;
        TextView createTimeTextView;
        ImageView status_pho;
        TextView status_info;

        public CheckInCourseViewHolder(@NonNull View itemView) {
            super(itemView);
            courseNameTextView = itemView.findViewById(R.id.student_courseName);
            courseAddrTextView = itemView.findViewById(R.id.student_courseAddr);
            createTimeTextView = itemView.findViewById(R.id.student_createTime);
            status_pho = itemView.findViewById(R.id.checkInStatusPhoto);
            status_info = itemView.findViewById(R.id.checkInStatusInfo);
        }

        public void bind1(CheckInCourse course){
            courseNameTextView.setText(course.getCourseName());
            courseAddrTextView.setText(course.getCourseAddr());
            createTimeTextView.setText(course.getCreateTime());
            status_pho.setImageResource(R.drawable.no);
            status_info.setText("未签到");
        }

        public void bind2(CheckInCourse course){
            courseNameTextView.setText(course.getCourseName());
            courseAddrTextView.setText(course.getCourseAddr());
            createTimeTextView.setText(course.getCreateTime());
            status_pho.setImageResource(R.drawable.yes);
            status_info.setText("已成功");
        }
    }

}
