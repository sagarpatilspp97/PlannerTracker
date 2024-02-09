package MultiClients;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Multi extends Thread {
	private Socket s = null;
	DataInputStream infromClient;

	Multi() throws IOException {
	}

	Multi(Socket s) throws IOException {
		this.s = s;
		infromClient = new DataInputStream(s.getInputStream());
	}

	public void run() {

		String SQL = new String();
		try {
			SQL = infromClient.readUTF();
		} catch (IOException ex) {
			Logger.getLogger(Multi.class.getName()).log(Level.SEVERE, null, ex);
		}
		System.out.println("Query: " + SQL);
		try {
			System.out.println("Socket Closing");
			s.close();
		} catch (IOException ex) {
			Logger.getLogger(Multi.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}