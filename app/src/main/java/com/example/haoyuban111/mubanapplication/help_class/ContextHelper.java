package com.example.haoyuban111.mubanapplication.help_class;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;

/**
 * Created by haoyuban111 on 2017/3/24.
 */

public class ContextHelper {
    private static Context ApplicationContext;

    public static void setApplicationContext(Context applicationContext) {
        if (ContextHelper.ApplicationContext == null) {
            ContextHelper.ApplicationContext = applicationContext;
        }
    }

    public static Resources getResources() {
        return getApplicationContext().getResources();
    }

    public static Context getApplicationContext() {
        return ApplicationContext;
    }

    public static int getColor(int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return ContextHelper.getApplicationContext().getResources().getColor(id, getApplicationContext().getTheme());
        } else {
            return ContextHelper.getApplicationContext().getResources().getColor(id);
        }
    }

    public static String getString(int id) {
        return getApplicationContext().getString(id);
    }


    public static Drawable getDrawable(int drawableId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return ContextHelper.getApplicationContext().getResources().getDrawable(drawableId, getApplicationContext().getTheme());
        } else {
            return ContextHelper.getApplicationContext().getResources().getDrawable(drawableId);
        }
    }

    public static void setBackground(View view, Drawable drawable) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
    }

}
