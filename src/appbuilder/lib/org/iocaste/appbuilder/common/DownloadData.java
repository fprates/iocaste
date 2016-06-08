package org.iocaste.appbuilder.common;

public class DownloadData {
    public String filename, contenttype, contentencoding;
    public byte[] content;
    
    public DownloadData() {
        contenttype = "application/octet-stream";
    }
}
