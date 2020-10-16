package com.hly.learn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hly.learn.R;

import java.util.ArrayList;
import java.util.List;

public class MenuAdapter extends BaseAdapter {

    private Context context;
    private List<Integer> imgList = new ArrayList<>();
    private List<String> nameList = new ArrayList<>();

    public MenuAdapter(Context context) {
        this.context = context;
        initData();
    }

    private void initData() {
        imgList.add(R.drawable.ic_chuancai);
        imgList.add(R.drawable.ic_huicai);
        imgList.add(R.drawable.ic_lucai);
        imgList.add(R.drawable.ic_sucai);
        imgList.add(R.drawable.ic_xiangcai);
        imgList.add(R.drawable.ic_zhecai);
        nameList.add("川菜");
        nameList.add("徽菜");
        nameList.add("鲁菜");
        nameList.add("苏菜");
        nameList.add("湘菜");
        nameList.add("浙菜");
    }

    @Override
    public int getCount() {
        return imgList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_item, null);
            viewHolder = new ViewHolder();
            viewHolder.image = convertView.findViewById(R.id.gridView_item_image);
            viewHolder.name = convertView.findViewById(R.id.gridView_item_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.image.setImageResource(imgList.get(position));
        viewHolder.name.setText(nameList.get(position));
        return convertView;
    }

    class ViewHolder {
        ImageView image;
        TextView name;
    }
}
