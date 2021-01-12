package com.hly.learn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hly.learn.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Test data for recyclerview in NestedScrollView
 */
public class NestedItemAdapter extends RecyclerView.Adapter<NestedItemAdapter.NestedViewHolder> {
    private Context context;
    private List<Integer> imgList = new ArrayList<>();
    private List<String> nameList = new ArrayList<>();

    public NestedItemAdapter(Context context) {
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
        nameList.add("川菜");
        nameList.add("徽菜");
        nameList.add("鲁菜");
        nameList.add("苏菜");
        nameList.add("湘菜");
        nameList.add("浙菜");
    }

    @NonNull
    @Override
    public NestedItemAdapter.NestedViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
            int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.nested_item_layout, parent,
                false);
        return new NestedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NestedItemAdapter.NestedViewHolder holder, int position) {
        if (imgList != null && nameList != null) {
            holder.img.setImageResource(imgList.get(position));
            holder.name.setText(nameList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return imgList.size();
    }

    static class NestedViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name;

        public NestedViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.nestedView_item_image);
            name = itemView.findViewById(R.id.nestedView_item_name);
        }
    }
}
