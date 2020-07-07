package commands;

import java.util.Arrays;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class TextSpeech implements CommandI {
   
  private Voice voice;
  private VoiceManager vm;
  
  private static final String VOICENAME = "kevin16";  
  private static final String PARENT_DIR = "freetts.voices";
  private static final String VOICE_DIR = 
      "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory";
  
  private String text;
  private String userText;
  private boolean speakMode = false;
      
  public TextSpeech(){
    System.setProperty(PARENT_DIR, VOICE_DIR);
    this.vm = VoiceManager.getInstance();
    this.voice = this.vm.getVoice(VOICENAME);
    this.voice.allocate();
    this.text = "";
  }
  
  public void run(String[] args, String actualInput) {
    if(args.length == 0) speakMode = true;
    convertTextToSpeech(args, speakMode);        
    if(actualInput.endsWith("QUIT")) speakMode = false;
  }
  
  public void convertTextToSpeech(String[] textToSay, boolean speakMode) {  
      
    text = Arrays.toString(textToSay);
    text = text.substring(1, text.length() - 1).replace(",", "").trim();
    userText = text;
        
    if(text.endsWith("QUIT")) {
      text = text.substring(0, text.indexOf("QUIT")).trim();
    }
        
    if(!speakMode) {
      if(text.startsWith("\"") && text.endsWith("\"")) {
        if(!(text.length() <= 1))
          text = text.substring(1, text.lastIndexOf("\""));
      }else {
        System.out.println("Missing Quotation : " + userText);
        return;
      }
      
      if(text.indexOf("\"") != -1) {
        System.out.println("Malformed Text : " + userText);
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
