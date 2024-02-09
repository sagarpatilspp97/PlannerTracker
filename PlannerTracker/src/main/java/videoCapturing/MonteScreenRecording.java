package videoCapturing;

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
import org.monte.screenrecorder.ScreenRecorder;

public class MonteScreenRecording extends ScreenRecorder {

	private File movieFolder;
	private String name;

	public MonteScreenRecording(String name, File movieFolder) throws IOException, AWTException {

		super(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration(),

				new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI),
				new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
						CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE, DepthKey, 24, FrameRateKey,
						Rational.valueOf(15), QualityKey, 1.0f, KeyFrameIntervalKey, 15 * 60),
				new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black", FrameRateKey, Rational.valueOf(30)),
				null);
		/* output format for audio - null == no audio */
		this.movieFolder = movieFolder;
		this.name = name;
	}

	@Override
	public File createMovieFile(Format fileFormat) throws IOException {
		if (!movieFolder.exists()) {
			movieFolder.mkdirs();
		} else if (!movieFolder.isDirectory()) {
			throw new IOException("\"" + movieFolder + "\" is not a directory.");
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd 'at' HH.mm.ss");

		File f = new File(movieFolder, //
				 dateFormat.format(new Date()) + "."
						+ Registry.getInstance().getExtension(fileFormat));
		return f;
	}

//	public static void main(String[] args) throws IOException, AWTException {
//		MonteScreenRecording msr = new MonteScreenRecording(".mp4", new File("D:\\ScreenShots\\"));
//		msr.createMovieFile(new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
//				CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE, DepthKey, 24, FrameRateKey,
//				Rational.valueOf(15), QualityKey, 1.0f, KeyFrameIntervalKey, 15 * 60));
//		msr.start();
//		try {
//			Thread.sleep(10000);
//		} catch (Exception e) {
//
//		}
//		msr.stop();
//	}
	
	

}
