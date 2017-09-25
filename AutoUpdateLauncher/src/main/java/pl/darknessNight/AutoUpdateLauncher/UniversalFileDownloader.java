package pl.darknessNight.AutoUpdateLauncher;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.OutputStream;
import java.nio.file.NotDirectoryException;
import java.util.HashMap;
import java.util.Map;

class ProtocolNotSupported extends RuntimeException {
    public ProtocolNotSupported() {
    }

    public ProtocolNotSupported(String protocol) {
        super("Protocol not supported: " + protocol);
    }
}

public class UniversalFileDownloader extends FileDownloader {
    Map<String, FileDownloader> fileDownloaderMap = new HashMap<>();

    @Override
    public void SetTempLocation(String path) throws NotDirectoryException {
        super.SetTempLocation(path);
        fileDownloaderMap.forEach((s, fileDownloader) -> {
            try {
                fileDownloader.SetTempLocation(path);
            } catch (NotDirectoryException e) {
                //pass
            }
        });
    }

    void AddFileDownloader(String protocol, FileDownloader fileDownloader) {
        fileDownloaderMap.put(protocol, fileDownloader);
        try {
            fileDownloader.SetTempLocation((tempLocation == null) ? null : tempLocation.getPath());
        } catch (NotDirectoryException e) {
            //pass
        }
    }

    void RemoveFileDownloader(String protocol) {
        fileDownloaderMap.remove(protocol);
    }

    @Override
    protected void downloadToStream(String url, OutputStream stream){
        throw new NotImplementedException();
    }

    @Override
    public byte[] DownloadFileToMemory(String url) {
        try {
            return fileDownloaderMap.get(getProtocolFromUrl(url)).DownloadFileToMemory(url);
        } catch (NullPointerException e) {
            throw new ProtocolNotSupported(getProtocolFromUrl(url));
        }
    }

    private String getProtocolFromUrl(String url) {
        return url.split(":")[0];
    }

    @Override
    public void DownloadFileToDisc(String url, String location) {
        try {
            fileDownloaderMap.get(getProtocolFromUrl(url)).DownloadFileToDisc(url, location);
        } catch (NullPointerException e) {
            throw new ProtocolNotSupported(getProtocolFromUrl(url));
        }
    }

    @Override
    public String DownloadFileToTemp(String url) {
        try {
            return fileDownloaderMap.get(getProtocolFromUrl(url)).DownloadFileToTemp(url);

        } catch (NullPointerException e) {
            throw new ProtocolNotSupported(getProtocolFromUrl(url));
        }
    }
}
