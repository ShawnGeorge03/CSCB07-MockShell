package commands;

import java.util.Arrays;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class TextSpeech {
   
  private Voice voice;
  private VoiceManager vm;
  
  private static final String VOICENAME = "kevin16";  
  private static final String PARENT_DIR = "freetts.voices";
  private static final String VOICE_DIR = 
      "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory";
  
  private String text;
  private String userText;
  
  private ErrorHandler err;
  
  private boolean speakMode;
      
  public TextSpeech(){
    System.setProperty(PARENT_DIR, VOICE_DIR);
    this.vm = VoiceManager.getInstance();
    this.voice = this.vm.getVoice(VOICENAME);
    this.voice.allocate();
    this.text = "";
    this.err = new ErrorHandler();
  }
  
  public void run(String [] arguments, String fullInput) {
    if(arguments.length == 0) speakMode = true; 
    convertTextToSpeech(arguments, fullInput, speakMode);        
    if(fullInput.endsWith("QUIT")) speakMode = false;
  }
  
  public void convertTextToSpeech(String[] textToSay, String fullInput, boolean val) {  
      
    text = Arrays.toString(textToSay);
    text = text.substring(1, text.length() - 1).replace(",", "").trim();
    
    userText = fullInput.substring(0, fullInput.indexOf("speak") + 5).trim();
        
    if(text.endsWith("QUIT")) {
      text = text.substring(0, text.indexOf("QUIT")).trim();
    }
        
    if(!val) {
      if(text.startsWith("\"") && text.endsWith("\"")) {
        if(!(text.length() <= 1))
          text = text.substring(1, text.lastIndexOf("\""));
      }else {
        err.getError("Missing Quotes", userText);
        return;
      }
      
      if(text.indexOf("\"") != -1) {
        err.getError("Malformed Input", userText);
        return;
      } 
    }
    
    try {
      voice.speak(text);
    } catch (Exception e) {
      e.printStackTrace();
    }   
  }
}
