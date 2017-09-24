package pl.darknessNight.AutoUpdateLauncher;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.stubbing.Answer;

import java.nio.file.NotDirectoryException;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class UniversalFileDownloaderTest {

    @Test
    void downloadFileToMemory_FakeDownloader_returnsFakeData() {
        byte[] data="TestData".getBytes();
        FileDownloader fakeDownloader=mock(FileDownloader.class);
        when(fakeDownloader.DownloadFileToMemory("Test:TestUrl")).thenReturn(data);

        UniversalFileDownloader fileDownloader=new UniversalFileDownloader();
        fileDownloader.AddFileDownloader("Test",fakeDownloader);

        byte[] result=fileDownloader.DownloadFileToMemory("Test:TestUrl");

        assertArrayEquals(data,result);
    }

    @Test
    void downloadFileToMemory_TwoFakeDownloaders_returnsCorrectData() {
        byte[] data="TestData".getBytes();
        byte[] correctData="CorrectData".getBytes();
        FileDownloader fakeDownloader=mock(FileDownloader.class);
        when(fakeDownloader.DownloadFileToMemory("Test:TestUrl")).thenReturn(data);
        FileDownloader correctDownloader=mock(FileDownloader.class);
        when(correctDownloader.DownloadFileToMemory("TestCorrect:TestUrl")).thenReturn(correctData);

        UniversalFileDownloader fileDownloader=new UniversalFileDownloader();
        fileDownloader.AddFileDownloader("Test",fakeDownloader);
        fileDownloader.AddFileDownloader("TestCorrect",correctDownloader);

        byte[] result=fileDownloader.DownloadFileToMemory("TestCorrect:TestUrl");

        assertArrayEquals(correctData,result);
    }

    @Test
    void downloadFileToMemory_TwoFakeDownloadersIntoOneProtocol_returnsCorrectData() {
        byte[] data="TestData".getBytes();
        byte[] correctData="CorrectData".getBytes();
        FileDownloader fakeDownloader=mock(FileDownloader.class);
        when(fakeDownloader.DownloadFileToMemory("Test:TestUrl")).thenReturn(data);
        FileDownloader correctDownloader=mock(FileDownloader.class);
        when(correctDownloader.DownloadFileToMemory("Test:TestUrl")).thenReturn(correctData);

        UniversalFileDownloader fileDownloader=new UniversalFileDownloader();
        fileDownloader.AddFileDownloader("Test",fakeDownloader);
        fileDownloader.AddFileDownloader("Test",correctDownloader);

        byte[] result=fileDownloader.DownloadFileToMemory("Test:TestUrl");

        assertArrayEquals(correctData,result);
    }

    @Test
    void downloadFileToMemory_TwoFakeDownloaders_returnsCorrectDataFromBoth() {
        byte[] data="TestData".getBytes();
        byte[] correctData="CorrectData".getBytes();
        FileDownloader fakeDownloader=mock(FileDownloader.class);
        when(fakeDownloader.DownloadFileToMemory("Test:TestUrl")).thenReturn(data);
        FileDownloader correctDownloader=mock(FileDownloader.class);
        when(correctDownloader.DownloadFileToMemory("TestCorrect:TestUrl")).thenReturn(correctData);

        UniversalFileDownloader fileDownloader=new UniversalFileDownloader();
        fileDownloader.AddFileDownloader("Test",fakeDownloader);
        fileDownloader.AddFileDownloader("TestCorrect",correctDownloader);

        byte[] result=fileDownloader.DownloadFileToMemory("TestCorrect:TestUrl");
        byte[] resultSecond=fileDownloader.DownloadFileToMemory("Test:TestUrl");

        assertArrayEquals(correctData,result);
        assertArrayEquals(data,resultSecond);
    }

    @Test
    void downloadFileToMemory_TwoFakeDownloadersAndOneRemoved_throws() {
        byte[] data="TestData".getBytes();
        byte[] correctData="CorrectData".getBytes();
        FileDownloader fakeDownloader=mock(FileDownloader.class);
        when(fakeDownloader.DownloadFileToMemory("Test:TestUrl")).thenReturn(data);
        FileDownloader correctDownloader=mock(FileDownloader.class);
        when(correctDownloader.DownloadFileToMemory("TestCorrect:TestUrl")).thenReturn(correctData);

        UniversalFileDownloader fileDownloader=new UniversalFileDownloader();
        fileDownloader.AddFileDownloader("Test",fakeDownloader);
        fileDownloader.AddFileDownloader("TestCorrect",correctDownloader);
        fileDownloader.RemoveFileDownloader("TestCorrect");

        assertThrows(ProtocolNotSupported.class,()->fileDownloader.DownloadFileToMemory("TestCorrect:TestUrl"));
    }

    @Test
    void downloadFileToTemp_FakeDownloader_returnsFakeData() {
        String tempFileName="TEMP_FILE";
        FileDownloader fakeDownloader=mock(FileDownloader.class);
        when(fakeDownloader.DownloadFileToTemp("Test:TestUrl")).thenReturn(tempFileName);

        UniversalFileDownloader fileDownloader=new UniversalFileDownloader();
        fileDownloader.AddFileDownloader("Test",fakeDownloader);

        String result=fileDownloader.DownloadFileToTemp("Test:TestUrl");

        assertEquals(tempFileName,result);
    }

    @Test
    void downloadFileToTemp_TwoFakeDownloaders_returnsCorrectData() {
        String data="TestData";
        String correctData="CorrectData";
        FileDownloader fakeDownloader=mock(FileDownloader.class);
        when(fakeDownloader.DownloadFileToTemp("Test:TestUrl")).thenReturn(data);
        FileDownloader correctDownloader=mock(FileDownloader.class);
        when(correctDownloader.DownloadFileToTemp("TestCorrect:TestUrl")).thenReturn(correctData);

        UniversalFileDownloader fileDownloader=new UniversalFileDownloader();
        fileDownloader.AddFileDownloader("Test",fakeDownloader);
        fileDownloader.AddFileDownloader("TestCorrect",correctDownloader);

        String result=fileDownloader.DownloadFileToTemp("TestCorrect:TestUrl");

        assertEquals(correctData,result);
    }

    @Test
    void downloadFileToTemp_TwoFakeDownloadersIntoOneProtocol_returnsCorrectData() {
        String data="TestData";
        String correctData="CorrectData";
        FileDownloader fakeDownloader=mock(FileDownloader.class);
        when(fakeDownloader.DownloadFileToTemp("Test:TestUrl")).thenReturn(data);
        FileDownloader correctDownloader=mock(FileDownloader.class);
        when(correctDownloader.DownloadFileToTemp("Test:TestUrl")).thenReturn(correctData);

        UniversalFileDownloader fileDownloader=new UniversalFileDownloader();
        fileDownloader.AddFileDownloader("Test",fakeDownloader);
        fileDownloader.AddFileDownloader("Test",correctDownloader);

        String result=fileDownloader.DownloadFileToTemp("Test:TestUrl");

        assertEquals(correctData,result);
    }

    @Test
    void downloadFileToTemp_TwoFakeDownloaders_returnsCorrectDataFromBoth() {
        String data="TestData";
        String correctData="CorrectData";
        FileDownloader fakeDownloader=mock(FileDownloader.class);
        when(fakeDownloader.DownloadFileToTemp("Test:TestUrl")).thenReturn(data);
        FileDownloader correctDownloader=mock(FileDownloader.class);
        when(correctDownloader.DownloadFileToTemp("TestCorrect:TestUrl")).thenReturn(correctData);

        UniversalFileDownloader fileDownloader=new UniversalFileDownloader();
        fileDownloader.AddFileDownloader("Test",fakeDownloader);
        fileDownloader.AddFileDownloader("TestCorrect",correctDownloader);

        String result=fileDownloader.DownloadFileToTemp("TestCorrect:TestUrl");
        String resultSecond=fileDownloader.DownloadFileToTemp("Test:TestUrl");

        assertEquals(correctData,result);
        assertEquals(data,resultSecond);
    }

    @Test
    void downloadFileToTemp_TwoFakeDownloadersAndOneRemoved_throws() {
        String data="TestData";
        String correctData="CorrectData";
        FileDownloader fakeDownloader=mock(FileDownloader.class);
        when(fakeDownloader.DownloadFileToTemp("Test:TestUrl")).thenReturn(data);
        FileDownloader correctDownloader=mock(FileDownloader.class);
        when(correctDownloader.DownloadFileToTemp("TestCorrect:TestUrl")).thenReturn(correctData);

        UniversalFileDownloader fileDownloader=new UniversalFileDownloader();
        fileDownloader.AddFileDownloader("Test",fakeDownloader);
        fileDownloader.AddFileDownloader("TestCorrect",correctDownloader);
        fileDownloader.RemoveFileDownloader("TestCorrect");

        assertThrows(ProtocolNotSupported.class,()->fileDownloader.DownloadFileToTemp("TestCorrect:TestUrl"));
    }

    @Test
    void downloadFileToDisc_FakeDownloader_returnsFakeData() {
        String fileLocation="FILE_LOCATION";
        FileDownloader fakeDownloader=mock(FileDownloader.class);

        UniversalFileDownloader fileDownloader=new UniversalFileDownloader();
        fileDownloader.AddFileDownloader("Test",fakeDownloader);

        fileDownloader.DownloadFileToDisc("Test:TestUrl",fileLocation);

        verify(fakeDownloader).DownloadFileToDisc(ArgumentMatchers.eq("Test:TestUrl"), ArgumentMatchers.eq(fileLocation));
    }

    @Test
    void downloadFileToDisc_TwoFakeDownloaders_returnsCorrectData() {
        String firstFileName="TestData";

        FileDownloader fakeDownloader=mock(FileDownloader.class);
        FileDownloader correctDownloader=mock(FileDownloader.class);

        UniversalFileDownloader fileDownloader=new UniversalFileDownloader();
        fileDownloader.AddFileDownloader("Test",fakeDownloader);
        fileDownloader.AddFileDownloader("TestCorrect",correctDownloader);

        fileDownloader.DownloadFileToDisc("TestCorrect:TestUrl",firstFileName);

        verify(correctDownloader).DownloadFileToDisc("TestCorrect:TestUrl",firstFileName);
    }

    @Test
    void downloadFileToDisc_TwoFakeDownloadersIntoOneProtocol_returnsCorrectData() {
        String firstFileName="TestData";
        FileDownloader fakeDownloader=mock(FileDownloader.class);
        FileDownloader correctDownloader=mock(FileDownloader.class);

        UniversalFileDownloader fileDownloader=new UniversalFileDownloader();
        fileDownloader.AddFileDownloader("Test",fakeDownloader);
        fileDownloader.AddFileDownloader("Test",correctDownloader);

        fileDownloader.DownloadFileToDisc("Test:TestUrl", firstFileName);

        verify(correctDownloader).DownloadFileToDisc("Test:TestUrl",firstFileName);
    }

    @Test
    void downloadFileToDisc_TwoFakeDownloaders_returnsCorrectDataFromBoth() {
        String firstFileName="TestData";
        FileDownloader fakeDownloader=mock(FileDownloader.class);
        FileDownloader correctDownloader=mock(FileDownloader.class);

        UniversalFileDownloader fileDownloader=new UniversalFileDownloader();
        fileDownloader.AddFileDownloader("Test",fakeDownloader);
        fileDownloader.AddFileDownloader("TestCorrect",correctDownloader);

        fileDownloader.DownloadFileToDisc("TestCorrect:TestUrl", firstFileName);
        fileDownloader.DownloadFileToDisc("Test:TestUrl", firstFileName);

        verify(correctDownloader).DownloadFileToDisc("TestCorrect:TestUrl",firstFileName);
        verify(fakeDownloader).DownloadFileToDisc("Test:TestUrl",firstFileName);
    }

    @Test
    void downloadFileToDisc_TwoFakeDownloadersAndOneRemoved_throws() {
        String firstFileName="TestData";
        FileDownloader fakeDownloader=mock(FileDownloader.class);
        FileDownloader correctDownloader=mock(FileDownloader.class);

        UniversalFileDownloader fileDownloader=new UniversalFileDownloader();
        fileDownloader.AddFileDownloader("Test",fakeDownloader);
        fileDownloader.AddFileDownloader("TestCorrect",correctDownloader);
        fileDownloader.RemoveFileDownloader("TestCorrect");

        assertThrows(ProtocolNotSupported.class,()->fileDownloader.DownloadFileToDisc("TestCorrect:TestUrl",firstFileName));
    }

    @Test
    void setTempLocation_IncorrectDir_throws(){
        UniversalFileDownloader fileDownloader=new UniversalFileDownloader();

        assertThrows(NotDirectoryException.class,()->fileDownloader.SetTempLocation("/dev/null/Test"));
    }

    @Test
    void setTempLocation_CorrectDir_PassToExistingChildDownloaders() throws NotDirectoryException {
        UniversalFileDownloader fileDownloader=new UniversalFileDownloader();

        FileDownloader firstFakeDownloader=mock(FileDownloader.class);
        FileDownloader secondFakeDownloader=mock(FileDownloader.class);

        fileDownloader.AddFileDownloader("Test",firstFakeDownloader);
        fileDownloader.AddFileDownloader("TestCorrect",secondFakeDownloader);

        fileDownloader.SetTempLocation("./");

        verify(firstFakeDownloader).SetTempLocation("./");
        verify(secondFakeDownloader).SetTempLocation("./");
    }

    @Test
    void setTempLocation_CorrectDir_PassToNewChildDownloaders() throws NotDirectoryException {
        UniversalFileDownloader fileDownloader=new UniversalFileDownloader();

        FileDownloader firstFakeDownloader=mock(FileDownloader.class);
        FileDownloader secondFakeDownloader=mock(FileDownloader.class);

        fileDownloader.SetTempLocation(".");

        fileDownloader.AddFileDownloader("Test",firstFakeDownloader);
        fileDownloader.AddFileDownloader("TestCorrect",secondFakeDownloader);

        verify(firstFakeDownloader).SetTempLocation(".");
        verify(secondFakeDownloader).SetTempLocation(".");
    }

}