package driver;

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
      
  public TextSpeech(){
    System.setProperty(PARENT_DIR, VOICE_DIR);
    this.vm = VoiceManager.getInstance();
    this.voice = this.vm.getVoice(VOICENAME);
    this.voice.allocate();
    this.text = "";
  }
  
  public void convertTextToSpeech(String[] textToSay, boolean speakMode) {  
      
    text = Arrays.toString(textToSay);
    text = text.substring(1, text.length() - 1).replace(",", "").trim();
        
    if(text.endsWith("QUIT")) {
      text = text.substring(0, text.indexOf("QUIT")).trim();
    }
        
    if(!speakMode) {
      if(text.startsWith("\"") && text.endsWith("\"")) {
        if(!(text.length() <= 1))
          text = text.substring(1, text.lastIndexOf("\""));
      }else {
        System.out.println("Missing Quotation : " + textToSay);
        return;
      }
      
      if(text.indexOf("\"") != -1) {
        System.out.println("Malformed Text : " + textToSay);
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
