
public class Bullet {

	private int  ix, iy, fx, fy, range, initialTick, time,w,h;
	double xx,yy,x, y;
	boolean reachBoundary=false;

	public Bullet(int ix, int iy, int fx, int fy, int range, int initick, int time, int w,int h) {
		this.ix = ix+w/2;
		this.iy = iy+h/2;
		x = ix;
		y = iy;
		this.range = range;
		initialTick = initick;
		this.time = time;
		this.w=w;
		this.h=h;
		
		double rate = Math.sqrt(Math.pow(range, 2) / (Math.pow(fx - ix, 2) + Math.pow(fy - iy, 2)));
		fx = (int) (ix + rate * (fx - ix));
		fy = (int) (iy + rate * (fy - iy));
		xx=(fx-ix)/(double)time;
		yy=(fy-iy)/(double)time;
	}
	public void move(int tick){
		int td=tick-initialTick;
		if(td<=time){
			x+=xx;
			y+=yy;
		}else{
			reachBoundary=true;
		}
	}
	public int getIx() {
		return ix;
	}
	public void setIx(int ix) {
		this.ix = ix;
	}
	public int getIy() {
		return iy;
	}
	public void setIy(int iy) {
		this.iy = iy;
	}
	public int getFx() {
		return fx;
	}
	public void setFx(int fx) {
		this.fx = fx;
	}
	public int getFy() {
		return fy;
	}
	public void setFy(int fy) {
		this.fy = fy;
	}
	public int getRange() {
		return range;
	}
	public void setRange(int range) {
		this.range = range;
	}
	public int getInitialTick() {
		return initialTick;
	}
	public void setInitialTick(int initialTick) {
		this.initialTick = initialTick;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public int getW() {
		return w;
	}
	public void setW(int w) {
		this.w = w;
	}
	public int getH() {
		return h;
	}
	public void setH(int h) {
		this.h = h;
	}
	public double getXx() {
		return xx;
	}
	public void setXx(double xx) {
		this.xx = xx;
	}
	public double getYy() {
		return yy;
	}
	public void setYy(double yy) {
		this.yy = yy;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public boolean isReachBoundary() {
		return reachBoundary;
	}
	public void setReachBoundary(boolean reachBoundary) {
		this.reachBoundary = reachBoundary;
	}
	protected void finalize(){
		System.out.println("Bullet Cleared");
	}  

}
