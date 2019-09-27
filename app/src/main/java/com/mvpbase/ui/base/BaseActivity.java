package com.mvpbase.ui.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.mvpbase.R;
import com.mvpbase.app.VideoApp;
import com.mvpbase.utils.AppUtils;
import com.mvpbase.utils.Constants;
import com.mvpbase.utils.CustomAlertDialog;
import com.mvpbase.data.prefs.AppPreferencesHelper;
import com.mvpbase.databinding.LayoutExtraViewsBinding;


import static com.mvpbase.utils.AppUtils.setVisibility;


public class BaseActivity extends AppCompatActivity implements BaseContractView {

    protected final String TAG = BaseActivity.class.getName();
    public static String USER_ID;
    ReceiverNotification receiverNotification;
    IntentFilter intentFilter;

    public BaseContractPresenter<BaseContractView> baseContractPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        AppUtils.setLocalLanguage(this, "ko");
        super.onCreate(savedInstanceState);
        getUserId();
        baseContractPresenter = new BasePresenter<>();

    }

    public static String getUserId() {
        if (TextUtils.isEmpty(USER_ID))
            USER_ID = VideoApp.getDataManager().get(AppPreferencesHelper.PREF_USER_ID, "");
        return USER_ID;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    Toolbar toolbar;
    LayoutExtraViewsBinding extraViewBinding;


    public void setUpView(Toolbar toolbar, LayoutExtraViewsBinding extraViewBinding) {
        setUpView(toolbar, extraViewBinding, null, false);
    }

    public void setUpView(Toolbar toolbar, LayoutExtraViewsBinding extraViewBinding, String title, boolean isBackEnable) {
        if(toolbar!=null)
        this.toolbar = toolbar;
        this.extraViewBinding = extraViewBinding;
        if(toolbar!=null)
        setSupportActionBar(toolbar);
        if (!TextUtils.isEmpty(title)) {
            setTitle(title);
        }
        if (isBackEnable) {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
            }
        }

        if (extraViewBinding == null) return;
        this.extraViewBinding.btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRetryClicked();
            }
        });
    }

    public void setTitle(int titleId) {
        setTitle(getString(titleId));
    }

    public void setTitle(String title) {
        if (getSupportActionBar() == null) return;
        getSupportActionBar().setTitle(title);
    }

    public void showProgressView() {
        if (extraViewBinding == null) return;
        setVisibility(extraViewBinding.llProgressContainer, View.VISIBLE);
    }

    public void hideProgressView() {
        if (extraViewBinding == null) return;
        setVisibility(extraViewBinding.llProgressContainer, View.GONE);
    }


    // Fragment's Method and variables.
    public BaseFragment baseFragment;
    private int count = 0;

    public void addFragment(BaseFragment fragment, boolean isClearStack) {
        this.addFragment(fragment, isClearStack, true);
    }

    public void addFragment(BaseFragment fragment, boolean isClearStack, boolean isAddToStack) {
        Log.d(TAG, fragment.getClass().getName());
        String tag = fragment.getClass().getName() + count;
        baseFragment = fragment;
        if (isClearStack) {
            clearFragmentStack();
        }
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
//        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);

        if (isAddToStack)
            transaction.addToBackStack(tag);
        transaction.replace(R.id.frm_container, fragment, tag);
        transaction.commit();
        count = count + 1;
    }

    public void clearFragmentStack() {
        for (int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); i++) {
            String name = getSupportFragmentManager().getBackStackEntryAt(0).getName();
            getSupportFragmentManager().popBackStack(name, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            break;
        }
    }


    //Connection Broadcast
    private static boolean isConnected = false;
    ConnectionServices connectionServices;


    public class ConnectionServices extends BroadcastReceiver {
        Boolean isCalled = false;

        @Override
        public void onReceive(Context context, Intent intent) {

            if (AppUtils.isConnectingToInternet()) {
                isConnected = true;
            } else {
                isConnected = false;
            }
            if (isCalled) {
                isCalled = false;
                onNetWorkChanged(isConnected);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isCalled = true;
                    }
                }, 1000);
            } else {
                isCalled = true;
            }
        }
    }

    public void onNetWorkChanged(boolean isNetworkConnected) {

        if (baseFragment != null)
            baseFragment.onNetworkChanged(isNetworkConnected);
        if (!isNetworkConnected) {
//            showConnectionLostView();
        } else {
            onRetryClicked();
            hideConnectionLostView();
        }
    }

    public void onRetryClicked() {
        if (AppUtils.isConnectingToInternet()) {
            hideConnectionLostView();
            if (baseFragment != null)
                baseFragment.onRetryClicked();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (receiverNotification == null) {
            receiverNotification = new ReceiverNotification();
            intentFilter = new IntentFilter();
            intentFilter.addAction(Constants.INTENT_FILTER_RECEIVER_NOTIFICATION);
            LocalBroadcastManager.getInstance(BaseActivity.this).registerReceiver(receiverNotification, intentFilter);
        }
        connectionServices = new ConnectionServices();
        registerReceiver(connectionServices, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(connectionServices);
    }

    @Override
    protected void onDestroy() {
        if (receiverNotification != null) {
            LocalBroadcastManager.getInstance(BaseActivity.this).unregisterReceiver(receiverNotification);
            receiverNotification = null;
        }
        baseContractPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constants.PERMISSION_CODE) {
            if (AppUtils.isPermissionGranted(this, AppUtils.PERMISSIONS))
                AppUtils.checkOrCreateAppDirectories(this);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onSupportNavigateUp() {
        AppUtils.hideKeyboard(this);
        onBackPressed();
        return true;
    }

    public void onReceiverNotification(Context context, String type, Intent intent) {
        if (baseFragment != null)
            baseFragment.onReceiverNotification(context, type, intent);
    }

    public class ReceiverNotification extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            onReceiverNotification(context, intent.getStringExtra(Constants.INTENT_NOTIFFICATION_TYPE), intent);
        }
    }

    @Override
    public void showProgressBar() {
        showProgressView();
    }

    @Override
    public void hideProgressBar() {
        hideProgressView();
    }

    @Override
    public void showConnectionLostView() {
        if (extraViewBinding == null) return;
        setVisibility(extraViewBinding.llConnectionLostContainer, View.VISIBLE);
    }

    @Override
    public void hideConnectionLostView() {
        if (extraViewBinding == null) return;
        setVisibility(extraViewBinding.llConnectionLostContainer, View.GONE);
    }

    @Override
    public void showError(int code, String resId) {

        if (code == Constants.FAIL_INTERNET_CODE) {
            resId = getString(R.string.msg_no_network_connection);
        }

        CustomAlertDialog.showAlert(this, getString(R.string.app_name), resId, getString(R.string.btn_ok), new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
