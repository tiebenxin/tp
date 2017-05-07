package com.vipbcw.netroid.toolbox;

import java.io.File;
import java.util.Map;

/**
 * @author zhaotengfei
 * @version 1.0.0
 * @created 2016-4-20
 */
public interface MultiPartRequest {

    public void addFileUpload(String param, File file);
    
    public void addStringUpload(String param, String content);
    
    public Map<String,File> getFileUploads();
    
    public Map<String,String> getStringUploads(); 
}