package test;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

import data.FileSystemI;
import data.Node;

public class MockFileSystem implements FileSystemI {

  /**
   * Declare instance variable FileSystem which is yet to be intialized
   */
  static MockFileSystem filesys = null;
  /**
   * Declare instance variable of ArrayList to contain the logs of all inputs
   * given to JShell
   */
  private static ArrayList<String> CommandLog;
  /**
   * Declare instance variable of ArrayDeque
   */
  private static Deque<String> stack;

  /**
   * Initialize the directory nodes
   */

  Node root;
  Node current;
  Node users;
  Node user1;
  Node user2;
  Node documents;
  Node journal;
  Node week1;
  Node downloads;
  Node homework;
  Node games;

  public void setCurrent(Node newCurrent){
    this.current = newCurrent;
  }
  
  private MockFileSystem(String type) {
    CommandLog = new ArrayList<String>();
    stack = new ArrayDeque<String>();
    root = new Node.Builder(true, "/").setRoot(true).setParent(null).build();
    current = root;
    if (type.equals("MOCKENV")) {

      //Please leave as is -> Current Mock File Structure
      /**
       * \ 
       *  users 
       *    skeshavaa 
       *    guest 
       *  documents 
       *    txtone 
       *    txttwo 
       * downloads 
       *    homework
       *      HW8 
       *    Games 
       * A2
       */

      Node A2 = new Node.Builder(false, "A2").setParent(root).setContent("Wow what a project").build();
      root.getList().add(A2);

      users = new Node.Builder(true, "users").setParent(root).build();
      root.getList().add(users);
      user1 = new Node.Builder(true, "skeshavaa").setParent(users).build();
      users.getList().add(user1);
      user2 = new Node.Builder(true, "guest").setParent(users).build();
      users.getList().add(user2);

      documents = new Node.Builder(true, "documents").setParent(root).build();
      root.getList().add(documents);
      Node doc1 = new Node.Builder(false, "txtone").setParent(documents).setContent("this is a document").build();
      documents.getList().add(doc1);
      Node doc2 = new Node.Builder(false, "txttwo").setParent(documents).setContent("this is a document 2").build();
      documents.getList().add(doc2);
      journal = new Node.Builder(true, "journal").setParent(documents).build();
      documents.getList().add(journal);
      week1 = new Node.Builder(true, "week1").setParent(journal).build();
      journal.getList().add(week1);

      downloads = new Node.Builder(true, "downloads").setParent(root).build();
      root.getList().add(downloads);
      homework = new Node.Builder(true, "homework").setParent(downloads).build();
      downloads.getList().add(homework);
      Node hw8 = new Node.Builder(false, "HW8").setParent(homework).setContent("2+2=5").build();
      homework.getList().add(hw8);
      games = new Node.Builder(true, "Games").setParent(downloads).build();
      downloads.getList().add(games);
    }
  }

  public static MockFileSystem getMockFileSys(String type) {
    if (type.equals("EMPTYSYS")) {
      filesys = new MockFileSystem("EMPTYSYS");
    } else if (type.equals("MOCKENV")) {
      filesys = new MockFileSystem("MOCKENV");
    }
    return filesys;
  }

  // Trivial methods below that require no JavaDoc
  public Node getRoot() {
    return root;
  }

  public Node getCurrent() {
    return current;
  }

  public String getContent(Node file) {
    return file.getContent();
  }

  public void assignCurrent(Node currentDirectory) {
    current = currentDirectory;
  }

  public void addToDirectory(Node newNode) {
    newNode.setParent(current);
    current.getList().add(newNode);
  }

  public void removeFromDirectory(int i) {
    current.getList().remove(i);
  }

  public ArrayList<String> getCommandLog() {
    return CommandLog;
  }

  public Deque<String> getStack() {
    return stack;
  }

  /**
   * Method that checks if the file name that the user inputted is a valid file
   * name. If the file name contains illegal characters then this method returns
   * false. If the file name does not contain any illegal characters then it
   * returns true.
   * 
   * @param fileName String that stores the file name that the user inputted
   * @return boolean false if file name contains illegal characters, otherwise
   *         returns true
   */
  public boolean isValidName(String fileName) {
    String[] invalidChars = { "/", ".", "\\s+", "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "{", "}", "~", "|",
        "<", ">", "?", "'", "[", "]" };
    for (int i = 0; i < invalidChars.length; i++) {
      if (fileName.contains(invalidChars[i])) {
        return false;
      }
    }
    return true;
  }

  /**
   * Finds the current working directory
   * 
   * @return the path to the current directory
   */
  public String getCurrentPath() {
    // Grabs the current directory
    Node current = getCurrent();
    // Grabs the parent of the current directory
    Node parent = current.getParent();

    // If we are in the root folder
    if (parent == null)
      return current.getName();
    // Create the absolute path
    String currentPath = parent.getName() + "/" + current.getName();
    parent = parent.getParent();
    while (parent != null) {
      currentPath = parent.getName() + "/" + currentPath;
      parent = parent.getParent();
    }
    // Return the final absolute path
    if(currentPath.startsWith("//")) return currentPath.substring(1);
    return currentPath;
  }

  @Override
  public Node findFile(String filePath, boolean fileIsFolderNode) {
    String absolutePath = filePath.trim();

    if(filePath.startsWith("//")) return null;
    
    // If the given path is a relative path then make it a absolute path
    if (!filePath.startsWith("/")  && !fileIsFolderNode) {
      absolutePath = (getCurrentPath() + "/" + filePath).substring(1);
    }
    // Grabs root directory
    Node current = getRoot();
    // Splits the absolutePath into the individual folders
    String[] directories = absolutePath.split("/");

    // Loops through the directories array
    for (int i = 1; i < directories.length; i++) {
      // Loops through the ArrayList of directories
      for (int j = 0; j < current.getList().size(); j++) {
        // If the folder matches the one we need then return it
        if (current.getList().get(j).getName().equals(directories[i])) {
          if ((i + 1) == directories.length) {
            if (fileIsFolderNode == current.getList().get(j).getisDir()) {
              current = current.getList().get(j);
              return current;
            }
          } else {
            current = current.getList().get(j);
          }
        }
      }
    }
    return null;
  }

  @Override
  public void fileAppend(String content, String file) {
    Node fileNode = findFile(file, false);
    if (fileNode != null) {
      fileNode.setContent(fileNode.getContent() + "\n" + content);
    } else {
      String fileName = file.split("/")[file.split("/").length - 1];
      if (isValidName(fileName)) {
        Node currentNode = getCurrent();
        String desiredPath = fileName;
        if (!file.startsWith("/")) {
          desiredPath = (getCurrentPath() + "/" + file).substring(1);
        }
        desiredPath = desiredPath.substring(0, desiredPath.lastIndexOf("/"));
        Node parent;
        if (desiredPath.equals(""))
          parent = getRoot();
        else
          parent = findFile(desiredPath, true);
        assignCurrent(parent);
        fileNode = new Node.Builder(false, fileName).setContent(content).build();
        addToDirectory(fileNode);
        assignCurrent(currentNode);
      }
    }
  }

  @Override
  public void fileOverwrite(String content, String file) {
    Node fileNode = findFile(file, false);
    if (fileNode != null) {
      fileNode.setContent(content);
    } else {
      String fileName = file.split("/")[file.split("/").length - 1];
      if (isValidName(fileName)) {
        Node currentNode = getCurrent();
        String desiredPath = file;
        if (!file.startsWith("/")) {
          desiredPath = (getCurrentPath() + "/" + file).substring(1);
        }
        desiredPath = desiredPath.substring(0, desiredPath.lastIndexOf("/"));
        Node parent;
        if (desiredPath.equals(""))
          parent = getRoot();
        else
          parent = findFile(desiredPath, true);
        assignCurrent(parent);
        fileNode = new Node.Builder(false, fileName).setContent(content).build();
        addToDirectory(fileNode);
        assignCurrent(currentNode);
      }
    }
  }

  @Override
  public boolean checkRepeat(String name) {
    for (int i = 0; i < getCurrent().getList().size(); i++) {
      if (getCurrent().getList().get(i).getName().equals(name)) {
        return false;
      }
    }
    return true;
  }
}