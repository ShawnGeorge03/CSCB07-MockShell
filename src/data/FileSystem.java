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

    // First dummy in Root
    Node users = new Node();
    users.setDir(true);
    users.setContent(null);
    users.setName("users");
    users.setParent(root);

    // second dummy in Root
    Node pics = new Node();
    pics.setDir(true);
    pics.setContent(null);
    pics.setName("pics");
    pics.setParent(root);

    // Picfile in pics
    Node picfile = new Node();
    picfile.setDir(false);
    picfile.setContent("this is a picture");
    picfile.setName("picturefile");
    picfile.setParent(pics);

    pics.getList().add(picfile);

    root.getList().add(users);
    root.getList().add(pics);

    // C
    // Users
    // pics
    // picturefile
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
