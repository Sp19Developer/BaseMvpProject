package com.mvpbase.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.mvpbase.data.model.Notification;
import com.mvpbase.databinding.ItemNotificationBinding;
import com.mvpbase.ui.callback.OnRecyclerViewItemClickListener;

import java.util.ArrayList;

/**
 * Created by etech3 on 9/1/18.
 */

public class NotificationAdapter extends BaseMainAdpter {


    private ArrayList<Notification> arrIncidentList = new ArrayList<>();

    public NotificationAdapter(Context context, OnRecyclerViewItemClickListener onRecycleItemClickListener) {
        init(context, arrIncidentList, onRecycleItemClickListener);
    }

    public void addData(ArrayList<Notification> arrIncidentList) {
        this.arrIncidentList.addAll(arrIncidentList);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        arrIncidentList.remove(position);
        notifyDataSetChanged();
    }

    public Notification getLecture(int position) {
        return arrIncidentList.get(position);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);
        if (viewType == TYPE_ITEM) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            ItemNotificationBinding itemBinding = ItemNotificationBinding.inflate(layoutInflater, parent, false);
            viewHolder = new ViewHolder(itemBinding);
        }
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        if (holder instanceof ViewHolder) {
            final ViewHolder viewHolder = (ViewHolder) holder;
            Notification incident = arrIncidentList.get(position);


            viewHolder.bind(incident);
            viewHolder.itemView.setOnClickListener(view -> onRecycleItemClickListener.onClicked(getLecture(viewHolder.getAdapterPosition()), view, viewHolder.getAdapterPosition(), OnRecyclerViewItemClickListener.ViewType.View));
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemNotificationBinding binding;

        public ViewHolder(ItemNotificationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Notification lecture) {
            binding.setNotification(lecture);
            binding.executePendingBindings();
        }
    }
}
