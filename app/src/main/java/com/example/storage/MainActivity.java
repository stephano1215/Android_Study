package com.example.storage;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.storage.R.layout;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private FileListAdapter mAdapter;
    private ImageButton mToggleButton;
    private ImageButton mSearchButton;
    private static TextView mHistory;
    private ImageButton mBackButton;
    private LinearLayoutManager mLinearLM = new LinearLayoutManager(this);
    private GridLayoutManager mGridLM = new GridLayoutManager(this,3);

    public static void setmHistory(String path){
        mHistory.setText(path);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);
        init();

    }

    private void init() {
        mRecyclerView = findViewById(R.id.recyclerview);
        mAdapter = new FileListAdapter(this);
        mRecyclerView.setLayoutManager(mLinearLM);
        mRecyclerView.setAdapter(mAdapter);
        mToggleButton = findViewById(R.id.btn_toggle);
        mToggleButton.setOnClickListener(mylistener);
        mBackButton = findViewById(R.id.btn_back);
        mBackButton.setOnClickListener(mylistener);
        mHistory = findViewById(R.id.history);
        mHistory.setText(mAdapter.getCwd());
        mAdapter.update();
    }

    View.OnClickListener mylistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_toggle:
                    if (mAdapter.getIs_grid()){
                        mAdapter.setIs_Grid(false);
                        mRecyclerView.setLayoutManager(mLinearLM);
                        mToggleButton.setSelected(false);
                    }
                    else {
                        mAdapter.setIs_Grid(true);
                        mRecyclerView.setLayoutManager(mGridLM);
                        mToggleButton.setSelected(true);
                    }
                    break;
                case R.id.btn_back:
                    if (!mAdapter.getCwd().equals("/system")) {
                        mAdapter.setCwdtoParent();
                        mAdapter.update();
                        mHistory.setText(mAdapter.getCwd());
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Root Dir", Toast.LENGTH_SHORT).show();
                    }
            }
        }
    };
}
