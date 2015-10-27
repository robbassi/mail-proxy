import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.net.ssl.SSLSocketFactory;


public class AsyncProxy {
	public static void main(String args[]) {
		try {
			System.setProperty("javax.net.ssl.trustStore", "/home/rob/.keystore");
			System.setProperty("javax.net.ssl.trustStorePassword", "abc123");
			
			@SuppressWarnings("resource")
			ServerSocket server = new ServerSocket(14300);
		    SSLSocketFactory ssf = (SSLSocketFactory) SSLSocketFactory.getDefault();

		    while (true) {
				Socket client = server.accept();
				Socket gmail = ssf.createSocket("imap.gmail.com", 993);
				
				(new Handler(gmail, client)).start();
		    }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
