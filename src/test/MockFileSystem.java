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
     * Declare instance variable of ArrayList to contain the logs of all inputs given to JShell
     */
    private static ArrayList<String> CommandLog;
    /**
     * Declare instance variable of ArrayDeque
     */
    private static Deque<String> stack;

    /**
     * Initialize current and root nodes
     */
    Node root;
    Node current;

    private ArrayList<String> path;

    private MockFileSystem(String type){
        CommandLog = new ArrayList<String>();
        stack = new ArrayDeque<String>();
        root = new Node.Builder(true, "/").setRoot(true).setParent(null).build();
        current = root;
        if (type.equals("MOCKENV")){

            /**
             * \
             *  users
             *    skeshavaa
             *    guest
             *  documents
             *    txtone.txt
             *    txttwo.txt
             *  downloads
             *    homework
             *      HW8
             *    Games
             *  A2.txt 
             */
            

            Node A2 = new Node.Builder(false, "A2").setParent(root).setContent("Wow what a project").build();
            root.getList().add(A2);

            Node users = new Node.Builder(true, "users").setParent(root).build();
            root.getList().add(users);
            Node user1 = new Node.Builder(true, "skeshavaa").setParent(users).build();
            users.getList().add(user1);
            Node user2 = new Node.Builder(true, "guest").setParent(users).build();
            users.getList().add(user2);
          

            Node documents = new Node.Builder(true, "documents").setParent(root).build();
            root.getList().add(documents);
            Node doc1 = new Node.Builder(false, "txtone").setParent(documents).setContent("this is a document").build();
            documents.getList().add(doc1);
            Node doc2 = new Node.Builder(false, "txttwo").setParent(documents).setContent("this is a document 2").build();
            documents.getList().add(doc2);

            
            Node downloads = new Node.Builder(true, "downloads").setParent(root).build();
            root.getList().add(downloads);
            Node homework = new Node.Builder(true, "homework").setParent(downloads).build();
            downloads.getList().add(homework);
            Node hw8 = new Node.Builder(false, "HW8").setParent(homework).setContent("2+2=5").build();
            homework.getList().add(hw8);
            Node games = new Node.Builder(true, "Games").setParent(downloads).build();
            downloads.getList().add(games);
        }
    }

    public static MockFileSystem getMockFileSys(String type){
        if (type.equals("EMPTYSYS")){
            filesys = new MockFileSystem("EMPTYSYS");
        }else if (type.equals("MOCKENV")){
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
 * Method that checks if the file name that the user inputted is a valid file name. If the file name
 * contains illegal characters then this method returns false. If the file name does not contain any
 * illegal characters then it returns true.
 * 
 * @param fileName  String that stores the file name that the user inputted
 * @return boolean false if file name contains illegal characters, otherwise returns true
 */
public boolean isValidName(String fileName) {
  String[] invalidChars = {"/", ".", "\\s+", "!", "@", "#", "$", "%", "^",
      "&", "*", "(", ")", "{", "}", "~", "|", "<", ">", "?", "'", "[", "]"};
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
  String output = "";
    Node current = filesys.getCurrent();

    if (current.equals(filesys.getRoot())) {
      return filesys.getRoot().getName();
    }

    path.add(current.getName());
    while (current != filesys.getRoot()) {
      current = current.getParent();
      path.add(current.getName());
    }

    int pathLength = path.size();
    while (pathLength > 0) {
      output = output.concat(path.get(pathLength - 1) + "/");
      pathLength--;
    }

    path.clear();

    return output.substring(1, output.length() - 1);
}


@Override
public Node findFile(String filePath, boolean fileIsFolderNode) {
  String absolutePath = filePath.trim();

  //If the given path is a relative path then make it a absolute path
  if(!filePath.startsWith("/")){
    absolutePath = (getCurrentPath() + "/" + filePath).substring(1);
  }
  //Grabs root directory
  Node current = getRoot();
  //Splits the absolutePath into the individual folders
  String[] directories = absolutePath.split("/");

  //Loops through the directories array
  for (int i = 1; i < directories.length; i++) {
    //Loops through the ArrayList of directories
    for (int j = 0; j < current.getList().size(); j++) {
      //If the folder matches the one we need then return it
      if (current.getList().get(j).getName().equals(directories[i])) {
        if((i+1) == directories.length){
          if(fileIsFolderNode == current.getList().get(j).getisDir()){
            current = current.getList().get(j);
            return current;
          }
        }
        else{
          current = current.getList().get(j);
        }
      }
    }
  }
  return null;  
}

    @Override
    public void fileAppend(String content, String file) {
        return;
    }

    @Override
    public void fileOverwrite(String content, String file) {
        return;
    }

    @Override
    public boolean checkRepeat(String name){
      for (int i = 0; i < getCurrent().getList().size(); i++){
        if (getCurrent().getList().get(i).getName().equals(name)){
          return false;
        }
      }
      return true;
    }
}