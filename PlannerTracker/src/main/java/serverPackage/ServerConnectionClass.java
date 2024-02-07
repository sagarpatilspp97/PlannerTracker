package serverPackage;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
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
//				DateFormat dateFormat = new SimpleDateFormat("_yyMMdd_HHmmss");
//				String fileName = server.getInetAddress().getHostName().replace(".", "-");
//				System.out.println(fileName);
//				BufferedImage img = ImageIO.read(ImageIO.createImageInputStream(server.getInputStream()));
//				ImageIO.write(img, "png", new File("D:\\ScreenShots\\" + fileName + dateFormat.format(date) + ".png"));
//				System.out.println("Image received!!!!");
				// lblimg.setIcon(img);
				InputStream is = server.getInputStream();
				byte[] buffer = new byte[1024];
//				int byteRead;
				FileOutputStream fos = new FileOutputStream("vedioRecord.MP4");
				while ((is.read(buffer)) != -1) {
					fos.write(buffer);
				}
				fos.close();
				is.close();
				serverSocket.close();
			} catch (SocketTimeoutException st) {
				System.out.println("Socket timed out!");
//createLogFile("[stocktimeoutexception]"+stExp.getMessage());
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
}
