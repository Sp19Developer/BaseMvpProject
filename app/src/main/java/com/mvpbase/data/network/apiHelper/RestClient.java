package com.mvpbase.data.network.apiHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.webkit.MimeTypeMap;


import androidx.annotation.NonNull;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import com.mvpbase.utils.AppUtils;

@SuppressWarnings({"unused", "WeakerAccess"})
public class RestClient {
    private String TAG = "RestClient";
    private Call<String> call = null;
    private RestRequest restRequest;
    private RestClientListener restClientListener;

    private static final String UNKNOWN_ERR = "Unknown Error Occurred";
    private static final String INTERNAL_SERVER_ERR = "Internal server error";
    private static final String NO_INTERNET_ERR = "No internet available";
    private static final String UNABLE_TO_PROCESS_CALL = "Unable to process request";

    public RestClient(RestRequest restRequest, RestClientListener restClientListener) {
        this.restRequest = restRequest;
        this.restClientListener = restClientListener;
    }

    void execute() {
        Log.d(TAG, "execute()==" + restRequest.toString());
        final RestResponse res = new RestResponse(restRequest);
        CallFactory callFactory = getRetrofit(restRequest.getBaseUrl()).create(CallFactory.class);
        switch (restRequest.getReqMethod()) {
            case METHOD_GET: {
                call = callFactory.getGetCall(restRequest.getReqUrl(), restRequest.getHeader() != null ? restRequest.getHeader() : new HashMap<String, String>(), restRequest.getParams() != null ? restRequest.getParams() : new HashMap<String, String>());
                break;
            }
            case METHOD_POST: {
                switch (restRequest.getContentType()) {
                    case CONTENT_FORMDATA: {
                        call = callFactory.getPostCallWithParams(restRequest.getReqUrl(), restRequest.getHeader() != null ? restRequest.getHeader() : new HashMap<String, String>(), restRequest.getParams() != null ? restRequest.getParams() : new HashMap<String, String>());
                        break;
                    }
                    case CONTENT_JSON: {
                        call = callFactory.getPostCallWithJsonBody(restRequest.getReqUrl(), restRequest.getHeader() != null ? restRequest.getHeader() : new HashMap<String, String>(), RequestBody.create(MediaType.parse("Application/json"), String.valueOf(restRequest.getJsonObject())));
                        break;
                    }
                    case CONTENT_MULTIPART: {
                        List<MultipartBody.Part> arrListPart = new ArrayList<>();
                        if (restRequest.getAttachments() != null) {
                            for (Map.Entry<String, List<String>> pair : restRequest.getAttachments().entrySet()) {
                                String key = pair.getKey();
                                List<String> values = pair.getValue();
                                if (values != null) {
                                    for (String filePath : values) {
                                        File file = new File(filePath);
                                        RequestBody requestBody = RequestBody.create(MediaType.parse(getMimeType(filePath)), file);
                                        arrListPart.add(MultipartBody.Part.createFormData(key, file.getName(), requestBody));
                                    }
                                }
                            }
                        }
                        MultipartBody.Part[] parts = arrListPart.toArray(new MultipartBody.Part[0]);
                        call = callFactory.getPostCallWithFileUpload(restRequest.getReqUrl(), restRequest.getHeader() != null ? restRequest.getHeader() : new HashMap<String, String>(), restRequest.getParams() != null ? restRequest.getParams() : new HashMap<String, String>(), parts);
                        break;
                    }
                }
                break;
            }

        }
        if (call != null) {
            if (AppUtils.isConnectingToInternet()) {
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                        int statusCode = response.code();
                        String responseString = response.body();
                        Log.d(TAG, "onResponce for" + restRequest.getReqUrl());
                        Log.d(TAG, "responseCode=" + statusCode);
                        Log.d(TAG, "response=" + responseString);

                        if (response.raw().body() != null && response.raw().body().contentType() != null && "image/*".equalsIgnoreCase(String.valueOf(response.raw().body().contentType()))) {
                            res.setResType(RestConst.ResponseType.RES_TYPE_IMAGE);
                            try {
                                Bitmap bitmap = null;
                                if (response.body() != null) {
                                    byte[] bytes = response.body().getBytes();
                                    BitmapFactory.Options options = new BitmapFactory.Options();
                                    bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
                                }
                                res.setBitmap(bitmap);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            res.setResType(RestConst.ResponseType.RES_TYPE_JSON);
                        }

                        if (response.isSuccessful()) {
                            res.setResString(responseString);
                            restClientListener.onRequestComplete(RestConst.ResponseCode.SUCCESS, res);
                        } else {
                            try {

                                if (response.errorBody() != null) {
                                    res.setError(response.errorBody().string());
                                } else {
                                    throw new Exception("empty error string in response");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                res.setError(UNKNOWN_ERR);
                            }
                            restClientListener.onRequestComplete(RestConst.ResponseCode.FAIL, res);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                        Log.d("onFailure", "for" + restRequest.getReqUrl());
                        Log.d("onFailure", "cause" + t.toString());
                        /*if (t.toString().equalsIgnoreCase("java.net.SocketTimeoutException")) {
                            res.setError(INTERNAL_SERVER_ERR);
                        } else {
                            res.setError(t.toString());
                        }*/
                        res.setError(t.toString());
                        if (call.isCanceled()) {
                            restClientListener.onRequestComplete(RestConst.ResponseCode.CANCEL, res);
                        } else {
                            restClientListener.onRequestComplete(RestConst.ResponseCode.FAIL, res);
                        }
                    }
                });
            } else {
                Log.d(TAG, "onResponce for" + restRequest.getReqUrl());
                Log.d(TAG, "No internet detected");
                res.setError(NO_INTERNET_ERR);
                restClientListener.onRequestComplete(RestConst.ResponseCode.ERROR, res);
            }
        } else {
            Log.d(TAG, "onResponce for" + restRequest.getReqUrl());
            Log.d(TAG, "Suitable call not found");
            res.setError(UNABLE_TO_PROCESS_CALL);
            restClientListener.onRequestComplete(RestConst.ResponseCode.ERROR, res);
        }
    }

    void cancelRequest() {
        if (call != null)
            call.cancel();
    }

    private static Retrofit retrofit = null;
    private static String oldBaseUrl=null;

    private Retrofit getRetrofit(String baseUrl) {
        if (retrofit == null || !oldBaseUrl.equals(baseUrl)) {
            oldBaseUrl=baseUrl;
            OkHttpClient client = new OkHttpClient.Builder()
//                      .connectTimeout(30, TimeUnit.SECONDS)
//                      .readTimeout(30, TimeUnit.SECONDS)
                    .build();
            Retrofit.Builder builder = new Retrofit.Builder();
            builder.baseUrl(baseUrl);
            builder.client(client);
            builder.addConverterFactory(ScalarsConverterFactory.create());
            retrofit = builder.build();
        }
        return retrofit;
    }

    private interface CallFactory {
        @GET
        Call<String> getGetCall(@Url String url, @HeaderMap Map<String, String> headers, @QueryMap Map<String, String> param);

        @POST
        Call<String> getPostCall(@Url String url);

        @POST
        @FormUrlEncoded
        Call<String> getPostCallWithParams(@Url String url, @HeaderMap Map<String, String> headers, @FieldMap Map<String, String> param);

        @POST
        Call<String> getPostCallWithJsonBody(@Url String url, @HeaderMap Map<String, String> headers, @Body RequestBody object);

        @Multipart
        @POST
        Call<String> getPostCallWithFileUpload(@Url String url, @HeaderMap Map<String, String> headers, @PartMap Map<String, String> param, @Part MultipartBody.Part[] files);


    }

    public interface RestClientListener {
        void onRequestComplete(RestConst.ResponseCode responseCode, RestResponse restResponse);
    }

    private String getMimeType(String file) {
        String extension = MimeTypeMap.getFileExtensionFromUrl(file);

        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        if (mimeType == null)
            mimeType = "*/*";
        return mimeType;
    }

}
