package driver;

public class Node {
	boolean isDir;
	String content;
	String name;
	Node next;
	Node root;
	Node parent;

	private Node(boolean isDir, String content, String name, Node next, Node root, Node parent) {
		this.isDir = isDir;
		this.name = name;
		this.next = next;
		this.content = content;
		this.root = root;
		this.parent = parent;
	}

	public static void main() {
		System.out.println("hello world");
	}

}
