package pl.darknessNight.AutoUpdateLauncher;

import java.io.File;
import java.io.IOException;
import java.nio.file.NotDirectoryException;

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

    protected File CreateTempFile() throws IOException {
        File tempFile = File.createTempFile("AutoUpdateFile", ".tmp", tempLocation);
        tempFile.deleteOnExit();
        return tempFile;
    }

    public abstract byte[] DownloadFileToMemory(String url);

    public abstract void DownloadFileToDisc(String url, String location);

    public abstract String DownloadFileToTemp(String url);
}
