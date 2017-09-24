package pl.darknessNight.AutoUpdateLauncher;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;
import java.io.OutputStream;

class LoginException extends RuntimeException{}

public class FTPClient {
    org.apache.commons.net.ftp.FTPClient ftpLowLevelClient = null;
    FTPClient(){
        ftpLowLevelClient =new org.apache.commons.net.ftp.FTPClient();
    }
    FTPClient(org.apache.commons.net.ftp.FTPClient client) {
        ftpLowLevelClient =client;
    }

    public void RetrieveFileFromUrl(String url, OutputStream stream) throws IOException{
        ConnectToServerFromUrl(url);
        String filename=getFilenameFromUrl(url);
        RetrieveFile(filename,stream);
        Disconnect();
    }

    private static String getFilenameFromUrl(String url){
        return new String();
    }

    public void ConnectToServerFromUrl(String url) throws IOException {
        String host=getHostFromUrl(url);
        String user=getUserFromUrl(url);
        String pass=getPassFromUrl(url);
        if(user.isEmpty())
            ConnectToServerAnonymousAndPrepareConnection(host);
        else
            ConnectToServerAndPrepareConnection(host,user,pass);
    }

    private static String getHostFromUrl(String url){
        return new String();
    }

    private static String getUserFromUrl(String url){
        return new String();
    }

    private static String getPassFromUrl(String url){
        return new String();
    }

    public void ConnectToServerAndPrepareConnection(String host, String user, String pwd) throws IOException {
        ConnectToServer(host);
        LoginToServer(user, pwd);
        PrepareConnection();
    }

    private void LoginToServer(String user, String pwd) throws IOException {
        if(!ftpLowLevelClient.login(user, pwd))
            throw new LoginException();
    }

    public void ConnectToServer(String host) throws IOException {
        int reply;
        ftpLowLevelClient.connect(host);
        reply = ftpLowLevelClient.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftpLowLevelClient.disconnect();
            throw new IOException("Exception in connecting to FTP Server");
        }
    }

    public void ConnectToServerAnonymousAndPrepareConnection(String host) throws IOException {
        ConnectToServer(host);
        PrepareConnection();
    }

    private void PrepareConnection() throws IOException {
        ftpLowLevelClient.setFileType(FTP.BINARY_FILE_TYPE);
        ftpLowLevelClient.enterLocalPassiveMode();
    }

    public void RetrieveFile(String remoteFilePath, OutputStream stream) throws IOException {
        this.ftpLowLevelClient.retrieveFile(remoteFilePath, stream);
    }

    public void Disconnect() {
        if (ftpLowLevelClient.isConnected()) {
            try {
                ftpLowLevelClient.logout();
            } catch (IOException f) {
                //pass
            }
            try {
                ftpLowLevelClient.disconnect();
            } catch (IOException f) {
                //pass
            }
        }
    }
}
