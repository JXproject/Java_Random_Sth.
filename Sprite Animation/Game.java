import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.ImageGraphicAttribute;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {
	public static int width = 800;
	public static int height = width / 4 * 4;
	public static int scale = 1;
	public static int Quan_frames = 0;
	public static int Quan_updates = 0;
	public static double ticks = 0;
	public static final String TITLE = "Game";
	public static BufferedImage img = null,subs=null;
	public static int w, h;

	private Thread thread;// handle game
	private JFrame frame;
	private Screen screen;
	private boolean running = false;

	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

	public Game() {
		try {
			img = ImageIO.read(this.getClass().getResource("magic_002.png"));
		} catch (IOException e) {
			System.out.println("Error!!!");
			e.printStackTrace();
		}
		w = img.getWidth() / 5;
		h = img.getHeight() / 3;
		System.out.println(w + "|" + h);

		Dimension size = new Dimension(width * scale, height * scale);
		setPreferredSize(size);
		screen = new Screen(width, height);
		frame = new JFrame();
	}

	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Game");
		// handle this game, attached to the Game
		thread.start();
	}

	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.frame.setResizable(false);
		game.frame.setTitle(TITLE + " | " + Quan_updates + " UPS | " + Quan_frames + " FPS");
		game.frame.add(game);// add canvas component to the frame window
		game.frame.pack();// size up the frame to this game component
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);

		game.start();

	}

	@Override
	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		long timertick = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;

		Quan_frames = 0;
		Quan_updates = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;

			if (System.currentTimeMillis() - timertick > 1) {
				ticks += System.currentTimeMillis() - timertick;
				timertick = System.currentTimeMillis();
			}

			while (delta >= 1) {
				update();// hold logic
				Quan_updates++;
				delta--;
			}

			render();// render

			Quan_frames++;
			;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				frame.setTitle(TITLE + " | " + Quan_updates + " UPS | " + Quan_frames + " FPS");
				Quan_updates = 0;
				Quan_frames = 0;
			}
		}
		stop();
	}

	int iterw = 0, iterh = 0;

	public void update() {

		if (ticks % 3 == 0) {
//			System.out.println("0");
			iterw++;
		}
		if (iterw >= 4) {
			iterw = 0;
			iterh++;
		}
		if (iterh >= 2) {
			iterh = 0;
		}
		subs=img.getSubimage(iterw*w, iterh*h, w, h);
		
		// System.out.println("Update---!!!");
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);// triple buffering
			return;
		}
		screen.clear();
		screen.render();
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}

		Graphics g = bs.getDrawGraphics();
	
		g.drawImage(image, 0, 0, (int) (getWidth() * 1), (int) (getHeight() * 1), null);
		// Every time drawing, so we have to clean up the graphics
		g.dispose();
		bs.show();
	}

}
