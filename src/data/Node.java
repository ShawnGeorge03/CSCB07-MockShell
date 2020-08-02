// **********************************************************
// Assignment2:
// Student1:
// UTORID user_name: santhos7
// UT Student #: 1006094673
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
 * Class Node is responsible for making directories and files
 */
public class Node {

  /**
   * All the information that will not
   * be changed after the node is created
   */
  private final boolean isDir;
  private final boolean isRoot;
  private final String name;
  private final ArrayList<Node> list;
  
  /**
   * All information that can be changed
   * after the Node is created
   */
  private String content;
  private Node parent;

  /**
   * Class Builder is resposible for
   * creating the Node class based on 
   * the arguments it recieves
   */
  public static class Builder {
    /**
     * Required information for the Node
     */
    private final String name;
    private final boolean isDir;
    
    /**
     * Optional information fro the Node
     */
    private boolean isRoot = false;
    private String content = null;
    private Node parent = null;
    private ArrayList<Node> list = new ArrayList<Node>();

    // Builder constructor with required fields (2)
    public Builder(boolean isDir, String name) {
      this.name = name;
      this.isDir = isDir;
    }

    //Optional Parameter
    public Builder setRoot(boolean isRoot) {
      this.isRoot = isRoot;
      return this;
    }

    //Optional Parameter
    public Builder setContent(String content) {
      this.content = content;
      return this;
    }

    //Optional Parameter
    public Builder setList(ArrayList<Node> list) {
      this.list = list;
      return this;
    }

    //Optional Parameter
    public Builder setParent(Node parent) {
      this.parent = parent;
      return this;
    }

    //Creates the Node object
    public Node build() {
      return new Node(this);
    }
  }

  /**
   * Assigns all the given 
   * variable to the Node
   * 
   * @param b a Buil
   */
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