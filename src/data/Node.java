// **********************************************************
// Assignment2:
// Student1:
// UTORID user_name: patelt26
// UT Student #: 1005904103
// Author: Shawn Santhoshgeorge
//
// Student2:
// UTORID user_name: shaiskan
// UT Student #: 1006243940
// Author: Keshavaa Shaiskandan
//
// Student3:
// UTORID user_name: patelt26
// UT Student #: 1005904103
// Author: Tirth Patel
//
// Student4:
// UTORID user_name: pate1101
// UT Student #: 1006315765
// Author: Abhay Patel
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC B07 and understand the consequences.
// *********************************************************
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

  public void setRoot(){
    isRoot = true;
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
