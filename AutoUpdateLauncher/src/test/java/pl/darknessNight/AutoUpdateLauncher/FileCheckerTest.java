package pl.darknessNight.AutoUpdateLauncher;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class FileCheckerTest {
    static final String testData="sadfaf@L:KN* YDS #RK$JD)(FI$H N#$:(&$";
    static final String correctHash ="530d5da1f9b39d1d952294fde27649981b80d28a356af1a5ba4368dfbc7ab7be";
    static final String incorrectTestData ="Something not good!";
    static final String incorrectHash="78918521f9b39d1d952294fde27649981b80d28a356af1a5ba4368dfbc7ab810";
    static final String testFileName="TEST_FILECHECKER_FILE.txt";
    static final String incorrectTestFileName="BAD_TEST_FILECHECKER_FILE.txt";

    @BeforeAll
    static void prepareFile() throws IOException {
        File file=FileUtils.getFile(testFileName);
        FileUtils.writeStringToFile(file,testData,"UTF-8");

        file=FileUtils.getFile(incorrectTestFileName);
        FileUtils.writeStringToFile(file,incorrectTestData,"UTF-8");
    }

    @AfterAll
    static void deleteFile() throws IOException {
        File file=FileUtils.getFile(testFileName);
        FileUtils.forceDelete(file);

        file=FileUtils.getFile(incorrectTestFileName);
        FileUtils.forceDelete(file);
    }

    @Test
    void getChecksumOfInputStream_testDataFromMemory_returnsCorrectHash() throws IOException {
        byte[] data=testData.getBytes();
        InputStream stream=new ByteArrayInputStream(data);

        String checksum=FileChecker.GetChecksumOfInputStream(stream);

        stream.close();

        assertEquals(correctHash,checksum);
    }

    @Test
    void getChecksumOfInputStream_IncorrectDataFromMemory_returnsOtherHash() throws IOException {
        byte[] data=incorrectTestData.getBytes();
        InputStream stream=new ByteArrayInputStream(data);

        String checksum=FileChecker.GetChecksumOfInputStream(stream);

        stream.close();

        assertNotEquals(correctHash,checksum);
    }

    @Test
    void getChecksumOfFile_getDataFromDisc_returnCorrectHash() throws IOException {
        String checksum=FileChecker.GetChecksumOfFile(testFileName);

        assertEquals(correctHash,checksum);
    }

    @Test
    void getChecksumOfMemoryBlock_CorrectData_returnsCorrectHash() {
        byte[] data=testData.getBytes();

        String checksum=FileChecker.GetChecksumOfMemoryBlock(data);

        assertEquals(correctHash,checksum);
    }

    @Test
    void getChecksumOfMemoryBlock_otherData_returnsOtherHash() {
        byte[] data=incorrectTestData.getBytes();

        String checksum=FileChecker.GetChecksumOfMemoryBlock(data);

        assertNotEquals(correctHash,checksum);
    }

    @Test
    void compareInputStreamChecksum_CorrectDataAndCorrectHash_returnsTrue() throws IOException {
        byte[] data=testData.getBytes();
        InputStream stream=new ByteArrayInputStream(data);

        boolean result=FileChecker.CompareInputStreamChecksum(stream, correctHash);

        stream.close();

        assertTrue(result);
    }

    @Test
    void compareInputStreamChecksum_CorrectDataAndIncorrectHash_returnsFalse() throws IOException {
        byte[] data=testData.getBytes();
        InputStream stream=new ByteArrayInputStream(data);

        boolean result=FileChecker.CompareInputStreamChecksum(stream,incorrectHash);

        stream.close();

        assertFalse(result);
    }

    @Test
    void compareInputStreamChecksum_IncorrectDataAndCorrectHash_returnsFalse() throws IOException {
        byte[] data= incorrectTestData.getBytes();
        InputStream stream=new ByteArrayInputStream(data);

        boolean result=FileChecker.CompareInputStreamChecksum(stream, correctHash);

        stream.close();

        assertFalse(result);
    }

    @Test
    void compareFileChecksum_correctFileAndCorrectHash_returnsTrue() throws IOException {
        boolean result=FileChecker.CompareFileChecksum(testFileName, correctHash);
        assertTrue(result);
    }

    @Test
    void compareFileChecksum_correctFileAndIncorrectHash_returnsFalse() throws IOException {
        boolean result=FileChecker.CompareFileChecksum(testFileName,incorrectHash);
        assertFalse(result);
    }

    @Test
    void compareFileChecksum_incorrectFileAndCorrectHash_returnsFalse() throws IOException {
        boolean result=FileChecker.CompareFileChecksum(incorrectTestFileName, correctHash);
        assertFalse(result);
    }

    @Test
    void compareMemoryBlockChecksum_correctFileAndCorrectHash_returnsTrue() {
        byte[] data=testData.getBytes();

        boolean result=FileChecker.CompareMemoryBlockChecksum(data,correctHash);

        assertTrue(result);
    }

    @Test
    void compareMemoryBlockChecksum_correctFileAndIncorrectHash_returnsFalse() {
        byte[] data=testData.getBytes();

        boolean result=FileChecker.CompareMemoryBlockChecksum(data,incorrectHash);

        assertFalse(result);
    }

    @Test
    void compareMemoryBlockChecksum_incorrectFileAndCorrectHash_returnsFalse() {
        byte[] data=incorrectTestData.getBytes();

        boolean result=FileChecker.CompareMemoryBlockChecksum(data,correctHash);

        assertFalse(result);
    }

}