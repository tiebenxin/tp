package com.example.haoyuban111.mubanapplication.help_class;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Pair;

import com.example.haoyuban111.mubanapplication.files.FileWriter;
import com.example.haoyuban111.mubanapplication.log.LogWriter;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ImageHelper implements ITaskContext<Pair<String, Bitmap>> {

    public final static int IMAGE_RESULT = 0;
    public static final int MAX_IMAGE_SIZE = 1280;
    public final static int MAX_ALPHA = 255;

    public static final String EXTENSION_PNG = ".png";
    public static final String EXTENSION_JPEG = ".jpg";

    private static ImageHelper INSTANCE;

    private final Map<String, ArrayList<IEventListener>> _listeners;
    private Map<Integer, SoftReference<Bitmap>> _resourceImages;

    protected static ImageHelper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ImageHelper();
        }
        return INSTANCE;
    }

    public ImageHelper() {
        _resourceImages = new HashMap<>();
        _listeners = new HashMap<String, ArrayList<IEventListener>>();
    }


    @Override
    public void onCompleted(Pair<String, Bitmap> result) {
        if (result.second != null) {
            ImageCache.INSTANCE.addBitmap(result.first, new BitmapDrawable(ContextHelper.getResources(), result.second));
        }
        sendResult(result);
    }

    private synchronized void sendResult(final Pair<String, Bitmap> result) {
        Handler handler = new Handler(ContextHelper.getApplicationContext().getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                for (IEventListener listener : _listeners.get(result.first)) {
                    if (listener != null) {
                        listener.onListeningEvent(ImageHelper.this, IMAGE_RESULT, result);
                    }
                }
                _listeners.remove(result.first);
            }
        });
    }

    public static Bitmap getFromResource(int resourceId) {
        return getFromResource(ContextHelper.getResources(), resourceId);
    }

    public static Bitmap getFromResourceLarge(int resourceId, int maxSize) {
        return getInstance().getResourceImageLarge(ContextHelper.getResources(), resourceId, maxSize);
    }

    public static Bitmap getFromResourceLarge(Resources resources, int resourceId, int maxSize) {
        return getInstance().getResourceImageLarge(resources, resourceId, maxSize);
    }

    public static Bitmap getFromResource(Resources resources, int resourceId) {
        return getInstance().getResourceImage(resources, resourceId);
    }

    public Bitmap getResourceImageLarge(Resources resources, int resourceId, int maxSize) {
        try {
            SoftReference<Bitmap> reference = _resourceImages.get(resourceId);
            if (reference == null) {
                reference = new SoftReference<Bitmap>(decodeBitmap(resources, resourceId, maxSize, maxSize));
                _resourceImages.put(resourceId, reference);
            }

            Bitmap bitmap = reference.get();
            if (bitmap == null) {
                bitmap = decodeBitmap(resources, resourceId, maxSize, maxSize);
                _resourceImages.put(resourceId, new SoftReference<Bitmap>(bitmap));
            }
            return bitmap;
        } catch (Exception exc) {
            LogWriter.e(exc);
            return null;
        }
    }

    public Bitmap getResourceImage(Resources resources, int resourceId) {
        try {
            SoftReference<Bitmap> reference = _resourceImages.get(resourceId);
            if (reference == null) {
                reference = new SoftReference<Bitmap>(BitmapFactory.decodeResource(resources, resourceId));
                _resourceImages.put(resourceId, reference);
            }

            Bitmap bitmap = reference.get();
            if (bitmap == null) {
                bitmap = BitmapFactory.decodeResource(resources, resourceId);
                _resourceImages.put(resourceId, new SoftReference<Bitmap>(bitmap));
            }
            return bitmap;
        } catch (Exception exc) {
            LogWriter.e(exc);
            return null;
        }
    }

    public static int getRotationAngle(String path) {
        return getExifRotation(new File(path));
    }

    public static int getExifRotation(File file) {
        if (file == null) return 0;
        try {
            ExifInterface exif = new ExifInterface(file.getAbsolutePath());
            return getRotateDegreeFromOrientation(
                    exif.getAttributeInt(
                            ExifInterface.TAG_ORIENTATION,
                            ExifInterface.ORIENTATION_UNDEFINED
                    )
            );
        } catch (Exception e) {
            LogWriter.e("An error occurred while getting the exif data: " + e.getMessage(), e);
        }
        return 0;
    }

    public static int getRotateDegreeFromOrientation(int orientation) {
        int degree = 0;
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                degree = 90;
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                degree = 180;
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                degree = 270;
                break;
            default:
                break;
        }
        return degree;
    }


    public static Bitmap decodeBitmap(Resources resources, int resId, int reqWidth, int reqHeight) {
        try {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(resources, resId, options);
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
            options.inJustDecodeBounds = false;
            try {
                return BitmapFactory.decodeResource(resources, resId, options);
            } catch (OutOfMemoryError ex) {
                LogWriter.e(ex);
            }
            return null;
        } catch (Exception exc) {
            LogWriter.e(exc);
        }
        return null;
    }

    public static Bitmap decodeBitmapWithAngle(Resources resources, String filePath) {
        return decodeBitmapWithAngle(resources, filePath, 0, 0);
    }

    public static Bitmap decodeBitmapWithAngle(Resources resources, String filePath, int reqWidth, int reqHeight) {

        try {
            float displayWidthPx = 1.2f * resources.getDisplayMetrics().widthPixels;
            float displayHeightPx = 1.2f * resources.getDisplayMetrics().heightPixels;

            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(filePath, options);

            if (reqWidth == 0 || reqHeight == 0) {
                reqWidth = options.outWidth;
                reqHeight = options.outHeight;
            }

            if (reqWidth > displayWidthPx || reqHeight > displayHeightPx) {
                float diff = Math.min(displayWidthPx / reqWidth, displayHeightPx / reqHeight);
                reqWidth = (int) (diff * reqWidth);
                reqHeight = (int) (diff * reqHeight);
            }

            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
            options.inJustDecodeBounds = false;

            Bitmap result = BitmapFactory.decodeFile(filePath, options);
            int rotationAngle = ImageHelper.getRotationAngle(filePath);
            if (rotationAngle != 0) {
                result = ImageHelper.rotate(result, rotationAngle);
            }
            return result;
        } catch (Exception exc) {
            LogWriter.e(exc);
        }
        return null;
    }

    protected static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        return calculateInSampleSize(options.outWidth, options.outHeight, reqWidth, reqHeight);
    }

    protected static int calculateInSampleSize(int rawWidth, int rawHeight, int reqWidth, int reqHeight) {
        int inSampleSize = 1;
        if (rawHeight > reqHeight || rawWidth > reqWidth) {
            final int heightRatio = Math.round((float) rawHeight / (float) reqHeight);
            final int widthRatio = Math.round((float) rawWidth / (float) reqWidth);
            inSampleSize = heightRatio > widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }

    public static Bitmap scaleImage(Bitmap bm, int maxWidth, int maxHeight) {
        try {
            float scaleWidth = maxWidth / (float) bm.getWidth();
            float scaleHeight = maxHeight / (float) bm.getHeight();

            if (scaleHeight > scaleWidth) {
                maxHeight = (int) (bm.getHeight() * scaleWidth);
                maxWidth = (int) (bm.getWidth() * scaleWidth);
            } else {
                maxHeight = (int) (bm.getHeight() * scaleHeight);
                maxWidth = (int) (bm.getWidth() * scaleHeight);
            }
            return Bitmap.createScaledBitmap(bm, maxWidth, maxHeight, true);
        } catch (Exception exc) {
            return bm;
        }
    }

    public static File compressJPG(File file, Bitmap source) {
        return compress(file, source, Bitmap.CompressFormat.JPEG, 95);
    }

    public static File compressPNG(File file, Bitmap source) {
        return compress(file, source, Bitmap.CompressFormat.PNG, 95);
    }

    public static File compress(String dir, String fileName, Bitmap source, Bitmap.CompressFormat format, int quality) {
        return compress(new File(dir, fileName), source, format, quality);
    }

    public static File compress(File file, Bitmap source, Bitmap.CompressFormat format, int quality) {
        try {
            OutputStream out = new BufferedOutputStream(new FileOutputStream(file));
            source.compress(format, quality, out);
            out.flush();
            out.close();
        } catch (Exception ex) {
            LogWriter.e(ex);
            if (file != null && file.exists()) {
                file.delete();
            }
            return null;
        }
        return file;
    }

    public static String getExtension(Bitmap.CompressFormat format) {
        String result = "";
        switch (format) {

            case JPEG:
                result = EXTENSION_JPEG;
                break;
            case PNG:
                result = EXTENSION_PNG;
                break;
        }
        return result;
    }


    public static Bitmap tintImage(int color, Bitmap image) {
        if (image == null) return image;
        try {
            Canvas canvas = new Canvas(image);
            canvas.drawColor(color);
        } catch (Exception ignored) {

        }
        return image;
    }

    public static Bitmap paintColorImage(int color, int alpha, Bitmap image) {
        if (image == null) return image;
        Bitmap result = Bitmap.createBitmap(image.getWidth(), image.getHeight(), Bitmap.Config.ARGB_8888);

        Paint paint = new Paint();
        paint.setColorFilter(new LightingColorFilter(color, 0));
        paint.setAlpha(alpha);

        Canvas canvas = new Canvas(result);
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        canvas.drawBitmap(image, 0, 0, paint);

        return result;
    }

    public static Bitmap paintTransparentImage(int alpha, Bitmap image) {
        if (image == null) return image;
        Bitmap result = Bitmap.createBitmap(image.getWidth(), image.getHeight(), Bitmap.Config.ARGB_8888);

        Paint paint = new Paint();
        paint.setAlpha(alpha);

        Canvas canvas = new Canvas(result);
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        canvas.drawBitmap(image, 0, 0, paint);

        return result;
    }

    public static Bitmap rotate(Bitmap source, int angle) {
        return rotate(source, angle, true);
    }

    public static Bitmap rotate(Bitmap source, int angle, boolean recycleSource) {
        Bitmap result;

        if (source != null && angle != 0) {
            Matrix matrix = new Matrix();
            matrix.postRotate(angle);
            result = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);

            if (recycleSource) {
                source.recycle();
            }

        } else {
            result = source;
        }

        return result;
    }

    public static Bitmap toGrayScale(Bitmap bmpOriginal) {

        if (bmpOriginal == null) return null;

        int width, height;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();

        Bitmap bmpGrayScale = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmpGrayScale);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmpOriginal, 0, 0, paint);
        return bmpGrayScale;
    }

    public static void shareImage(File file, Activity activity) {
        if (file != null && file.exists() && activity != null) {
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("image/*");
            Uri uri = Uri.fromFile(file);
            share.putExtra(Intent.EXTRA_STREAM, uri);
            if (IntentHelper.isIntentAvailable(activity, share)) {
                activity.startActivity(Intent.createChooser(share, null));
            }
        }
    }

    public static Rect getBitmapSize(File file) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        return new Rect(0, 0, options.outWidth, options.outHeight);
    }

    public static Bitmap decodeBitmap(String filePath, int reqWidth, int reqHeight) {
        try {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(filePath, options);
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeFile(filePath, options);
        } catch (Exception exc) {
            LogWriter.e(exc);
        }
        return null;
    }

    public static File getImageFileFromUri(Uri uri, String dirPath) {
        File file = null;
        if (ContentResolver.SCHEME_FILE.equals(uri.getScheme())) {
            file = getPathFromUri(uri);
            if (file != null && !file.exists()) {
                file = null;
            }
        }

        if (file == null) {
            ContentResolver contentResolver = ContextHelper.getApplicationContext().getContentResolver();
            InputStream stream = null;
            try {
                File sourceFile = new File(dirPath, UUID.randomUUID().toString());
                if (sourceFile.exists()) {
                    sourceFile.delete();
                }
                sourceFile.createNewFile();

                stream = contentResolver.openInputStream(uri);
                FileWriter.DEFAULT.write(stream, sourceFile);
                file = sourceFile;
            } catch (Exception exc) {
                LogWriter.e(exc);
            } finally {
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (IOException e) {
                        LogWriter.e(e);
                    }
                }
            }
        }

        if (file != null && !file.exists()) {
            file = null;
        }

        return file;
    }

    public static Bitmap combineImages(Bitmap... bitmaps) {
        Bitmap result = null;

        try {
            int width = 0, height = 0;

            for (Bitmap bitmap : bitmaps) {
                height = Math.max(bitmap.getHeight(), height);
                width += bitmap.getWidth();
            }
            width += bitmaps.length - 1;

            result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas comboImage = new Canvas(result);
            comboImage.drawColor(Color.BLACK, PorterDuff.Mode.CLEAR);

            int x = 0;
            for (Bitmap bitmap : bitmaps) {
                comboImage.drawBitmap(bitmap, x, (height - bitmap.getHeight()) / 2, null);
                x += bitmap.getWidth() + 1;
            }
        } catch (Exception exc) {
            LogWriter.e(exc);
        }

        return result;
    }

    public static File getPathFromUri(Uri uri) {
        File result = null;
        if (ContentResolver.SCHEME_FILE.equals(uri.getScheme())) {
            result = new File(uri.getPath());
            if (!result.exists()) {
                result = null;
                try {
                    String[] projection = {MediaStore.MediaColumns.DATA};
                    Cursor cursor = ContextHelper.getApplicationContext().getContentResolver().query(uri, projection, null, null, null);
                    if (cursor != null) {
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                        String filePath = cursor.getString(columnIndex);
                        cursor.close();
                        result = new File(filePath);
                    }
                } catch (Exception exc) {
                    LogWriter.e(exc);
                }
            }
        }
        return result;
    }
}
