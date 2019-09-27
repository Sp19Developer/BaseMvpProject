package com.mvpbase.ui.base;

/**
 * Created by etech3 on 29/6/18.
 */

public interface BaseContractView {
    void showProgressBar();

    void hideProgressBar();

    void showConnectionLostView();

    void hideConnectionLostView();

    void showError(int code, String res);

}
