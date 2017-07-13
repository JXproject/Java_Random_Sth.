
public class StudentRecords {

	String name;
	int[] mark = new int[4];
	double average;

	public StudentRecords(String n, int m1, int m2, int m3, int m4, double ave) {
		name = n;
		mark[0] = m1;
		mark[1] = m2;
		mark[2] = m3;
		mark[3] = m4;
		average = ave;
	}

	public StudentRecords() {
		name = "";
		mark[0] = -1;
		mark[1] = -1;
		mark[2] = -1;
		mark[3] = -1;
		average = -1;
	}

	public String getName() {
		return name;
	}

	public int getMark1() {
		return mark[0];
	}

	public int getMark2() {
		return mark[1];
	}

	public int getMark3() {
		return mark[2];
	}

	public int getMark4() {
		return mark[3];
	}

	public int[] getAllMark() {
		return mark;
	}

	public double getAverage() {
		return average;
	}

	public void setName(String n) {
		name = n;
	}

	public void setMark1(int m) {
		mark[0] = m;
	}

	public void setMark2(int m) {
		mark[1] = m;
	}

	public void setMark3(int m) {
		mark[2] = m;
	}

	public void setMark4(int m) {
		mark[3] = m;
	}

	public void setAllMark(int[] m) {
		mark = m;
	}

	public void setAllMark(int m1, int m2, int m3, int m4) {
		mark[0] = m1;
		mark[1] = m2;
		mark[2] = m3;
		mark[3] = m4;
	}

	public void setAverage(double a) {
		average = a;
	}

	public String toString() {
		return ("This student info: Name: " + name + ", Mark#1: " + mark[0] + ", Mark#2: " + mark[1] + ", Mark#3: "
				+ mark[2] + ", Mark#4: " + mark[3] + ", Average: " + average);
	}
}
