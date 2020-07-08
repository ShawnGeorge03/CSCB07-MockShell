package commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import data.FileSystem;

public class TestCases {

  private FileSystem fs;

  private Cd cd;
  private Pwd pwd;
  private Mkdir mkdir;
  private Ls ls;
  private Echo echo;
  private Man man;
  private History history;
  private Cat cat;

  private String output;
  private String correctOuput;

  public TestCases() {
    this.fs = FileSystem.getFileSys();
    this.cd = new Cd();
    this.pwd = new Pwd();
    this.mkdir = new Mkdir();
    this.ls = new Ls();
    this.echo = new Echo();
    this.man = new Man();
    this.history = new History();
    this.cat = new Cat();
    setupEnviro();
  }

  public void setupEnviro() {

    String[] setupDirs = new String[1];

    // C Folder
    setupDirs[0] = "users";
    mkdir.MakeDirectory(setupDirs);
    setupDirs[0] = "pics";
    mkdir.MakeDirectory(setupDirs);
    setupDirs[0] = "Sys";
    mkdir.MakeDirectory(setupDirs);
    echo.run(setupDirs, "echo \"Wow what a project\" > A2");

    // users Folder
    setupDirs[0] = "C/users";
    cd.run(setupDirs, "cd C/users");
    setupDirs[0] = "desktop";
    mkdir.MakeDirectory(setupDirs);

    // desktop Folder
    setupDirs[0] = "C/users/desktop";
    cd.run(setupDirs, "cd C/users/desktop");
    echo.run(setupDirs, "echo \"Hello TA\" > CSCB07");
    echo.run(setupDirs, "echo \"2+2=5\" > Hwk");
    setupDirs[0] = "../../";
    cd.run(setupDirs, "cd ../../");

    // pics Folder
    echo.run(setupDirs, "echo \"this is a picturefile indeed\" > pics/picfile");
    setupDirs[0] = "pics";
    cd.run(setupDirs, "cd pics");
    echo.run(setupDirs, "echo \"Hello TA from the pics Folder\" > CSCB07");
    setupDirs[0] = "..";
    cd.run(setupDirs, "cd ..");

    // Sys Folder
    setupDirs[0] = "Sys";
    cd.run(setupDirs, "cd Sys");
    setupDirs[0] = "IO";
    mkdir.MakeDirectory(setupDirs);
    setupDirs[0] = "LOL";
    mkdir.MakeDirectory(setupDirs);

    // IO Folder
    setupDirs[0] = "IO";
    cd.run(setupDirs, "cd IO");
    setupDirs[0] = "keyboard";
    mkdir.MakeDirectory(setupDirs);
    setupDirs[0] = "Mouse";
    mkdir.MakeDirectory(setupDirs);

    // keyboard Folder
    setupDirs[0] = "keyboard";
    cd.run(setupDirs, "cd keyboard");
    echo.run(setupDirs, "echo \"QWERTY\" > keys");
    echo.run(setupDirs, "echo \"RGB == ways more      F    P   S\" > RGB");
    setupDirs[0] = "../";
    cd.run(setupDirs, "cd ../");

    // Mouse Folder
    setupDirs[0] = "Mouse";
    cd.run(setupDirs, "cd Mouse");
    echo.run(setupDirs, "echo \"Mouse is in Mouse Folder\" > Presses");

    // C Folder
    setupDirs[0] = "../../../";
    cd.run(setupDirs, "cd ../../../");

    // Setup History
    history.addCommands("mkdir users");
    history.addCommands("mkdir pics");
    history.addCommands("mkdir Sys");
    history.addCommands("echo \"Wow what a project\" > A2");
    history.addCommands("cd C/users");
    history.addCommands("mkdir desktop");

  }

  public void cdTestCases() {
    System.out.println("Testing Command: cd");

    String[] testCase1cd = new String[0];
    String[] testCase2cd = new String[1];
    String[] testCase3cd = new String[2];
    String[] inputArrayForPwd = {};

    // Case 1: No Input
    output = cd.run(testCase1cd, "cd");
    correctOuput = pwd.run(inputArrayForPwd, "pwd");

    if (correctOuput.contains("C") && output.equals("Error : No parameters provided : "))
      System.out.println("Case #1 Passed");
    else
      System.out.println("Case #1 Failed");

    // Case 2: Change Directory to pics
    testCase2cd[0] = "pics";

    output = cd.run(testCase2cd, "cd pics");
    correctOuput = pwd.run(inputArrayForPwd, "pwd");

    if (correctOuput.equals("C/pics") && output == null)
      System.out.println("Case #2 Passed");
    else
      System.out.println("Case #2 Failed");

    // Case 3: Change Directory back one
    testCase2cd[0] = "..";

    output = cd.run(testCase2cd, "cd ..");
    correctOuput = pwd.run(inputArrayForPwd, "pwd");

    if (correctOuput.equals("C") && output == null)
      System.out.println("Case #3 Passed");
    else
      System.out.println("Case #3 Failed");

    // Case 4: Change Directory to users
    testCase2cd[0] = "users/desktop";

    output = cd.run(testCase2cd, "cd users/desktop");
    correctOuput = pwd.run(inputArrayForPwd, "pwd");

    if (correctOuput.equals("C/users/desktop") && output == null)
      System.out.println("Case #4 Passed");
    else
      System.out.println("Case #4 Failed");

    // Case 5: Pattern -> ../..
    testCase2cd[0] = "../..";

    output = cd.run(testCase2cd, "cd ../..");
    correctOuput = pwd.run(inputArrayForPwd, "pwd");

    if (correctOuput.equals("C") && output == null)
      System.out.println("Case #5 Passed");
    else
      System.out.println("Case #5 Failed");

    // Case 6: Pattern -> Change directory to a file
    testCase2cd[0] = "A2";

    output = cd.run(testCase2cd, "cd A2");
    correctOuput = pwd.run(inputArrayForPwd, "pwd");

    if (correctOuput.equals("C") && output.equals("Error: Invalid Directory : A2"))
      System.out.println("Case #6 Passed");
    else
      System.out.println("Case #6 Failed");

    // Case 7: Changing directory to keyboard Folder
    testCase2cd[0] = "C/Sys/IO/keyboard";

    output = cd.run(testCase2cd, "cd C/Sys/IO/keyboard");
    correctOuput = pwd.run(inputArrayForPwd, "pwd");

    if (correctOuput.equals("C/Sys/IO/keyboard") && output == null)
      System.out.println("Case #7 Passed");
    else
      System.out.println("Case #7 Failed");

    // Case 8: Change directory to C
    testCase2cd[0] = "../../../";

    output = cd.run(testCase2cd, "cd ../../../");
    correctOuput = pwd.run(inputArrayForPwd, "pwd");

    if (correctOuput.equals("C") && output == null)
      System.out.println("Case #8 Passed");
    else
      System.out.println("Case #8 Failed");

    // Case 9: Multiple directory
    testCase3cd[0] = "C/pics";
    testCase3cd[1] = "C/Sys/LOL";

    output = cd.run(testCase3cd, "cd C/pics C/Sys/LoL");
    correctOuput = pwd.run(inputArrayForPwd, "pwd");

    if (correctOuput.equals("C")
        && output.equals("Error : Multiple Parameters have been provided : C/pics C/Sys/LoL"))
      System.out.println("Case #9 Passed\n");
    else
      System.out.println("Case #9 Failed\n");
  }

  public void manTestCases() {

    String output;
    String[] testInput = {"ls"};

    System.out.println("Testing Command: man");

    // Case 1: Get the documentation for ls
    output = man.run(testInput, "man");

    if (output.contains("Command: ls")) {
      System.out.println("Case #1 Passed");
    } else {
      System.out.println("Case #1 Failed");
    }

    // Case 2: Get documentation for invalid input
    testInput[0] = "false command";
    output = man.run(testInput, "man");

    if (output.contains("Invalid Argument")) {
      System.out.println("Case #2 Passed");
    } else {
      System.out.println("Case #2 Failed");
    }

    // Case 3: Get documentation for multiple arguments (not supported)
    String[] multipleArguments = {"ls", "cd"};
    output = man.run(testInput, "man");

    if (output.contains("Invalid Argument")) {
      System.out.println("Case #3 Passed\n");
    } else {
      System.out.println("Case #3 Failed\n");
    }
  }

  public void mkdirTestCases() {
    System.out.println("Testing Command: mkdir");

    // Case 1: Make a directory under root directory
    String[] input = {"users"};
    mkdir.run(input, "mkdir " + input[0]);

    ArrayList<String> allContent = new ArrayList<String>();

    for (int i = 0; i < fs.getCurrent().getList().size(); i++) {
      allContent.add(fs.getCurrent().getList().get(i).getName());
    }

    if (allContent.contains(input[0])) {
      System.out.println("Case #1 Passed");
    } else {
      System.out.println("Case #1 Failed");
    }

    allContent.clear();

    // Case 2: Make a directory using absolute path
    input[0] = "C/users/newUser";
    mkdir.run(input, "mkdir " + input[0]);

    String[] enterDirectoryAbove = {"C/users"};
    cd.run(enterDirectoryAbove);

    for (int i = 0; i < fs.getCurrent().getList().size(); i++) {
      allContent.add(fs.getCurrent().getList().get(i).getName());
    }

    if (allContent.contains("newUser")) {
      System.out.println("Case #2 Passed");
    } else {
      System.out.println("Case #2 Failed");
    }

    allContent.clear();

    // Case 3: Make a directory using relative path
    input[0] = "users/newUser2";

    mkdir.run(input, "mkdir " + input[0]);
    cd.run(enterDirectoryAbove);

    for (int i = 0; i < fs.getCurrent().getList().size(); i++) {
      allContent.add(fs.getCurrent().getList().get(i).getName());
    }

    if (allContent.contains("newUser2")) {
      System.out.println("Case #3 Passed\n");
    } else {
      System.out.println("Case #3 Failed\n");
    }

  }

  public void historyTestCases() {
    System.out.println("Testing Command: history");
    
    String[] testCase1history = {};

    HashMap<Integer, String[]> historyInputMap = new HashMap<Integer, String[]>();

    historyInputMap.put(0, testCase1history);
    historyInputMap.put(1, "100".split(" "));
    historyInputMap.put(2, "5".split(" "));
    historyInputMap.put(3, "-3".split(" "));
    historyInputMap.put(4, "1.0".split(" "));
    historyInputMap.put(5, "hello".split(" "));
    historyInputMap.put(6, "1 3 5".split(" "));
    
    HashMap<Integer, String> historyCommandMap = new HashMap<Integer, String>();

    historyCommandMap.put(0, "history");
    historyCommandMap.put(1, "history 100");
    historyCommandMap.put(2, "history 5");
    historyCommandMap.put(3, "history -3");
    historyCommandMap.put(4, "history 1.0");
    historyCommandMap.put(5, "history hello");
    historyCommandMap.put(6, "history 1 3 5");

    HashMap<Integer, String> historyCorrectOutputMap = new HashMap<Integer, String>();

    historyCorrectOutputMap.put(0,
        "1. mkdir users\n" + "2. mkdir pics\n" + "3. mkdir Sys\n"
            + "4. echo \"Wow what a project\" > A2\n" + "5. cd C/users\n" + 
            "6. mkdir desktop\n" + "7. history");
    historyCorrectOutputMap.put(1,
        "1. mkdir users\n" + "2. mkdir pics\n" + "3. mkdir Sys\n"
            + "4. echo \"Wow what a project\" > A2\n" + "5. cd C/users\n" + 
            "6. mkdir desktop\n" + "7. history\n" + "8. history 100");
    historyCorrectOutputMap.put(2,
        "5. cd C/users\n" + "6. mkdir desktop\n" + "7. history\n" + 
            "8. history 100\n" + "9. history 5");
    historyCorrectOutputMap.put(3, "Invalid Argument : -3");
    historyCorrectOutputMap.put(4, "Invalid Argument : 1.0");
    historyCorrectOutputMap.put(5, "Invalid Argument : hello");
    historyCorrectOutputMap.put(6, 
        "Error : Multiple Parameters have been provided : 1 3 5");
    
    for(int i = 0; i < historyInputMap.size(); i++) {
      history.addCommands(historyCommandMap.get(i));
      output = history.run(historyInputMap.get(i), "history");

      if (output.contains(historyCorrectOutputMap.get(i)))
        System.out.println("Case # " + (i+1) +"  : Passed");
      else
        System.out.println("Case # " + (i+1) +"  : Failed");
    }
    System.out.println();
  }

  public void pwdTestCases() {
    System.out.println("Testing Command: pwd");

    String[] testCase1_cd = {};
    String[] testCase1_pwd = {};

    String[] testCase2_cd = new String[1];
    String[] testCase2_pwd = new String[2];

    // Case 1: Startup on FileSystem
    output = cd.run(testCase1_cd, "cd");
    correctOuput = pwd.run(testCase1_pwd, "pwd");

    if (correctOuput.contains("C"))
      System.out.println("Case #1 Passed");
    else
      System.out.println("Case #1 Failed");

    // Case 2: Change Directory
    testCase2_cd[0] = "C/Sys/IO/keyboard";

    output = cd.run(testCase2_cd, "cd pics");
    correctOuput = pwd.run(testCase1_pwd, "pwd");

    if (correctOuput.equals(testCase2_cd[0]))
      System.out.println("Case #2 Passed");
    else
      System.out.println("Case #2 Failed");

    // Case 3: Change directory back to C
    testCase2_cd[0] = "../../../";

    output = cd.run(testCase2_cd, "cd ../../../");
    correctOuput = pwd.run(testCase1_pwd, "pwd");

    if (correctOuput.equals("C") && output == null)
      System.out.println("Case #3 Passed");
    else
      System.out.println("Case #3 Failed");

    // Case 4: Multiple arguments given
    testCase2_cd[0] = "C";
    testCase2_pwd[0] = "C/Sys/LOL";
    testCase2_pwd[1] = "C/pics";

    output = cd.run(testCase2_cd, "cd C");
    correctOuput = pwd.run(testCase2_pwd, "pwd");

    if (correctOuput.equals("Error: Invalid Argument : pwd doesn't take any arguments"))
      System.out.println("Case #4 Passed\n");
    else
      System.out.println("Case #4 Failed\n");
  }

  public void catTestCases() {
    System.out.println("Testing Command : cat");
    
    String[] testCase1cat = {};
    
    HashMap<Integer, String[]> catInputMap = new HashMap<Integer, String[]>();

    catInputMap.put(0, testCase1cat);
    catInputMap.put(1, "C/pics/picflex".split(" "));
    catInputMap.put(2, "C/pics/picfile".split(" "));
    catInputMap.put(3, "A0".split(" "));
    catInputMap.put(4, "A2".split(" "));
    catInputMap.put(5, "C/users/desktop/CSCB07 A2".split(" "));
    catInputMap.put(6, "C/users/desktop/Hwk C/Sys/LOL C/Sys/IO/keyboard/RGB".split(" "));
    
    HashMap<Integer, String> catCommandMap = new HashMap<Integer, String>();

    catCommandMap.put(0, "cat");
    catCommandMap.put(1, "cat C/pics/picflex");
    catCommandMap.put(2, "cat C/pics/picfile");
    catCommandMap.put(3, "cat A0");
    catCommandMap.put(4, "cat A2");
    catCommandMap.put(5, "cat C/users/desktop/CSCB07 A2");
    catCommandMap.put(6, "cat C/users/desktop/Hwk C/Sys/LOL C/Sys/IO/keyboard/RGB");
    
    HashMap<Integer, String> catCorrectOutputMap = new HashMap<Integer, String>();

    catCorrectOutputMap.put(0, "Error : No parameters provided : ");
    catCorrectOutputMap.put(1, "Error: File Not Found : C/pics/picflex");
    catCorrectOutputMap.put(2, "this is a picturefile indeed");
    catCorrectOutputMap.put(3, "Error: File Not Found : A0");
    catCorrectOutputMap.put(4, "Wow what a project");
    catCorrectOutputMap.put(5, "Hello TA"
        + "\n" 
        + "\n" 
        + "\n" 
        + "Wow what a project");
    catCorrectOutputMap.put(6,"2+2=5" 
        + "\n" 
        + "\n" 
        + "\n" 
        + "Error: File Not Found : C/Sys/LOL"
        + "\n" 
        + "\n" 
        + "\n"
        + "RGB == ways more      F    P   S");
    
    for(int i = 0; i < catInputMap.size(); i++) {
      output = cat.run(catInputMap.get(i), "history");

      if (output.contains(catCorrectOutputMap.get(i)))
        System.out.println("Case # " + (i+1) +"  : Passed");
      else
        System.out.println("Case # " + (i+1) +"  : Failed");
    }
    System.out.println();
  }

  public void lsTestCases() {
    Cd toRoot = new Cd();
    String[] root = {"C"};
    cd.run(root);
    
    
    System.out.println("Testing Command : ls");
    //Case 1: Testing ls in root directory
    Ls test = new Ls();
    String[] input = {"/"};
    String output = test.run(input, "ls");

    if (output.equals("users|pics|Sys|A2|")) {
        System.out.println("Case #1 Passed");
    }else {
        System.out.println("Case #1 Failed");
    }
    
    //Case 2: Testing with Absolute path specified
    input[0] = "C/users";
    
    output = test.run(input, "ls " + input[0]);
    
    if (output.equals("desktop|newUser|")) {
        System.out.println("Case #2 Passed");
    } else {
        System.out.println("Case #2 Failed");
    }
    
    //Case 3: Testing with Relative path specified
    
    input[0] = "users/desktop";
    
    output = test.run(input, "ls " + input[0]);

    if (output.equals("CSCB07|Hwk|")) {
        System.out.println("Case #3 Passed");
    } else {
        System.out.println("Case #3 Failed");
    }
    
    //Case 4: Testing with multiple arguments
    String[] multipleArguments = {"C/users", "users/desktop"};
    
    output = test.run(multipleArguments, "ls " + multipleArguments[0] + " " + multipleArguments[1]);
    
    if (output.equals("Error : Multiple Parameters have been provided : Expecting 0 or 1 parameter")) {
        System.out.println("Case #4 Passed");
    } else {
        System.out.println("Case #4 Failed");
    }
    
    //Case 5: Giving an invalid path
    input[0] = "definitelyfalsecase";
    
    output = test.run(input, "ls " + input[0]);

    if (output.equals("Error: Invalid Directory : definitelyfalsecaseis not a valid directory")) {
        System.out.println("Case #5 Passed");
    } else {
        System.out.println("Case #5 Failed");
    }
    
    System.out.println();
}
  
  public void pushAndPopTestCases() {
    Push testPush = new Push();
    Pop testPop = new Pop();
    
    System.out.println("Testing Command : pushd & popd");
    
    //Case #1 Pushing an absolute file path onto the stack
    String[] input = {"C/users"};
    String output = testPush.run(input, "pushd " + input[0]);
    
    if (output.equals("C/users")) {
        System.out.println("Case #1 Passed");
    } else {
        System.out.println("Case #1 Failed");
    }
    
    //Case #2 Pushing a relative file path onto the stack
    input[0] = "users/desktop";
    
    output = testPush.run(input, "pushd " + input[0]);
    System.out.println(output);
    if (output.equals("C/users")) {
        System.out.println("Case #1 Passed");
    } else {
        System.out.println("Case #1 Failed");
    }
}
  
  public void echoTestCases() {
    System.out.println("Testing Command : echo");
    
    String[] testCase1cat = {};
    
    HashMap<Integer, String[]> echoInputMap = new HashMap<Integer, String[]>();

    echoInputMap.put(0, testCase1cat);
    echoInputMap.put(1, "echo \"Hello\"".split(" "));
    echoInputMap.put(2, "echo \"Hello".split(" "));
    echoInputMap.put(3, "echo Hello".split(" "));
    echoInputMap.put(4, "echo \"Hello\" >".split(" "));
    echoInputMap.put(5, "echo \"Hello\" >>".split(" "));
    echoInputMap.put(6, "echo \"Hello\" > file".split(" "));
    echoInputMap.put(7, "echo \"Bye\" >> file".split(" "));
    echoInputMap.put(8, "echo \"Bye\" >> file".split(" "));
    echoInputMap.put(9,"echo \"KeyWASD\" > C/Sys/IO/keyboard/keys".split(" "));
    echoInputMap.put(10,"echo \"QWERTY\" >> C/Sys/IO/keyboard/keys".split(" "));
    
    HashMap<Integer, String> echoCommandMap = new HashMap<Integer, String>();

    echoCommandMap.put(0, "echo");
    echoCommandMap.put(1, "echo \"Hello\"");
    echoCommandMap.put(2, "echo \"Hello");
    echoCommandMap.put(3, "echo Hello");
    echoCommandMap.put(4, "echo \"Hello\" >");
    echoCommandMap.put(5, "echo \"Hello\" >>");
    echoCommandMap.put(6, "echo \"Hello\" > file");
    echoCommandMap.put(7, "echo \"Bye\" >> file");
    echoCommandMap.put(8, "echo \"okay\" > file");
    echoCommandMap.put(9,"echo \"KeyWASD\" > C/Sys/IO/keyboard/keys");
    echoCommandMap.put(10,"echo \"QWERTY\" >> C/Sys/IO/keyboard/keys");
    
    HashMap<Integer, String> echoCorrectOutputMap = new HashMap<Integer, String>(); 
    
    echoCorrectOutputMap.put(0, "Error : No parameters provided : ");
    echoCorrectOutputMap.put(1, "Hello");
    echoCorrectOutputMap.put(2, "Error : Missing Quotes : echo \"Hello");
    echoCorrectOutputMap.put(3, "Error : Missing Quotes : echo Hello");
    echoCorrectOutputMap.put(4, "Error: Invalid File : echo \"Hello\" >");
    echoCorrectOutputMap.put(5, "Error: Invalid File : echo \"Hello\" >>");
    echoCorrectOutputMap.put(6, "Hello");
    echoCorrectOutputMap.put(7, "Hello" + "\n" + "Bye");
    echoCorrectOutputMap.put(8, "okay");
    echoCorrectOutputMap.put(9, "KeyWASD");
    echoCorrectOutputMap.put(10, "QWERTY");
    

    HashMap<Integer, String[]> catInputMap = new HashMap<Integer, String[]>(); 
    catInputMap.put(6, "file".split(" "));
    catInputMap.put(7, "file".split(" ")); 
    catInputMap.put(8, "file".split(" ")); 
    catInputMap.put(9, "C/Sys/IO/keyboard/keys".split(" ")); 
    catInputMap.put(10, "C/Sys/IO/keyboard/keys".split(" ")); 

   
    for(int i = 0; i < echoInputMap.size(); i++) {
      System.out.println(Arrays.toString(echoInputMap.get(i)) + "  " + echoCommandMap.get(i));

      output = echo.run(echoInputMap.get(i), echoCommandMap.get(i));

      if(i < 6) {
        if (output.contains(echoCorrectOutputMap.get(i)))
          System.out.println("Case # " + (i) +"  : Passed LOL");
        else
          System.out.println("Case # " + (i) +"  : Failed");
      }else {
       String catOutput = cat.run(catInputMap.get(i), echoCommandMap.get(i));
       System.out.println(output + " | " + catOutput);
        if (output == null && catOutput.equals(echoCorrectOutputMap.get(i)))
          System.out.println("Case # " + (i) +"  : Passed");
        else
          System.out.println("Case # " + (i) +"  : Failed");
      } 
    }
    System.out.println();

  }
}
