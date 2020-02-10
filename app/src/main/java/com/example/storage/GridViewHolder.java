package com.example.storage;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

public class GridViewHolder extends BaseViewHolder {

    protected ConstraintLayout layout;

    public GridViewHolder(@NonNull View itemView) {
        super(itemView);
        this.layout = itemView.findViewById(R.id.griditem);
        this.file_icon = itemView.findViewById(R.id.file_icon_grid);
        this.file_name = itemView.findViewById(R.id.file_name_grid);
        this.file_info = itemView.findViewById(R.id.file_info_grid);
    }
}
