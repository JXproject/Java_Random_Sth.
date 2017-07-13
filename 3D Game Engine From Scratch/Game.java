import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {
	public static Game game;
	public static int width = 900;
	public static int height = width /16 * 9;
	public static int scale = 1;
	public static int Quan_frames = 0;
	public static int Quan_updates = 0;
	public static final String TITLE = "Game";
	public static long ticks = 0;

	private Thread thread;// handle game
	private JFrame frame;
	private Screen screen;
	private boolean running = false;

	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

	public Game() {
		Dimension size = new Dimension(width * scale, height * scale);
		setPreferredSize(size);
		screen = new Screen(width, height);
		frame = new JFrame();

		input = new InputHandler();
		addKeyListener(input);
		addFocusListener(input);
		addMouseListener(input);
		addMouseMotionListener(input);
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
		game = new Game();
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
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;

		Quan_frames = 0;
		Quan_updates = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			requestFocus();// Activate automatically
			while (delta >= 1) {
				update();// hold logic
				Quan_updates++;
				delta--;
			}
			render();// render

			// UPS and FRS counter
			Quan_frames++;
			if (System.currentTimeMillis() - timer >= 1)
				ticks++;
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				FPS = Quan_frames;
				frame.setTitle(TITLE + " | " + Quan_updates + " UPS | " + Quan_frames + " FPS");
				Quan_updates = 0;
				Quan_frames = 0;
			}
		}
		stop();
	}

	public void update() {

	}

	int FPS = 0;

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);// triple buffering
			return;
		}
		screen.clear();
		tick(input.key);
		screen.render(controls);
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}

		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHints(rh);
		// Draw
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		// Every time drawing, so we have to clean up the graphics
		g.setColor(Color.YELLOW);
		g.setFont(new Font("Garamond", Font.BOLD, 20));
		g.drawString(FPS + " FPS", 20, 30);
		g.dispose();
		bs.show();
	}

	public Controller controls = new Controller();
	private InputHandler input;

	public void tick(boolean[] key) {
		boolean forward = key[KeyEvent.VK_W];
		boolean back = key[KeyEvent.VK_S];
		boolean left = key[KeyEvent.VK_A];
		boolean right = key[KeyEvent.VK_D];
		boolean turnLeft = key[KeyEvent.VK_LEFT];
		boolean turnRight = key[KeyEvent.VK_RIGHT];
		boolean jump = key[KeyEvent.VK_SPACE];
		boolean crouch = key[KeyEvent.VK_CONTROL];
		boolean run = key[KeyEvent.VK_SHIFT];

		controls.tick(forward, back, left, right, turnLeft, turnRight, jump, crouch, run);
	}
}
