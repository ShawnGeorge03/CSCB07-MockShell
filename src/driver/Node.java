package driver;

import java.util.ArrayList;

public class Node {
	boolean isDir;
	String content;
	String name;
	ArrayList<Node> list;
	Node parent;

	public Node(boolean isDir, String content, String name, Node parent) {
		this.isDir = isDir;
		this.name = name;
		this.content = content;
		this.parent = parent;
	}

}
