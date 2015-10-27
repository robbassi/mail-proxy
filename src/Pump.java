import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;


public class Pump extends Thread {
	
	BufferedReader in;
	BufferedWriter out;

	public Pump (Socket source, Socket dest) {
		try {
			in =  new BufferedReader(new InputStreamReader(source.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(dest.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		String line;
		try {
			while ((line = in.readLine()) != null) {
				System.out.println(line);
				out.write(line + "\r\n");
				out.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}