import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

class GraphicsPanel extends JPanel {

	GraphicsPanel() {
		setBackground(Color.BLACK);
		setBounds(0, 0, Game.frameW, Game.frameH);
		setBackground(Color.WHITE);
	}

	Image offscreen;
	Graphics2D bg;

	// CONTENT
	Player tempPlayer;

	@Override
	public void paintComponent(Graphics g) {
		offscreen = createImage(Game.frameW, Game.frameH);
		// Make Image editable and apply graphics on the image
		bg = (Graphics2D) offscreen.getGraphics();
		bg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		bg.setColor(Color.BLACK);

		tempPlayer = Game.player1;
		fillRect(tempPlayer.getCurrentCoordinate(), tempPlayer.getSize(), bg);
		///
		for (int i = 0; i < Game.Ammo.size(); i++) {
			Bullet bullet = (Bullet) Game.Ammo.get(i);
			bg.fillOval((int)bullet.x, (int)bullet.y, 10, 10);
		}
		
		
		Cursour(bg);

		g.drawImage(offscreen, 0, 0, this);
		g.dispose();
		bg.dispose();
		offscreen.flush();

	}

	public void fillRect(Point coor, Dimension size, Graphics g) {
		g.fillRect(coor.x, coor.y, size.width, size.height);
	}

	public void Cursour(Graphics g) {
		int x = Game.input.MouseX;
		int y = Game.input.MouseY;
		g.setColor(Color.RED);
		g.drawLine(x - 10, y, x + 10, y);
		g.drawLine(x, y - 10, x, y + 10);
		//
		g.drawLine(Game.player1.getCentralCoordinate().x, Game.player1.getCentralCoordinate().y, x, y);
	}

}