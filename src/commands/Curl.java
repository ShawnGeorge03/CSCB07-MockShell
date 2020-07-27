// **********************************************************
// Assignment2:
// Student1:
// UTORID user_name: patelt26
// UT Student #: 1005904103
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
package commands;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import java.util.Arrays;

import data.FileSystemI;

/**
 * Class Curl returns a file from the given URL and adds it to 
 * the current working directory. 
 * 
 * Note : Some parts of this code was from the following website
 * https://docs.oracle.com/javase/tutorial/networking/urls/readingURL.html
 */
public class Curl implements CommandI {
  
  /**
   * Declare instance of ErrorHandler to handle error messages
   */
  private ErrorHandler errorManager;

  /**
   * Declare instance of Echo to add the file from URL to current directory
   */
  private Echo echo;
  
  /**
   * Constructor for class Curl which initalizes instance variables
   */
  public Curl() {
    // Initializes an Errorhandler Object
    this.errorManager = new ErrorHandler();
    // Initializes an Echo Object
    this.echo = new Echo();
  }

  /**
   * This function retrives the file from the URL and adds it to the current directory
   * 
   * @param args  the string array of URL(s)
   * @param fullInput  the full line of input that the user gives into JShell
   * @param val  a boolean value
   * @return any error messages if there are any or null
   */
  public String run(FileSystemI filesys, String[] args, String fullInput, boolean val) {

    //Declare instance of URL to handle the user input
    URL site;

    //If the user provides no URL
    if (args.length == 0) {
      //Returns an error when no URL is provided
      return errorManager.getError("No parameters provided", Arrays.toString(args));
    //If the user provides more than one URL
    }else if(args.length > 1) {
      //Returns an error when more than one URL is provided
      return errorManager.getError("Mulptile parameters provided", Arrays.toString(args));
    //If the user provides one URL
    } else {
      try {
        //Initializes a URL object
        site = new URL(args[0]);
        //Declares and Initializes a URLConnection object to setup the connection with the file server 
        final URLConnection siteConnection = site.openConnection();
        //Collects the name of the file from the end of the URL
        String fileName = site.getFile().trim().substring(
          site.getFile().trim().lastIndexOf("/"), site.getFile().trim().lastIndexOf("."));

        //Declares and Initializes a InputStreamReader to collect the data of the file from the server
        final InputStreamReader reader = new InputStreamReader(siteConnection.getInputStream());
        //Declares and Initializes a BufferedReader to read the collected data
        final BufferedReader info = new BufferedReader(reader);
        
        //Declares and Initializes some String to hold the the text
        String inputLine, text = "";

        //Reads the content returned from the file server line by line
        while ((inputLine = info.readLine()) != null) {
          //Appends each line of content and continues on until the end
          text += inputLine + "\n";
        }

        //Closes the BufferedReader
        info.close();
        //Closes the InputStreamReader
        reader.close();
        
        filesys.fileOverwrite(text, fileName);

        //Stores the text to a file with a certain fileName on the current directory 
        //echo.run(filesys, text.split(" "), "echo \" " + text + "\" > " + fileName, false);

      //If the user provides any Malformed URL
      } catch (MalformedURLException e) {
        //Returns an error for malformed URL
        return "Parameter given is invalid " + args[0];
      //If the user provides a URL with out a file or file is not on the server   
      } catch (FileNotFoundException | StringIndexOutOfBoundsException e) {
        //Returns an error for this expection
        return "URL provided does not contain a file " + args[0];
      //If a connection could not be made to the server for various reasons
      } catch (IOException e) {
        //Returns the appropriate error
        return "Connection could not be made to " + args[0];
      }
    }

    //If all goes well then it returns null
    return null;
  }
}
