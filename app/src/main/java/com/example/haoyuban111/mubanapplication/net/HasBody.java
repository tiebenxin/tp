package com.example.haoyuban111.mubanapplication.net;

import android.support.annotation.NonNull;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by haoyuban111 on 2016/12/16.
 */

public interface HasBody<R> {
    R requestBody(@NonNull RequestBody requestBody);

    R params(String key, File file);

    R addFileParams(String key, List<File> files);

    R addFileWrapperParams(String key, List<HttpParams.FileWrapper> fileWrappers);

    R params(String key, File file, String fileName);

    R params(String key, File file, String fileName, MediaType contentType);

    R upString(String string);

    R upJson(String json);

    R upBytes(byte[] bs);
}
