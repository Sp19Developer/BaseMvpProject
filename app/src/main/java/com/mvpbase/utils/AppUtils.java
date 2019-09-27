package com.mvpbase.utils;

import android.Manifest;
import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputLayout;
import com.mvpbase.R;
import com.mvpbase.app.VideoApp;
import com.mvpbase.data.network.apiHelper.RestResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static com.mvpbase.utils.Constants.RES_CODE_KEY;
import static com.mvpbase.utils.Constants.RES_MSG_KEY;


public class AppUtils {
    public static final String TAG = "AppUtils";
    public static String[] PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    public static String[] PERMISSION_STORAGE = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    public static String[] PERMISSION_LOCATION = new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    public static String[] WRITE_SD_CARD_PERMISSION = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static String[] PHONE_CALL_PERMISSION = new String[]{
            Manifest.permission.CALL_PHONE
    };


    public static String getDefaultLanguage() {
        return Locale.getDefault().getLanguage();
    }

    public static void setLocalLanguage(Context context, String language_code) {
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        Locale locale = new Locale(language_code.toLowerCase());
        Locale.setDefault(locale);
        conf.setLocale(locale);

        res.updateConfiguration(conf, dm);

        /*Locale locale = new Locale(language_code);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        VideoApp.appContext.getResources().updateConfiguration(config,
                VideoApp.appContext.getResources().getDisplayMetrics());*/
    }


    public static void checkOrCreateAppDirectories(Context context) {
        // copy db
        Log.e(AppUtils.class + "", " : checkOrCreateAppDirectories()");
        Storage.verifyDirPath(Constants.APP_HOME);
        Storage.verifyDirPath(Constants.DIR_MEDIA);
    }

    /**
     * True if all permission are granted.
     *
     * @param context
     * @param permission
     * @param requestCode
     * @return
     */
    public static boolean AskAndCheckPermission(Activity context, String permission[], int requestCode) {
        boolean flag = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkFirstTimePermission(permission)) {
                context.requestPermissions(permission, requestCode);
                for (String s : permission) {
                    VideoApp.getDataManager().setBooleanAppPref(s, true);
                }
            } else {
                if (isPermissionGranted(context, permission))
                    return true;
                for (String per : permission) {
                    if (context.checkSelfPermission(per) != PackageManager.PERMISSION_GRANTED && !context.shouldShowRequestPermissionRationale(per)) {
                        flag = true;
                        break;
                    } else {
                        flag = false;
                    }
                }

                if (flag) {
                    flag = false;
//                    CustomAlertDialog.showPermissionAlert(context, context.getResources().getString(R.string.msg_alert_you_have_permission));
                } else {
                    context.requestPermissions(permission, requestCode);
                }
            }
        } else {
            return true;
        }
        return flag;
    }

    private static boolean checkFirstTimePermission(String permission[]) {
        for (String s : permission) {
            if (!VideoApp.getDataManager().getBooleanAppPref(s, false)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isPermissionGranted(Activity context, String permission[]) {
        boolean flag = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String per : permission) {
                if (context.checkSelfPermission(per) == PackageManager.PERMISSION_GRANTED) {
                    flag = true;
                } else {
                    flag = false;
                    break;
                }
            }
        } else {
            flag = true;
        }
        return flag;
    }


    public static boolean hasSelfPermission(Activity activity, String[] permissions) {

        // Verify that all the permissions.
        for (String permission : permissions) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (activity.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isConnectingToInternet() {
        ConnectivityManager cm = (ConnectivityManager) VideoApp.appContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }


    public interface Callback {
        void onSuccess();

        void onError();
    }

    public static void setImageUrl(Context context, String path, final ImageView imageView) {
        setImageUrl(context, path, imageView, null, false);
    }

    public static void setImageUrl(Context context, String path, final ImageView imageView, final ProgressBar progressBar) {
        setImageUrl(context, path, imageView, progressBar, false);
    }

    public static void setImageUrl(Context context, String path, final ImageView imageView, final ProgressBar progressBar, boolean isLocal) {
        setImageUrl(context, path, imageView, progressBar, isLocal, null);
    }

    public static void setImageUrl(Context context, String path, final ImageView imageView, final ProgressBar progressBar, final Callback callback) {
        setImageUrl(context, path, imageView, progressBar, false, callback);
    }

    public static void setImageUrl(Context context, String path, final ImageView imageView, final ProgressBar progressBar, boolean isLocal, final Callback callback) {

        imageView.setImageDrawable(context.getDrawable(DummyLists.getImageResourceId(path)));

//        if (!TextUtils.isEmpty(path)) {
//            if (isLocal) {
//                Glide.with(context)
//                        .load(new File(path))
//                        .apply(new RequestOptions().placeholder(R.mipmap.default_media_image_gallery).error(R.mipmap.default_media_image_gallery))
//                        .listener(new RequestListener<Drawable>() {
//                            @Override
//                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                                if (progressBar != null)
//                                    progressBar.setVisibility(View.GONE);
////                    imageView.setImageResource(R.drawable.no_category_available);
//                                if (callback != null)
//                                    callback.onError();
//                                return false;
//                            }
//
//                            @Override
//                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                                if (progressBar != null)
//                                    progressBar.setVisibility(View.GONE);
//                                if (callback != null)
//                                    callback.onSuccess();
//                                return false;
//                            }
//                        }).into(imageView);
//            } else {
//                Glide.with(context)
//                        .load(path)
//                        .apply(new RequestOptions().placeholder(R.mipmap.default_media_image_gallery).error(R.mipmap.default_media_image_gallery))
//                        .listener(new RequestListener<Drawable>() {
//                            @Override
//                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                                if (progressBar != null)
//                                    progressBar.setVisibility(View.GONE);
////                    imageView.setImageResource(R.drawable.no_category_available);
//                                if (callback != null)
//                                    callback.onError();
//                                return false;
//                            }
//
//                            @Override
//                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                                if (progressBar != null)
//                                    progressBar.setVisibility(View.GONE);
//                                if (callback != null)
//                                    callback.onSuccess();
//                                return false;
//                            }
//                        })
//                        .into(imageView);
//            }
//        } else {
//            if (progressBar != null)
//                progressBar.setVisibility(View.GONE);
////            imageView.setImageResource(R.mipmap.default_media_image_gallery);
//            if (callback != null)
//                callback.onError();
//        }

    }

    public static void setMarkerImageUrl(Context context, String path, final ImageView imageView, final Callback callback) {
        if (!TextUtils.isEmpty(path)) {
//            int size = (int) context.getResources().getDimension(R.dimen._40sdp);
//            Glide.with(context)
//                    .load(path)
//                    .apply(new RequestOptions().placeholder(R.mipmap.user_default_icon_round).error(R.mipmap.user_default_icon_round).override(size, size))
//                    .listener(new RequestListener<Drawable>() {
//                        @Override
//                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                            if (callback != null)
//                                callback.onError();
//                            return false;
//                        }
//
//                        @Override
//                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                            imageView.setImageDrawable(resource);
//                            if (callback != null)
//                                callback.onSuccess();
//                            return true;
//                        }
//                    })
//                    .into(imageView);

        } else {
//            imageView.setImageResource(R.mipmap.user_default_icon_round);
            if (callback != null)
                callback.onError();
        }

    }


    /**
     * View Related Utils Methods
     */
    public static boolean isEmpty(@Nullable CharSequence str) {
        return str == null || str.length() == 0;
    }

    public final static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public static void showToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void hideKeyboard(Context ctx) {
        InputMethodManager inputManager = (InputMethodManager) ctx
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no baseView has focus:
        View v = ((Activity) ctx).getCurrentFocus();
        if (v == null)
            return;

        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }


    public static void showUnderDevelopmentToast(Context context) {
        showToast(context, context.getString(R.string.msg_under_developement));
    }


    public static void setVisibility(View view, int visibility) {
        if (view == null) return;
        if (view.getVisibility() != visibility)
            view.setVisibility(visibility);
    }


    //Temp methods End


    public static void addTextChangedListener(EditText e, final TextInputLayout t) {
        e.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    if (!TextUtils.isEmpty(t.getError())) {
                        t.setErrorEnabled(false);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public static JSONObject checkResponse(int code, String message, RestResponse restResponse) {
        if (code == Constants.SUCCESS_CODE) {
            String flag = null;
            if (code == Constants.SUCCESS_CODE) {
                try {
                    JSONObject res = null;
                    try {
                        res = new JSONObject(restResponse.getResString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.d("response ", res.toString());

                    flag = res.getString(RES_CODE_KEY);
                    String msg = res.getString(RES_MSG_KEY);
                    if (flag.equalsIgnoreCase("1")) {
                    } else {
                        res = null;
                    }
                    return res;

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (code == Constants.FAIL_CODE) {

        } else {

        }
        return null;

    }

    public interface OnSetListener {
        void onSet(Calendar calendar);
    }

    public static void showTimePickerDialog(Context context, final Calendar calendar, final OnSetListener listener) {

        TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        listener.onSet(calendar);
                    }
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);
        timePickerDialog.show();
    }

    public static String getHastTag(ArrayList<String> tag) {
        if (tag != null && tag.size() > 0) {
            StringBuffer buffer = new StringBuffer();
            for (String str : tag
            ) {
                buffer.append("#");
                buffer.append(str);
                buffer.append(" ");
            }
            return buffer.toString();
        }
        return "";
    }




}
