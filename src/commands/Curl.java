package commands;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;

public class Curl implements CommandI {
  
  private ErrorHandler errorManager;
  private Echo echo;
  
  public Curl() {
    errorManager = new ErrorHandler();
    echo = new Echo();
  }

  @Override
  public String run(String[] args, String fullInput, boolean val) {
    URL site;
    if (args.length == 0) {
      return errorManager.getError("No parameters provided", Arrays.toString(args));
    }else if(args.length > 1) {
      return errorManager.getError("Mulptile parameters provided", Arrays.toString(args));
    } else {
      
      try {
        site = new URL(args[0]);
        final URLConnection siteConnection = site.openConnection();
        String[] tempArr = site.getFile().trim().split("/");
        String file = tempArr[tempArr.length - 1].trim();
        String fileName = file.substring(0,file.indexOf("."));

        final InputStreamReader reader = new InputStreamReader(siteConnection.getInputStream());
        final BufferedReader info = new BufferedReader(reader);

        String inputLine, text = "";
        while ((inputLine = info.readLine()) != null) {
          text += inputLine;
        }
        info.close();
   
        String[] echoArgs = text.split(" ");
        String echoFullInput = "echo \" " + text + "\" > " + fileName;
        
        echo.run(echoArgs, echoFullInput, false);
      } catch (MalformedURLException e) {
        System.out.println("Parameter given is invalid " + e.getMessage());
      } catch (FileNotFoundException e) {
        System.out.println("File could not be found at : " + e.getMessage());
      } catch (IOException e) {
        System.out.println("Connection could not be made to " + e.getMessage());
      }
    }

    return null;
  }
}
