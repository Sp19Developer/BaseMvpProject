package com.mvpbase.data.network.model;

import java.util.ArrayList;

/**
 * Created by etech3 on 3/7/18.
 */

public class Pagging<V> {
    private int pageNumber = 1;
    private int limit = 20;
    private boolean hasMore = true;
    private ArrayList<V> arrList = new ArrayList<>();

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public ArrayList getArrList() {
        return arrList;
    }

    public void addItemToList(ArrayList<V> arrList) {
        this.arrList.addAll(arrList);
    }
}
