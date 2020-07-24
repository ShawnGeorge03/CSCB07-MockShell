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
    Node root = new Node();
    Node current = new Node();

    private MockFileSystem(String type){
        CommandLog = new ArrayList<String>();
        stack = new ArrayDeque<String>();
        root.setDir(true);
        root.setContent(null);
        root.setName("/");
        root.setRoot();
        root.setParent(null);
        current = root;
        if (type.equals("MOCKENV")){
            
        }
    }

    @Override
    public Node getRoot() {
        // TODO Auto-generated method stub
        return root;
    }

    @Override
    public Node getCurrent() {
        // TODO Auto-generated method stub
        return current;
    }

    @Override
    public String getContent(Node file) {
        // TODO Auto-generated method stub
        return file.getContent();
    }

    @Override
    public void assignCurrent(Node currentDirectory) {
        // TODO Auto-generated method stub
        current = currentDirectory;
    }

    @Override
    public void addToDirectory(Node newNode) {
        // TODO Auto-generated method stub
        current.getList().add(newNode);
    }

    @Override
    public void removeFromDirectory(int i) {
        // TODO Auto-generated method stub
        current.getList().remove(i);
    }

    @Override
    public ArrayList<String> getCommandLog() {
        // TODO Auto-generated method stub
        return CommandLog;
    }

    @Override
    public Deque<String> getStack() {
        // TODO Auto-generated method stub
        return stack;
    }

    @Override
    public void setStack(Deque<String> stack) {
        // TODO Auto-generated method stub
        this.stack = stack;
    }

    @Override
    public Node createFile() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String fileAppend(String content, String file) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String fileOverwrite(String content, String file) {
        // TODO Auto-generated method stub
        return null;
    }
    
}