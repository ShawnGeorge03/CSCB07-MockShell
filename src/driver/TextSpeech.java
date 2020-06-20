package driver;
// Java code to convert text to speech

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
  
  public void speak(String input) {
    try {
      voice.speak(input);
    }catch(Exception e) {
      e.printStackTrace();
    }
  }
  
  public void endSpeak() {
    voice.deallocate();
  }
  
}
