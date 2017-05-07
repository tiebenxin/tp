package com.example.haoyuban111.mubanapplication.help_class;

import android.graphics.drawable.BitmapDrawable;
import android.util.LruCache;

public class ImageCache {

    public static final ImageCache INSTANCE = new ImageCache();

    private LruCache<String, BitmapDrawable> _cache;

    private LruCache<String, BitmapDrawable> getCache() {
        if (_cache == null) {
            _cache = new LruCache<String, BitmapDrawable>((int) (Runtime.getRuntime().maxMemory() / 1024) / 8) {
                @Override
                protected int sizeOf(String key, BitmapDrawable value) {
                    return value.getBitmap().getByteCount() / 1024;
                }
            };
        }

        return _cache;
    }

    public void addBitmap(String key, BitmapDrawable bitmap) {
        getCache().put(key, bitmap);
    }

    public BitmapDrawable getBitmap(String key) {
        return getCache().get(key);
    }
}
