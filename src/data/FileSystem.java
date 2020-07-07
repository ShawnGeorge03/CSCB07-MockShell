package data;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

public class FileSystem {

  static FileSystem fileSys = null;
  private static ArrayList<String> CommandLog = new ArrayList<String>();
  private static Deque<String> stack = new ArrayDeque<String>();

  Node root = new Node();
  Node current = new Node();


  private FileSystem() {
    root.setDir(true);
    root.setContent(null);
    root.setName("C");
    root.isRoot = true;
    root.setParent(null);
    current = root;
  }

  public static FileSystem getFileSys() {
    if (fileSys == null) {
      fileSys = new FileSystem();
      return fileSys;
    } else {
      return fileSys;
    }
  }

  public Node getRoot() {
    return root;
  }

  public Node getCurrent() {
    return current;
  }

  public String getContent(Node file) {
    return file.content;
  }

  public void assignCurrent(Node currentDirectory) {
    current = currentDirectory;
  }

  public void addToDirectory(Node newNode) {
    newNode.setParent(current);
    current.getList().add(newNode);
  }

  public static ArrayList<String> getCommandLog() {
    return CommandLog;
  }

  public static void setCommandLog(ArrayList<String> commandLog) {
    CommandLog = commandLog;
  }

  public static Deque<String> getStack() {
    return stack;
  }

  public static void setStack(Deque<String> stack) {
    FileSystem.stack = stack;
  }

}
