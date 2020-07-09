package data;

import java.util.ArrayList;

/**
 * Class Node is what each object in the filesystem is made of including directories and files
 */
public class Node {

  private boolean isDir;
  boolean isRoot = false;
  /**
   * Instance variable of String to contain the content of the node
   */
  protected String content;
  private String name;
  private ArrayList<Node> list = new ArrayList<Node>();
  private Node parent;

  // Trivial methods below do not require JavaDoc
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
