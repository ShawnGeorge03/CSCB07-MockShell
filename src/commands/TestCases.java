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
    echo.run(setupDirs, "echo \"Wow what a project\" > A2", false);

    // users Folder
    setupDirs[0] = "C/users";
    cd.run(setupDirs, "cd C/users", false);
    setupDirs[0] = "desktop";
    mkdir.MakeDirectory(setupDirs);

    // desktop Folder
    setupDirs[0] = "C/users/desktop";
    cd.run(setupDirs, "cd C/users/desktop", false);
    echo.run(setupDirs, "echo \"Hello TA\" > CSCB07", false);
    echo.run(setupDirs, "echo \"2+2=5\" > Hwk", false);
    setupDirs[0] = "../../";
    cd.run(setupDirs, "cd ../../", false);

    // pics Folder
    echo.run(setupDirs, "echo \"this is a picturefile indeed\" > pics/picfile", false);
    setupDirs[0] = "pics";
    cd.run(setupDirs, "cd pics", false);
    echo.run(setupDirs, "echo \"Hello TA from the pics Folder\" > CSCB07", false);
    setupDirs[0] = "..";
    cd.run(setupDirs, "cd ..", false);

    // Sys Folder
    setupDirs[0] = "Sys";
    cd.run(setupDirs, "cd Sys", false);
    setupDirs[0] = "IO";
    mkdir.MakeDirectory(setupDirs);
    setupDirs[0] = "LOL";
    mkdir.MakeDirectory(setupDirs);

    // IO Folder
    setupDirs[0] = "IO";
    cd.run(setupDirs, "cd IO", false);
    setupDirs[0] = "keyboard";
    mkdir.MakeDirectory(setupDirs);
    setupDirs[0] = "Mouse";
    mkdir.MakeDirectory(setupDirs);

    // keyboard Folder
    setupDirs[0] = "keyboard";
    cd.run(setupDirs, "cd keyboard", false);
    echo.run(setupDirs, "echo \"QWERTY\" > keys", false);
    echo.run(setupDirs, "echo \"RGB == ways more      F    P   S\" > RGB", false);
    setupDirs[0] = "../";
    cd.run(setupDirs, "cd ../", false);

    // Mouse Folder
    setupDirs[0] = "Mouse";
    cd.run(setupDirs, "cd Mouse", false);
    echo.run(setupDirs, "echo \"Mouse is in Mouse Folder\" > Presses", false);

    // C Folder
    setupDirs[0] = "../../../";
    cd.run(setupDirs, "cd ../../../", false);

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
    
    String[] emptyArr = {};
    
    HashMap<Integer, String[]> cdInputMap = new  HashMap<Integer, String[]>();
    
    cdInputMap.put(0, emptyArr);      //Error
    cdInputMap.put(1, "pics".split(" "));
    cdInputMap.put(2, "..".split(" "));   
    cdInputMap.put(3, "users/desktop".split(" "));
    cdInputMap.put(4, "../..".split(" "));
    cdInputMap.put(5, "A2".split(" "));    //Error
    cdInputMap.put(6, "C/Sys/IO/keyboard".split(" "));
    cdInputMap.put(7, "../../../".split(" "));
    cdInputMap.put(8, "C/pics C/Sys/LOL".split(" "));
    
    HashMap<Integer, String> cdCommandMap = new  HashMap<Integer, String>();
    
    cdCommandMap.put(0, "cd ");      //Error
    cdCommandMap.put(1, "cd pics");
    cdCommandMap.put(2, "cd ..");   
    cdCommandMap.put(3, "cd users/desktop");
    cdCommandMap.put(4, " cd ../..");
    cdCommandMap.put(5, "cd A2");    //Error
    cdCommandMap.put(6, "cd C/Sys/IO/keyboard");
    cdCommandMap.put(7, "cd ../../../");
    cdCommandMap.put(8, "cd C/pics C/Sys/LOL");
    
    HashMap<Integer, String> cdCorrectOutputMap = new  HashMap<Integer, String>();
    
    cdCorrectOutputMap.put(0, "Error : No parameters provided : ");      //Error
    cdCorrectOutputMap.put(1, null);
    cdCorrectOutputMap.put(2, null);   
    cdCommandMap.put(3, null);
    cdCorrectOutputMap.put(4, null);
    cdCorrectOutputMap.put(5, "Error: Invalid Directory : A2");    //Error
    cdCorrectOutputMap.put(6, null);
    cdCorrectOutputMap.put(7, null);
    cdCorrectOutputMap.put(8, "Error : Multiple Parameters have been provided : C/pics C/Sys/LOL");
    
    HashMap<Integer, String> pwdOutputMap = new  HashMap<Integer, String>();
    
    pwdOutputMap.put(0, "C");      //Error
    pwdOutputMap.put(1, "C/pics");
    pwdOutputMap.put(2, "C");   
    pwdOutputMap.put(3, "C/users/desktop");
    pwdOutputMap.put(4, "C");
    pwdOutputMap.put(5, "C");    //Error
    pwdOutputMap.put(6, "C/Sys/IO/keyboard");
    pwdOutputMap.put(7, "C");
    pwdOutputMap.put(8, "C");

    for(int i = 0; i < cdInputMap.size(); i++) {
      output = cd.run(cdInputMap.get(i),cdCommandMap.get(i) , false);
      correctOuput = pwd.run(emptyArr, "pwd", false);
      
        if (correctOuput.contains(pwdOutputMap.get(i)) && 
            (output == cdCorrectOutputMap.get(i) || output.equals(cdCorrectOutputMap.get(i))))
          System.out.println("Case #" + (i+1) + " Passed");
        else
          System.out.println("Case #" + (i+1) + " Failed");     
    }
    System.out.println();
    
   }

  public void manTestCases() {

    String output;
    String[] testInput = {"ls"};

    System.out.println("Testing Command: man");

    // Case 1: Get the documentation for ls
    output = man.run(testInput, "man", false);

    if (output.contains("Command: ls")) {
      System.out.println("Case #1 Passed");
    } else {
      System.out.println("Case #1 Failed");
    }

    // Case 2: Get documentation for invalid input
    testInput[0] = "false command";
    output = man.run(testInput, "man", false);

    if (output.contains("Invalid Argument")) {
      System.out.println("Case #2 Passed");
    } else {
      System.out.println("Case #2 Failed");
    }

    // Case 3: Get documentation for multiple arguments (not supported)
    String[] multipleArguments = {"ls", "cd"};
    output = man.run(testInput, "man", false);

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
    mkdir.run(input, "mkdir " + input[0],false);

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
    mkdir.run(input, "mkdir " + input[0],false);

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
    String[] root = {FileSystem.getFileSys().getRoot().getName()};
    cd.run(root);
    input[0] = "users/newUser2";

    String output = mkdir.run(input, "mkdir " + input[0],false);
    cd.run(enterDirectoryAbove);

    for (int i = 0; i < fs.getCurrent().getList().size(); i++) {
      allContent.add(fs.getCurrent().getList().get(i).getName());
    }

    if (allContent.contains("newUser2")) {
      System.out.println("Case #3 Passed");
    } else {
      System.out.println("Case #3 Failed");
    }
    
    //Case 4: Make a directory that already exists
    input[0] = "users/newUser2";
    cd.run(root);
    output = mkdir.run(input, "mkdir " + input[0],false);
    
    if (output.equals("Error: Same Directory with that name already exists! : users already exists")) {
      System.out.println("Case #4 Passed");
    } else {
      System.out.println("Case #4 Failed");
    }
    
    //Case 5: Make a directory using an invalid pathname
    input[0] = "deifnitelyfalse/newUser2";
    output = mkdir.run(input, "mkdir " + input[0],false);
    
    if (output.equals("Error: Invalid Directory : deifnitelyfalse is not a valid directory")) {
      System.out.println("Case #5 Passed");
    } else {
      System.out.println("Case #5 Failed");
    }
    
    System.out.println();
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
      output = history.run(historyInputMap.get(i), "history", false);

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
    output = cd.run(testCase1_cd, "cd", false);
    correctOuput = pwd.run(testCase1_pwd, "pwd", false);

    if (correctOuput.contains("C"))
      System.out.println("Case #1 Passed");
    else
      System.out.println("Case #1 Failed");

    // Case 2: Change Directory
    testCase2_cd[0] = "C/Sys/IO/keyboard";

    output = cd.run(testCase2_cd, "cd pics", false);
    correctOuput = pwd.run(testCase1_pwd, "pwd", false);

    if (correctOuput.equals(testCase2_cd[0]))
      System.out.println("Case #2 Passed");
    else
      System.out.println("Case #2 Failed");

    // Case 3: Change directory back to C
    testCase2_cd[0] = "../../../";

    output = cd.run(testCase2_cd, "cd ../../../", false);
    correctOuput = pwd.run(testCase1_pwd, "pwd", false);

    if (correctOuput.equals("C") && output == null)
      System.out.println("Case #3 Passed");
    else
      System.out.println("Case #3 Failed");

    // Case 4: Multiple arguments given
    testCase2_cd[0] = "C";
    testCase2_pwd[0] = "C/Sys/LOL";
    testCase2_pwd[1] = "C/pics";

    output = cd.run(testCase2_cd, "cd C", false);
    correctOuput = pwd.run(testCase2_pwd, "pwd", false);

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
      output = cat.run(catInputMap.get(i), "history", false);

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
    String output = test.run(input, "ls", false);

    if (output.equals("users\npics\nSys\nA2")) {
        System.out.println("Case #1 Passed");
    }else {
        System.out.println("Case #1 Failed");
    }
    
    //Case 2: Testing with Absolute path specified
    input[0] = "C/users";
    
    output = test.run(input, "ls " + input[0], false);
    if (output.equals("desktop\nnewUser\nnewUser2")) {
        System.out.println("Case #2 Passed");
    } else {
        System.out.println("Case #2 Failed");
    }
    
    //Case 3: Testing with Relative path specified
    
    input[0] = "users/desktop";
    
    output = test.run(input, "ls " + input[0], false);

    if (output.equals("CSCB07\nHwk")) {
        System.out.println("Case #3 Passed");
    } else {
        System.out.println("Case #3 Failed");
    }
    
    //Case 4: Testing with multiple arguments
    String[] multipleArguments = {"C/users", "users/desktop"};
    
    output = test.run(multipleArguments, "ls " + multipleArguments[0] + " " + multipleArguments[1] , false);
    
    if (output.equals("Error : Multiple Parameters have been provided : Expecting 0 or 1 parameter")) {
        System.out.println("Case #4 Passed");
    } else {
        System.out.println("Case #4 Failed");
    }
    
    //Case 5: Giving an invalid path
    input[0] = "definitelyfalsecase";
    
    output = test.run(input, "ls " + input[0], false);

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
    String output = testPush.run(input, "pushd " + input[0], false);
    
    if (output.equals("C/users")) {
        System.out.println("Case #1 Passed");
    } else {
        System.out.println("Case #1 Failed");
    }
    
    //Case #2 Pushing a relative file path onto the stack
    input[0] = "desktop";
    
    output = testPush.run(input, "pushd " + input[0], false);
    if (output.equals("desktop")) {
        System.out.println("Case #2 Passed");
    } else {
        System.out.println("Case #2 Failed");
    }
    
    //Case #3 Giving multiple arguments
    String[] multipleArguments = {"C/users", "C/users/desktop"};
    
    output = testPush.run(multipleArguments, "pushd " + multipleArguments[0] + " " + multipleArguments[1], false);
    if (output.equals("Error: Invalid Argument : 2 arguments, expecting 1 argument")) {
        System.out.println("Case #3 Passed");
    } else {
        System.out.println("Case #3 Failed");
    }
    
    //Case #4 Pushing invalid directory
    input[0] = "definitelyFalsepath";
    
    output = testPush.run(input, "pushd " + input[0], false);
    if (output.equals("Error: Invalid Directory : definitelyFalsepathis not a valid directory")) {
        System.out.println("Case #4 Passed");
    } else {
        System.out.println("Case #4 Failed");
    }
    
    //Case #5 Popping back into first pushed directory
    output = testPop.pop();
    if (output.equals("C/users")) {
    	 System.out.println("Case #5 Passed");
    }else {
    	System.out.println("Case #5 Failed");
    }
    
    //Case #6 Popping into last directory in stack
    output = testPop.pop();
    if (output.equals("C")) {
    	 System.out.println("Case #6 Passed");
    }else {
    	System.out.println("Case #6 Failed");
    }
    
  //Case #6 Popping an empty stack
    output = testPop.pop();
    if (output.equals("Stack is empty")) {
    	 System.out.println("Case #7 Passed");
    }else {
    	System.out.println("Case #7 Failed");
    }
    
    System.out.println();
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
      output = echo.run(echoInputMap.get(i), echoCommandMap.get(i), false);
      if(i < 6) {
        if (output.contains(echoCorrectOutputMap.get(i)))
          System.out.println("Case # " + (i+1) +"  : Passed");
        else
          System.out.println("Case # " + (i+1) +"  : Failed");
      }else {
       String catOutput = cat.run(catInputMap.get(i), echoCommandMap.get(i), false);
        if (output == null && catOutput.equals(echoCorrectOutputMap.get(i)))
          System.out.println("Case # " + (i+1) +"  : Passed");
        else
          System.out.println("Case # " + (i+1) +"  : Failed");
      } 
    }
    System.out.println();

  }
}
