package com.mvpbase.ui.activity.Dashboard;


import com.mvpbase.data.model.Category;
import com.mvpbase.data.model.User;
import com.mvpbase.ui.base.BaseContractPresenter;
import com.mvpbase.ui.base.BaseContractView;

import java.util.ArrayList;

/**
 * Created by etech3 on 27/6/18.
 */

public interface DashboardContract {
    interface View extends BaseContractView {

        void logoutCompleted();

        void loadCategoryToView(ArrayList<Category> list);

        void setDataOfUser(User user,boolean isPushNotificationEnabled,boolean isDNDEnabled,long startTime,long endTime);

    }

    interface Presenter<V extends View> extends BaseContractPresenter<V> {

        void doLogout();

        void getCategoryList();

        void getUserData();

    }
}
