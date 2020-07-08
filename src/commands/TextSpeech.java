package commands;

import java.util.Arrays;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class TextSpeech implements CommandI {

  private Voice voice;
  private VoiceManager vm;
  private ErrorHandler err;

  private static final String VOICENAME = "kevin16";
  private static final String PARENT_DIR = "freetts.voices";
  private static final String VOICE_DIR =
      "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory";

  private String text;
  private String userText;

  public TextSpeech() {
    System.setProperty(PARENT_DIR, VOICE_DIR);
    this.vm = VoiceManager.getInstance();
    this.voice = this.vm.getVoice(VOICENAME);
    this.voice.allocate();
    this.text = "";
    this.err = new ErrorHandler();
  }

  public String run(String[] args, String actualInput, boolean val) {

    text = Arrays.toString(args);
    text = text.substring(1, text.length() - 1).replace(",", "").trim();
    userText = text;

    if (text.endsWith("QUIT")) {
      text = text.substring(0, text.indexOf("QUIT")).trim();
    }

    if (text.length() == 0) {
      return null;
    }


    if (!val) {
      if (text.startsWith("\"") && text.endsWith("\"")) {
        if (!(text.length() <= 1))
          text = text.substring(1, text.lastIndexOf("\""));
      } else {
        return err.getError("Missing Quotes", userText);
      }

      if (text.indexOf("\"") != -1) {
        return err.getError("Malformed Input", userText);
      }
    }

    try {
      voice.speak(text);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }


}
