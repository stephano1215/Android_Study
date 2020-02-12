package com.example.storage;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

public class FileListAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private ArrayList<File> mFilelist;
    private boolean[] mSelectedlist;
    private boolean mSelecteditem = false;
    private boolean is_Grid;

    void setmFilelist(ArrayList<File> mFilelist) {
        this.mFilelist = mFilelist;
        mSelectedlist = new boolean[mFilelist.size()];
    }

    void mFiledelete(int position){
        mFilelist.remove(position);
    }

    FileListAdapter() {
        this.is_Grid = false;
    }

    public boolean ismSelecteditem() {
        return mSelecteditem;
    }

    public void setmSelecteditem(boolean mSelecteditem) {
        this.mSelecteditem = mSelecteditem;
    }

    public interface OnItemClickListener{
        void onItemClick(File data);
    }

    public interface OnItemLongClickListener{
        void onItemLongClick(File data);
    }

    private OnItemClickListener listener;
    private OnItemLongClickListener longlistener;

    void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
    void setOnItemLongClickListener(OnItemLongClickListener longlistener) {this.longlistener = longlistener;}

    void setIs_Grid(boolean is_Grid) {
        this.is_Grid = is_Grid;
    }

    boolean getIs_grid() {
        return is_Grid;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ItemType.TYPE_LINEAR) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
            return new LinearViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
            return new GridViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (!is_Grid) {
            return ItemType.TYPE_LINEAR;
        } else {
            return ItemType.TYPE_GRID;
        }
    }

    private static final int POSITION_TAG = 0xFFFFFFF1;

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MainActivity.setItemposition((int)v.getTag(POSITION_TAG));
            listener.onItemClick(mFilelist.get((int) v.getTag(POSITION_TAG)));
        }
    };


    private View.OnLongClickListener longClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
//            v.setBackgroundColor(v.getResources().getColor(R.color.colorAccent));
            mSelectedlist[(int)v.getTag(POSITION_TAG)] = true;
            mSelecteditem = true;
            MainActivity.setItemposition((int)v.getTag(POSITION_TAG));
            notifyItemChanged((int)v.getTag(POSITION_TAG));
            longlistener.onItemLongClick(mFilelist.get((int) v.getTag(POSITION_TAG)));
            return true;
        }
    };

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.setData(mFilelist.get(position));
        holder.itemView.setTag(POSITION_TAG, position);
        holder.itemView.setOnClickListener(clickListener);
        holder.itemView.setOnLongClickListener(longClickListener);
        if (mSelectedlist[position]){
            holder.itemView.setBackgroundColor(0x1496F1);
        }
        else {
            holder.itemView.setBackgroundColor(0xFFFFFFFF);
        }
    }

    public class ItemType {
        static final int TYPE_LINEAR = 0;
        static final int TYPE_GRID = 1;
    }

    @Override
    public int getItemCount() {
        if (mFilelist != null) {
            return mFilelist.size();
        } else {
            return 0;
        }
    }

    void delete(int position) {
        notifyItemRemoved(position);
    }
}
