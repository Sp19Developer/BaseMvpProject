package com.mvpbase.ui.callback;

import android.view.View;

/**
 * Created by etech3 on 14/5/18.
 */

public interface OnRecyclerViewItemClickListener<V> {
    public static enum ViewType {
        View,
        ViewPager,
        More,
        Button,
        CategoryTitle,
        Approve,
        Reject
    }

    void onClicked(V bean, View view, int position, ViewType viewType);

    void onLastItemReached();
}
