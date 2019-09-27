package com.mvpbase.ui.activity.Dashboard;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.mvpbase.R;
import com.mvpbase.data.model.Category;
import com.mvpbase.data.model.User;
import com.mvpbase.databinding.ActivityDashBoardBinding;
import com.mvpbase.ui.base.BaseActivity;
import com.mvpbase.ui.base.BaseFragment;
import com.mvpbase.ui.fragment.DashBoard.DashBoardFragment;
import com.mvpbase.utils.CustomAlertDialog;
import com.mvpbase.utils.helper.BottomNavigationViewHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class DashBoardActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, DashboardContract.View {

    private String TAG = getClass().getName();
    public static boolean isProfileChange = true;
    private boolean isBackPress = false;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private BottomNavigationView nav_bottom;
    private ActivityDashBoardBinding binding;

    private ProgressDialog progressDialog;
    private ArrayList<Category> categoryList;

    private enum NavMenuOption {
        IDLE,
        DASHBOARD,
        ABOUT_US
    }

    private NavMenuOption currentTab = NavMenuOption.IDLE;

    private DashboardContract.Presenter<DashboardContract.View> presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new DashboardPresenter<>();

        init();
        setUpNavigationView();


        presenter.onAttach(this);
        presenter.getUserData();
        presenter.getCategoryList();

    }

    private void init() {

        DashBoardActivity.isProfileChange = true;
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dash_board);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setUpView(toolbar, binding.appBarDashBoard.containerFrame.extraView);
//        todo set switch change listeners and call apis....
    }

    private void setUpNavigationView() {
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        toggle.setDrawerIndicatorEnabled(false);
        toggle.setHomeAsUpIndicator(R.drawable.ic_navbar);
        toggle.setToolbarNavigationClickListener(view -> drawer.openDrawer(GravityCompat.START));

        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);

        setSideNavigationClickListeners();
        nav_bottom = findViewById(R.id.nav_bottom);
        nav_bottom.setItemIconTintList(null);

        nav_bottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                final int id = item.getItemId();
                if (id == R.id.nav_education_info) {
                    addFragment(DashBoardFragment.newInstance(), true, NavMenuOption.DASHBOARD);
                    BottomNavigationViewHelper.removeNotification(DashBoardActivity.this, nav_bottom, 0);
                } else if (id == R.id.nav_home) {
                    return false;
                } else if (id == R.id.nav_temp) {
                    return false;
                } else if (id == R.id.nav_temp2) {
                    return false;
                } else if (id == R.id.nav_history) {
                    return false;
                }
                return true;
            }
        });

    }

    private void setSideNavigationClickListeners() {
        View.OnClickListener navClickListener = v -> {

        };

    }

    private void setTime(TextView tv, Calendar calendar) {
        DateFormat dateFormat = new SimpleDateFormat("a hh:mm");
        tv.setText(dateFormat.format(calendar.getTime()));
    }

    private void logout() {
        CustomAlertDialog.showAlert(DashBoardActivity.this, getString(R.string.app_name), getString(R.string.msg_confirm_logout), getString(R.string.btn_yes), getString(R.string.btn_no), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.doLogout();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setHomeTab();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isProfileChange) {
            isProfileChange = false;
            setProfileDetail();
        }
    }

    private void setProfileDetail() {

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
       /* boolean isSelected = true;
        final int id = item.getItemId();

//        if (id == R.id.nav_setting
//                || id == R.id.nav_survey
//                || id == R.id.nav_notice_board) {
        isSelected = false;
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(DashBoardActivity.this, LoadFragmentActivity.class);
                if (id == R.id.nav_setting) {
                    intent.putExtra(LoadFragmentActivity.ARG_FRAGMENT, Constants.FRG_SETTING);
                } else if (id == R.id.nav_survey) {
                    intent.putExtra(LoadFragmentActivity.ARG_FRAGMENT, Constants.FRG_SURVEY);
                } else if (id == R.id.nav_notice_board) {
                    intent.putExtra(LoadFragmentActivity.ARG_FRAGMENT, Constants.FRG_NOTICE);
                } else if (id == R.id.nav_education_info) {
                    intent.putExtra(LoadFragmentActivity.ARG_FRAGMENT, Constants.FRG_EDUCATION_INFO);
                }
                startActivity(intent);

            }
        }, 500);

//        }
//        else {
//            Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    if (id == R.id.nav_dashboard) {
//                        addFragment(DashBoardFragment.newInstance(), true, NavMenuOption.DASHBOARD);
//                    }
//
//                }
//            }, 500);
//        }


        drawer.closeDrawer(GravityCompat.START);

        if (isSelected) {
            return true;
        }*/
        return true;
    }


    private void setHomeTab() {
//        navigationView.setCheckedItem(R.id.nav_dashboard);
        addFragment(DashBoardFragment.newInstance(), true, NavMenuOption.DASHBOARD);
    }

    private void addFragment(BaseFragment baseFragment, boolean isClearTask, NavMenuOption item) {
        if (this.currentTab != item) {
            this.currentTab = item;
            addFragment(baseFragment, isClearTask);
        }
    }

    Toast toast;

    @Override
    public void onBackPressed() {
        int pos = getSupportFragmentManager().getBackStackEntryCount();
        Log.d(TAG, " " + pos);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (pos > 1) {
            setHomeTab();
        } else {
            if (!isBackPress) {
                isBackPress = true;
                toast = Toast.makeText(this, getResources().getString(R.string.lbl_press_again_to_exit), Toast.LENGTH_SHORT);
                toast.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isBackPress = false;
                    }
                }, 2000);
            } else {
                if (toast != null)
                    toast.cancel();
                this.finishAffinity();
            }

        }
    }

    @Override
    public void showProgressBar() {
        if (progressDialog != null && !progressDialog.isShowing())
            progressDialog.show();

    }

    @Override
    public void hideProgressBar() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.hide();
            progressDialog.dismiss();
        }
    }

    @Override
    public void logoutCompleted() {
        finish();
    }

    @Override
    public void loadCategoryToView(ArrayList<Category> list) {
        for (int i = list.size() - 1; i >= 0; i--) {
            if (categoryList == null) {
                categoryList = new ArrayList<>();
            }
            this.categoryList.add(list.get(i));
        }


    }

    private Drawable getColorFilteredrable(Drawable drawable, String color) {
        drawable.setColorFilter(Color.parseColor(color), PorterDuff.Mode.MULTIPLY);
        return drawable;
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void setDataOfUser(User user, boolean isPushNotificationEnabled, boolean isDNDEnabled, long startTime, long endTime) {
//todo set user data to side menu
        binding.headerNavigation.tvUserId.setText(user.getUserId());
        binding.headerNavigation.tvUsername.setText(user.getName());
        binding.headerNavigation.tvUserTeam.setText(user.getCom());
        binding.headerNavigation.tvCompanyGroup.setText(user.getTeam());
    }
}
