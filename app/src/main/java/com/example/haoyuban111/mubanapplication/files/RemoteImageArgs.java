package com.example.haoyuban111.mubanapplication.files;

public class RemoteImageArgs extends RemoteFileArgs {

    private boolean _replaceExisting;

    public RemoteImageArgs(String url) {
        super(url);
    }

    public RemoteImageArgs(IRemoteFileGetter fileGetter) {
        super(fileGetter);
    }

    public boolean isReadOnly() {
        return getFileGetter() == null;
    }


    public String getKeyCache() {
        return getUrl();
    }


    public boolean isReplaceExisting() {
        return _replaceExisting;
    }

    public RemoteImageArgs setReplaceExisting(boolean replaceExisting) {
        _replaceExisting = replaceExisting;
        return this;
    }
}
