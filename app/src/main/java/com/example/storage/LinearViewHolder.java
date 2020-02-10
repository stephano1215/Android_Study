package com.example.storage;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;


public class LinearViewHolder extends BaseViewHolder {

    protected ConstraintLayout layout;
    protected TextView file_date;

    public LinearViewHolder(@NonNull View itemView) {
        super(itemView);
        this.layout = itemView.findViewById(R.id.listitem);
        this.file_icon = itemView.findViewById(R.id.file_icon);
        this.file_name = itemView.findViewById(R.id.file_name);
        this.file_date = itemView.findViewById(R.id.file_date);
        this.file_info = itemView.findViewById(R.id.file_info);
    }
}
