package com.example.storage;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

    protected ImageView file_icon;
    protected TextView file_name;
    protected TextView file_info;

    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public abstract void setData(File file);

}
