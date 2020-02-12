package com.example.storage;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.io.File;

public class GridViewHolder extends BaseViewHolder {

    protected ConstraintLayout layout;

    public GridViewHolder(@NonNull View itemView) {
        super(itemView);
        this.layout = itemView.findViewById(R.id.griditem);
        this.file_icon = itemView.findViewById(R.id.file_icon_grid);
        this.file_name = itemView.findViewById(R.id.file_name_grid);
        this.file_info = itemView.findViewById(R.id.file_info_grid);
    }

    @Override
    public void setData(File file) {
        file_name.setText(file.getName());
        file_icon.setImageResource(file.isDirectory() ? R.drawable.ic_folder_24px : R.drawable.ic_insert_drive_file_24px);
        file_info.setText(file.listFiles() != null ?
                file.listFiles().length + "개" : file.length() + "B");
    }
}
