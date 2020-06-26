package driver;

import java.util.Arrays;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class TextSpeech {
   
  Voice voice;
  VoiceManager vm;
  
  private static final String VOICENAME = "kevin16";  
  private static final String PARENT_DIR = "freetts.voices";
  private static final String VOICE_DIR = 
      "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory";
    
  public TextSpeech(){
    System.setProperty(PARENT_DIR, VOICE_DIR);
    this.vm = VoiceManager.getInstance();
    this.voice = this.vm.getVoice(VOICENAME);
    this.voice.allocate();
  }
  
  public void speak(String[] input) {
    
    //Creates the text to be said
    String textToSay = Arrays.toString(input);
    textToSay = textToSay.substring(1, textToSay.length() - 1).replace(",", "");
    
    //Only when no arguments where provided
    if(textToSay.length() == 0) return;
    
    //Checks if it starts with Quotes
    if(textToSay.startsWith("\"") && textToSay.endsWith("\"")) {
      textToSay = textToSay.substring(textToSay.indexOf("\"") + 1, textToSay.lastIndexOf("\""));
    }else {
      System.out.println("Error -> Invalid Argument(s)");
      return;
    }
    
    //Check if the string is malformed
    if(textToSay.contains("\"")) {
      System.out.println("Error > Malformed Argument(s)");
      return;
    }
    
    //If the user chooses to exit the speak module
    if(textToSay.contains("QUIT")){
      textToSay = textToSay.substring(0, textToSay.indexOf("QUIT"));
    }
    
    //Says it
    voice.speak(textToSay);
  }  
  
  public boolean containsQUIT(String[] input) {
    for(String word: input) {
      word = word.replace("\"", "");
      if(word.contentEquals("QUIT")) {
        return true;
      }
    }
    return false;
  }
  
}
