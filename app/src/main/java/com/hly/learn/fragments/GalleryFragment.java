package com.hly.learn.fragments;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import com.hly.learn.R;

public class GalleryFragment extends BaseFragment {

    private ImageSwitcher imageSwitcher;
    private Gallery gallery;

    private Integer[] myImageIds = {
            R.drawable.ic_chuancai, R.drawable.ic_huicai, R.drawable.ic_lucai, R.drawable.ic_sucai,
            R.drawable.ic_xiangcai, R.drawable.ic_zhecai
    };

    @Override
    public int getLayoutId() {
        return R.layout.gallery_layout;
    }

    @Override
    public void initData(View view) {
        // 通过控件的ID获得imageSwitcher的对象
        imageSwitcher = (ImageSwitcher) view.findViewById(R.id.imageSwitcher);
        // 设置自定义的图片显示工厂类
        imageSwitcher.setFactory(new MyViewFactory(mContext));
        Animation();
        gallery = (Gallery) view.findViewById(R.id.gallery);
        gallery.setAdapter(new ImageAdapter(mContext));
        gallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                    int position, long id) {
                // 通过求余数，循环显示图片
                imageSwitcher.setImageResource(myImageIds[position
                        % myImageIds.length]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
    }

    /**
     * 动画效果
     */
    public void Animation() {
        // 设置ImageSwitcher组件显示图像的动画效果
        imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(mContext,
                android.R.anim.fade_in));
        imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(mContext,
                android.R.anim.fade_out));
    }

    /**
     * Gallery的图片适配器
     * @author AF74776
     *
     */
    public class ImageAdapter extends BaseAdapter {
        // 声明一个变量
        private Context mContext;

        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {// 返回数组长度
            return myImageIds.length;//Integer.MAX_VALUE;
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {// 得到图片ID
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView i = new ImageView(mContext);
            i.setImageResource(myImageIds[position % myImageIds.length]);
            i.setAdjustViewBounds(true); // 图片自动调整显示
            i.setScaleType(ImageView.ScaleType.FIT_XY);// 重新设置图片的宽高
            i.setLayoutParams(new Gallery.LayoutParams(200, 200));// 设置layout的宽高
            i.setBackgroundResource(android.R.drawable.alert_light_frame);// 设置背景
            return i;// 返回对象
        }
    }

    // 自定义图片显示工厂类，继承ViewFactory
    class MyViewFactory implements ViewSwitcher.ViewFactory {
        private Context context; // 定义上下文
        // 参数为上下文的构造方法
        public MyViewFactory(Context context) {
            this.context = context;
        }
        // 显示图标区域
        public View makeView() {
            ImageView iv = new ImageView(context); // 创建ImageView对象
            iv.setScaleType(ImageView.ScaleType.FIT_XY); // 图片自动居中显示
            iv.setBackgroundResource(android.R.drawable.alert_light_frame);// 设置背景
            return iv; // 返回ImageView对象
        }
    }
}
