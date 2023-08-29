package com.example.aclass.teacher.response.vo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.aclass.R;
import com.example.aclass.teacher.response.GetCheckDetailResponse;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CheckDetailAdapter extends RecyclerView.Adapter<CheckDetailAdapter.ViewHolder>{
    private List<GetCheckDetailResponse.Data> data;

    public CheckDetailAdapter(List<GetCheckDetailResponse.Data> data) {
        this.data =data;
    }

    @NotNull
    @Override
    public CheckDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate item layout
        return new CheckDetailAdapter.ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_check_detail, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CheckDetailAdapter.ViewHolder holder, int position) {
        GetCheckDetailResponse.Data dataItem = data.get(position);
        holder.stuId.setText(holder.stuId.getText()+dataItem.getUserId());
        holder.checkCourse.setText(dataItem.getCourseName());
        holder.addrCourse.setText(dataItem.getCourseAddr());
//        holder.checkStatus.setText(dataItem.getStatus());
        long timestamp = Long.parseLong(dataItem.getSignTime());
        // 创建一个SimpleDateFormat对象，定义日期格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date;
        String dateString;
        if(dataItem.getStatus()==0)
        {
            holder.checkStatus.setText("未签到");
            holder.checkTime.setText("");
        }
        else {
            holder.checkStatus.setText("已签到");
            // 将时间戳转换为Date对象
            date = new Date(timestamp);
            // 使用SimpleDateFormat将Date对象格式化为日期字符串
            dateString = sdf.format(date);
            System.out.println("签到时间：" + dateString);
            holder.checkTime.setText(holder.checkTime.getText()+dateString);
        }
        timestamp = Long.parseLong(dataItem.getSignTime());
        date = new Date(timestamp);
        dateString = sdf.format(date);
        System.out.println("发起时间：" + dateString);
        holder.createTime.setText(holder.createTime.getText()+dateString);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView stuId;
        TextView checkCourse;
        TextView addrCourse;
        TextView checkStatus;
        TextView checkTime;
        TextView createTime;

        public ViewHolder (@NonNull View itemView) {
            super(itemView);
            stuId = itemView.findViewById(R.id.stuId);
            checkCourse = itemView.findViewById(R.id.checkCourse);
            addrCourse = itemView.findViewById(R.id.addrCourse);
            checkStatus = itemView.findViewById(R.id.checkStatus);
            checkTime = itemView.findViewById(R.id.checkTime);
            createTime = itemView.findViewById(R.id.createTime);
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
