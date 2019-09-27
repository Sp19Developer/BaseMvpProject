package com.mvpbase.ui.activity.Notification;

import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.mvpbase.R;
import com.mvpbase.data.model.Notification;
import com.mvpbase.databinding.ActivityNotificationBinding;
import com.mvpbase.ui.adapter.NotificationAdapter;
import com.mvpbase.ui.base.BaseActivity;
import com.mvpbase.utils.AppUtils;
import com.mvpbase.ui.callback.OnRecyclerViewItemClickListener;

import java.util.ArrayList;


public class NotificationActivity extends BaseActivity implements NotificationContract.View {

    private NotificationAdapter dashboardAdapter;
    private ActivityNotificationBinding binding;
    private NotificationContract.Presenter<NotificationContract.View> presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notification);

        presenter = new NotificationPresenter<>();
        presenter.onAttach(this);

        setUpView(binding.header.mainToolbar, binding.extraViews, getString(R.string.menu_notification), true);

        presenter.init();

        binding.swRefDashboard.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.reset();
            }
        });
    }


    @Override
    public void onRetryClicked() {
        presenter.reset();
    }


    @Override
    public void setUpView(boolean isReset) {
        if (isReset) {
            dashboardAdapter = null;
        }
        AppUtils.setVisibility(binding.tvNotFound, View.GONE);
        if (dashboardAdapter == null) {
            dashboardAdapter = new NotificationAdapter(this, new OnRecyclerViewItemClickListener() {


                @Override
                public void onClicked(Object bean, View view, int position, ViewType viewType) {
                    if (viewType == ViewType.View) {

                    }
                }

                @Override
                public void onLastItemReached() {
                    presenter.loadMoreRecords();
                }
            });
            binding.rvDashboard.setAdapter(dashboardAdapter);
//            skeletonScreen = AppUtils.bindRecyclerViewForSkeleton(binding.rvMyComplain, dashboardAdapter);
        } else {
            binding.rvDashboard.setAdapter(dashboardAdapter);
        }

    }

    @Override
    public void loadDataToView(ArrayList<Notification> list) {
        dashboardAdapter.addData(list);
        hideSkeletonView();
    }


    private void hideSkeletonView() {
//        if (skeletonScreen != null) {
//            skeletonScreen.hide();
//            skeletonScreen = null;
//        }
    }

    @Override
    public void setNotRecordsFoundView(boolean isActive) {
        if (isActive) {
            AppUtils.setVisibility(binding.tvNotFound, View.VISIBLE);
        } else {
            AppUtils.setVisibility(binding.tvNotFound, View.GONE);
        }
    }

    @Override
    public void showProgressBar() {
        dashboardAdapter.updateBottomProgress(0);
    }

    @Override
    public void hideProgressBar() {
        dashboardAdapter.updateBottomProgress(1);
        binding.swRefDashboard.setRefreshing(false);
        hideSkeletonView();
    }

    @Override
    public void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }


}
