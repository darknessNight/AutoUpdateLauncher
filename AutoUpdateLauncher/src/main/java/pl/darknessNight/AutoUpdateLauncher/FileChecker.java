package pl.darknessNight.AutoUpdateLauncher;

import java.io.*;
import java.util.Objects;

public abstract class FileChecker {
    static public String GetChecksumOfInputStream(InputStream stream)throws IOException{
        byte[] hash = org.apache.commons.codec.digest.DigestUtils.sha256(stream);
        return binaryToHexString(hash);
    }

    static private String binaryToHexString(byte[] data){
        char[] hexEncodedHash=org.apache.commons.codec.binary.Hex.encodeHex(data);
        return new String(hexEncodedHash);
    }

    static public String GetChecksumOfFile(String filename) throws IOException{
        InputStream stream=new FileInputStream(filename);
        String result = GetChecksumOfInputStream(stream);
        stream.close();
        return result;
    }

    static public String GetChecksumOfMemoryBlock(byte[] data){
        byte[] hash = org.apache.commons.codec.digest.DigestUtils.sha256(data);
        return binaryToHexString(hash);
    }

    static public boolean CompareInputStreamChecksum(InputStream stream, String checksum)throws IOException{
        return Objects.equals(GetChecksumOfInputStream(stream), checksum);
    }

    static public boolean CompareFileChecksum(String filename, String checksum)throws IOException{
        return Objects.equals(GetChecksumOfFile(filename), checksum);
    }

    static public boolean CompareMemoryBlockChecksum(byte[] data, String checksum){
        return Objects.equals(GetChecksumOfMemoryBlock(data), checksum);
    }
}
