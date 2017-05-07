package com.example.haoyuban111.mubanapplication.help_class;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

public class IntentHelper {
    public static boolean isIntentAvailable(Context context, Intent intent) {
        return context.getPackageManager().resolveActivity(intent, PackageManager.PERMISSION_GRANTED) != null;
    }
}
