package com.mvpbase.ui.activity.Splash;

import android.os.Handler;

import com.mvpbase.ui.base.BasePresenter;


/**
 * Created by etech3 on 27/6/18.
 */

public class SplashPresenter<V extends SplashContract.View> extends BasePresenter<V> implements SplashContract.Presenter<V> {


    @Override
    public void onAttach(V baseView) {
        super.onAttach(baseView);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {

                    if (getView() == null) return;
                    getView().openDashboardActivity();
                    getView().finishActivity();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 1000);
    }

}
