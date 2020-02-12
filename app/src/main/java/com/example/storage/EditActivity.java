package com.example.storage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class EditActivity extends AppCompatActivity {

    File cwd;
    boolean is_Inserted;
    EditText mEditContent;
    EditText mEditTitle;
    ImageButton mBackButton;
    ImageButton mCommitButton;
    ImageButton mEditButton;
    FileWriter fw = null;
    FileReader fr = null;
    private static final int FILE_INSERTED = 1000;
    private static final int FILE_EDITED = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent intent = getIntent();
        String temp = intent.getStringExtra("cwd");
        is_Inserted = intent.getBooleanExtra("insert?", true);

        mCommitButton = findViewById(R.id.btn_edit_commit);
        mCommitButton.setOnClickListener(listener);

        mBackButton = findViewById(R.id.btn_edit_back);
        mBackButton.setOnClickListener(listener);

        mEditButton = findViewById(R.id.btn_edit_edit);
        mEditButton.setOnClickListener(listener);

        cwd = new File(temp);

        mEditContent = findViewById(R.id.edit_content);
        String[] tmp = cwd.list();
        mEditTitle = findViewById(R.id.edit_title);
        if (!is_Inserted){
            mEditButton.setVisibility(View.VISIBLE);
            mCommitButton.setVisibility(View.GONE);
            mEditTitle.setText(cwd.getName());
            mEditTitle.setEnabled(false);
            try {
                fr = new FileReader(cwd.getAbsolutePath());
                char[] buf = new char[512];
                String content;
                int size = 0;
                while((size = fr.read(buf)) != -1){
                    content = String.copyValueOf(buf);
                    mEditContent.append(content);
                    buf = new char[512];
                }
                mEditContent.setEnabled(false);
                fr.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.btn_edit_back:
                    finish();
                    break;
                case R.id.btn_edit_commit:
                    commit();
                    break;
                case R.id.btn_edit_edit:
                    mEditContent.setEnabled(true);
                    mEditButton.setVisibility(View.GONE);
                    mCommitButton.setVisibility(View.VISIBLE);
            }
        }
    };

    void commit() {
        if (is_Inserted) {
            if (mEditTitle.getText().length() == 0 || mEditContent.getText().length() == 0) {
                Toast.makeText(this, "제목, 내용을 입력하세요.", Toast.LENGTH_SHORT).show();
            }
            else {
                ArrayList<String> filelist = new ArrayList<>(Arrays.asList(cwd.list()));
                if (filelist.contains(mEditTitle.getText() + ".txt")) {
                    Toast.makeText(this, "이미 있는 파일이름입니다.", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        fw = new FileWriter(cwd.getAbsolutePath() + "/" + mEditTitle.getText().toString() + ".txt");
                        fw.write(mEditContent.getText().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (fw != null) {
                        try {
                            fw.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    setResult(FILE_INSERTED);
                    finish();
                }
            }
        }
        else {
            try {
                fw = new FileWriter(cwd.getAbsolutePath());
                fw.write(mEditContent.getText().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            setResult(FILE_EDITED);
            finish();
        }
    }
}
