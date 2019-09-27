package com.mvpbase.ui.activity.Notification;


import com.mvpbase.data.model.Notification;
import com.mvpbase.ui.base.BaseContractPresenter;
import com.mvpbase.ui.base.BaseContractView;

import java.util.ArrayList;

/**
 * Created by etech3 on 27/6/18.
 */

public interface NotificationContract {
    interface View extends BaseContractView {
        void setUpView(boolean isReset);

        void loadDataToView(ArrayList<Notification> list);

        void setNotRecordsFoundView(boolean isActive);

    }

    interface Presenter<V extends NotificationContract.View> extends BaseContractPresenter<V> {

        void init();

        void reset();

        void loadMoreRecords();

    }
}
