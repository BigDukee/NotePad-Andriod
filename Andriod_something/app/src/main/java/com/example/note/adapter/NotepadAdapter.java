package com.example.note.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.example.note.R;
import com.example.note.bean.NotepadBean;

import java.util.List;

public class NotepadAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    // bean类列表
    private List<NotepadBean> list;
    public NotepadAdapter(Context context, List<NotepadBean> list) {
        this.layoutInflater = LayoutInflater.from(context);
        this.list = list;
    }
    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.note_layout, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        NotepadBean noteInfo = (NotepadBean) getItem(position);
        viewHolder.tvContent.setText(noteInfo.getContent());
        viewHolder.tvTime.setText(noteInfo.getTime());
        return convertView;
    }

    class ViewHolder {
        TextView tvContent;
        TextView tvTime;
        public ViewHolder(View view) {
            tvContent = (TextView) view.findViewById(R.id.item_content);
            tvTime = (TextView) view.findViewById(R.id.item_time);
        }
    }
}
