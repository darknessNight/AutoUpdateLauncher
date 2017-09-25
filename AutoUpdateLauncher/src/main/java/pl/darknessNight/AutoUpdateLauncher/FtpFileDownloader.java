package pl.darknessNight.AutoUpdateLauncher;

import java.io.*;


public class FtpFileDownloader extends FileDownloader {
    private FTPClient predefinedFTPClient=null;
    public FtpFileDownloader(){
        predefinedFTPClient=new FTPClient();
    }
    public FtpFileDownloader(FTPClient ftpClient){
        predefinedFTPClient=ftpClient;
    }

    @Override
    protected void downloadToStream(String url, OutputStream stream) {
        FTPClient client=predefinedFTPClient;
        try {
            client.RetrieveDataToStreamFromUrl(url,stream);
        } catch (IOException e) {
            throw new DownloadError(e);
        }
    }
}
