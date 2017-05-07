package com.example.haoyuban111.mubanapplication.files;


import com.example.haoyuban111.mubanapplication.log.LogWriter;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.net.URLDecoder;

public abstract class RemoteFileGetter implements IRemoteFileGetter {

    private String _url;
    private File _dir;

    public RemoteFileGetter(String url, File dir) {
        try {
            _url = URLDecoder.decode(url, "utf-8");
        } catch (Exception e) {
            LogWriter.e(e);
            _url = url;
        }
        _dir = dir;
    }

    @Override
    public String getUrl() {
        return _url;
    }

    public File getDir() {
        return _dir;
    }

    @Override
    public File getFile() {
        return new File(getDir(), FilenameUtils.getName(getUrl()));
    }

    public static File getFile(String url, File dir){
        String path;
        try {
            path = URLDecoder.decode(url, "utf-8");
        } catch (Exception e) {
            LogWriter.e(e);
            path = url;
        }
        return new File(dir, FilenameUtils.getName(path));
    }

    public void onLoadComplete(){

    }
}


