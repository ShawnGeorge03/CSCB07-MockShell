package commands;

import java.util.ArrayList;
import java.util.Arrays;

import data.FileSystemI;
import data.Node;


public class Find extends DirectoryManager implements CommandI {

  ErrorHandler error;
  String output = null;

  public Find() {
    error = new ErrorHandler();
  }

  public boolean isValid(ArrayList<String> args) {
    if (args.size() == 0 || args.size() < 4) {
      output = error.getError("Invalid Argument", "Missing arguments");
      return false;
    } else if (args.size() > 4) {
      output = error.getError("Multiple parameters provided", "");
      return false;
    } else if (!(args.get(0).equals("-type"))) {
      output = error.getError("Invalid Argument", args.get(0));
      return false;
    } else if (!(args.get(1).equals("f") || args.get(1).equals("d"))) {
      output = error.getError("Invalid Argument", args.get(1));
      return false;
    } else if (!(args.get(2).equals("-name"))) {
      output = error.getError("Invalid Argument", args.get(2));
      return false;
    } else if (args.get(3).equals(null)) {
      output = error.getError("Invalid Argument", args.get(3));
      return false;
    } else {
      return true;
    }
  }

  @Override
  public String run(FileSystemI filesys, String[] args, String fullInput, boolean val) {
    ArrayList<String> paths = new ArrayList<String>();
    ArrayList<String> arguments = new ArrayList<String>(Arrays.asList(args));
    if (arguments.size() == 0) {
      output = error.getError("No parameters provided", "Find");
      return output;
    }
    for (int i = 0; i < args.length; i++) {
      paths.add(args[i]);
      if (i < args.length - 1) {
        if (args[i + 1].equals("-type")) {
          break;
        }
      }
    }
    for (String x : paths) {
      arguments.remove(x);
    }
    if (isValid(arguments)) {
      for (String x : paths) {
        output = checkList(filesys, x, arguments.get(3), arguments.get(1));
      }
    }
    return output;
  }

  public String checkList(FileSystemI filesys, String path, String expression, String type) {
    expression = expression.substring(1, expression.length() - 1);
    String output = null;
    String[] currPath = {filesys.getCurrentPath()};
    Cd newPath = new Cd();
    String[] pathArr = {path};
    if (newPath.run(pathArr, filesys) != false) {
      ArrayList<Node> toCheck = filesys.getCurrent().getList();
      for (int i = 0; i < toCheck.size(); i++) {
        if (type.equals("d")) {
          if (toCheck.get(i).getisDir() && toCheck.get(i).getName().equals(expression)) {
            System.out.println(toCheck.get(i).getName());
          }
        } else if (type.equals("f")) {
          if (!toCheck.get(i).getisDir() && toCheck.get(i).getName().equals(expression)) {
            System.out.println(toCheck.get(i).getName());
          }
        }
      }
    } else {
      output = error.getError("Directory Not Found", pathArr[0]);
    }
    newPath.run(currPath, filesys);
    return output;


  }


  // public static void main(String[] args) {
  // Find f = new Find();
  // String[] g = {"/users/desktop", "/users/pics", "-type", "f", -name, "lmao"};
  // f.run(g, "/users/desktop /users/pics -type f -name "lmao"", false);
  // }


}
