// **********************************************************
// Assignment2:
// Student1:
// UTORID user_name: santhos7
// UT Student #: 1006094673
// Author: Shawn Santhoshgeorge
//
// Student2:
// UTORID user_name: shaiskan
// UT Student #: 1006243940
// Author: Keshavaa Shaiskandan
//
// Student3:
// UTORID user_name: patelt26
// UT Student #: 1005904103
// Author: Tirth Patel
//
// Student4:
// UTORID user_name: pate1101
// UT Student #: 1006315765
// Author: Abhay Patel
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC B07 and understand the consequences.
// *********************************************************
package data;

import java.util.ArrayList;
import java.util.Deque;

import errors.FileException;

/**
 * Interface FileSystemI is responsible for providing a common method to
 * interact with the filesystem
 */  
public interface FileSystemI {

    //Trivial methods
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

    /**
     * Checks for invalid characters in a filename
     * 
     * @param fileName  String that stores the file name that the user inputted
     * @return boolean false if file name contains illegal characters, otherwise returns true
     */
    public boolean isValidName(String fileName);
    
    /**
     * Looks for a file or folder not in the filesystem
     * 
     * @param fileName  String that stores the file name that the user inputted
     * @param fileIsFolderNode  boolean false if the file to be found is a file node and false if the file is a folder node
     * @return  Node that holds the node that the method is searching for, else returns null if node is not in filesystem
     */
    public Node findFile(String filePath, boolean fileIsFolderNode); 
    
    /**
     * Appends content to a file or creates the file if needed
     * 
     * @param content  String that stores the content of the file node
     * @param file  String that stores the filename/file location in the filesystem
     */
    public void fileAppend(String content, String file) throws FileException;
    
    /**
     * Overwrites content of a file or creates the file if needed
     * 
     * @param content  String that stores the content of the file node
     * @param file  String that stores the filename/file location in the filesystem
     */
    public void fileOverwrite(String content, String file) throws FileException;
    
    /**
     * Checks if the given filename exists within the current directory node of the user
     * 
     * @param name  filename to look for within current directory
     * @return  boolean indiciating if the file exists or not in the current directory
     * 
     */
    public boolean checkRepeat(String name);
    
}