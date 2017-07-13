import java.awt.Dimension;
import java.awt.Point;

public class Player {

	private int ID,speed;
	private Point currentCoor;
	private Dimension size;

	public Player(int ID, int iX, int iY, int iW, int iH) {
		this.ID = ID;
		currentCoor = new Point(iX, iY);
		size = new Dimension(iW, iH);
		speed=3;
	}
	public Point getCentralCoordinate(){
		return new Point(currentCoor.x+size.width/2,currentCoor.y+size.height/2);
	}

	public Point getCurrentCoordinate() {
		return currentCoor;
	}

	public Dimension getSize() {
		return size;
	}

	public int getX() {
		return getCentralCoordinate().x;
	}
	public int getY() {
		return getCentralCoordinate().y;
	}
	
	public int getID() {
		return ID;
	}
	
	public int getSpeed(){
		return speed;
	}
	
	public void setID(int ID) {
		this.ID = ID;
	}

	public void setCurrentCoordinate(int x, int y) {
		currentCoor.setLocation(x, y);
	}

	public void move(int x,int y){
		currentCoor.setLocation(currentCoor.x+x, currentCoor.y+y);
	}

	public void setCurrentCoordinate(Point coor) {
		currentCoor.setLocation(coor);
	}

	public void setSize(int w, int h) {
		size.setSize(w, h);
	}

	public void setSize(Dimension wh) {
		size.setSize(wh);
	}
	
	public void setSpeed(int newspeed){
		this.speed=newspeed;
	}
	
	public int getMass(){
		return size.width*size.height;
	}
}
