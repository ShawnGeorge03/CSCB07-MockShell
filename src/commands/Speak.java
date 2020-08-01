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

import java.util.Arrays;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import data.FileSystemI;
import errors.InvalidRedirectionError;
import errors.MalformedInputException;
import errors.MissingQuotesException;

/**
 * Class TextSpeech is responsible for converting given text to audio Note :
 * Parts of this class was built from
 * https://www.youtube.com/watch?v=d50sZteNC1E
 */

public class Speak implements CommandI {

  /**
   * Declare instance of Voice so we can convert the text to audio
   */
  private Voice voice;

  /**
   * Declare instance of VoiceManager to choose a certain Voice Object
   */
  private VoiceManager voiceManager;

  /**
   * Declare instance of String to store the voice that is to be used
   */
  private static final String VOICENAME = "kevin16";

  /**
   * Declare instance of String to store the voice that is to be used
   */
  private static final String PARENTDIR = "freetts.voices";

  /**
   * Declare instance of String to store the voice that is to be used
   */
  private static final String VOICEDIR = "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory";

  /**
   * Declares an instance of RedirectionManager to handle redirection
   */
  private RedirectionManager rManager;

  /**
   * Declare instance of String to hold the user input
   */
  private String text, userText;

  /**
   * Declares instance of boolean which tells if in speak mode or not
   */
  private boolean speakMode;

  /**
   * Constructor for class TextSpeech which initializes instance variables
   */
  public Speak() {
    // Set Property to where the voice exists
    System.setProperty(PARENTDIR, VOICEDIR);
    // Creates a VoiceManager Object
    this.voiceManager = VoiceManager.getInstance();
    // Creates a Voice Object
    this.voice = this.voiceManager.getVoice(VOICENAME);
    // Allocates the voice
    this.voice.allocate();
    // Initializes the text
    this.text = "";
    // Initializing the RedirectionManager object
    this.rManager = new RedirectionManager();
  }

  /**
   * This function checks if the text given is valid and then converts it audio
   * 
   * @param filesys   refrence of FileSystemI object (MockFileSystem or
   *                  FileSystem)
   * @param args      the string array of arguments
   * @param fullInput the full line of input that the user gives into JShell
   * @param val       tells if the it should enter speakMode or not
   * 
   * @return any error messages if there are any or null
   */
  public String run(FileSystemI filesys, String fullInput, boolean val) {
    // Sets the if in speak mode or not
    this.speakMode = val;

    //Seperates the parameters from everything else from the user input
    String[] args = rManager.setParams(fullInput);

    try {
      rManager.isRedirectionableCommand(fullInput);

      // Converts a String array containing user words to a single String sentence
      text = Arrays.toString(args);
      text = text.substring(1, text.length() - 1).replace(",", "").trim();

      // If the user enters nothing or just QUIT
      if (text.length() == 0) {
        // The method returns null
        return null;
      }

      // Stores the exact user input
      userText = text;

      // If the text ends with the special keyword QUIR
      if (text.endsWith("QUIT")) {
        // Stores a new copy of the user text except no QUIT
        text = text.substring(0, text.indexOf("QUIT")).trim();
      }

      // Converts the voice to audio
      if (checkArgs(filesys, args, text)) {
        voice.speak(text);
      }
      // Catches if user used redirection or misses quotes or has malformed input
    } catch (InvalidRedirectionError | MissingQuotesException | MalformedInputException e) {
      return e.getLocalizedMessage();
    } catch (Exception e) {
      e.printStackTrace();
    }
    // The method returns null
    return null;
  }

  /**
   * Checks the user input for any redirection error if used and other issues from
   * user if there are none then it return true else throws the respective
   * exception
   * 
   * @param filesys   refrence of FileSystemI object (MockFileSystem or
   *                  FileSystem)
   * @param arguments the list of arguments from user which may contain a
   *                  redirection error
   * @param fullInput the user input
   * 
   * @throws MissingQuotesException  if user provides input with no quotes
   * @throws MalformedInputException user provides malformed text (Quotes in
   *                                 incorrect places)
   * 
   * @return true if the parameter meet requirements and false if not
   */
  public boolean checkArgs(FileSystemI fs, String[] arguments, String fullInput)
      throws MissingQuotesException, MalformedInputException {
    // If we are not in speak mode
    if (!speakMode) {
      // If the user did not use or partially used quote(s) at the beginning
      // and/or end of their input
      if (fullInput.startsWith("\"") && fullInput.endsWith("\"")) {
        // If the use text is not an alphabet
        if (!(fullInput.length() <= 1))
          // Stores a new copy of the user text except no quotes
          fullInput = fullInput.substring(1, fullInput.lastIndexOf("\""));
      } else {
        // Returns an error if the quotes are not used properly
        throw new MissingQuotesException("Error : Missing Quotes : " + this.userText);
      }

      // If the user used multiple quotations
      if (text.indexOf("\"") != -1) {
        // Returns an error if the quotes are not used properly
        throw new MalformedInputException("Error : Malformed Input :" + this.userText);
      }
    }
    // If the user has meet all the requirements
    return true;
  }
}