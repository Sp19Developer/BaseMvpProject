package com.mvpbase.ui.base;

/**
 * Created by etech3 on 29/6/18.
 */

public interface BaseContractPresenter<V extends BaseContractView> {
    void onAttach(V baseView);

    void onDetach();
}
