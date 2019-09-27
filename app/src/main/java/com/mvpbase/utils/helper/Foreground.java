package com.mvpbase.utils.helper;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;




public class Foreground implements Application.ActivityLifecycleCallbacks {


    private static Foreground instance;
    private String TAG = "Foreground";
    private boolean foreground = false, paused = true, isFirst = false;
    private Handler handler = new Handler();
    private Runnable check;
    public static final long CHECK_DELAY = 500;
    private static Context appCtx;

    private List<Listener> listeners = new CopyOnWriteArrayList();

    public boolean isForeground() {
        return foreground;
    }

    public boolean isBackground() {
        return !foreground;
    }

    public static Foreground init(Application application) {
        if (instance == null) {
            instance = new Foreground();

            application.registerActivityLifecycleCallbacks(instance);
        }
        return instance;
    }

    public static Foreground get(Context ctx) {
        if (instance == null) {
            appCtx = ctx.getApplicationContext();
            if (appCtx instanceof Application) {
                init((Application) appCtx);
            }
            throw new IllegalStateException("IllegalException");
        }
        return instance;
    }

    public static Foreground get() {
        return instance;
    }


    @Override
    public void onActivityCreated(final Activity activity, Bundle savedInstanceState) {


    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        paused = false;
        boolean wasBackground = !foreground;
        foreground = true;


        if (check != null)
            handler.removeCallbacks(check);

        if (wasBackground) {
            for (Listener l : listeners) {
                try {
                    l.onBecameForeground();
                } catch (Exception exc) {
                    Log.e(TAG, "Listener threw exception!", exc);
                }
            }

        } else {
            Log.i(TAG, "still foreground");
        }

    }

    @Override
    public void onActivityPaused(final Activity activity) {
        // foreground = false;
        paused = true;

        if (check != null)
            handler.removeCallbacks(check);

        handler.postDelayed(check = new Runnable() {
            @Override
            public void run() {
                if (foreground && paused) {
                    foreground = false;

                    for (Listener l : listeners) {
                        try {
                            l.onBecameBackground();
                        } catch (Exception exc) {
                            //	Log.e(TAG, "Listener threw exception!", exc);
                        }
                    }
                } else {
                    //	Log.i(TAG, "still foreground");
                }
            }
        }, CHECK_DELAY);

    }


    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {


    }

    public interface Listener {
        public void onBecameForeground();

        public void onBecameBackground();
    }


    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    public void removeListener(Listener listener) {
        listeners.remove(listener);
    }

}