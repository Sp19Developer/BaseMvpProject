package com.mvpbase.ui.activity.Splash;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.common.GoogleApiAvailability;
import com.mvpbase.R;
import com.mvpbase.ui.activity.Dashboard.DashBoardActivity;


public class SplashActivity extends AppCompatActivity implements SplashContract.View {

    private SplashContract.Presenter<SplashContract.View> presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        for fcm
        GoogleApiAvailability.getInstance().makeGooglePlayServicesAvailable(this);
        setContentView(R.layout.activity_splash);
        ImageView load = (ImageView) findViewById(R.id.imageView2);
        Glide.with(this).load(R.raw.shinple_logo).into(load);
        Glide.with(this).load(R.raw.shinple_logo).into(new DrawableImageViewTarget(load) {
            @Override
            public void onResourceReady(Drawable resource, @Nullable Transition<? super Drawable> transition) {
                if (resource instanceof GifDrawable) {
                    ((GifDrawable) resource).setLoopCount(1);
                }
                super.onResourceReady(resource, transition);
            }
        });
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //        for fcm
        GoogleApiAvailability.getInstance().makeGooglePlayServicesAvailable(this);
    }

    private void init() {
        presenter = new SplashPresenter<>();
        presenter.onAttach(this);
    }

    @Override
    public void openDashboardActivity() {
        Intent intent = new Intent(SplashActivity.this, DashBoardActivity.class);
        startActivity(intent);
    }



    @Override
    public void finishActivity() {
        finish();
    }


    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void showConnectionLostView() {

    }

    @Override
    public void hideConnectionLostView() {

    }

    @Override
    public void showError(int code, String res) {

    }

}
