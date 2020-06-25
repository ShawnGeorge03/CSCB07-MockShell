package driver;

import java.util.ArrayList;
import java.util.Arrays;

public class Cd {

  ArrayList<String> cd_args;
  FileSystem filesys;

  public Cd(String[] arguments) {
    filesys = FileSystem.getFileSys();
    this.cd_args = new ArrayList<String>(Arrays.asList(arguments)); 
  }

  public boolean isValid() {
    String cur = this.cd_args.get(0);

    if ((cur == "." || cur == ".." || cur == "/") && (cd_args.size() == 1)) {
      return true;
    } else if (cur.matches("[A-Za-z0-9/]") && cd_args.size() == 1) {
      return true;
    } else {
      return false;
    }
  }

  public void run() {
    String argument = this.cd_args.get(0);
    String[] split_args = argument.split("/");
    System.out.println(split_args);
    if (split_args.length == 1) {

      if (argument == "..") {
        System.out.println("Retard");
        if (!filesys.equals(null)) {
          filesys.assignCurrent(this.filesys.current.parent);
        }
      }

      else if (argument == "/") {
        this.filesys.assignCurrent(this.filesys.root);
      }
    }

    else {
      for (int i = 0; i < split_args.length; i++) {
        if (split_args[0] == this.filesys.root.name) {
          // Make path from root
        } else {
          // Check relative path
        }
      }
    }
  }
  
  public static void main(String[] args) {
    String[] sponge = new String[1];
    sponge[0] = "..";
    Cd test = new Cd(sponge);
    if (test.isValid()) {
      test.run();
    }
  }
  

}



// Access file system and then work on commands from there


