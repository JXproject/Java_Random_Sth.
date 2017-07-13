public class LinkList {
	protected LinkEmployeeRecord top;

	// start a null list

	public LinkList() {
		top = null;
	}

	// add a LinkEmployeeRecord object to the end of the list

	public void addNode(LinkEmployeeRecord lpr) {
		if (top == null) {
			top = lpr;
		} else {
			LinkEmployeeRecord current = null;
			current = top;
			while (current.getNext() != null) {
				current = current.getNext();
			}
			current.setNext(lpr);
		}
	}

	// display the linked list

	public void showList() {
		if (top == null) {
			System.out.println("Empty list ");
		} else {
			System.out.println("The current list:");

			LinkEmployeeRecord current;
			current = top;

			while (current != null) {
				System.out.println(current.getName() + " " + current.getEmpNum());
				current = current.getNext();
			}
			// System.out.println(current.getName() + " " +
			// current.getEmpNum());
			System.out.println("*******");
		}
	}

	// Node List Searching
	public LinkEmployeeRecord getNode(String name) {
		LinkEmployeeRecord current;
		current = top;
		/*
		 * while (true) { if (current == null) break; else if
		 * (current.getName().equals(name)) break; else current =
		 * current.getNext(); } return current;
		 */
		while (!(current == null || current.getName().equals(name))) {
			current = current.getNext();
		}
		return current;
	}

	// Node Delete
	public void deleteNode(String name) {
		/*
		 * LinkEmployeeRecord current, previous; current = top; previous = null;
		 * if (current.getName().equals(name)) { top=current.getNext();
		 * current=null; } else { previous = current; current =
		 * current.getNext(); while (true) { if (current == null) break;// fail
		 * . null else if (current.getName().equals(name)) break;// found. else
		 * { previous = current; current = current.getNext(); } } } while
		 * (current != null) { previous.setNext(current.getNext()); previous =
		 * previous.getNext(); current = current.getNext(); }
		 */
		LinkEmployeeRecord current, previous;
		current = top;
		previous = null;
		if (current.getName().equals(name)) {
			top = current.getNext();
			current = null;
		} else {
			while (!(current == null || current.getName().equals(name))) {
				previous = current;
				current = current.getNext();
			}
		}

		while (current != null) {
			current = current.getNext();
			previous.setNext(current);
			previous = previous.getNext();
		}
	}
}
