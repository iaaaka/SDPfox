package painter;

import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class ColorPanel extends JPanel {
	private double[][] data;

	public ColorPanel(double[][] Data) {
		data = new double[Data.length][];
		double max = Data[0][0], min = max;
		for (int i = 0; i < Data.length; i++) {
			for (int j = 0; j < Data[i].length; j++) {
				max = Math.max(max, Data[i][j]);
				min = Math.min(min, Data[i][j]);
			}
		}
		for (int i = 0; i < Data.length; i++) {
			data[i] = new double[Data[i].length];
			for (int j = 0; j < Data[i].length; j++) {
				data[i][j] = (Data[i][j] - min) / (max - min);
			}
		}

	}

	private static Color getColor(double f) {
		return getColor((float) f);
	}

	private static Color getColor(float f) {
		int r = (int) (255 * f);
		int b = (int) (255 * (1 - f));
		return new Color(r, b, 0);
	}

	public static void saveImageJPG(String fname, int size, double[][] data) {
		double max = data[0][0];
		double min = data[0][0];
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data.length; j++) {
				max = Math.max(max, data[i][j]);
				min = Math.min(min, data[i][j]);
			}
		}
		BufferedImage img = new BufferedImage(size * data.length, size
				* data.length, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D) img.getGraphics();

		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data.length; j++) {
				g.setColor(getColor((data[i][j] - min) / (max - min)));
				g.fillRect(i * size, j * size, size, size);
			}
		}

		try {
			OutputStream os = new FileOutputStream(fname + ".png");

			ImageIO.write(img, "png", os);
			os.close();
		} catch (Exception e) {
		}
	}

	public void paintComponent(Graphics gr) {
		super.paintComponent(gr);
		Graphics2D g = (Graphics2D) gr;
		int sizeX = 10000 / data.length;
		int sizeY = 10000 / data[0].length;
		g.setTransform(AffineTransform.getScaleInstance(
				(double) getWidth() / 10000, (double) getHeight() / 10000));
		if (data == null || data.length == 0)
			return;

		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[0].length; j++) {
				gr.setColor(getColor(data[i][j]));
				gr.fillRect(i * sizeX, j * sizeY, sizeX, sizeY);
			}
		}
	}

}
