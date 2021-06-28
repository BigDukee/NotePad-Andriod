package com.example.note;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.note.adapter.NotepadAdapter;
import com.example.note.bean.NotepadBean;
import com.example.note.database.SQLiteHelper;

import java.util.List;

public class MainActivity extends Activity {
    ListView listView;
    List<NotepadBean> list;
    SQLiteHelper mSQLiteHelper;
    NotepadAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notepad);

        listView = findViewById(R.id.listView);
        ImageView add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent
                        (MainActivity.this, RecordActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        initData();
    }

    private void initData() {
        mSQLiteHelper = new SQLiteHelper(this);
        showQueryData();
        // 查看记录
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                NotepadBean notepadBean = list.get(position);
                Intent intent=new Intent
                        (MainActivity.this, RecordActivity.class);
                intent.putExtra("id", notepadBean.getId());
                intent.putExtra("time", notepadBean.getTime());
                intent.putExtra("content", notepadBean.getContent());
                MainActivity.this.startActivityForResult(intent, 1);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick
                    (AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog dialog;
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(MainActivity.this)
                                .setMessage("是否删除此记录?")
                                .setPositiveButton
                                        ("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NotepadBean notepadBean = list.get(position);
                        if(mSQLiteHelper.deleteDate(notepadBean.getId())) {
                            list.remove(position);
                            adapter.notifyDataSetChanged();
                            Toast.makeText(MainActivity.this,
                                    "删除成功",Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                dialog = builder.create();
                dialog.show();
                return true;
            }
        });
    }

    private void showQueryData() {
        if (list != null) {
            list.clear();
        }
        list = mSQLiteHelper.query();
        adapter = new NotepadAdapter(this, list);
        listView.setAdapter(adapter);

    }

    // 数据交流回调
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 2) {
            showQueryData();
        }
    }
}