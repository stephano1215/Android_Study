package com.example.storage;

import android.content.Context;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileListAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private File[] mFilelist;
    private boolean is_Grid;
    private String cwd;
    private String parentDir;
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy년 MM월 dd일 HH:mm");

    public FileListAdapter(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.cwd = Environment.getRootDirectory().toString();
        this.mFilelist = new File(cwd).listFiles();
        this.is_Grid = false;
    }



    public void setCwdtoParent() {
        if (!cwd.equals(Environment.getRootDirectory().toString())) {
            parentDir = new File(cwd).getParent();
            this.cwd = parentDir;
            mFilelist = new File(cwd).listFiles();
        }
    }

    public String getCwd() {
        return cwd;
    }

    public void setIs_Grid(boolean is_Grid) {
        this.is_Grid = is_Grid;
    }

    public boolean getIs_grid() {
        return is_Grid;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        viewType = getItemViewType(0);
        if (viewType == ItemType.TYPE_LINEAR) {
            View view = mInflater.inflate(R.layout.list_item, parent, false);
            return new LinearViewHolder(view);
        } else {
            View view = mInflater.inflate(R.layout.grid_item, parent, false);
            return new GridViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (!is_Grid) {
            return 0;
        } else {
            return 1;
        }
    }


    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, final int position) {
        if (!is_Grid) {
            Date LMdate = new Date();
            LMdate.setTime(mFilelist[position].lastModified());
            String S_LMdate = formatter.format(LMdate);
            LinearViewHolder linearViewHolder = (LinearViewHolder) holder;
            linearViewHolder.file_name.setText(mFilelist[position].getName());
            linearViewHolder.file_icon.setImageResource(mFilelist[position].isDirectory() ? R.drawable.baseline_folder_black_18dp : R.drawable.baseline_insert_drive_file_black_18dp);
            linearViewHolder.file_date.setText(S_LMdate);
            linearViewHolder.file_info.setText(String.valueOf(mFilelist[position].listFiles() != null ?
                    mFilelist[position].listFiles().length + "개" : mFilelist[position].length() + "B"));
            linearViewHolder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    parentDir = mFilelist[position].getParent();
                    cwd = mFilelist[position].getAbsolutePath();
                    if(mFilelist[position].isDirectory()) {
                        mFilelist = new File(cwd).listFiles();
                        MainActivity.setmHistory(cwd);
                        update();
                    }
                    else {
                        Toast.makeText(mContext,"Not a Dir", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            GridViewHolder gridViewHolder = (GridViewHolder) holder;
            gridViewHolder.file_name.setText(mFilelist[position].getName());
            gridViewHolder.file_icon.setImageResource(mFilelist[position].isDirectory() ? R.drawable.baseline_folder_black_18dp : R.drawable.baseline_insert_drive_file_black_18dp);
            gridViewHolder.file_info.setText(String.valueOf(mFilelist[position].listFiles() != null ?
                    mFilelist[position].listFiles().length + "개" : mFilelist[position].length() + "B"));
            gridViewHolder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    parentDir = mFilelist[position].getParent();
                    cwd = mFilelist[position].getAbsolutePath();
                    if(mFilelist[position].isDirectory()) {
                        mFilelist = new File(cwd).listFiles();
                        update();
                    }
                    else {
                        Toast.makeText(mContext,"Not a Dir", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public class ItemType {
        public static final int TYPE_LINEAR = 0;
        public static final int TYPE_GRID = 1;
    }

    @Override
    public int getItemCount() {
        if (mFilelist != null) {
            return mFilelist.length;
        } else {
            return 0;
        }
    }

    public void update() {
        notifyDataSetChanged();
    }
}
