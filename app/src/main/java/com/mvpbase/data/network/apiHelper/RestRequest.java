package com.mvpbase.data.network.apiHelper;

import android.util.Log;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by etech7 on 19/6/17.
 */

@SuppressWarnings({"unused", "WeakerAccess"})
public class RestRequest {

    private final String TAG = "RestRequest";
    private RestConst.RequestMethod reqMethod;
    private RestConst.ContentType contentType;
    private String baseUrl = null;
    private String reqUrl = null;
    private Map<String, String> header = null;
    private Map<String, String> params = null;
    private HashMap<String, List<String>> attachments = null;
    private JSONObject jsonObject = null;

    public RestRequest(RestConst.RequestMethod reqMethod, RestConst.ContentType contentType, String baseUrl, String reqUrl, Map<String, String> header, Map<String, String> params, HashMap<String, List<String>> attachments, JSONObject jsonObject) {
        this.reqMethod = reqMethod;
        this.contentType = contentType;
        this.baseUrl = baseUrl;
        this.reqUrl = reqUrl;
        this.header = header;
        this.params = params;
        this.attachments = attachments;
        this.jsonObject = jsonObject;
    }

    public RestRequest() {
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public void setReqMethod(RestConst.RequestMethod reqMethod) {
        this.reqMethod = reqMethod;
    }

    public RestConst.RequestMethod getReqMethod() {
        return reqMethod;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getReqUrl() {
        return reqUrl;
    }

    public void setReqUrl(String reqUrl) {
        this.reqUrl = reqUrl;
    }

    public void setContentType(RestConst.ContentType contentType) {
        this.contentType = contentType;
    }

    public RestConst.ContentType getContentType() {
        return contentType;
    }


    public Map<String, String> getParams() {
        return params;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    //addParam Method Use for Set Parameter
    public void addParam(String key, String value) {
        if (key == null) {
            Log.e(TAG, "Key can not be null");
        } else if (value == null) {
            Log.e(TAG, "Value can not be null");
        } else {
            if (params == null) {
                params = new HashMap<>();
            }
            params.put(key, value);
        }
    }

    public HashMap<String, List<String>> getAttachments() {
        return attachments;
    }

    public void setAttachments(HashMap<String, List<String>> attachments) {
        if (attachments == null)
            this.attachments = new HashMap<>();
        else
            this.attachments = attachments;
    }

    public void addheader(String key, String value) {
        if (key == null) {
            Log.e(TAG, "Key can not be null");
        } else if (value == null) {
            Log.e(TAG, "Value can not be null");
        } else {
            if (header == null) {
                header = new HashMap<>();
            }
            header.put(key, value);
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("RestRequest");
        stringBuilder.append("\n reqMethod=");
        if (reqMethod == RestConst.RequestMethod.METHOD_GET) {
            stringBuilder.append("get");
        } else {
            stringBuilder.append("post").append("\n content type=");

            switch (contentType) {
                case CONTENT_JSON: {
                    stringBuilder.append("json");
                    if (jsonObject != null) {
                        stringBuilder.append("\n JSONObject=").append(jsonObject.toString());
                    }
                }
                break;
                case CONTENT_FORMDATA:
                    stringBuilder.append("formdata");
                    break;
                case CONTENT_MULTIPART:
                    stringBuilder.append("multipart");
                    break;
            }
        }
        stringBuilder.append("\nbaseurl=").append(baseUrl)
                .append("\n url").append(reqUrl);
        if (header != null) {
            stringBuilder.append("\n headers");
            for (Map.Entry<String, String> entry : header.entrySet()) {
                stringBuilder.append("\n").append(entry.getKey()).append(":")
                        .append(entry.getValue());
            }
        }

        if (params != null) {
            stringBuilder.append("\n params");
            for (Map.Entry<String, String> entry : params.entrySet()) {
                stringBuilder.append("\n").append(entry.getKey()).append(":")
                        .append(entry.getValue());
            }
        }
        if (attachments != null) {
            stringBuilder.append("\n attachments");
            for (Map.Entry<String, List<String>> entry : attachments.entrySet()) {
                stringBuilder.append("\n").append(entry.getKey()).append(":");
                if (entry.getValue() != null)
                    for (String files : entry.getValue()) {
                        stringBuilder.append("\n ").append(files);
                    }
            }
        }
        return stringBuilder.toString();
    }
}
