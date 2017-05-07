package com.example.haoyuban111.mubanapplication.ui.view;

import android.app.Activity;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

import com.example.haoyuban111.mubanapplication.R;
import com.example.haoyuban111.mubanapplication.help_class.ContextHelper;


public class DecoratorNotificationBar {
    public static void updateNotificationBar(Activity activity, Window window) {
        if (activity != null && window != null) {
            updateNotificationBar(activity, window, ContextHelper.getColor(R.color.tp_gray_first));
        }
    }

    public static void updateNotificationBar(Activity activity, Window window, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(color);
                if (activity.getActionBar() != null)
                    activity.getActionBar().setElevation(0f);
            } catch (Exception ignored) {

            }
        }
    }
}
