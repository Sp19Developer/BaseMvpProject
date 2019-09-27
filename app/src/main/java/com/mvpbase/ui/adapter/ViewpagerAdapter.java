package com.mvpbase.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.PagerAdapter;

import com.mvpbase.R;
import com.mvpbase.data.model.Lecture;
import com.mvpbase.databinding.ItemViewpagerDashBinding;
import com.mvpbase.utils.AppUtils;
import com.mvpbase.ui.callback.OnRecyclerViewItemClickListener;

import java.util.ArrayList;

public class ViewpagerAdapter extends PagerAdapter {

    private Context mContext;
    private ArrayList<Lecture> arrList;
    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;

    public ViewpagerAdapter(Context mContext, ArrayList<Lecture> arrList, OnRecyclerViewItemClickListener<Lecture> onRecyclerViewItemClickListener) {
        this.mContext = mContext;
        this.arrList = arrList;
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ItemViewpagerDashBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_viewpager_dash, collection, false);

        Lecture lecture = arrList.get(position);
        AppUtils.setImageUrl(mContext, lecture.getThumb(), binding.ivThumb);

        binding.ivThumb.setOnClickListener(v -> onRecyclerViewItemClickListener.onClicked(arrList.get(position), v, position, OnRecyclerViewItemClickListener.ViewType.ViewPager));

        binding.setLecture(lecture);

        collection.addView(binding.getRoot());
        return binding.getRoot();
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return arrList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


}