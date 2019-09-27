package com.mvpbase.ui.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;

import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mvpbase.utils.AppUtils;

/**
 * Created by etech3 on 5/12/17.
 */

public class BaseFragment extends Fragment implements BaseContractView {

    public BaseActivity activity;
    public Menu menu;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        init();
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        init();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void init() {
        AppUtils.setLocalLanguage(requireContext(),"ko");
        activity = (BaseActivity) getActivity();
    }

    public void onRetryClicked() {
        Log.d("BaseFragment", "onRetryCalled");
    }

    public void onNetworkChanged(Boolean isNetworkChanged) {
        Log.d("BaseFragment", "onNet");
    }

    public void onReceiverNotification(Context context, String type, Intent intent) {
        Log.d("BaseFragment", "onNotification");
    }


    @Override
    public void showProgressBar() {
        activity.showProgressBar();
    }

    @Override
    public void hideProgressBar() {
        activity.hideProgressBar();
    }

    @Override
    public void showConnectionLostView() {
        activity.showConnectionLostView();
    }

    @Override
    public void hideConnectionLostView() {
        activity.hideConnectionLostView();
    }

    @Override
    public void showError(int code, String res) {
        activity.showError(code, res);
    }

}
