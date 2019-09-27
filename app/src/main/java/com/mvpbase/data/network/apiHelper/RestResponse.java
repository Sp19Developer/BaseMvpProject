package com.mvpbase.data.network.apiHelper;

import android.graphics.Bitmap;

/**
 * Created by etech7 on 20/6/17.
 */

@SuppressWarnings({"WeakerAccess", "unused"})
public class RestResponse {

    private String resString = null;
    private String error = null;
    private RestRequest request;
    private RestConst.ResponseType resType;
    private Bitmap bitmap;

    public RestResponse(RestRequest request) {
        this.request = request;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setResType(RestConst.ResponseType resType) {
        this.resType = resType;
    }

    public RestConst.ResponseType getResType() {
        return resType;
    }

    public String getResString() {
        return resString;
    }

    public void setResString(String resString) {
        this.resString = resString;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setRequest(RestRequest request) {
        this.request = request;
    }

    public RestRequest getRequest() {
        return request;
    }
}
