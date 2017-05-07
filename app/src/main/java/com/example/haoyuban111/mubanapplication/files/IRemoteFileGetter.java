package com.example.haoyuban111.mubanapplication.files;

public interface IRemoteFileGetter extends IFileGetter {
    String getUrl();

    void onLoadComplete();
}
