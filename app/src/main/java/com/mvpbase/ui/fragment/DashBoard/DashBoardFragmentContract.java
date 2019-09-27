package com.mvpbase.ui.fragment.DashBoard;


import java.util.ArrayList;

import com.mvpbase.ui.base.BaseContractPresenter;
import com.mvpbase.ui.base.BaseContractView;
import com.mvpbase.ui.model.DashboardItem;

/**
 * Created by etech3 on 27/6/18.
 */

public interface DashBoardFragmentContract {
    interface View extends BaseContractView {
        void setUpView(boolean isReset);

        void loadDataToView(ArrayList<DashboardItem> list);

        void setNotRecordsFoundView(boolean isActive);

    }

    interface Presenter<V extends View> extends BaseContractPresenter<V> {

        void init();

        void reset();

        void loadMoreRecords();

    }
}
