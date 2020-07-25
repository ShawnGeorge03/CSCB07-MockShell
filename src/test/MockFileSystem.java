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
    static FileSystemI emptySys = null;
    static FileSystemI configuredSys = null;
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
            
        }
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
    public void setStack(Deque<String> stack) {
        // TODO Auto-generated method stub
        MockFileSystem.stack = stack;
    }

    @Override
    public Node findFile(String filePath) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getCurrentPath() {
        // TODO Auto-generated method stub
        return null;
    }

    
    @Override
    public boolean isValidName(String fileName) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Node createFile(String content, String fileName, String filePath) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void fileAppend(String content, String file) {
        // TODO Auto-generated method stub

    }

    @Override
    public void fileOverwrite(String content, String file) {
        // TODO Auto-generated method stub

    }

    
}