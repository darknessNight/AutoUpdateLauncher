package pl.darknessNight.AutoUpdateLauncher;

import org.apache.commons.io.FileUtils;

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
    public byte[] DownloadFileToMemory(String url){
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        downloadToStream(url, stream);
        byte[] result = stream.toByteArray();
        try {
            stream.close();
        } catch (IOException e) {
            throw new DownloadError(e);
        }
        return result;
    }

    private void downloadToStream(String url, OutputStream stream) {
        FTPClient client=predefinedFTPClient;
        try {
            client.RetrieveFileFromUrl(url,stream);
        } catch (IOException e) {
            throw new DownloadError(e);
        }
    }

    @Override
    public void DownloadFileToDisc(String url, String location) {
        String tempFile=DownloadFileToTemp(url);
        try {
            FileUtils.copyFile(new File(tempFile),new File(location));
        } catch (IOException e) {
            throw new DownloadError(e);
        }
    }

    @Override
    public String DownloadFileToTemp(String url) {
        try {
            File tempFile=super.CreateTempFile();
            FileOutputStream stream=new FileOutputStream(tempFile);
            downloadToStream(url,stream);
            return tempFile.getAbsolutePath();
        } catch (IOException e) {
            throw new DownloadError(e);
        }
    }
}
