package com.example.note;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.note.database.SQLiteHelper;
import com.example.note.utils.DBUtils;

public class RecordActivity extends Activity implements View.OnClickListener {
    ImageView note_back;
    TextView note_time;
    EditText content;
    ImageView delete;
    ImageView note_save;
    SQLiteHelper mSQLiteHelper;
    TextView noteName;
    String id;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        note_back = (ImageView) findViewById(R.id.note_back);
        note_time = (TextView) findViewById(R.id.tv_time);
        content = (EditText) findViewById(R.id.note_content);
        delete = (ImageView) findViewById(R.id.delete);
        note_save = (ImageView) findViewById(R.id.note_save);
        noteName = (TextView) findViewById(R.id.note_name);
        note_back.setOnClickListener(this);
        delete.setOnClickListener(this);
        note_save.setOnClickListener(this);
        initData();
    }

    private void initData() {
        mSQLiteHelper = new SQLiteHelper(this);
        noteName.setText("添加记录");
        // 接受数据并显示
        Intent intent = getIntent();
        if (intent != null){
            id = intent.getStringExtra("id");
            if (id != null){
                noteName.setText("修改记录");
                content.setText(intent.getStringExtra("content"));
                note_time.setText(intent.getStringExtra("time"));
                note_time.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.note_back:
                finish();
                break;
            case R.id.delete:
                content.setText("");
                break;
            case R.id.note_save:
                String noteContent = content.getText().toString().trim();
                if (id != null) {
                    if (noteContent.length() > 0){
                        if(mSQLiteHelper.updateData
                                (id, noteContent, DBUtils.getNotepadTime())){
                            showToast("修改成功");
                            setResult(2);
                            finish();
                        }else{
                            showToast("修改失败");
                        }
                    }else{
                        showToast("修改内容不能为空！");
                    }
                } else {
                    if (noteContent.length() > 0) {
                        if (mSQLiteHelper.insertDate(noteContent,
                                DBUtils.getNotepadTime())) {
                            showToast("保存成功");
                            setResult(2);
                            finish();
                        } else {
                            showToast("保存失败");
                        }
                    } else {
                        showToast("保存内容不能为空");
                    }
                }
                    break;
        }
    }

    private void showToast(String message) {
        Toast.makeText(RecordActivity.this,
                message, Toast.LENGTH_SHORT).show();
    }
}
