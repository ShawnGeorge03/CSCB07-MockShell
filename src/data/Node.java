package data;

import java.util.ArrayList;

public class Node {

  private final boolean isDir;
  private final boolean isRoot;
  private final String name;
  private final ArrayList<Node> list;
  
  private String content;
  private Node parent;

  public static class Builder {
    private final String name;
    private final boolean isDir;
    
    private boolean isRoot = false;
    private String content = null;
    private Node parent = null;
    private ArrayList<Node> list = new ArrayList<Node>();

    // Builder constructor with required fields (3)
    public Builder(boolean isDir, String name) {
      this.name = name;
      this.isDir = isDir;
    }

    public Builder setRoot(boolean isRoot) {
      this.isRoot = isRoot;
      return this;
    }

    public Builder setContent(String content) {
      this.content = content;
      return this;
    }

    public Builder setList(ArrayList<Node> list) {
      this.list = list;
      return this;
    }

    public Builder setParent(Node parent) {
      this.parent = parent;
      return this;
    }

    public Node build() {
      return new Node(this);
    }

  }

  private Node(Builder b) {
    this.isDir = b.isDir;
    this.isRoot = b.isRoot;
    this.name = b.name;
    this.list = b.list;
    this.content = b.content;
    this.parent = b.parent;

  }

  public boolean getisDir() {
    return isDir;
  }

  public boolean getisRoot(){
    return isRoot;
  }

  public String getName() {
    return name;
  }

  public String getContent() {
    return content;
  }

  public ArrayList<Node> getList() {
    return list;
  }

  public Node getParent() {
    return parent;
  }
  
  public void setContent(String content) {
    this.content = content;
  }

  public void setParent(Node parent) {
    this.parent = parent;
  }

  @Override
  public String toString() {
		String name = "Name: " + this.name;
		String isDir = "isDir: " + this.isDir;
		String isRoot = "isRoot: " + this.isRoot;
		String parent = "Parent: " + this.parent;
		String list = "List: " + this.list;
		String content = "Content: " + this.content;
		return name + "\n" + isDir + "\n" + isRoot + "\n" + parent + "\n" + list + "\n" + content + "\n";
  }
  
}