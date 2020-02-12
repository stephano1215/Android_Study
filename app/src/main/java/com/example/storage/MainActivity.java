package com.example.storage;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import static com.example.storage.R.layout;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private FileListAdapter mAdapter;
    private File cwd;
    private File edit_File;
    private File delete_File;
    private ImageButton mToggleButton;
    private ImageButton mSearchButton;
    private ImageButton mDeleteButton;
    private TextView mHistory;
    private ImageButton mBackButton;
    private ImageButton mAddButton;
    private LinearLayoutManager mLinearLM = new LinearLayoutManager(this);
    private GridLayoutManager mGridLM = new GridLayoutManager(this, 3);
    private static final int FILE_INSERTED = 1000;
    private static final int FILE_EDITED = 2000;
    ArrayList<File> ArrayFileList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);
        cwd = getFilesDir();
        init();

    }

    private void init() {
        mRecyclerView = findViewById(R.id.recyclerview);

        mAdapter = new FileListAdapter();
        ArrayFileList = new ArrayList<>(Arrays.asList(cwd.listFiles()));
        mAdapter.setmFilelist(ArrayFileList);
        mAdapter.setOnItemClickListener(itemClickListener);
        mAdapter.setOnItemLongClickListener(itemLongClickListener);

        mRecyclerView.setLayoutManager(mLinearLM);
        mRecyclerView.setAdapter(mAdapter);

        mToggleButton = findViewById(R.id.btn_toggle);
        mToggleButton.setOnClickListener(mylistener);

        mBackButton = findViewById(R.id.btn_back);
        mBackButton.setOnClickListener(mylistener);

        mAddButton = findViewById(R.id.btn_add);
        mAddButton.setOnClickListener(mylistener);

        mDeleteButton = findViewById(R.id.btn_delete);
        mDeleteButton.setOnClickListener(mylistener);

        mHistory = findViewById(R.id.history);
        mHistory.setText(cwd.getAbsolutePath());

        mAdapter.notifyDataSetChanged();
    }

    View.OnClickListener mylistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_toggle:
                    if (mAdapter.getIs_grid()) {
                        mAdapter.setIs_Grid(false);
                        mRecyclerView.setLayoutManager(mLinearLM);
                        mToggleButton.setSelected(false);
                    } else {
                        mAdapter.setIs_Grid(true);
                        mRecyclerView.setLayoutManager(mGridLM);
                        mToggleButton.setSelected(true);
                    }
                    break;
                case R.id.btn_back:
                    if (!cwd.getAbsolutePath().equals(getFilesDir().getAbsolutePath())) {
                        cwd = cwd.getParentFile();
                        ArrayFileList = new ArrayList<>(Arrays.asList(cwd.listFiles()));
                        mAdapter.setmFilelist(ArrayFileList);
                        mAdapter.notifyDataSetChanged();
                        mHistory.setText(cwd.getAbsolutePath());
                    } else {
                        Toast.makeText(MainActivity.this, "Root Dir", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.btn_add:
                    Intent intent = new Intent(MainActivity.this, EditActivity.class);
                    intent.putExtra("cwd", cwd.getAbsolutePath());
                    intent.putExtra("insert?", true);
                    startActivityForResult(intent, FILE_INSERTED);
                    break;
                case R.id.btn_delete:
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("파일 삭제");
                    builder.setMessage("파일을 삭제하시겠습니까?");
                    builder.setPositiveButton("예",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    int position = ArrayFileList.indexOf(delete_File);
                                    delete_File.delete();
                                    ArrayFileList = new ArrayList<>(Arrays.asList(cwd.listFiles()));
                                    mAdapter.notifyItemRemoved(position);
                                    mAdapter.notifyItemRangeChanged(position, mAdapter.getItemCount());
                                    mAdapter.setmFilelist(ArrayFileList);
                                }
                            });
                    builder.setNegativeButton("아니오",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                    builder.show();
                    mDeleteButton.setVisibility(View.GONE);
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == FILE_INSERTED) {
            ArrayFileList = new ArrayList<>(Arrays.asList(cwd.listFiles()));
            mAdapter.setmFilelist(ArrayFileList);
            mAdapter.notifyDataSetChanged();
        } else if (resultCode == FILE_EDITED) {
            mAdapter.notifyItemChanged(ArrayFileList.indexOf(edit_File));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private FileListAdapter.OnItemClickListener itemClickListener
            = new FileListAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(File data) {
            if (data.isDirectory()) {
                cwd = data;
                ArrayFileList = new ArrayList<>(Arrays.asList(cwd.listFiles()));
                mAdapter.setmFilelist(ArrayFileList);
                mAdapter.notifyDataSetChanged();
                mHistory.setText(cwd.getAbsolutePath());
            } else if (data.getName().endsWith(".txt")) {
                edit_File = data;
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra("cwd", edit_File.getAbsolutePath());
                intent.putExtra("insert?", false);
                startActivityForResult(intent, FILE_EDITED);
            } else {
                Toast.makeText(MainActivity.this, "not a dir", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private FileListAdapter.OnItemLongClickListener itemLongClickListener = new FileListAdapter.OnItemLongClickListener() {
        @SuppressLint("ResourceAsColor")
        @Override
        public void onItemLongClick(File data) {
//            if (mAdapter.getIs_grid()){
//                View view = mGridLM.findViewByPosition(ArrayFileList.indexOf(data));
//                view.setBackgroundColor(R.color.selectedColor);
//            }
            mDeleteButton.setVisibility(View.VISIBLE);
            delete_File = data;
        }
    };

    @Override
    public void onBackPressed() {
        if (!cwd.getAbsolutePath().equals(getFilesDir().getAbsolutePath())) {
            cwd = cwd.getParentFile();
            ArrayFileList = new ArrayList<>(Arrays.asList(cwd.listFiles()));
            mAdapter.setmFilelist(ArrayFileList);
            mAdapter.notifyDataSetChanged();
            mHistory.setText(cwd.getAbsolutePath());
        } else {
            super.onBackPressed();
        }
    }

}
