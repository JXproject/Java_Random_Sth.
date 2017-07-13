import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

//Class display pixels
public class Screen {
	private int width, height;
	public int[] pixels, bitmap;
	public int wP = 0, hP = 0;
	private double forward, right, cosine, sine, up;

	public Screen(int width, int height) {
		this.height = height;
		this.width = width;
		pixels = new int[width * height];
		zBuffer = new double[width * height];
		bitmap = getBitTexture("AA.png");
	}

	public void render(Controller control) {
		// Drawing and rendering here

		floor(control);
//		renderWall(0, 0.5, 1.5,1.5, 0);
		renderDistanceLimiter();
		// Pixel
		// for(int y=0;y<height;y++){
		// for(int x=0;x<width;x++){
		// pixels[x+y*width]=0xff00ff;
		// }
		// }
	}

	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;// clear to black
		}
	}

	public void floor(Controller control) {

		double floorPosition = 8;
		double ceillingPosition = 8;

		forward = control.z;
		right = control.x;
		double rotation = control.rotation;
		up = control.y;

		cosine = Math.cos(rotation);
		sine = Math.sin(rotation);

		for (int y = 0; y < height; y++) {
			double ceiling = (y + -height / 2.0) / height;
			double z = (floorPosition + up) / ceiling;
		
			if (ceiling < 0)
				z = (ceillingPosition - up) / -ceiling;

			for (int x = 0; x < width; x++) {
				double Depth = (x - width / 2.0) / height;
				Depth *= z;
				double xx = Depth * cosine + z * sine;
				double yy = z * cosine - Depth * sine;
				int xP = (int) (xx + right);
				int yP = (int) (yy + forward);
				zBuffer[x + y * width] = z;
				pixels[x + y * width] = bitmap[(xP & (wP - 1)) + (yP & (hP - 1)) * wP];
				if (z > 200) {
					pixels[x + y * width] = 0;
				}
			}
		}
	}

	public void renderWall(double xLeft, double xRight, double zDistanceR, double zDistanceL, double yHeight) {
		double upCorrect=0.062;
		double rightCorrect=0.062;
		double forwardCorrect=0.062;
		
		double xcLeft = ((xLeft) - right* rightCorrect) * 2;
		double zcLeft = ((zDistanceL) - forward* forwardCorrect) * 2;

		double rotLeftSideX = xcLeft * cosine - zcLeft * sine;
		double yCornerTL = ((-yHeight) + up*upCorrect) * 2;
		double yCornerBL = ((0.5 - yHeight) + up*upCorrect) * 2;
		double rotLeftSideZ = zcLeft * cosine + xcLeft * sine;

		// right side
		double xcRight = ((xRight) - right*rightCorrect) * 2;
		double zcRight = ((zDistanceR) - forward*forwardCorrect) * 2;

		double rotRightSideX = xcRight * cosine - zcRight * sine;
		double yCornerTR = ((-yHeight) + up*upCorrect) * 2;
		double yCornerBR = ((0.5 - yHeight) + up*upCorrect) * 2;
		double rotRightSideZ = zcRight * cosine + xcRight * sine;

		double xPL = (rotLeftSideX / rotLeftSideZ * height + width / 2);// xPixelLeft
		double xPR = (rotRightSideX / rotRightSideZ * height + width / 2);// xPixeRight

		if (xPL >= xPR)
			return;

		int xPL_int = (int) (xPL);
		int xPR_int = (int) (xPR);

		if (xPL_int < 0) {
			xPL_int = 0;
		}
		if (xPR_int > width) {
			xPR_int = width;
		}

		double yPLT = (yCornerTL / rotLeftSideZ * height + height / 2);// yPixelLeftTop
		double yPLB = (yCornerBL / rotLeftSideZ * height + height / 2);// yPixelLeftBot
		double yPRT = (yCornerTR / rotRightSideZ * height + height / 2);// yPixelRightTop
		double yPRB = (yCornerBR / rotRightSideZ * height + height / 2);// yPixelRightBot

		double tex1 = 1 / rotLeftSideZ;
		double tex2 = 1 / rotRightSideZ;
		double tex3 = 0 / rotRightSideZ;
		double tex4 = 8 / rotRightSideZ - tex3;

		for (int x = xPL_int; x < xPR_int; x++) {
			double pixelRot = (x - xPL) / (xPR - xPL);
			double yPixelTop = yPLT + (yPRT - yPLT) * pixelRot;
			double yPixelBot = yPLB + (yPRB - yPLB) * pixelRot;

			int xTexture = (int) ((tex3 + tex4 * pixelRot) / (tex1 + (tex2 - tex1) * pixelRot));

			int yPixT_int = (int) (yPixelTop);
			int yPixB_int = (int) (yPixelBot);

			if (yPixT_int < 0)
				yPixT_int = 0;
			if (yPixB_int > height) {
				yPixB_int = height;
			}
			for (int y = yPixT_int; y < yPixB_int; y++) {
				double pixRotY = (y - yPixelTop) / (yPixelBot - yPixelTop);
				int yTexture = (int) (8 * pixRotY);
				pixels[x + y * width] = xTexture * 100 + yTexture * 100 * 256;
				zBuffer[x + y * width] = 1/(tex1+(tex2-tex1)*pixelRot)*8;
			}
		}
	}

	public void walls() {
		Random random = new Random(100);
		for (int i = 0; i < 20000; i++) {
			double xx = random.nextDouble();
			double yy = random.nextDouble();
			double zz = 1.5 - forward / 16;

			int xP = (int) (xx / zz * height / 2 + width / 2);
			int yP = (int) (yy / zz * height / 2 + height / 2);
			if (xP >= 0 && yP >= 0 && xP < width && yP < height) {
				pixels[xP + yP * width] = 0xfffff;
			}
		}
	}

	public double[] zBuffer;
	private double renderDistance = 5000;

	public void renderDistanceLimiter() {
		for (int i = 0; i < width * height; i++) {
			int color = pixels[i];
			int brightness = (int) (renderDistance / zBuffer[i]);

			if (brightness < 0) {
				brightness = 0;
			}
			if (brightness > 255) {
				brightness = 255;
			}

			int r = (color >> 16) & 0xff;
			int g = (color >> 8) & 0xff;
			int b = (color) & 0xff;

			r = r * brightness / 255;
			g = g * brightness / 255;
			b = b * brightness / 255;

			pixels[i] = r << 16 | g << 8 | b;
		}
	}

	public int[] getBitTexture(String Name) {
		int[] pixel = null;
		BufferedImage image = null;
		try {
			image = ImageIO.read(this.getClass().getResource(Name));
			int w = image.getWidth();
			int h = image.getHeight();
			pixel = new int[w * h];
			image.getRGB(0, 0, w, h, pixel, 0, w);
			wP = w;
			hP = h;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Nah files-crashed");
			e.printStackTrace();
		}
		return pixel;
	}
}
