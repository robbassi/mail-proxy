import java.io.IOException;
import java.net.Socket;


public class FilterPump extends Pump {
	
	public FilterPump(Socket source, Socket dest) {
		super(source, dest);
	}

	@Override
	public void run() {
		boolean buffered = false;
		String line;
		int bufferedBytes = 0;
		String message = "";
		try {
			while ((line = in.readLine()) != null) {
				System.out.println(line);
				
				if (line.startsWith("* CAPABILITY")) {
					line = line.replace("COMPRESS=DEFLATE", "");
				}
				
				if (line.endsWith("}") && line.contains("BODY")) {
					buffered = true;
					message = "";
					bufferedBytes = Integer.parseInt(line.substring(line.lastIndexOf("{") + 1, line.length() - 1));
					out.write(line + "\r\n");
					out.flush();
				} else if (buffered) {
					message += line + "\r\n";
					bufferedBytes -= line.length() + 2;
					if (bufferedBytes <= 0) {
						buffered = false;
						
						// do scrubbing
						message = Scrubber.scrub(message);
						
						out.write(message);
						out.flush();
					}
				} else {
					out.write(line + "\r\n");
					out.flush();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}