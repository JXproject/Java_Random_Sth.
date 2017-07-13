import java.awt.Dimension;
import java.awt.Point;
import java.util.Arrays;

public class AnimationMath {
	public static int w, h, r;
	private Point centre = new Point();
	private int[][] pixels;// 0-no color; 1-color;2-Dot

	AnimationMath(int width, int height) {
		w = width;
		h = height;
		pixels = new int[width][height];
		centre.setLocation(width / 2, height / 2);
		if (width >= height)
			r = height / 2 - 10;
		else
			r = width / 2 - 10;
	}

	public int[][] drawLineDotMoveOnly(double thelta, double dott) {

		thelta = Math.toRadians(thelta);

		double dot = Math.toRadians(dott);

		double Period = 1;

		int Dot = (int) (r * Math.cos(Period * dot));

		for (int i = 0; i < w; i++) {
			Arrays.fill(pixels[i], 0);
		}

		for (int i = 0; i <= r; i++) {

			int dh = (int) (Math.sin(thelta) * i);
			int dw = (int) (Math.cos(thelta) * i);

			pixels[centre.x + dw][centre.y + dh] = 1;
			pixels[centre.x - dw][centre.y - dh] = 1;

			if (Dot == i) {
				for (int a = 0; a < 8; a++) {
					for (int b = 0; b < 8; b++) {
						pixels[centre.x + dw - 4 + a][centre.y + dh - 4 + b] = 2;
					}
				}
			} else if (Dot == -i) {
				for (int a = 0; a < 8; a++) {
					for (int b = 0; b < 8; b++) {
						pixels[centre.x - dw - 4 + a][centre.y - dh - 4 + b] = 2;
					}
				}
			}
		}
		return pixels;
	}
	public int[][] drawDotMoveOnly(double thelta, double dott) {
		return drawDotMoveOnly( thelta, dott, new Dimension(10, 10));
	}
	
	public int[][] drawDotMoveOnly(double thelta, double dott, Dimension size) {
		int dotw = size.width, doth = size.height;

		thelta = Math.toRadians(thelta);

		double dot = Math.toRadians(dott);

		double Period = 1;

		int Dot = (int) (r * Math.cos(Period * dot));

		for (int i = 0; i < w; i++) {
			Arrays.fill(pixels[i], 0);
		}

		for (int i = 0; i <= r; i++) {

			int dh = (int) (Math.sin(thelta) * i);
			int dw = (int) (Math.cos(thelta) * i);

			if (Dot == i) {
				for (int a = 0; a < dotw; a++) {
					for (int b = 0; b < doth; b++) {
						pixels[centre.x + dw - dotw / 2 + a][centre.y + dh - doth / 2 + b] = 2;
					}
				}
			} else if (Dot == -i) {
				for (int a = 0; a < dotw; a++) {
					for (int b = 0; b < doth; b++) {
						pixels[centre.x - dw - dotw / 2 + a][centre.y - dh - doth / 2 + b] = 2;
					}
				}
			}
		}
		return pixels;
	}

}
