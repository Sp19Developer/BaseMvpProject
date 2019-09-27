package com.mvpbase.ui.activity.Dashboard;


import com.mvpbase.app.VideoApp;
import com.mvpbase.data.DataManager;
import com.mvpbase.data.model.Category;
import com.mvpbase.data.model.User;
import com.mvpbase.ui.base.BasePresenter;

import java.util.ArrayList;

/**
 * Created by etech3 on 27/6/18.
 */

public class DashboardPresenter<V extends DashboardContract.View> extends BasePresenter<V> implements DashboardContract.Presenter<V> {

    private ArrayList<Category> categories;

    @Override
    public void onAttach(V baseView) {
        super.onAttach(baseView);
        categories = new ArrayList<>();
    }


    @Override
    public void doLogout() {
//        VideoApp.getDataManager().logout();
        getView().logoutCompleted();
    }

    @Override
    public void getCategoryList() {
        DataManager dataManager=VideoApp.getDataManager();
//        dataManager.getCategoryList(dataManager.get(AppPreferencesHelper.PREF_USER_ID,null), new DataManager.Callback<ArrayList<Category>>() {
//            @Override
//            public void onSuccess(ArrayList<Category> baseResponse) {
//                if (getView() == null) return;
//                categories = baseResponse;
//                getView().loadCategoryToView(categories);
//            }
//
//            @Override
//            public void onFailed(int code, String message) {
//                if (getView() == null) return;
//                DashboardPresenter.super.onFaild(code,message,true,false);
//            }
//        });
    }

    @Override
    public void getUserData() {
        User user=null;
    }
}
