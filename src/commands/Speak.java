package commands;

import java.util.Arrays;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

/**
 * Class TextSpeech is responsible for converting given text to audio
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
   * Declare instance of ErrorHandler to handle error messages
   */
  private ErrorHandler errorManager;

  /**
   * Declare instance of String to store the voice that is to be used
   */
  private static final String VOICENAME = "kevin16";

  /**
   * Declare instance of String to store the voice that is to be used
   */
  private static final String PARENT_DIR = "freetts.voices";

  /**
   * Declare instance of String to store the voice that is to be used
   */
  private static final String VOICE_DIR =
      "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory";

  private String text;
  private String userText;


  /**
   * Constructor for class TextSpeech which initializes instance variables
   */
  public Speak() {
    // Set Property to where the voice exists
    System.setProperty(PARENT_DIR, VOICE_DIR);
    // Creates a VoiceManager Object
    this.voiceManager = VoiceManager.getInstance();
    // Creates a Voice Object
    this.voice = this.voiceManager.getVoice(VOICENAME);
    // Allocates the voice
    this.voice.allocate();
    // Initializes the text
    this.text = "";
    // Initializes a Errorhandler Object
    this.errorManager = new ErrorHandler();
  }

  /**
   * This function checks if the text given is valid and then converts it audio
   * 
   * @param args  the string array of arguments
   * @param fullInput  the full line of input that the user gives into JShell
   * @param val  tells if the it should enter speakMode or not
   * @return any error messages if there are any or null
   */
  public String run(String[] args, String actualInput, boolean val) {

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

    // If we are not in speak mode
    if (!val) {
      // If the user did not use or partially used quote(s) at the beginning
      // and/or end of their input
      if (text.startsWith("\"") && text.endsWith("\"")) {
        // If the use text is not an alphabet
        if (!(text.length() <= 1))
          // Stores a new copy of the user text except no quotes
          text = text.substring(1, text.lastIndexOf("\""));
      } else {
        // Returns an error if the quotes are not used properly
        return errorManager.getError("Missing Quotes", userText);
      }

      // If the user used multiple quotations
      if (text.indexOf("\"") != -1) {
        // Returns an error if the quotes are not used properly
        return errorManager.getError("Malformed Input", userText);
      }
    }

    try {
      // Converts the voice to audio
      voice.speak(text);
    } catch (Exception e) {
      e.printStackTrace();
    }
    // The method returns null
    return null;
  }


}