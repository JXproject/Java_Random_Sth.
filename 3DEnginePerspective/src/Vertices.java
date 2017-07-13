
public class Vertices {
	public double x, y, z;

	public Vertices() {
		x = 0;
		y = 0;
		z = 0;
	}

	public Vertices(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public double[] getXYZ() {
		double list[] = { x, y, z };
		return list;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public void setXYZ(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
}
