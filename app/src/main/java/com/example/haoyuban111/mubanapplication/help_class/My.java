package com.example.haoyuban111.mubanapplication.help_class;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Patterns;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;
import android.widget.TextView;


import com.example.haoyuban111.mubanapplication.R;
import com.example.haoyuban111.mubanapplication.log.LogWriter;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Pattern;

public class My {
    public enum EDeviceType {
        PHONE(1),
        PHONE_HD(2),
        TABLET(4),
        TABLET_HD(8);

        public final int value;

        EDeviceType(int value) {
            this.value = value;
        }

        public static EDeviceType fromInt(int value) {
            EDeviceType result = null;
            for (EDeviceType item : values()) {
                if (item.value == value) {
                    result = item;
                    break;
                }
            }
            if (result == null) {
                throw new IllegalArgumentException("EDeviceType - fromInt");
            }
            return result;
        }
    }

    public static class Environment {

        private static String _newLine;

        public static String getNewLine() {
            if (_newLine == null) {
                _newLine = System.getProperty("line.separator");
            }
            return _newLine;
        }
    }

    public static class Device {

        public enum ERingerMode {
            Normal,
            Silent,
            Vibrate
        }

        public static final float BYTES_IN_MB = 1024.0f * 1024.0f;

        private static Integer STATUS_BAR_HEIGHT;
        private static Integer NAVIGATION_BAR_HEIGHT;

        public static final int MIN_HD_DPI = 280;

        private static float _readExternal;
        private static float _writeExternal;

        private static float _writeInternal;
        private static float _readInternal;

        private static String _pushToken;
        private static String _androidId;
        private static String _androidDeviceId;
        private static Float _density;

        private static EDeviceType _deviceType;
        private static String _userEmail;

        private static String _userName;

        public static ERingerMode getRingMode(Context context) {
            try {
                AudioManager am =
                        (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

                switch (am.getRingerMode()) {
                    case AudioManager.RINGER_MODE_SILENT:
                        return ERingerMode.Silent;
                    case AudioManager.RINGER_MODE_VIBRATE:
                        return ERingerMode.Vibrate;
                }
            } catch (Exception exc) {
                LogWriter.e(exc);
            }
            return ERingerMode.Normal;
        }

        public static boolean hasCamera(Context context) {
            if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                return true;
            } else {
                return false;
            }
        }

        public static boolean openExternalBrowser(Context context, String url) {
            try {
                Intent intentBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                intentBrowser.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (IntentHelper.isIntentAvailable(context, intentBrowser)) {
                    context.startActivity(intentBrowser);
                    return true;
                }
            } catch (Exception exc) {
                LogWriter.e(exc);
            }
            return false;
        }


        public static String getUserEmail() {
            if (_userEmail == null) {
                try {
                    Pattern emailPattern = Patterns.EMAIL_ADDRESS;
                    if (ActivityCompat.checkSelfPermission(ContextHelper.getApplicationContext(), Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
//                        return TODO;
                    }
                    Account[] accounts = AccountManager.get(ContextHelper.getApplicationContext()).getAccounts();
                    for (Account account : accounts) {
                        if (emailPattern.matcher(account.name).matches()) {
                            _userEmail = account.name;
                            break;
                        }
                    }
                } catch (Exception exc) {
                    LogWriter.e(exc);
                }
            }
            return _userEmail;
        }

        public static float megabytesFree() {
            return ((float) Runtime.getRuntime().maxMemory() / BYTES_IN_MB) - ((float) Runtime.getRuntime().totalMemory()) / BYTES_IN_MB;
        }

        public static boolean isInternetAvailable() {
            ConnectivityManager cm = (ConnectivityManager) ContextHelper.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()) {
                return true;
            }
            return false;
        }

        public static boolean isPackageInstalled(String packageName, Context context) {
            PackageManager pm = context.getPackageManager();
            try {
                pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
                return true;
            } catch (PackageManager.NameNotFoundException e) {
                return false;
            }
        }

        public static String getUserName() {
            if (_userName == null) {
                try {
                    Cursor c = ContextHelper.getApplicationContext().getContentResolver().query(ContactsContract.Profile.CONTENT_URI, null, null, null, null);
                    int count = c.getCount();
                    boolean b = c.moveToFirst();
                    int position = c.getPosition();
                    if (count == 1 && position == 0) {
                        _userName = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Nickname.DISPLAY_NAME));
                    }
                    c.close();
                    if (TextUtils.isEmpty(_userName)) {
                        _userName = "";
                    }
                } catch (Exception exc) {
                    _userName = "";
                    LogWriter.e(exc);
                }
            }
            return _userName;
        }

        public static boolean isTablet() {
            return ContextHelper.getApplicationContext().getResources().getBoolean(R.bool.isTablet);
        }
        public static float getReadExternal() {
            return _readExternal;
        }

        public static void setReadExternal(float readExternal) {
            _readExternal = readExternal;
        }

        public static float getWriteExternal() {
            return _writeExternal;
        }

        public static void setWriteExternal(float writeExternal) {
            _writeExternal = writeExternal;
        }

        public static float getWriteInternal() {
            return _writeInternal;
        }

        public static void setWriteInternal(float writeInternal) {
            _writeInternal = writeInternal;
        }

        public static float getReadInternal() {
            return _readInternal;
        }

        public static void setReadInternal(float readInternal) {
            _readInternal = readInternal;
        }

        public static EDeviceType getDeviceType() {
            if (_deviceType == null) {
                _deviceType = isTablet() ? EDeviceType.TABLET : EDeviceType.PHONE;
                int dpi = My.Device.getDisplayDpi();
                switch (_deviceType) {
                    case PHONE:
                        if (dpi > MIN_HD_DPI) {
                            _deviceType = EDeviceType.PHONE_HD;
                        }
                    case TABLET:
                        if (dpi > MIN_HD_DPI) {
                            _deviceType = EDeviceType.TABLET_HD;
                        }
                }
            }
            return _deviceType;
        }

        public static boolean isHDDevice() {
            final EDeviceType deviceType = getDeviceType();
            return deviceType == EDeviceType.PHONE_HD || deviceType == EDeviceType.TABLET_HD;
        }

        public static double getDisplayInches() {
            DisplayMetrics displayMetrics = ContextHelper.getApplicationContext().getResources().getDisplayMetrics();
            float screenWidth = displayMetrics.widthPixels / displayMetrics.xdpi;
            float screenHeight = displayMetrics.heightPixels / displayMetrics.ydpi;
            return Math.sqrt(Math.pow(screenWidth, 2) + Math.pow(screenHeight, 2));
        }

        public static int getNavigationBarHeight(){
            if (NAVIGATION_BAR_HEIGHT == null) {
                NAVIGATION_BAR_HEIGHT = ContextHelper.getResources().getDimensionPixelSize(R.dimen.dimen_44);
                int resourceId = ContextHelper.getApplicationContext().getResources().getIdentifier("navigation_bar_height", "dimen", "android");
                if (resourceId > 0) {
                    NAVIGATION_BAR_HEIGHT = ContextHelper.getApplicationContext().getResources().getDimensionPixelSize(resourceId);
                }
            }
            return NAVIGATION_BAR_HEIGHT;
        }

        public static int getStatusBarHeight() {
            if (STATUS_BAR_HEIGHT == null) {
                STATUS_BAR_HEIGHT = ContextHelper.getResources().getDimensionPixelSize(R.dimen.dimen_20);
                int resourceId = ContextHelper.getApplicationContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
                if (resourceId > 0) {
                    STATUS_BAR_HEIGHT = ContextHelper.getApplicationContext().getResources().getDimensionPixelSize(resourceId);
                }
            }
            return STATUS_BAR_HEIGHT;
        }

        public static float getDisplayWidthDp() {
            WindowManager wm = (WindowManager) ContextHelper.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            DisplayMetrics outMetrics = new DisplayMetrics();
            display.getMetrics(outMetrics);
            return outMetrics.widthPixels / ContextHelper.getApplicationContext().getResources().getDisplayMetrics().density;
        }

        public static float getDisplayHeightDp() {
            WindowManager wm = (WindowManager) ContextHelper.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            DisplayMetrics outMetrics = new DisplayMetrics();
            display.getMetrics(outMetrics);
            return outMetrics.heightPixels / ContextHelper.getApplicationContext().getResources().getDisplayMetrics().density;
        }

        public static int getDisplayDpi() {
            return ContextHelper.getApplicationContext().getResources().getDisplayMetrics().densityDpi;
        }

        public static float getDisplayDpiX() {
            return ContextHelper.getApplicationContext().getResources().getDisplayMetrics().xdpi;
        }

        public static float getDisplayDpiY() {
            return ContextHelper.getApplicationContext().getResources().getDisplayMetrics().ydpi;
        }

        public static int getDisplayWidthPx() {
            return ContextHelper.getApplicationContext().getResources().getDisplayMetrics().widthPixels;
        }

        public static int getDisplayHeightPx() {
            return ContextHelper.getApplicationContext().getResources().getDisplayMetrics().heightPixels;
        }

        public static int getDeviceOrientation() {
            return ContextHelper.getApplicationContext().getResources().getConfiguration().orientation;
        }

        public static String getTextSizeDpByPx() {
            TextView tv = new TextView(ContextHelper.getApplicationContext());

            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
            float dp12 = tv.getTextSize();
            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
            float dp14 = tv.getTextSize();
            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
            float dp18 = tv.getTextSize();
            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 22);
            float dp22 = tv.getTextSize();

            return new StringBuilder().append(String.format("Fonts dp/px: [%s/%s] [%s/%s] [%s/%s] [%s/%s]", 12, dp12, 14, dp14, 18, dp18, 22, dp22)).toString();
        }

        public static String getTextSizeSpByPx() {
            TextView tv = new TextView(ContextHelper.getApplicationContext());

            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            float sp12 = tv.getTextSize();
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            float sp14 = tv.getTextSize();
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            float sp18 = tv.getTextSize();
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
            float sp22 = tv.getTextSize();

            return new StringBuilder().append(String.format("Fonts dp/px: [%s/%s] [%s/%s] [%s/%s] [%s/%s]", 12, sp12, 14, sp14, 18, sp18, 22, sp22)).toString();
        }

        public static String getDeviceId() {
            if (_androidId == null) {
                try {
                    _androidId = Settings.Secure.getString(ContextHelper.getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
                } catch (Exception exc) {
                    LogWriter.e(exc);
                }
            }
            return _androidId;
        }

        public static boolean shareApp(Activity activity, String chooseHeader, String subject, String text) {
            boolean result = false;

            try {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
                shareIntent.putExtra(Intent.EXTRA_TEXT, text);
                shareIntent.addCategory(Intent.CATEGORY_DEFAULT);
                shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                shareIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                shareIntent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                if (IntentHelper.isIntentAvailable(activity, shareIntent)) {
                    Intent chooser = Intent.createChooser(shareIntent, chooseHeader);
                    if (IntentHelper.isIntentAvailable(activity, chooser)) {
                        activity.startActivity(chooser);
                        result = true;
                    }
                }
            } catch (Exception e) {
                LogWriter.e(e);
            }

            return result;
        }

        public static int getNumCores() {
            try {
                File dir = new File("/sys/devices/system/cpu/");
                File[] files = dir.listFiles(new CpuFilter());
                return files.length;
            } catch (Exception ex) {
                LogWriter.e(ex);
                return 4;
            }
        }

        private static class CpuFilter implements FileFilter {
            @Override
            public boolean accept(File pathname) {
                return Pattern.matches("cpu[0-9]+", pathname.getName());
            }
        }

        public static int dpToPx(int dp) {
            return Math.round(dp * getDensity());
        }

        public static float getDensity() {
            if (_density == null) {
                _density = ContextHelper.getResources().getDisplayMetrics().density;
            }
            return _density;
        }

        public static String getDeviceName() {
            return Build.MODEL;
        }

        public static String getOSVersion() {
            return Build.VERSION.RELEASE;
        }

        public static String generateDeviceInfoReport() {
            StringBuilder builder = new StringBuilder();
            builder.append(String.format("Type: %s", My.Device.getDeviceType().toString()));
            builder.append("\n");
            builder.append(String.format("Orientation: %s", My.Device.getDeviceOrientation() == Configuration.ORIENTATION_PORTRAIT ? "portrait" : "landscape"));
            builder.append("\n");
            builder.append(String.format("Display Inches: %s", My.Device.getDisplayInches()));
            builder.append("\n");
            builder.append(String.format("Resolution: W[%s] H[%s]", My.Device.getDisplayWidthPx(), My.Device.getDisplayHeightPx()));
            builder.append("\n");
            builder.append(String.format("Density DPI: %s", My.Device.getDisplayDpi()));
            builder.append("\n");
            builder.append(String.format("X Y DPI: X[%s] Y[%s]", My.Device.getDisplayDpiX(), My.Device.getDisplayDpiY()));
            builder.append("\n");
            builder.append(getTextSizeSpByPx());
            builder.append("\n");
            builder.append(getTextSizeDpByPx());
            return builder.toString();
        }
    }

    public static class Application {

        private static Boolean _isEmulator;
        private static String _versionString;
        private static String _versionShortString;
        private static Integer _versionCode;
        private static String _installer;
        private static String _packageName;

        public static String getPackageName() {
            if (_packageName == null) {
                _packageName = ContextHelper.getApplicationContext().getPackageName();
            }
            return _packageName;
        }

        public static String getInstaller() {
            if (TextUtils.isEmpty(_installer)) {
                try {
                    PackageManager pm = ContextHelper.getApplicationContext().getPackageManager();
                    _installer = pm.getInstallerPackageName(ContextHelper.getApplicationContext().getPackageName());
                } catch (Exception exc) {
                    LogWriter.e(exc);
                }
                if (TextUtils.isEmpty(_installer)) {
                    _installer = "unknown";
                }
            }
            return _installer;
        }

        public static boolean getIsEmulator() {
            if (_isEmulator == null) {
                _isEmulator = "generic".equals(Build.BRAND.toLowerCase());
            }

            return _isEmulator;
        }

//        public static String getVersion() {
//            if (_versionString == null || _versionString.isEmpty()) {
//                try {
//                    _versionString = "";
//                    _versionString = ContextHelper.getApplicationContext().getPackageManager().getPackageInfo(ContextHelper.getApplicationContext().getPackageName(), 0).versionName;
//                } catch (PackageManager.NameNotFoundException ignored) {
//                }
//            }
//            return ContextHelper.getVersion();
//        }

//        public static String getShowVersion() {
//            if (_versionShortString == null || _versionShortString.isEmpty()) {
//                try {
//                    _versionShortString = convertVersionToShow(Version.parse(getVersion()));
//                } catch (Exception exc) {
//                }
//            }
//            return _versionShortString;
//        }

//        public static String convertVersionToShow(Version version) {
//            String result = "";
//            if (version.getBuild() > 0) {
//                result = String.format("%s.%s.%s", String.valueOf(version.getMajor()), String.valueOf(version.getMinor()), String.valueOf(version.getBuild()));
//            } else {
//                result = String.format("%s.%s", String.valueOf(version.getMajor()), String.valueOf(version.getMinor()));
//            }
//            return result;
//        }

        public static int getVersionCode() {
            if (_versionCode == null) {
                try {
                    final Context context = ContextHelper.getApplicationContext();
                    PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                    _versionCode = packageInfo.versionCode;
                } catch (PackageManager.NameNotFoundException e) {
                    _versionCode = 0;
                }
            }
            return _versionCode;
        }

        public static boolean openAppOnGooglePlay(Activity activity, String appPackageName) {
            if (activity != null) {
                Intent intentGoogleStore = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName));
                if (IntentHelper.isIntentAvailable(activity, intentGoogleStore)) {
                    intentGoogleStore.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    activity.startActivity(intentGoogleStore);
                    return true;
                } else {
                    try {
                        Intent intentWebGoogleStore = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName));
                        if (IntentHelper.isIntentAvailable(activity, intentWebGoogleStore)) {
                            intentWebGoogleStore.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            activity.startActivity(intentWebGoogleStore);
                            return true;
                        }
                    } catch (Exception exc) {
                        LogWriter.e(exc);
                    }
                }
            }

            return false;
        }

        public static boolean openConnectionSettings(Activity activity) {
            if (activity != null) {
                Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                if (IntentHelper.isIntentAvailable(activity, intent)) {
                    activity.startActivity(intent);
                    return true;
                }
            }
            return false;
        }

        public static boolean openAppOnGooglePlay(Activity activity) {
            return activity != null && openAppOnGooglePlay(activity, activity.getPackageName());
        }


        public static boolean openDeveloperOnGooglePlay(Activity activity) {
            if (activity != null) {
                Intent intentGoogleStore = new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=pub:Hello Pal International Inc."));
                if (IntentHelper.isIntentAvailable(activity, intentGoogleStore)) {
                    intentGoogleStore.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    activity.startActivity(intentGoogleStore);
                    return true;
                } else {
                    return openLink(activity, "http://play.google.com/store/search?q=pub:Hello Pal International Inc.");
                }
            }

            return false;
        }

        public static boolean openLink(Activity activity, String link) {
            if (activity != null) {
                try {
                    Intent intentBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                    if (IntentHelper.isIntentAvailable(activity, intentBrowser)) {
                        intentBrowser.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        activity.startActivity(intentBrowser);
                        return true;
                    }
                } catch (Exception exc) {
                    LogWriter.e(exc);
                }
            }

            return false;
        }

        public static boolean launchApp(Context context, String packageName) {
            try {
                PackageManager pm = context.getPackageManager();
                Intent launchIntent = pm.getLaunchIntentForPackage(packageName);
                context.startActivity(launchIntent);
                return true;
            } catch (Exception exc) {
                LogWriter.e(exc);
            }
            return false;
        }

        public static boolean isBrokenSamsungDevice() {
            return (Build.MANUFACTURER.equalsIgnoreCase("samsung")
                    && isBetweenAndroidVersions(
                    Build.VERSION_CODES.LOLLIPOP,
                    Build.VERSION_CODES.LOLLIPOP_MR1));
        }

        public static boolean isBetweenAndroidVersions(int min, int max) {
            return Build.VERSION.SDK_INT >= min && Build.VERSION.SDK_INT <= max;
        }
    }
}
