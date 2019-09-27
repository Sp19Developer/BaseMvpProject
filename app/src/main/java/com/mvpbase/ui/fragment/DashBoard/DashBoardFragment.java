package com.mvpbase.ui.fragment.DashBoard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.mvpbase.R;
import com.mvpbase.databinding.FragmentDashBoardBinding;
import com.mvpbase.ui.adapter.DashboardAdapter;
import com.mvpbase.ui.base.BaseFragment;
import com.mvpbase.ui.model.DashboardItem;
import com.mvpbase.utils.AppUtils;
import com.mvpbase.ui.callback.OnRecyclerViewItemClickListener;

import java.util.ArrayList;


public class DashBoardFragment extends BaseFragment implements DashBoardFragmentContract.View {

    public DashBoardFragment() {
        // Required empty public constructor
    }

    public static DashBoardFragment newInstance() {
        return newInstance(null);
    }

    public static DashBoardFragment newInstance(Bundle bundle) {
        DashBoardFragment fragment = new DashBoardFragment();
        if (bundle != null)
            fragment.setArguments(bundle);
        return fragment;
    }

    private DashboardAdapter dashboardAdapter;
    private FragmentDashBoardBinding binding;
    private DashBoardFragmentContract.Presenter<DashBoardFragmentContract.View> presenter;
    private SearchView searchView = null;
    private ImageView imageBadgeView = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new DashBoardFragmentPresenter<>();
        presenter.onAttach(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState);
        activity.setTitle(R.string.menu_dashboard);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dash_board, container, false);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        activity.setUpView(null, binding.extraViews);

        presenter.init();

        binding.swRefDashboard.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.reset();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onRetryClicked() {
        presenter.reset();
    }


    @Override
    public void onReceiverNotification(Context context, String type, Intent intent) {
//        if (type != null) {
//            if (type.equalsIgnoreCase(Constants.NOT_TYPE_REVIEW_ADD) || type.equalsIgnoreCase(Constants.NOT_TYPE_COMPLAIN_UPDATED)) {
//                int position = AppUtils.getPositionFromList(dashboardAdapter.getIncidents(), intent.getStringExtra(ARG_INCIDENT_ID));
//                if (position >= 0) {
//                    presenter.reset();
//                }
//            } else if (type.equalsIgnoreCase(Constants.NOT_TYPE_COMPLAIN_CREATE)) {
//                presenter.reset();
//            }
//        }
    }


    @Override
    public void setUpView(boolean isReset) {
        if (isReset) {
            dashboardAdapter = null;
        }
        AppUtils.setVisibility(binding.tvNotFound, View.GONE);
        if (dashboardAdapter == null) {
            dashboardAdapter = new DashboardAdapter(activity, new OnRecyclerViewItemClickListener<DashboardItem>() {
                @Override
                public void onClicked(DashboardItem bean, View view, int position, ViewType viewType) {
                    if (viewType == OnRecyclerViewItemClickListener.ViewType.View) {
                    } else if (viewType == ViewType.ViewPager) {
                    } else if (viewType == ViewType.More) {
                    } else if (viewType == OnRecyclerViewItemClickListener.ViewType.CategoryTitle) {
                    }
                }

                @Override
                public void onLastItemReached() {
                }
            });
            binding.rvDashboard.setAdapter(dashboardAdapter);
        } else {
            binding.rvDashboard.setAdapter(dashboardAdapter);
        }

    }

    @Override
    public void loadDataToView(ArrayList<DashboardItem> list) {
        dashboardAdapter.addIncidents(list);
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
    }

    @Override
    public void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }


}
