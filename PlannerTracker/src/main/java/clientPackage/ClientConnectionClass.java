package clientPackage;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Random;
import javax.imageio.ImageIO;
import videoCapturing.MonteScreenRecording;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.AWTException;
import org.monte.media.math.Rational;
import org.monte.media.Format;
import org.monte.media.FormatKeys.MediaType;
import org.monte.media.Registry;
import static org.monte.media.VideoFormatKeys.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;

import org.monte.screenrecorder.ScreenRecorder;

public class ClientConnectionClass implements Runnable {

	private static long nextTime = 0;
	private static ClientConnectionClass clientApp = null;
	boolean isConnected = false;

//	    private String serverName = "127.0.0.1"; //loop back ip
//	    private int portNo = 1729;
	// private Socket serverSocket = null;
	/**
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		clientApp = new ClientConnectionClass();
//	        clientApp.getNextFreq();

		while (true) {
			if (nextTime < System.currentTimeMillis()) {
				System.out.println(" get screen shot ");
				try {
					clientApp.sendScreen();
					clientApp.getNextFreq();
				} catch (AWTException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			// System.out.println(" statrted ....");
		}
//	        Thread thread = new Thread(clientApp);
//	        thread.start();
	}

	private void getNextFreq() {
		long currentTime = System.currentTimeMillis();
		Random random = new Random();
		long value = random.nextInt(100); // 1800000
		nextTime = currentTime + value;
		// return currentTime+value;
	}

	public void run() {

	}

	private void sendScreen() throws AWTException, IOException {
		Socket ClientSocket = new Socket("192.168.2.86", 868);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension dimensions = toolkit.getScreenSize();
		Robot robot = new Robot(); // Robot class
		BufferedImage screenshot = robot.createScreenCapture(new Rectangle(dimensions));
		ImageIO.write(screenshot, "png", ClientSocket.getOutputStream());
//		System.out.println("Screenshot taken");
//		try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		sendScreenRecording(ClientSocket);
	}

	private static void sendScreenRecording(Socket clientSocket) throws AWTException, IOException {
		try {
			MonteScreenRecording msr = new MonteScreenRecording(".mp4", new File("D:\\ScreenShots\\"));
			File f = msr.createMovieFile(new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey,
					ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE, CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
					DepthKey, 24, FrameRateKey, Rational.valueOf(15), QualityKey, 1.0f, KeyFrameIntervalKey, 15 * 60));
			msr.start();
			try {
				Thread.sleep(10000);
			} catch (Exception e) {

			}
			msr.stop();
			
			InputStream is = new FileInputStream(new File(f.getName()));
			byte[] bytes = new byte[1024];
			OutputStream stream = clientSocket.getOutputStream();

			int count = is.read(bytes, 0, 1024);
			while (count != -1) {
			    stream.write(bytes, 0, 1024);

			    count = is.read(bytes, 0, 1024);
			}                         
			is.close();
			stream.flush();
			stream.close(); 
			new File(f.getAbsolutePath()).delete();
			
//			OutputStream os = clientSocket.getOutputStream();
//			os.write(videoBytes);
//			os.flush();
			clientSocket.close();

			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
