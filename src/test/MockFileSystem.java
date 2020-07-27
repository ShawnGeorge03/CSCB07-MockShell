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

    private MockFileSystem(String type){
        CommandLog = new ArrayList<String>();
        stack = new ArrayDeque<String>();
        root = new Node.Builder(true, "/").setRoot(true).setParent(null).build();
        current = root;
        if (type.equals("MOCKENV")){
            Node users = new Node.Builder(true, "users").setParent(root).build();
            root.getList().add(users);
            Node documents = new Node.Builder(true, "documents").setParent(root).build();
            root.getList().add(documents);
            Node downloads = new Node.Builder(true, "downloads").setParent(root).build();
            root.getList().add(downloads);

            Node user1 = new Node.Builder(true, "skeshavaa").setParent(users).build();
            users.getList().add(user1);
            Node user2 = new Node.Builder(true, "guest").setParent(users).build();
            users.getList().add(user2);

            Node doc1 = new Node.Builder(false, "txtone").setParent(documents).setContent("this is a document").build();
            documents.getList().add(doc1);
            Node doc2 = new Node.Builder(false, "txttwo").setParent(documents).setContent("this is a document 2").build();
            documents.getList().add(doc2);

            Node homework = new Node.Builder(true, "homework").setParent(downloads).build();
            downloads.getList().add(homework);
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

    @Override
    public Node getRoot() {
        return root;
    }

    @Override
    public Node getCurrent() {
        return current;
    }

    @Override
    public String getContent(Node file) {
        return file.getContent();
    }

    @Override
    public void assignCurrent(Node currentDirectory) {
        current = currentDirectory;
    }

    @Override
    public void addToDirectory(Node newNode) {
        current.getList().add(newNode);
    }

    @Override
    public void removeFromDirectory(int i) {
        current.getList().remove(i);
    }

    @Override
    public ArrayList<String> getCommandLog() {
        return CommandLog;
    }

    @Override
    public Deque<String> getStack() {
        return stack;
    }

    @Override
    public Node findFile(String filePath, boolean fileIsFolderNode) {
        return null;
    }

    @Override
    public String getCurrentPath() {
        return null;
    }

    
    @Override
    public boolean isValidName(String fileName) {
        return false;
    }

    @Override
    public Node createFile(String content, String fileName, String filePath) {
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