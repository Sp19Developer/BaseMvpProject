package com.mvpbase.ui.activity.Splash;


import com.mvpbase.ui.base.BaseContractPresenter;
import com.mvpbase.ui.base.BaseContractView;

/**
 * Created by etech3 on 27/6/18.
 */

public interface SplashContract {
    interface View extends BaseContractView {



        void openDashboardActivity();

        void finishActivity();

    }

    interface Presenter<V extends View> extends BaseContractPresenter<V> {

    }
}
