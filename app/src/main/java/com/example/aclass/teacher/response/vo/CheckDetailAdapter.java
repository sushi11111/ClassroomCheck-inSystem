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
        holder.stuId.setText(dataItem.getUserId());
        holder.checkCourse.setText(dataItem.getCourseName());
        holder.addrCourse.setText(dataItem.getCourseAddr());
//        holder.checkStatus.setText(dataItem.getStatus());
        if(dataItem.getStatus()==0)
        {
            holder.checkStatus.setText("未签到");
        }
        else {
            holder.checkStatus.setText("已签到");
        }
        holder.checkTime.setText(dataItem.getSignTime());
        holder.createTime.setText(dataItem.getCreateTime());
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
