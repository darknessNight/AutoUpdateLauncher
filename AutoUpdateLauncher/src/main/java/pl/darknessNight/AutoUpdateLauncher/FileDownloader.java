package pl.darknessNight.AutoUpdateLauncher;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.file.NotDirectoryException;

class DownloadError extends RuntimeException{
    DownloadError(Exception previous){
        super(previous);
    }
}

public abstract class FileDownloader {
    protected File tempLocation = null;


    public void SetTempLocation(String location) throws NotDirectoryException {
        tempLocation = ThrowIfNotDir(location);
    }

    private File ThrowIfNotDir(String location) throws NotDirectoryException {
        File file = new File(location);
        if (!file.isDirectory())
            throw new NotDirectoryException(location);
        return file;
    }

    protected abstract void downloadToStream(String url, OutputStream stream);

    public byte[] DownloadFileToMemory(String url){
        try (ByteArrayOutputStream stream=new ByteArrayOutputStream()){
            downloadToStream(url, stream);
            return stream.toByteArray();
        } catch (IOException e) {
            throw new DownloadError(e);
        }
    }

    public void DownloadFileToDisc(String url, String location) {
        String tempFile=DownloadFileToTemp(url);
        try {
            FileUtils.copyFile(new File(tempFile),new File(location));
        } catch (IOException e) {
            throw new DownloadError(e);
        }
    }

    public String DownloadFileToTemp(String url) {
        try {
            File tempFile=CreateTempFile();
            FileOutputStream stream=new FileOutputStream(tempFile);
            downloadToStream(url,stream);
            return tempFile.getAbsolutePath();
        } catch (IOException e) {
            throw new DownloadError(e);
        }
    }

    protected File CreateTempFile() throws IOException {
        File tempFile = File.createTempFile("AutoUpdateFile", ".tmp", tempLocation);
        tempFile.deleteOnExit();
        return tempFile;
    }
}
