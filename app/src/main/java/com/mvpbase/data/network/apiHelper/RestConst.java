package com.mvpbase.data.network.apiHelper;

/**
 * Created by etech7 on 20/6/17.
 */

public class RestConst {
    public enum RequestMethod {
        METHOD_GET,
        METHOD_POST;
    }

    public enum ContentType {
        CONTENT_JSON,
        CONTENT_FORMDATA,
        CONTENT_MULTIPART;
    }

    public enum ResponseCode {
        SUCCESS,
        ERROR,
        CANCEL,
        FAIL;
    }

    public enum ResponseType {
        RES_TYPE_JSON,
        RES_TYPE_IMAGE;
    }
}
