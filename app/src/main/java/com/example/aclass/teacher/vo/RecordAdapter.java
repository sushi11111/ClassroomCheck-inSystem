package com.example.aclass.teacher.vo;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.aclass.R;
import com.example.aclass.teacher.response.GetUnFinishClassResponse;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder>{
    private List<GetUnFinishClassResponse.Record> records;

    public RecordAdapter(List<GetUnFinishClassResponse.Record> records) {
        this.records = records;
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            courseName = itemView.findViewById(R.id.courseName);
            collegeName = itemView.findViewById(R.id.collegeName);
            courseId = itemView.findViewById(R.id.courseId);
            coursePhoto = itemView.findViewById(R.id.coursePhoto);
        }

    }
}
