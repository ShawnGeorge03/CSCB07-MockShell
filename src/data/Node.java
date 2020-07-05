package data;

import java.util.ArrayList;

public class Node {
  
  private boolean isDir;
  boolean isRoot = false;
  protected String content;
  private String name;
  private ArrayList<Node> list = new ArrayList<Node>();
  private Node parent;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Node getParent() {
    return parent;
  }

  public void setParent(Node parent) {
    this.parent = parent;
  }

  public ArrayList<Node> getList() {
    return list;
  }

  public void setList(ArrayList<Node> list) {
    this.list = list;
  }

  public boolean isDir() {
    return isDir;
  }

  public void setDir(boolean isDir) {
    this.isDir = isDir;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

}
