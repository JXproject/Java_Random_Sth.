import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Cube {
	public JFrame J;
	public final int frameW = 600, frameH = 600;
	public GuiPanel panel;

	public static void main(String[] args) {
		new Cube();
	}

	public Cube() {

		J = new JFrame();
		J.setSize(frameW, frameH);
		J.setLocationRelativeTo(null);

		panel = new GuiPanel();
		J.add(panel);

		J.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		J.setVisible(true);

		panel.repaint();
	}
}

@SuppressWarnings("serial")
class GuiPanel extends JPanel implements ActionListener, MouseListener {

	public final int frameW = 600, frameH = 420;

	public Timer timer = new Timer(10, this);
	public boolean Demo = false;
	public Ellipse2D button = new Ellipse2D.Double(frameW - 100, 80, 50, 50);
	public Polygon playSymbol = new Polygon(new int[] { frameW - 100 + 15, frameW - 100 + 40, frameW - 100 + 15 },
			new int[] { 90, 105, 120 }, 3);

	Point2D A2d[] = new Point2D[8];
	Vertices Vertices[] = { new Vertices(-50, -50, -50), new Vertices(50, -50, -50), new Vertices(50, 50, -50),
			new Vertices(-50, 50, -50), new Vertices(-50, -50, 50), new Vertices(50, -50, 50), new Vertices(50, 50, 50),
			new Vertices(-50, 50, 50) };

	Vertices CAMERA = new Vertices(0, 0, 0);
	Vertices ORIENTATION = new Vertices(0, 0, 0);// Tait-Bryan angles
	Vertices Eye=new Vertices(100,100,100);
	///// ====================////////
	Vertices VerticesGuidePlaneXY[] = { new Vertices(-50, 50, 0), new Vertices(50, 50, 0), new Vertices(50, -50, 0),
			new Vertices(-50, -50, 0) };
	Vertices VerticesGuidePlaneXZ[] = { new Vertices(-50, 0, 50), new Vertices(50, 0, 50), new Vertices(50, 0, -50),
			new Vertices(-50, 0, -50) };
	Vertices VerticesGuidePlaneYZ[] = { new Vertices(0, -50, 50), new Vertices(0, 50, 50), new Vertices(0, 50, -50),
			new Vertices(0, -50, -50) };
	Point2D V2dXY[] = new Point2D[4];
	Point2D V2dXZ[] = new Point2D[4];
	Point2D V2dYZ[] = new Point2D[4];
	///// ====================////////
	///// ==========Grids==========////////
	public double kGrids = 3;
	Vertices VerticesGridsPlaneXY[] = { new Vertices(-50 * kGrids, 50 * kGrids, 0),
			new Vertices(50 * kGrids, 50 * kGrids, 0), new Vertices(50 * kGrids, -50 * kGrids, 0),
			new Vertices(-50 * kGrids, -50 * kGrids, 0) };
	Vertices VerticesGridsPlaneXZ[] = { new Vertices(-50 * kGrids, 0, 50 * kGrids),
			new Vertices(50 * kGrids, 0, 50 * kGrids), new Vertices(50 * kGrids, 0, -50 * kGrids),
			new Vertices(-50 * kGrids, 0, -50 * kGrids) };
	Vertices VerticesGridsPlaneYZ[] = { new Vertices(0, -50 * kGrids, 50 * kGrids),
			new Vertices(0, 50 * kGrids, 50 * kGrids), new Vertices(0, 50 * kGrids, -50 * kGrids),
			new Vertices(0, -50 * kGrids, -50 * kGrids) };
	Point2D G2dXY[] = new Point2D[4];
	Point2D G2dXZ[] = new Point2D[4];
	Point2D G2dYZ[] = new Point2D[4];

	///// ====================////////
	public GuiPanel() {
		addMouseListener(this);
	}

	public void paintComponent(Graphics g) {
		Graphics2D G = (Graphics2D) g;

		G.draw(button);
		if (!Demo) {
			G.draw(playSymbol);
		} else {
			g.drawRect(frameW - 100 + 12, 90, 10, 30);
			g.drawRect(frameW - 100 + 27, 90, 10, 30);
		}

		G.translate(frameW / 2, frameH / 2);

		// guide
		 XYZgrids(G);

		drawCubic(G);

		paintCubic(G);

		// Circles in Cube surface
//		 Point2D Vs[] = { A2d[0], A2d[1], A2d[2], A2d[3] };
//		 G.draw(pathEllipseInPolygon(Vs));
		 guide(G);

	}

	public void guide(Graphics2D G) {
		Arrays.fill(V2dXY, new Point.Double(0, 0));
		for (int i = 0; i < 4; i++) {
			V2dXY[i] = Mat(VerticesGuidePlaneXY[i]);
		}
		Arrays.fill(V2dXZ, new Point.Double(0, 0));
		for (int i = 0; i < 4; i++) {
			V2dXZ[i] = Mat(VerticesGuidePlaneXZ[i]);
		}
		Arrays.fill(V2dYZ, new Point.Double(0, 0));
		for (int i = 0; i < 4; i++) {
			V2dYZ[i] = Mat(VerticesGuidePlaneYZ[i]);
		}
		// -----//
		G.translate(-frameW / 2, -frameH / 2);
		G.translate(frameW - 100, frameH + 50);
//		 Path2D XYpanel = pathPolygon(V2dXY);
//		 Path2D YZpanel = pathPolygon(V2dYZ);
//		 Path2D XZpanel = pathPolygon(V2dXZ);
		Path2D XYpanel = pathEllipseInPolygon(V2dXY);
		Path2D YZpanel = pathEllipseInPolygon(V2dYZ);
		Path2D XZpanel = pathEllipseInPolygon(V2dXZ);

		G.draw(XYpanel);
		G.draw(XZpanel);
		G.draw(YZpanel);

	}

	public void XYZgrids(Graphics2D G) {
		Arrays.fill(G2dXY, new Point.Double(0, 0));
		for (int i = 0; i < 4; i++) {
			G2dXY[i] = Mat(VerticesGridsPlaneXY[i]);
		}
		Arrays.fill(G2dXZ, new Point.Double(0, 0));
		for (int i = 0; i < 4; i++) {
			G2dXZ[i] = Mat(VerticesGridsPlaneXZ[i]);
		}
		Arrays.fill(G2dYZ, new Point.Double(0, 0));
		for (int i = 0; i < 4; i++) {
			G2dYZ[i] = Mat(VerticesGridsPlaneYZ[i]);
		}

		// Real grids
		// Point2D grids2D[][][] = new Point2D[30][4][3];
		//
		// for (int i = 0; i < 30; i++) {
		// //XY
		// grids2D[i][0][0] = Mat(new Vertices(-150 + 10 * i, -150, 0));
		// grids2D[i][1][0] = Mat(new Vertices(-150 + 10 * i, 150, 0));
		// grids2D[i][2][0] = Mat(new Vertices(-150, -150 + 10 * i, 0));
		// grids2D[i][3][0] = Mat(new Vertices(150, -150 + 10 * i, 0));
		// //YZ
		// grids2D[i][0][1] = Mat(new Vertices(0,-150 + 10 * i, -150));
		// grids2D[i][1][1] = Mat(new Vertices(0,-150 + 10 * i, 150));
		// grids2D[i][2][1] = Mat(new Vertices(0,-150, -150 + 10 * i));
		// grids2D[i][3][1] = Mat(new Vertices(0,150, -150 + 10 * i));
		// //XZ
		// grids2D[i][0][2] = Mat(new Vertices(-150 + 10 * i,0, -150));
		// grids2D[i][1][2] = Mat(new Vertices(-150 + 10 * i,0, 150));
		// grids2D[i][2][2] = Mat(new Vertices(-150,0, -150 + 10 * i));
		// grids2D[i][3][2] = Mat(new Vertices(150,0, -150 + 10 * i));
		//
		// G.setColor(Color.RED);
		// drawLine(grids2D[i][0][0], grids2D[i][1][0], G);
		// drawLine(grids2D[i][2][0], grids2D[i][3][0], G);
		// G.setColor(Color.BLUE);
		// drawLine(grids2D[i][0][1], grids2D[i][1][1], G);
		// drawLine(grids2D[i][2][1], grids2D[i][3][1], G);
		// G.setColor(Color.GREEN);
		// drawLine(grids2D[i][0][2], grids2D[i][1][2], G);
		// drawLine(grids2D[i][2][2], grids2D[i][3][2], G);
		// }

		// Panels
		Path2D XYpanel = pathPolygon(G2dXY);
		Path2D YZpanel = pathPolygon(G2dYZ);
		Path2D XZpanel = pathPolygon(G2dXZ);

		//
		G.setColor(Color.RED);
		G.draw(XYpanel);
		G.setColor(Color.GREEN);
		G.draw(XZpanel);
		G.setColor(Color.BLUE);
		G.draw(YZpanel);
		G.setColor(Color.BLACK);

	}

	private Path2D pathEllipseInPolygon(Point2D Vs[]) {

		Path2D ec = new Path2D.Double();
		ec.moveTo((Vs[0].getX() + Vs[1].getX()) / 2, (Vs[0].getY() + Vs[1].getY()) / 2);
		for (int i = 0; i < Vs.length; i++) {
			int m = i + 1;
			int E = i + 2;
			if (E == Vs.length) {
				E = 0;
			} else if (E == Vs.length + 1) {
				E = 1;
				m = 0;
			}
			ec.curveTo((Vs[i].getX() + Vs[m].getX()) / 2, (Vs[i].getY() + Vs[m].getY()) / 2, Vs[m].getX(), Vs[m].getY(),
					(Vs[m].getX() + Vs[E].getX()) / 2, (Vs[E].getY() + Vs[m].getY()) / 2);
		}
		ec.closePath();
		return ec;
	}

	public void drawCubic(Graphics2D G) {
		Arrays.fill(A2d, new Point.Double(0, 0));
		for (int i = 0; i < 8; i++) {
			A2d[i] = Mat(Vertices[i]);
		}

//		System.out.println("Paint");/// ----------------===

		Point2D Vs[] = { A2d[0], A2d[1], A2d[2], A2d[3] };
		Path2D bottom = pathPolygon(Vs);

		Point2D Vs2[] = { A2d[4], A2d[5], A2d[6], A2d[7] };
		Path2D top = pathPolygon(Vs2);

		for (int i = 0; i < 4; i++) {
			drawLine(A2d[i], A2d[4 + i], G);
		}

		G.draw(bottom);
		G.draw(top);
	}

	public void paintCubic(Graphics2D G) {

//		System.out.println("Paint");/// ----------------===

		Point2D Vs[] = { A2d[0], A2d[1], A2d[2], A2d[3] };
		Path2D back = pathPolygon(Vs);

		Point2D Vs2[] = { A2d[4], A2d[5], A2d[6], A2d[7] };
		Path2D front = pathPolygon(Vs2);

		Point2D sVs1[] = { A2d[0], A2d[1], A2d[5], A2d[4] };
		Point2D sVs2[] = { A2d[1], A2d[2], A2d[6], A2d[5] };
		Point2D sVs3[] = { A2d[2], A2d[3], A2d[7], A2d[6] };
		Point2D sVs4[] = { A2d[3], A2d[0], A2d[4], A2d[7] };

		Path2D sides1 = pathPolygon(sVs1);
		Path2D sides2 = pathPolygon(sVs2);
		Path2D sides3 = pathPolygon(sVs3);
		Path2D sides4 = pathPolygon(sVs4);

		double xx = Math.toDegrees(ORIENTATION.x % (Math.PI * 2));
		double yy = Math.toDegrees(ORIENTATION.y % (Math.PI * 2));
		double zz = Math.toDegrees(ORIENTATION.z % (Math.PI * 2));


//		G.setColor(new Color(130, 232, 193));
//		G.fill(front);// front
//
//		G.setColor(new Color(135, 255, 138));
//		G.fill(sides1);// Top
//
//		G.setColor(new Color(111, 228, 255));
//		G.fill(back);// Back
//
//		G.setColor(new Color(220, 107, 232));
//		G.fill(sides3);// Bottom

		
		// G.setColor(new Color(156, 104, 255));
		// G.fill(sides2);// right
		
		// G.setColor(new Color(255, 117, 136));
		// G.fill(sides4);// left

	}

	public Path2D pathPolygon(Point2D Vs[]) {
		Path2D A = new Path2D.Double();

		A.moveTo(Vs[0].getX(), Vs[0].getY());
		for (int i = 0; i < Vs.length; i++) {
			int n = i + 1;
			if (n == Vs.length)
				n = 0;
			A.lineTo(Vs[n].getX(), Vs[n].getY());
		}
		A.closePath();

		return A;
	}

	public void drawLine(Point2D A, Point2D B, Graphics2D G) {
		Line2D line = new Line2D.Double(A, B);
		G.draw(line);
	}

	public Point2D Mat(Vertices VerticesXYZ_3D) {
		Point2D planeXY2D = new Point2D.Double();

		Matrix m = new Matrix();

		double[][] Mat1 = { { 1, 0, 0 }, { 0, Math.cos(-ORIENTATION.x), -Math.sin(-ORIENTATION.x) },
				{ 0, Math.sin(-ORIENTATION.x), Math.cos(-ORIENTATION.x) } };
		double[][] Mat2 = { { Math.cos(-ORIENTATION.y), 0, Math.sin(-ORIENTATION.y) }, { 0, 1, 0 },
				{ -Math.sin(-ORIENTATION.y), 0, Math.cos(-ORIENTATION.y) } };
		double[][] Mat3 = { { Math.cos(-ORIENTATION.z), -Math.sin(-ORIENTATION.z), 0 },
				{ Math.sin(-ORIENTATION.z), Math.cos(-ORIENTATION.z), 0 }, { 0, 0, 1 } };
		double[][] Mat4 = { { VerticesXYZ_3D.x }, { VerticesXYZ_3D.y }, { VerticesXYZ_3D.z } };
		double[][] Mat5 = { { CAMERA.x }, { CAMERA.y }, { CAMERA.z } };

		double[][] result = m.multiply(m.multiply(m.multiply(Mat1, Mat2), Mat3), (m.subtract(Mat4, Mat5)));
		double dx=result[0][0], dy= result[1][0],dz=result[2][0];
		System.out.println("-------------------\n"+dx+"|"+dy+"|"+dz+"|");
//		
//		double bx=Eye.x/dz*dx-Eye.x;
//		double by=Eye.y/dz*dy-Eye.y;
		
		planeXY2D.setLocation(dx,dy);
		// System.out.println("-------------------");
		// System.out.println("X: "+ORIENTATION.x%(Math.PI*2)+"Y:
		// "+ORIENTATION.y+"Z: "+ORIENTATION.z);
		// System.out.println("X: " + planeXY2D.getX() + " |Y: " +
		// planeXY2D.getY());

		return planeXY2D;
	}

	int Angle = 0;

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Angle++;
		 ORIENTATION.setZ(Math.toRadians(Angle));
		ORIENTATION.setX(Math.toRadians(Angle));
		ORIENTATION.setY(Math.toRadians(Angle));
		this.repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if (button.contains(e.getX(), e.getY())) {
			if (Demo) {
				Demo = false;
				timer.stop();
				this.repaint();
			} else
				Demo = true;
		}

		if (Demo)
			timer.start();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
