package driver;

import java.util.ArrayList;

public class Node {
	boolean isDir;
	boolean isRoot = false;
	String content;
	String name;
	ArrayList<Node> list = new ArrayList<Node>();
	Node parent;

	public Node() {

	}

}
