package com.mvpbase.utils.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.reflect.Field;

import com.mvpbase.R;

public class BottomNavigationViewHelper {
    @SuppressLint("RestrictedApi")
    public static void removeShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                //noinspection RestrictedApi
                item.setShifting(false);
                // set once again checked value, so view will be updated
                //noinspection RestrictedApi
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("BottomNav", "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            Log.e("BottomNav", "Unable to change value of shift mode", e);
        }
    }

    public static void setNotification(Context context, BottomNavigationView nav_bottom, int index, String value) {
        BottomNavigationMenuView bottomNavigationMenuView =
                (BottomNavigationMenuView) nav_bottom.getChildAt(0);
        View v = bottomNavigationMenuView.getChildAt(index);
        BottomNavigationItemView itemView = (BottomNavigationItemView) v;
        if (itemView == null) return;

        View badge = LayoutInflater.from(context)
                .inflate(R.layout.notification_badge, bottomNavigationMenuView, false);
        TextView tvCount = badge.findViewById(R.id.tvBadgeCount);
        tvCount.setText(value);
        itemView.addView(badge);
    }

    public static void removeNotification(Context context, BottomNavigationView nav_bottom, int index) {
        BottomNavigationMenuView bottomNavigationMenuView =
                (BottomNavigationMenuView) nav_bottom.getChildAt(0);
        View v = bottomNavigationMenuView.getChildAt(index);
        BottomNavigationItemView itemView = (BottomNavigationItemView) v;
        if (itemView == null) return;
        View badge = itemView.findViewById(R.id.frame_notification);
        itemView.removeView(badge);
    }
}