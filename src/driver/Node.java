package driver;

public class Node {
	boolean isDir;
	String content;
	String name;
	Node next;
	Node root;

	private Node(boolean isDir, String content, String name, Node next, Node root) {
		this.isDir = isDir;
		this.name = name;
		this.next = next;
		this.content = content;
		this.root = root;
	}

	public static void main() {
		System.out.println("hello world");
	}

}
