package com.mvpbase.ui.fragment.DashBoard;

import com.mvpbase.data.model.Lecture;
import com.mvpbase.data.network.model.DashboardResponse;
import com.mvpbase.data.network.model.Pagging;
import com.mvpbase.ui.base.BasePresenter;
import com.mvpbase.ui.model.DashboardItem;
import com.mvpbase.utils.Constants;

import java.util.ArrayList;

public class DashBoardFragmentPresenter<V extends DashBoardFragmentContract.View> extends BasePresenter<V> implements DashBoardFragmentContract.Presenter<V> {

    private Pagging<DashboardResponse> pagging;

    @Override
    public void onAttach(V baseView) {
        super.onAttach(baseView);
        pagging = new Pagging<>();
    }

    @Override
    public void init() {
        getView().setUpView(false);
        loadMoreRecords();

    }

    @Override
    public void reset() {
        getView().setUpView(true);
        clearPagging();
        loadMoreRecords();
//        getDashboardData();
    }

    private void clearPagging() {
        pagging = new Pagging<>();
    }

    @Override
    public void loadMoreRecords() {
//        VideoApp.getDataManager().doGetDashboardVideo(new DataManager.Callback<ArrayList<DashboardResponse>>() {
//            @Override
//            public void onSuccess(ArrayList<DashboardResponse> baseResponse) {
//                if(getView()==null)return;
//                if(baseResponse.size()>0){
//                    getView().setNotRecordsFoundView(false);
//                    getView().loadDataToView(convertArrayListToItem(baseResponse));
//                }else{
//                    getView().setNotRecordsFoundView(true);
//                }
//            }
//
//            @Override
//            public void onFailed(int code, String message) {
//                    onFaild(code,message,true,false);
//            }
//        });
    }

    private ArrayList<DashboardItem> convertArrayListToItem(ArrayList<DashboardResponse> responses) {
        ArrayList<DashboardItem> data = new ArrayList<>();
        if (responses != null && responses.size() > 0) {
            for (DashboardResponse response : responses) {
                if (response.getType().equals(Constants.DASH_TYPE_VIEWPAGER)) {
                    DashboardItem dashboardItem = new DashboardItem();
                    dashboardItem.setList(response.getLecture());
                    dashboardItem.setType(Constants.DASH_TYPE_VIEWPAGER);
                    data.add(dashboardItem);
                } else if (response.getType().equals(Constants.DASH_TYPE_LIST)) {
                    DashboardItem dashboardItem = new DashboardItem();
                    dashboardItem.setHeader(response.getHeader());
                    dashboardItem.setType(Constants.DASH_TYPE_HEADER);
                    dashboardItem.setHeaderType(response.getHeaderType());
                    data.add(dashboardItem);
                      for (Lecture lecture : response.getLecture()) {
                        DashboardItem item = new DashboardItem();
                        item.setType(Constants.DASH_TYPE_LIST);
                        item.setLecture(lecture);
                        data.add(item);
                    }
                }
            }
        }

        return data;
    }
}
