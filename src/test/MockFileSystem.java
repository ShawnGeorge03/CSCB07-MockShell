package test;

import java.util.ArrayList;
import java.util.Deque;

import data.FileSystemI;
import data.Node;

public class MockFileSystem implements FileSystemI {

    @Override
    public Node getRoot() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Node getCurrent() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getContent(Node file) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void assignCurrent(Node currentDirectory) {
        // TODO Auto-generated method stub

    }

    @Override
    public void addToDirectory(Node newNode) {
        // TODO Auto-generated method stub

    }

    @Override
    public void removeFromDirectory(int i) {
        // TODO Auto-generated method stub

    }

    @Override
    public ArrayList<String> getCommandLog() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Deque<String> getStack() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setStack(Deque<String> stack) {
        // TODO Auto-generated method stub

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