package data;

import java.util.ArrayList;
import java.util.Deque;

import errors.FileException;

public interface FileSystemI {

    public Node getRoot();
    public Node getCurrent();
    public String getContent(Node file);
    public void assignCurrent(Node currentDirectory);
    public void addToDirectory(Node newNode);
    public void removeFromDirectory(int i);

    //Current Path -> pwd operations
    public String getCurrentPath();

    //Command Log -> history operations
    public ArrayList<String> getCommandLog();

    //Directory Stack -> pushd, popd operations
    public Deque<String> getStack();

    //Redirection and other File operations
    public boolean isValidName(String fileName);
    public Node findFile(String filePath, boolean fileIsFolderNode); 
    public void fileAppend(String content, String file) throws FileException;
    public void fileOverwrite(String content, String file) throws FileException;
    public boolean checkRepeat(String name);
    
}