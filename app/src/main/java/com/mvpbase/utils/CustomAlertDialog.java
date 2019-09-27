package com.mvpbase.utils;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mvpbase.R;


/**
 * Created by etech7 on 31/10/17.
 */

public class CustomAlertDialog {

    public static void showAlert(Context mContext, String message) {

//        showAlert(mContext, mContext.getString(R.string.app_name), message, mContext.getResources().getString(R.string.btn_ok), null, null, null);

    }

    public static void showAlert(Context mContext, String title, String message, String firstBtnTitle, View.OnClickListener btnClickListner) {

        showAlert(mContext, title, message, firstBtnTitle, null, null, btnClickListner, null, null);
    }

    public static void showAlert(Context mContext, String title, String message, String firstBtnTitle, String secondBtnTitle, View.OnClickListener btnClickListner, View.OnClickListener secondbtnClickListner) {

        showAlert(mContext, title, message, firstBtnTitle, secondBtnTitle, null, btnClickListner, secondbtnClickListner, null);
    }

    public static void showAlert(Context mContext, String title, String message, String firstBtnTitle, String secondBtnTitle, final String thirdBtnTitle, final View.OnClickListener btnClickListner, final View.OnClickListener secondbtnClickListner, final View.OnClickListener thirdbtnClickListner) {
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.custom_alert_dialog);
        LinearLayout linButtons = (LinearLayout) dialog.findViewById(R.id.linButtons);
        linButtons.setOrientation(LinearLayout.HORIZONTAL);

        TextView lblTitle = (TextView) dialog.findViewById(R.id.lblDialogTitle);
        TextView lblMessage = (TextView) dialog.findViewById(R.id.lblMessage);
        final Button btnDial1 = (Button) dialog.findViewById(R.id.btn1);
        final Button btnDial2 = (Button) dialog.findViewById(R.id.btn2);
        final Button btnDial3 = (Button) dialog.findViewById(R.id.btn3);

        dialog.setCancelable(false);

        if (null == title || "".equalsIgnoreCase(title)) {
            lblTitle.setVisibility(View.GONE);
        } else {
            lblTitle.setText(title);
        }
        if (null == message || "".equalsIgnoreCase(message)) {
            lblMessage.setVisibility(View.GONE);
        } else {
            lblMessage.setText(message);
        }
        if (null == firstBtnTitle || "".equalsIgnoreCase(firstBtnTitle)) {
            btnDial1.setVisibility(View.GONE);
        } else {
            btnDial1.setText(firstBtnTitle);
            btnDial1.setTag(dialog);
            btnDial1.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (btnClickListner != null) {
                        btnClickListner.onClick(v);
                    }
                    dialog.dismiss();
                }
            });
        }
        if (null == secondBtnTitle || "".equalsIgnoreCase(secondBtnTitle)) {
            btnDial2.setVisibility(View.GONE);
        } else {
            btnDial2.setText(secondBtnTitle);
            dialog.findViewById(R.id.viewDividerDialog).setVisibility(View.GONE);
            btnDial2.setTag(dialog);
            btnDial2.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (secondbtnClickListner != null) {
                        secondbtnClickListner.onClick(v);
                    }
                    dialog.dismiss();
                }
            });

        }
        if (null == thirdBtnTitle || "".equalsIgnoreCase(thirdBtnTitle)) {
            btnDial3.setVisibility(View.GONE);
        } else {
            dialog.setCancelable(true);
            btnDial3.setText(thirdBtnTitle);
            btnDial3.setTag(dialog);
            btnDial3.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if (thirdbtnClickListner != null) {
                        thirdbtnClickListner.onClick(v);
                    }
                }
            });

        }
        dialog.show();
    }


public     static ProgressDialog showProgressDialog(Context context){
        return ProgressDialog.show(context,null,context.getString(R.string.str_msg_loading));
    }

}
