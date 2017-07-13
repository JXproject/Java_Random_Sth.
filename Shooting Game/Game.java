import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends JFrame implements Runnable {
	public static Game game;
	public static int frameX = 0;
	public static int frameY = 0;
	public static int frameW = 800;
	public static int frameH = 800;
	public static GraphicsPanel panel;
	public Controller controls = new Controller();
	public static InputHandler input;
	private Thread thread;// handle game
	private boolean running = false;
	public static int Quan_frames = 0;
	public static int Quan_updates = 0;
	public static int FPS = 0, tick = 0;
	public static final String TITLE = "Game";
	// Player Info
	public static Player player1;
	public static ArrayList Ammo;
	//

	public static void main(String[] args) {
		game = new Game();
		game.start();

	}

	public Game() {
		setSize(frameW, frameH);
		setResizable(false);
		setTitle(TITLE + " | " + Quan_updates + " UPS | " + Quan_frames + " FPS");
		panel = new GraphicsPanel();
		add(panel);// add graphic panel component to the frame window
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);

		input = new InputHandler();
		addKeyListener(input);
		addFocusListener(input);
		addMouseListener(input);
		addMouseMotionListener(input);
		addMouseWheelListener(input);

		// Player set up
		player1 = new Player(0, 200, 200, 30, 30);
		Ammo = new ArrayList<Bullet>();
	}

	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Game");
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

			while (delta >= 1) {
				update();// hold logic
				Quan_updates++;
				tick++;
				delta--;
			}

			render();// render

			Quan_frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				setTitle(TITLE + " | " + Quan_updates + " UPS | " + Quan_frames + " FPS");
				Quan_updates = 0;
				Quan_frames = 0;
			}
		}
		stop();
	}

	public void update() {
		tick(input.key);
		for (int i = 0; i < Ammo.size(); i++) {
			Bullet bullet = (Bullet) Ammo.get(i);
			if (bullet.isReachBoundary()) {
				Ammo.remove(i);
				i = i - 1;
			} else {
				bullet.move(tick);
			}
		}
		System.gc();
		// if(bullet!=null){
		// bullet.move(tick);
		// }
	}

	public void render() {
		panel.repaint();
	}
	
	public void shoot(boolean state){
		shoot=state;
	}

	boolean shoot = false;
	public static int yfdelayc = -1, ybdelayc = -1, xldelayc = -1, xrdelayc = -1, bulletReloadTimeC = -1,
			bulletReloadTime = 10;
	boolean previousforward = false, previousBack = false, previousLeft = false, previousRight = false;

	public void tick(boolean[] key) {
		boolean forward = key[KeyEvent.VK_W];
		boolean back = key[KeyEvent.VK_S];
		boolean left = key[KeyEvent.VK_A];
		boolean right = key[KeyEvent.VK_D];
		//
		shoot=key[68836];
		if (shoot) {
//			System.out.println(bulletReloadTimeC - tick);
			if ((-bulletReloadTimeC + tick) >= bulletReloadTime) {
				bulletReloadTimeC = -1;
			}
			if (bulletReloadTimeC == -1) {
				bulletReloadTimeC = tick;
			} else {
				shoot = false;
			}
		}
		//
		int[] result = delayEffects(yfdelayc, forward, previousforward);
		yfdelayc = result[0];
		forward = (result[1] == 1);

		result = delayEffects(ybdelayc, back, previousBack);
		ybdelayc = result[0];
		back = (result[1] == 1);

		result = delayEffects(xldelayc, left, previousLeft);
		xldelayc = result[0];
		left = (result[1] == 1);

		result = delayEffects(xrdelayc, right, previousRight);
		xrdelayc = result[0];
		right = (result[1] == 1);

		controls.tick(forward, back, left, right, shoot);

		previousforward = forward;
		previousBack = back;
		previousLeft = left;
		previousRight = right;

	}

	public int[] delayEffects(int delayCount, boolean command, boolean previouscommand) {
		if (previouscommand == command) {
			delayCount = -1;
		}
		if (previouscommand != command && delayCount == -1) {
			delayCount = tick;
			command = true;
		}
		if (delayCount != -1 && (tick - delayCount) >= (player1.getMass())) {
			delayCount = -1;
			command = false;
		} else if (delayCount != -1) {
			command = true;
		}
		int num = 0;
		if (command) {
			num = 1;
		}
		int[] result = { delayCount, num };
		return result;
	}

}