package MultiClients;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String args[]) throws IOException, InterruptedException {

		while (true) {
			ServerSocket ss = new ServerSocket(11111);
			System.out.println("Server is Awaiting");
			Socket s = ss.accept();
			Multi t = new Multi(s);
			t.start();

			Thread.sleep(2000);
			ss.close();
		}

	}
}