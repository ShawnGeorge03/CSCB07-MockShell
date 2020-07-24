package data;

import java.util.ArrayList;
import java.util.Deque;

public interface FileSystemI {

    public Node getRoot();
    public Node getCurrent();
    public String getContent(Node file);
    public void assignCurrent(Node currentDirectory);
    public void addToDirectory(Node newNode);
    public void removeFromDirectory(int i);

    //Command Log -> history operations
    public ArrayList<String> getCommandLog();

    //Directory Stack -> pushd, popd operations
    public Deque<String> getStack();
    public void setStack(Deque<String> stack);

    //Redirection
    public Node createFile();
    public String fileAppend(String content, String file);
    public String fileOverwrite(String content, String file);

    
}