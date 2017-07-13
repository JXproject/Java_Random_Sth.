import java.io.*;

// The "LinkListTest" class.
public class LinkListTest {
	public static void main(String[] args) throws IOException {
		LinkList list = new LinkList();

		LinkEmployeeRecord lpr1 = new LinkEmployeeRecord("Bob", 1212, 10, 30);
		LinkEmployeeRecord lpr2 = new LinkEmployeeRecord("Anna", 7777, 12, 40);
		LinkEmployeeRecord lpr3 = new LinkEmployeeRecord("Peter", 6565, 20, 45);
		LinkEmployeeRecord lpr4 = new LinkEmployeeRecord("Gina", 1234, 50, 35);

		// add nodes

		list.addNode(lpr1);
		list.addNode(lpr2);
		list.addNode(lpr3);

		list.showList();

		// add another node and then print list again
		list.addNode(lpr4);
		list.showList();

		// Ask User to search Name
		System.out.print("Who are you searching for? Name: ");
		BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
		String name = r.readLine();
		LinkEmployeeRecord result = list.getNode(name);
		if (result == null)
			System.out.print(name + " is not found!");
		else {
			System.out.print("List info: Name: " + result.getName() + " ,Employee Number" + result.getEmpNum()
					+ " ,Hourly Rate:" + result.getHourlyRate() + " ,Hourly Worked" + result.getHoursWorked());
		}
		//Delete
		System.out.print("\nWho are you going to delete? Name: ");
		name = r.readLine();
		list.deleteNode(name);
		list.showList();
	} // main method
} // LinkListTest class
