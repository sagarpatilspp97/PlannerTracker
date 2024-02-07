package videoCapturing;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.AWTException;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;

import org.jcodec.api.awt.AWTSequenceEncoder;

public class ScreenRecorder {
	public static void makeVideoFromImages(List<BufferedImage> imageList, File file) throws IOException {

		AWTSequenceEncoder sequenceEncoder = AWTSequenceEncoder.createSequenceEncoder(file, 25);
		for (int i = 0; i < imageList.size(); i++) {
			sequenceEncoder.encodeImage(imageList.get(i));
			System.out.println("list encode " + i);

		}
		sequenceEncoder.finish();

	}

	public static void main(String[] args) throws AWTException, IOException {

		List<BufferedImage> imageList = new ArrayList<BufferedImage>();

		Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		Robot robot = new Robot();

		File file = new File("D:\\ScreenShots\\outputVideo.mp4");

		System.out.println("getting screen images...");

		int count = 0;

		while (count < 100) {

			BufferedImage image = robot.createScreenCapture(screenRect);
			imageList.add(image);

			count++;

		}

		makeVideoFromImages(imageList, file);

	}
}
