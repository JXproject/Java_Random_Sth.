import java.awt.Color;
import java.awt.Dimension;

//Class display pixels
public class Screen {
	private int width, height;
	public int[] pixels;
	public int timer = 0;

	public Screen(int width, int height) {
		this.height = height;
		this.width = width;
		pixels = new int[width * height];
	}

	public void render() {
		// System.out.println("R---!!!");
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				pixels[x + y * width] = 0xf5f5f5;
			}
		}

	}

	public void applyPixels(int[] temp) {
		for (int i = 0; i < temp.length; i++) {
			if (temp[i] == 1) {
				pixels[i] = 0;
			} else if (temp[i] > 1) {
				// pixels[i] = ColorLighter(70, 10 + 5).hashCode();
				pixels[i] = ColorLighter(70, 10 + 1 * temp[i]).hashCode();
			} else
				pixels[i] = 0xf5f5f5;
		}
	}

	int length = 1;
	int timerL = 0;

	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;// clear to black
		}
	}

	public static Color ColorLighter(int ampp, int shiftCC) {
		int Frequency = 0;
		int center = 255 - ampp;
		int Y1 = (int) (Math.sin(Frequency + shiftCC) * ampp + center);
		// sin max=1 min=-1;begin at 0;neutral color: 128;
		// int v = (int) (Math.cos (frequency * a) * amp + center)
		// cos max=1;min=-1;begin at 1;start from 255;
		int Y2 = (int) (Math.sin(Frequency + 2 + shiftCC) * ampp + center);
		int Y3 = (int) (Math.sin(Frequency + 4 + shiftCC) * ampp + center);
		Color vvv1 = new Color(Y1, Y2, Y3);
		return (vvv1);
	}
}
