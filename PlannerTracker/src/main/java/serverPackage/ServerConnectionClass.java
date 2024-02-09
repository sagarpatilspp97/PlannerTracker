package serverPackage;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ServerConnectionClass extends Thread {
	private Date date = null;
	private static final String DIR_NAME = "ScreenShots";

	public void run() {
		while (true) {
			try {
				ServerSocket serverSocket = new ServerSocket(868);
				Socket server = serverSocket.accept();
				System.out.println("Connection Established");
				date = new Date();
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd 'at' HH.mm.ss");
//				BufferedImage img = ImageIO.read(ImageIO.createImageInputStream(server.getInputStream()));
//				ImageIO.write(img, ".png", new File("D:\\ScreenShots\\"  + dateFormat.format(date) + ".png"));
//				System.out.println("Image received!!!!");



				File givenfile = new File("C:\\Users\\HP\\Documents\\New folder\\" + dateFormat.format(date) + ".mp4");

				InputStream is = server.getInputStream();

				if ((givenfile.exists() == true) && (givenfile.canWrite() == false)) {

					System.out.println(
							"The File is already existing and currently it is read only mode. Now we are going to make it writable");
//					givenfile.setWritable(true);
//					givenfile.setReadable(isAlive());
					System.out.println("Writter is on now");
				}
				copyInputStreamToFile(is, givenfile);
				is.close();
				server.close();
				serverSocket.close();
			} catch (SocketTimeoutException st) {
				System.out.println("Socket timed out!");
				break;
			} catch (IOException e) {
				e.printStackTrace();
				break;
			} catch (Exception ex) {
				System.out.println(ex);
			}
		}
	}

	public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException, Exception {
		ServerConnectionClass serverApp = new ServerConnectionClass();
//		serverApp.createDirectory(DIR_NAME);
		Thread thread = new Thread(serverApp);
		thread.start();
	}

	private void createDirectory(String dirName) {
		File newDir = new File("D:\\" + dirName);
		if (!newDir.exists()) {
			boolean isCreated = newDir.mkdir();
		}
	}

	private static File copyInputStreamToFile(InputStream inputStream, File file) throws IOException {

		// append = false
		try (FileOutputStream outputStream = new FileOutputStream(file, true)) {
			int read;
			byte[] bytes = new byte[8192];
			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
			outputStream.flush();
			outputStream.close();
			return file;
		}

	}
}
