package com.example.haoyuban111.mubanapplication.files;

import java.io.File;
import java.io.InputStream;

public class RemoteFileArgs {

    private File _file;
    private String _url;
    private IRemoteFileGetter _fileGetter;
    private int _maxAge = -1;
    private int _revision = -1;
//    private Response _response;

    public RemoteFileArgs(IRemoteFileGetter fileGetter) {
        _url = fileGetter.getUrl();
        _fileGetter = fileGetter;
    }

    public RemoteFileArgs(String url, File file) {
        _url = url;
        _file = file;
    }

    protected RemoteFileArgs() {
    }

    protected RemoteFileArgs(String url) {
        _url = url;
    }

//    public Response getResponse() {
//        return _response;
//    }
//
//    public void setResponse(Response response) {
//        _response = response;
//    }

    public String getUrl() {
        return _url;
    }

    protected void setUrl(String url){
        _url = url;
    }

    public File getFile()    {
        if(_file == null && _fileGetter != null){
            _file = _fileGetter.getFile();
        }
        return _file;
    }

    public void setMaxAge(int maxAge){
        _maxAge = maxAge;
    }

    public int getMaxAge() {
        return _maxAge;
    }

    public boolean isValidFile(){
        final File file = getFile();
        return file != null && file.exists() && file.length() > 0;
    }

    public int getRevision() {
        return _revision;
    }

    public void setRevision(int revision) {
        _revision = revision;
    }

    public void setFileGetter(IRemoteFileGetter fileGetter){
        _fileGetter = fileGetter;
    }

    protected IFileGetter getFileGetter() {
        return _fileGetter;
    }

    public boolean save(InputStream stream){
       return getWriter().write(stream, getFile());
    }

    protected IFileWriter getWriter(){
        return FileWriter.DEFAULT;
    }

    public void onLoadComplete(){
        if(_fileGetter != null){
            _fileGetter.onLoadComplete();
        }
    }
}
