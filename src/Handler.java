import java.net.Socket;


public class Handler extends Thread {
	
	Pump serverPump;
	Pump clientPump;
	
	public Handler (Socket server, Socket client) {
		serverPump = new FilterPump(server, client);
		clientPump = new Pump(client, server);
	}
	
	@Override
	public void run() {
		serverPump.start();
		clientPump.start();
		
		try {
			serverPump.join();
			clientPump.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}