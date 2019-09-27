package com.mvpbase.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.mvpbase.R;
import com.mvpbase.data.model.Lecture;
import com.mvpbase.databinding.ItemDashboardHeaderBinding;
import com.mvpbase.databinding.ItemDashboardListBinding;
import com.mvpbase.databinding.ItemDashboardViewpagerBinding;
import com.mvpbase.ui.model.DashboardItem;
import com.mvpbase.utils.AppUtils;
import com.mvpbase.utils.Constants;
import com.mvpbase.ui.callback.OnRecyclerViewItemClickListener;

import java.util.ArrayList;

/**
 * Created by etech3 on 9/1/18.
 */

public class DashboardAdapter extends BaseMainAdpter {


    private static final int TYPE_DASH_VIEWPAGER = 3;
    private static final int TYPE_DASH_HEADER = 4;
    private static final int TYPE_DASH_LIST = 5;

    private ArrayList<DashboardItem> arrIncidentList = new ArrayList<>();

    public DashboardAdapter(Context context, OnRecyclerViewItemClickListener<DashboardItem> onRecycleItemClickListener) {
        init(context, arrIncidentList, onRecycleItemClickListener);
    }

    public void addIncidents(ArrayList<DashboardItem> arrIncidentList) {
        this.arrIncidentList.addAll(arrIncidentList);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        arrIncidentList.remove(position);
        notifyDataSetChanged();
    }

    public DashboardItem getIncident(int position) {
        return arrIncidentList.get(position);
    }

    public ArrayList getIncidents() {
        return arrIncidentList;
    }

    @Override
    public int getItemViewType(int position) {

        if (arrIncidentList != null && arrIncidentList.size() > position) {
            switch (arrIncidentList.get(position).getType()) {
                case Constants.DASH_TYPE_VIEWPAGER:
                    return TYPE_DASH_VIEWPAGER;
                case Constants.DASH_TYPE_HEADER:
                    return TYPE_DASH_HEADER;
                case Constants.DASH_TYPE_LIST:
                    return TYPE_DASH_LIST;
            }
        }
        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);
        if (viewType == TYPE_DASH_LIST) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            ItemDashboardListBinding itemBinding = ItemDashboardListBinding.inflate(layoutInflater, parent, false);
            viewHolder = new ViewHolder(itemBinding);
        } else if (viewType == TYPE_DASH_HEADER) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            ItemDashboardHeaderBinding itemBinding = ItemDashboardHeaderBinding.inflate(layoutInflater, parent, false);
            viewHolder = new HeaderViewHolder(itemBinding);
        } else if (viewType == TYPE_DASH_VIEWPAGER) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            ItemDashboardViewpagerBinding itemBinding = ItemDashboardViewpagerBinding.inflate(layoutInflater, parent, false);
            viewHolder = new ViewPagerViewHolder(itemBinding);
        }
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        if (holder instanceof ViewHolder) {
            final ViewHolder viewHolder = (ViewHolder) holder;
            Lecture incident = (Lecture) arrIncidentList.get(position).getLecture();
            AppUtils.setImageUrl(context, incident.getThumb(), viewHolder.binding.ivThumb);

            if (position == arrIncidentList.size() - 1) {
                viewHolder.binding.ivBottomDivider.setVisibility(View.VISIBLE);
            } else {
                viewHolder.binding.ivBottomDivider.setVisibility(View.GONE);
            }

            viewHolder.bind(incident);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onRecycleItemClickListener.onClicked(getIncident(viewHolder.getAdapterPosition()), view, viewHolder.getAdapterPosition(), OnRecyclerViewItemClickListener.ViewType.View);
                }
            });
            viewHolder.binding.tvCategory.setOnClickListener(view -> {
                onRecycleItemClickListener.onClicked(getIncident(viewHolder.getAdapterPosition()), view, viewHolder.getAdapterPosition(), OnRecyclerViewItemClickListener.ViewType.CategoryTitle);
            });
        } else if (holder instanceof HeaderViewHolder) {
            final HeaderViewHolder viewHolder = (HeaderViewHolder) holder;
            DashboardItem incident = arrIncidentList.get(position);
            viewHolder.bind(incident);
            viewHolder.itemView.setOnClickListener(view -> onRecycleItemClickListener.onClicked(getIncident(viewHolder.getAdapterPosition()), view, viewHolder.getAdapterPosition(), OnRecyclerViewItemClickListener.ViewType.More));

        } else if (holder instanceof ViewPagerViewHolder) {
            final ViewPagerViewHolder viewHolder = (ViewPagerViewHolder) holder;
            DashboardItem incident = arrIncidentList.get(position);
            ViewpagerAdapter adapter = new ViewpagerAdapter(context, incident.getList(), new OnRecyclerViewItemClickListener<Lecture>() {
                @Override
                public void onClicked(Lecture bean, View view, int position, ViewType viewType) {
                    onRecycleItemClickListener.onClicked(incident, view, position, viewType);
                }

                @Override
                public void onLastItemReached() {

                }
            });
            viewHolder.binding.vpDash.setAdapter(adapter);
            viewHolder.binding.vpDash.setClipToPadding(false);
            viewHolder.binding.vpDash.setPadding((int) context.getResources().getDimension(R.dimen._8sdp)
                    , (int) context.getResources().getDimension(R.dimen._6sdp)
                    , (int) context.getResources().getDimension(R.dimen._8sdp)
                    , (int) context.getResources().getDimension(R.dimen._6sdp));

        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemDashboardListBinding binding;

        public ViewHolder(ItemDashboardListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Lecture lecture) {
            binding.setVideoitem(lecture);
            binding.executePendingBindings();
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        ItemDashboardHeaderBinding binding;

        public HeaderViewHolder(ItemDashboardHeaderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(DashboardItem item) {
            binding.setDashitem(item);
            binding.executePendingBindings();
        }
    }

    public class ViewPagerViewHolder extends RecyclerView.ViewHolder {
        ItemDashboardViewpagerBinding binding;

        public ViewPagerViewHolder(ItemDashboardViewpagerBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
