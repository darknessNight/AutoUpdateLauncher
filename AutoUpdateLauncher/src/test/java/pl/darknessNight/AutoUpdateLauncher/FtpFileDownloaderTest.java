package pl.darknessNight.AutoUpdateLauncher;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FtpFileDownloaderTest {
    static class FakeFTPClient extends FTPClient{
        @Override
        public void RetrieveDataToStreamFromUrl(String url, OutputStream stream) throws IOException {
            stream.write(url.getBytes());
        }
    }

    @BeforeAll
    static void PrepareForCases(){
        File file=new File("./FileLocation.txt");
        if(file.exists()) file.delete();
    }

    @Test
    void downloadFileToMemory_fakeFTPClient_returnsCorrectBytes() throws IOException {
        FTPClient fakeFTPClient= Mockito.spy(FakeFTPClient.class);
        String testAddress="TestAddress";
        FtpFileDownloader fileDownloader=new FtpFileDownloader(fakeFTPClient);

        byte[] result=fileDownloader.DownloadFileToMemory(testAddress);

        verify(fakeFTPClient).RetrieveDataToStreamFromUrl(ArgumentMatchers.eq(testAddress), ArgumentMatchers.any());
        assertArrayEquals(testAddress.getBytes(),result);
    }

    @Test
    void downloadFileToMemory_fakeFTPClientThrowingException_throwDownloadError() throws IOException {
        FTPClient fakeFTPClient= Mockito.mock(FTPClient.class);
        String testAddress="TestAddress";
        FtpFileDownloader fileDownloader=new FtpFileDownloader(fakeFTPClient);

        Mockito.doThrow(IOException.class).when(fakeFTPClient).RetrieveDataToStreamFromUrl(Mockito.any(String.class),Mockito.any(OutputStream.class));

        assertThrows(DownloadError.class,()->fileDownloader.DownloadFileToMemory(testAddress));
    }

    @Test
    void downloadFileToDisc_fakeFTPClient_returnsCorrectBytes() throws IOException {
        FTPClient fakeFTPClient= Mockito.spy(FakeFTPClient.class);
        String testAddress="TestAddress";
        FtpFileDownloader fileDownloader=new FtpFileDownloader(fakeFTPClient);

        fileDownloader.DownloadFileToDisc(testAddress,"./FileLocation.txt");

        File file=new File("./FileLocation.txt");
        file.deleteOnExit();

        verify(fakeFTPClient).RetrieveDataToStreamFromUrl(ArgumentMatchers.eq(testAddress), ArgumentMatchers.any());
        assertTrue(file.exists() && file.isFile());
        assertArrayEquals(testAddress.getBytes(), Files.readAllBytes(file.toPath()));
    }

    @Test
    void downloadFileToDisc_fakeFTPClientThrowingException_throwDownloadError() throws IOException {
        FTPClient fakeFTPClient= Mockito.spy(FakeFTPClient.class);
        String testAddress="TestAddress";
        FtpFileDownloader fileDownloader=new FtpFileDownloader(fakeFTPClient);

        Mockito.doThrow(IOException.class).when(fakeFTPClient).RetrieveDataToStreamFromUrl(Mockito.any(String.class),Mockito.any(OutputStream.class));

        File file=new File("./FileLocation2.txt");

        assertThrows(DownloadError.class,()->fileDownloader.DownloadFileToDisc(testAddress,"./FileLocation2.txt"));
        assertFalse(file.exists());
    }



    @Test
    void downloadFileToTemp_fakeFTPClient_returnsCorrectBytes() throws IOException {
        FTPClient fakeFTPClient= Mockito.spy(FakeFTPClient.class);
        String testAddress="TestAddress";
        FtpFileDownloader fileDownloader=new FtpFileDownloader(fakeFTPClient);

        String tempFilePath=fileDownloader.DownloadFileToTemp(testAddress);

        File file=new File(tempFilePath);
        file.deleteOnExit();

        verify(fakeFTPClient).RetrieveDataToStreamFromUrl(ArgumentMatchers.eq(testAddress), ArgumentMatchers.any());
        assertTrue(file.exists() && file.isFile());
        assertArrayEquals(testAddress.getBytes(), Files.readAllBytes(file.toPath()));
    }

    @Test
    void downloadFileToTemp_fakeFTPClientThrowingException_throwDownloadError() throws IOException {
        FTPClient fakeFTPClient= Mockito.spy(FakeFTPClient.class);
        String testAddress="TestAddress";
        FtpFileDownloader fileDownloader=new FtpFileDownloader(fakeFTPClient);

        Mockito.doThrow(IOException.class).when(fakeFTPClient).RetrieveDataToStreamFromUrl(Mockito.any(String.class),Mockito.any(OutputStream.class));

        assertThrows(DownloadError.class,()->fileDownloader.DownloadFileToTemp(testAddress));
    }

    @Test
    void FTPFileDownloader_DefaultConstructorTest_NoThrows(){
        new FtpFileDownloader();
    }

}