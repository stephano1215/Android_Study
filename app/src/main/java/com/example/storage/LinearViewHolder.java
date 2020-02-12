package com.example.storage;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


public class LinearViewHolder extends BaseViewHolder {

//    protected ConstraintLayout layout;
    protected TextView file_date;
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy년 MM월 dd일 HH:mm");


    public LinearViewHolder(@NonNull View itemView) {
        super(itemView);
        this.layout = itemView.findViewById(R.id.listitem);
        this.file_icon = itemView.findViewById(R.id.file_icon);
        this.file_name = itemView.findViewById(R.id.file_name);
        this.file_date = itemView.findViewById(R.id.file_date);
        this.file_info = itemView.findViewById(R.id.file_info);
    }

    @Override
    public void setData(File file) {
        Date LMdate = new Date();
        LMdate.setTime(file.lastModified());
        String S_LMdate = formatter.format(LMdate);
        file_name.setText(file.getName());
        file_icon.setImageResource(file.isDirectory() ? R.drawable.ic_folder_24px : R.drawable.ic_insert_drive_file_24px);
        file_date.setText(S_LMdate);
        file_info.setText(file.listFiles() != null ?
                file.listFiles().length + "개" : file.length() + "B");
    }


}
