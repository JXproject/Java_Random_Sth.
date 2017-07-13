import java.io.*;

public class UseStudentRecords {

	public static void main(String[] args) throws IOException {
		StudentRecords[] s = new StudentRecords[26];
		for (int i = 0; i < 26; i++) {
			s[i] = new StudentRecords();
			s[i].setName("A");
		} // Code set Names from A to Z
		for (int i = 0; i < 26; i++) {
			int[] mark = new int[4];
			for (int a = 0; a < 4; a++) {
				mark[a] = (int) (Math.random() * 101);
			}
			s[i].setAllMark(mark);
		} // Code set marks

		// Importing
		RandomAccessFile raf = new RandomAccessFile("employees.bin", "rw");
		for (int a = 0; a < 26; a++) {
			byte[] nameBytes = new byte[20];
			s[a].getName().getBytes(0, s[a].getName().length(), nameBytes, 0);
			raf.seek(a * 39);
			raf.write(nameBytes);
			raf.writeInt(s[a].getMark1());
			raf.writeInt(s[a].getMark2());
			raf.writeInt(s[a].getMark3());
			raf.writeInt(s[a].getMark4());
			raf.writeDouble(s[a].getAverage());
		}
		// close
		raf.close();

		// Following is Question #2 Read Bin files saved previous
		RandomAccessFile raf2 = new RandomAccessFile("employees.bin", "rw");
		for (int a = 0; a < 26; a++) {
			byte[] nameBytes2 = new byte[20];
			raf2.seek(a * 39);
			raf2.read(nameBytes2);
			int mark1 = raf2.readInt();
			int mark2 = raf2.readInt();
			int mark3 = raf2.readInt();
			int mark4 = raf2.readInt();
			raf2.writeDouble((mark1 + mark2 + mark3 + mark4) / 4);
		}
		// close
		raf2.close();

		// Following if Question #3 Ask and Output
		BufferedReader R = new BufferedReader(new InputStreamReader(System.in));
		RandomAccessFile raf3 = new RandomAccessFile("employees.bin", "rw");
		System.out.print("Whose records do you want to check? Name: ");
		String nameR = R.readLine();
		int cursour = -1;
		for (int a = 0; a < 26; a++) {
			raf3.seek(a * 39);
			byte[] nameBytes3 = new byte[20];
			raf3.read(nameBytes3);
			String nameS = new String(nameBytes3, 0);
			if (nameR.equals(nameS.trim())) {
				cursour = a;
				break;
			}
		}
		if (cursour == -1) {
			System.out.println("****************************************");
			System.out.print("Student " + nameR + " cannot be found from datas.");
		} else {
			raf3.seek(cursour * 39 + 20);
			int mark1 = raf3.readInt();
			int mark2 = raf3.readInt();
			int mark3 = raf3.readInt();
			int mark4 = raf3.readInt();
			double ave = raf3.readDouble();
			System.out.println("****************************************");
			System.out.print("Student Name: " + nameR + " ,mark#1: " + mark1 + " ,mark#2: " + mark2 + " ,mark#3: "
					+ mark3 + " ,mark#4: " + mark4 + " ,Average: " + ave);
		}
		raf3.close();
	}
}
