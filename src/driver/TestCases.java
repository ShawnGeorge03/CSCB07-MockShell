// **********************************************************
// Assignment2:
// Student1:
// UTORID user_name: patelt26
// UT Student #: 1005904103
// Author: Shawn Santhoshgeorge
//
// Student2:
// UTORID user_name: shaiskan
// UT Student #: 1006243940
// Author: Keshavaa Shaiskandan
//
// Student3:
// UTORID user_name: patelt26
// UT Student #: 1005904103
// Author: Tirth Patel
//
// Student4:
// UTORID user_name: pate1101
// UT Student #: 1006315765
// Author: Abhay Patel
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC B07 and understand the consequences.
// *********************************************************
package driver;

import java.util.ArrayList;
import java.util.HashMap;
import commands.*;
import data.FileSystem;

/**
 * Class TestCases is responsible for testing all the commands
 */
public class TestCases {

  /**
   * Declare instance of FileSystem so we can access the filesystem
   */
  private FileSystem fs;
  
  /**
   * Declare instance of Cd to test Cd
   */
  private Cd cd;
  
  /**
   * Declare instance of Pwd to test pwd
   */
  private Pwd pwd;
  
  /**
   * Declare instance of Mkdir to test mkdir
   */
  private Mkdir mkdir;
  
  /**
   * Declare instance of Ls to test ls
   */
  private Ls ls;
  
  /**
   * Declare instance of Echo to test echo
   */
  private Echo echo;
  
  /**
   * Declare instance of Man to test man
   */
  private Man man;
  
  /**
   * Declare instance of History to test history
   */
  private History history;
  
  /**
   * Declare instance of Cat to test cat
   */
  private Cat cat;

  /**
   * Declare instance of a String object called output 
   */
  private String output;
  
  /**
   * Declare instance of a String object called correctOuput 
   */
  private String correctOuput;

  /**
   * Constructor for TestCases that sets up the test environment and initializes 
   * the instance variables
   */
  public TestCases() {
    //Get the current FileSystem
    this.fs = FileSystem.getFileSys();
    // Initializes a Cd Object
    this.cd = new Cd();
    // Initializes a Pwd Object
    this.pwd = new Pwd();
    // Initializes a mkdir Object
    this.mkdir = new Mkdir();
    // Initializes a ls Object
    this.ls = new Ls();
    // Initializes a echo Object
    this.echo = new Echo();
    // Initializes a man Object
    this.man = new Man();
    // Initializes a history Object
    this.history = new History();
    // Initializes a cat Object
    this.cat = new Cat();
    //Calls the function to setup the test environment
    setupEnviro();
  }

  private void setupEnviro() {
    
    //Declares and initializes a String array of size 1
    String[] setupDirs = new String[1];

    //Sets up the C Folder
    setupDirs[0] = "users";
    mkdir.MakeDirectory(setupDirs);
    setupDirs[0] = "pics";
    mkdir.MakeDirectory(setupDirs);
    setupDirs[0] = "Sys";
    mkdir.MakeDirectory(setupDirs);
    echo.run(setupDirs, "echo \"Wow what a project\" > A2", false);

    //Sets up the users Folder
    setupDirs[0] = "C/users";
    cd.run(setupDirs, "cd C/users", false);
    setupDirs[0] = "desktop";
    mkdir.MakeDirectory(setupDirs);

    //Sets up the desktop Folder
    setupDirs[0] = "C/users/desktop";
    cd.run(setupDirs, "cd C/users/desktop", false);
    echo.run(setupDirs, "echo \"Hello TA\" > CSCB07", false);
    echo.run(setupDirs, "echo \"2+2=5\" > Hwk", false);
    setupDirs[0] = "../../";
    cd.run(setupDirs, "cd ../../", false);

    //Sets up the pics Folder
    echo.run(setupDirs, "echo \"this is a picturefile indeed\" > pics/picfile",
        false);
    setupDirs[0] = "pics";
    cd.run(setupDirs, "cd pics", false);
    echo.run(setupDirs, "echo \"Hello TA from the pics Folder\" > CSCB07",
        false);
    setupDirs[0] = "..";
    cd.run(setupDirs, "cd ..", false);

    //Sets up the Sys Folder
    setupDirs[0] = "Sys";
    cd.run(setupDirs, "cd Sys", false);
    setupDirs[0] = "IO";
    mkdir.MakeDirectory(setupDirs);
    setupDirs[0] = "LOL";
    mkdir.MakeDirectory(setupDirs);

    //Sets up the IO Folder
    setupDirs[0] = "IO";
    cd.run(setupDirs, "cd IO", false);
    setupDirs[0] = "keyboard";
    mkdir.MakeDirectory(setupDirs);
    setupDirs[0] = "Mouse";
    mkdir.MakeDirectory(setupDirs);

    //Sets up the keyboard Folder
    setupDirs[0] = "keyboard";
    cd.run(setupDirs, "cd keyboard", false);
    echo.run(setupDirs, "echo \"QWERTY\" > keys", false);
    echo.run(setupDirs, "echo \"RGB == ways more      F    P   S\" > RGB",
        false);
    setupDirs[0] = "../";
    cd.run(setupDirs, "cd ../", false);

    //Sets up the Mouse Folder
    setupDirs[0] = "Mouse";
    cd.run(setupDirs, "cd Mouse", false);
    echo.run(setupDirs, "echo \"Mouse is in Mouse Folder\" > Presses", false);

    //Sets up the C Folder
    setupDirs[0] = "../../../";
    cd.run(setupDirs, "cd ../../../", false);

    //Setups the  History
    history.addCommands("mkdir users");
    history.addCommands("mkdir pics");
    history.addCommands("mkdir Sys");
    history.addCommands("echo \"Wow what a project\" > A2");
    history.addCommands("cd C/users");
    history.addCommands("mkdir desktop");
  }

  /**
   * Runs all the test cases
   */
  public void runTestCases() {
    cdTestCases();
    manTestCases();
    mkdirTestCases();
    historyTestCases();
    pwdTestCases();
    catTestCases();
    lsTestCases();
    pushAndPopTestCases();
    echoTestCases();
  }

  private void cdTestCases() {
    System.out.println("Testing Command: cd");

    String[] emptyArr = {};

    //Declares and initializes the cdInputMap as a HashMap Object
    HashMap<Integer, String[]> cdInputMap = new HashMap<Integer, String[]>();

    //Adds the respective key and value to the cdInputMap
    cdInputMap.put(0, emptyArr); // Not input given
    cdInputMap.put(1, "pics".split(" ")); //Relative directory
    cdInputMap.put(2, "..".split(" ")); //Going back one folder
    cdInputMap.put(3, "users/desktop".split(" "));//Relative Path to a directory
    cdInputMap.put(4, "../..".split(" ")); //Going back two directories
    cdInputMap.put(5, "A2".split(" ")); //Going to a traverse to a File
    cdInputMap.put(6, "C/Sys/IO/keyboard".split(" ")); //Absolute Path
    cdInputMap.put(7, "../../../".split(" ")); //Going back to root
    cdInputMap.put(8, "C/pics C/Sys/LOL".split(" ")); //Multiple Input

    //Declares and initializes the cdCommandMap as a HashMap Object
    HashMap<Integer, String> cdCommandMap = new HashMap<Integer, String>();

    //Adds the respective key and value to the cdCommandMap
    cdCommandMap.put(0, "cd "); // Error
    cdCommandMap.put(1, "cd pics");
    cdCommandMap.put(2, "cd ..");
    cdCommandMap.put(3, "cd users/desktop");
    cdCommandMap.put(4, " cd ../..");
    cdCommandMap.put(5, "cd A2"); // Error
    cdCommandMap.put(6, "cd C/Sys/IO/keyboard");
    cdCommandMap.put(7, "cd ../../../");
    cdCommandMap.put(8, "cd C/pics C/Sys/LOL");

    //Declares and initializes the cdCorrectOutputMap as a HashMap Object
    HashMap<Integer, String> cdCorrectOutputMap =
        new HashMap<Integer, String>();

    //Adds the respective key and value to the cdCorrectOutputMap
    cdCorrectOutputMap.put(0, "Error : No parameters provided : "); // Error
    cdCorrectOutputMap.put(1, null);
    cdCorrectOutputMap.put(2, null);
    cdCorrectOutputMap.put(3, null);
    cdCorrectOutputMap.put(4, null);
    cdCorrectOutputMap.put(5, "Error: Invalid Directory : A2"); // Error
    cdCorrectOutputMap.put(6, null);
    cdCorrectOutputMap.put(7, null);
    cdCorrectOutputMap.put(8,
        "Error : Multiple Parameters have been provided : C/pics C/Sys/LOL");

    //Declares and initializes the pwdOutputMap as a HashMap Object
    HashMap<Integer, String> pwdOutputMap = new HashMap<Integer, String>();

    
    //Adds the respective key and value to the pwdOutputMap
    pwdOutputMap.put(0, "C"); // Error
    pwdOutputMap.put(1, "C/pics");
    pwdOutputMap.put(2, "C");
    pwdOutputMap.put(3, "C/users/desktop");
    pwdOutputMap.put(4, "C");
    pwdOutputMap.put(5, "C"); // Error
    pwdOutputMap.put(6, "C/Sys/IO/keyboard");
    pwdOutputMap.put(7, "C");
    pwdOutputMap.put(8, "C");

    //Runs through different test cases and checks if they return the correct 
    //values
    for (int i = 0; i < cdInputMap.size(); i++) {
      output = cd.run(cdInputMap.get(i), cdCommandMap.get(i), false);
      correctOuput = pwd.run(emptyArr, "pwd", false);

      if (correctOuput.contains(pwdOutputMap.get(i))
          && (output == cdCorrectOutputMap.get(i)
              || output.equals(cdCorrectOutputMap.get(i))))
        System.out.println("Case #" + (i + 1) + " Passed");
      else
        System.out.println("Case #" + (i + 1) + " Failed");
    }
    System.out.println();

  }

  private void manTestCases() {

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
    output = man.run(testInput, "man", false);

    if (output.contains("Invalid Argument")) {
      System.out.println("Case #3 Passed\n");
    } else {
      System.out.println("Case #3 Failed\n");
    }
  }

  private void mkdirTestCases() {
    System.out.println("Testing Command: mkdir");

    // Case 1: Make a directory under root directory
    String[] input = {"users"};
    mkdir.run(input, "mkdir " + input[0], false);

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
    mkdir.run(input, "mkdir " + input[0], false);

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

    String output = mkdir.run(input, "mkdir " + input[0], false);
    cd.run(enterDirectoryAbove);

    for (int i = 0; i < fs.getCurrent().getList().size(); i++) {
      allContent.add(fs.getCurrent().getList().get(i).getName());
    }

    if (allContent.contains("newUser2")) {
      System.out.println("Case #3 Passed");
    } else {
      System.out.println("Case #3 Failed");
    }

    // Case 4: Make a directory that already exists
    input[0] = "users/newUser2";
    cd.run(root);
    output = mkdir.run(input, "mkdir " + input[0], false);
    System.out.println(output);
    if (output.equals(
        "Error: Same Directory with that name already exists! : users already exists")) {
      System.out.println("Case #4 Passed");
    } else {
      System.out.println("Case #4 Failed");
    }

    // Case 5: Make a directory using an invalid pathname
    input[0] = "deifnitelyfalse/newUser2";
    output = mkdir.run(input, "mkdir " + input[0], false);

    if (output.equals(
        "Error: Invalid Directory : deifnitelyfalse is not a valid directory")) {
      System.out.println("Case #5 Passed");
    } else {
      System.out.println("Case #5 Failed");
    }

    System.out.println();
  }

  private void historyTestCases() {
    System.out.println("Testing Command: history");

    String[] testCase1history = {};

    //Declares and initializes the historyInputMap as a HashMap Object
    HashMap<Integer, String[]> historyInputMap =
        new HashMap<Integer, String[]>();
        
    //Adds the respective key and value to the historyInputMap
    historyInputMap.put(0, testCase1history); //No Arguments
    historyInputMap.put(1, "100".split(" ")); //Very large integer
    historyInputMap.put(2, "5".split(" ")); //Very small integer
    historyInputMap.put(3, "-3".split(" ")); //Negative integer
    historyInputMap.put(4, "1.0".split(" ")); //Not an intger
    historyInputMap.put(5, "hello".split(" "));//Not an intger
    historyInputMap.put(6, "1 3 5".split(" ")); //Multiple Arguments

    //Declares and initializes the historyCommandMap as a HashMap Object
    HashMap<Integer, String> historyCommandMap = new HashMap<Integer, String>();

    //Adds the respective key and value to the historyCommandMap
    historyCommandMap.put(0, "history");
    historyCommandMap.put(1, "history 100");
    historyCommandMap.put(2, "history 5");
    historyCommandMap.put(3, "history -3");
    historyCommandMap.put(4, "history 1.0");
    historyCommandMap.put(5, "history hello");
    historyCommandMap.put(6, "history 1 3 5");

    //Declares and initializes the historyCorrectOutputMap as a HashMap Object
    HashMap<Integer, String> historyCorrectOutputMap =
        new HashMap<Integer, String>();

    //Adds the respective key and value to the historyCorrectOutputMap
    historyCorrectOutputMap.put(0,
        "1. mkdir users\n" + "2. mkdir pics\n" + "3. mkdir Sys\n"
            + "4. echo \"Wow what a project\" > A2\n" + "5. cd C/users\n"
            + "6. mkdir desktop\n" + "7. history");
    historyCorrectOutputMap.put(1,
        "1. mkdir users\n" + "2. mkdir pics\n" + "3. mkdir Sys\n"
            + "4. echo \"Wow what a project\" > A2\n" + "5. cd C/users\n"
            + "6. mkdir desktop\n" + "7. history\n" + "8. history 100");
    historyCorrectOutputMap.put(2, "5. cd C/users\n" + "6. mkdir desktop\n"
        + "7. history\n" + "8. history 100\n" + "9. history 5");
    historyCorrectOutputMap.put(3, "Invalid Argument : -3");
    historyCorrectOutputMap.put(4, "Invalid Argument : 1.0");
    historyCorrectOutputMap.put(5, "Invalid Argument : hello");
    historyCorrectOutputMap.put(6,
        "Error : Multiple Parameters have been provided : 1 3 5 , either one or no input");

    //Runs through different test cases and checks if they return the correct 
    //values
    for (int i = 0; i < historyInputMap.size(); i++) {
      history.addCommands(historyCommandMap.get(i));
      output = history.run(historyInputMap.get(i), "history", false);
      if (output.contains(historyCorrectOutputMap.get(i)))
        System.out.println("Case # " + (i + 1) + "  : Passed");
      else
        System.out.println("Case # " + (i + 1) + "  : Failed");
    }
    System.out.println();
  }

  private void pwdTestCases() {
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

    if (correctOuput
        .equals("Error: Invalid Argument : pwd doesn't take any arguments"))
      System.out.println("Case #4 Passed\n");
    else
      System.out.println("Case #4 Failed\n");
  }

  private void catTestCases() {
    System.out.println("Testing Command : cat");

    String[] testCase1cat = {};

    //Declares and initializes the catInputMap as a HashMap Object
    HashMap<Integer, String[]> catInputMap = new HashMap<Integer, String[]>();

    //Adds the respective key and value to the catInputMap
    catInputMap.put(0, testCase1cat); //No Input
    //Absolute path to file, but path does not exist
    catInputMap.put(1, "C/pics/picflex".split(" ")); 
    //Absolute path to file, but path does exist
    catInputMap.put(2, "C/pics/picfile".split(" ")); 
    //Relative path to file, but path does not exist
    catInputMap.put(3, "A0".split(" "));
    //Relative path to file, but path does exist
    catInputMap.put(4, "A2".split(" "));
    //Multiple Files, one Absolute and relative files both exists
    catInputMap.put(5, "C/users/desktop/CSCB07 A2".split(" "));
    //Multiple Files, some Absolute and relative files some exists or not
    catInputMap.put(6,
        "C/users/desktop/Hwk C/Sys/LOL C/Sys/IO/keyboard/RGB".split(" "));

    //Declares and initializes the catCommandMap as a HashMap Object
    HashMap<Integer, String> catCommandMap = new HashMap<Integer, String>();

    //Adds the respective key and value to the catCommandMap
    catCommandMap.put(0, "cat");
    catCommandMap.put(1, "cat C/pics/picflex");
    catCommandMap.put(2, "cat C/pics/picfile");
    catCommandMap.put(3, "cat A0");
    catCommandMap.put(4, "cat A2");
    catCommandMap.put(5, "cat C/users/desktop/CSCB07 A2");
    catCommandMap.put(6,
        "cat C/users/desktop/Hwk C/Sys/LOL C/Sys/IO/keyboard/RGB");

    //Declares and initializes the catCorrectOutputMap as a HashMap Object
    HashMap<Integer, String> catCorrectOutputMap =
        new HashMap<Integer, String>();

    //Declares and initializes the catCorrectOutputMap as a HashMap Object
    catCorrectOutputMap.put(0, "Error : No parameters provided : ");
    catCorrectOutputMap.put(1, "Error: File Not Found : C/pics/picflex");
    catCorrectOutputMap.put(2, "this is a picturefile indeed");
    catCorrectOutputMap.put(3, "Error: File Not Found : A0");
    catCorrectOutputMap.put(4, "Wow what a project");
    catCorrectOutputMap.put(5,
        "Hello TA" + "\n" + "\n" + "\n" + "Wow what a project");
    catCorrectOutputMap.put(6,
        "2+2=5" + "\n" + "\n" + "\n" + "Error: File Not Found : C/Sys/LOL"
            + "\n" + "\n" + "\n" + "RGB == ways more      F    P   S");

    //Runs through different test cases and checks if they return the correct 
    //values
    for (int i = 0; i < catInputMap.size(); i++) {
      output = cat.run(catInputMap.get(i), "history", false);
      if (output.contains(catCorrectOutputMap.get(i)))
        System.out.println("Case # " + (i + 1) + "  : Passed");
      else
        System.out.println("Case # " + (i + 1) + "  : Failed");
    }
    System.out.println();
  }

  private void lsTestCases() {
    String[] root = {"C"};
    cd.run(root);


    System.out.println("Testing Command : ls");
    // Case 1: Testing ls in root directory
    String[] input = {"C"};
    String output = ls.run(input, "ls", false);
    System.out.println(output);

    if (output == null) {
      System.out.println("Case #1 Passed");
    } else {
      System.out.println("Case #1 Failed");
    }

    // Case 2: Testing with Absolute path specified
    input[0] = "C/users";

    output = ls.run(input, "ls " + input[0], false);
    if (output == null) {
      System.out.println("Case #2 Passed");
    } else {
      System.out.println("Case #2 Failed");
    }

    // Case 3: Testing with Relative path specified

    input[0] = "users/desktop";

    output = ls.run(input, "ls " + input[0], false);

    if (output == null) {
      System.out.println("Case #3 Passed");
    } else {
      System.out.println("Case #3 Failed");
    }

    // Case 4: Testing with multiple arguments
    String[] multipleArguments = {"C/users", "users/desktop"};

    output = ls.run(multipleArguments,
        "ls " + multipleArguments[0] + " " + multipleArguments[1], false);

    if (output == null) {
      System.out.println("Case #4 Passed");
    } else {
      System.out.println("Case #4 Failed");
    }

    // Case 5: Giving an invalid path
    input[0] = "definitelyfalsecase";

    output = ls.run(input, "ls " + input[0], false);

    if (output.equals(
        "Error: Invalid Directory : definitelyfalsecase is not a valid directory")) {
      System.out.println("Case #5 Passed");
    } else {
      System.out.println("Case #5 Failed");
    }

    System.out.println();
  }

  private void pushAndPopTestCases() {
    Push testPush = new Push();
    Pop testPop = new Pop();

    System.out.println("Testing Command : pushd & popd");

    // Case #1 Pushing an absolute file path onto the stack
    String[] input = {"C/users"};
    String output = testPush.run(input, "pushd " + input[0], false);

    if (output == null) {
      System.out.println("Case #1 Passed");
    } else {
      System.out.println("Case #1 Failed");
    }

    // Case #2 Pushing a relative file path onto the stack
    input[0] = "desktop";

    output = testPush.run(input, "pushd " + input[0], false);
    if (output == null) {
      System.out.println("Case #2 Passed");
    } else {
      System.out.println("Case #2 Failed");
    }

    // Case #3 Giving multiple arguments
    String[] multipleArguments = {"C/users", "C/users/desktop"};

    output = testPush.run(multipleArguments,
        "pushd " + multipleArguments[0] + " " + multipleArguments[1], false);
    if (output.equals(
        "Error: Invalid Argument : 2 arguments, expecting 1 argument")) {
      System.out.println("Case #3 Passed");
    } else {
      System.out.println("Case #3 Failed");
    }

    // Case #4 Pushing invalid directory
    input[0] = "definitelyFalsepath";

    output = testPush.run(input, "pushd " + input[0], false);
    if (output.equals(
        "Error: Invalid Directory : definitelyFalsepathis not a valid directory")) {
      System.out.println("Case #4 Passed");
    } else {
      System.out.println("Case #4 Failed");
    }

    // Case #5 Popping back into first pushed directory
    output = testPop.pop();
    if (output == null) {
      System.out.println("Case #5 Passed");
    } else {
      System.out.println("Case #5 Failed");
    }

    // Case #6 Popping into last directory in stack
    output = testPop.pop();
    if (output == null) {
      System.out.println("Case #6 Passed");
    } else {
      System.out.println("Case #6 Failed");
    }

    // Case #6 Popping an empty stack
    output = testPop.pop();
    if (output.equals("Stack is empty")) {
      System.out.println("Case #7 Passed");
    } else {
      System.out.println("Case #7 Failed");
    }

    System.out.println();
  }

  private void echoTestCases() {
    System.out.println("Testing Command : echo");

    String[] testCase1cat = {};

    //Declares and initializes the echoInputMap as a HashMap Object
    HashMap<Integer, String[]> echoInputMap = new HashMap<Integer, String[]>();

    //Adds the respective key and value to the echoInputMap
    
    //No Input
    echoInputMap.put(0, testCase1cat);
    //Proper Text but no file given
    echoInputMap.put(1, "echo \"Hello\"".split(" ")); 
    //Malformed Text 
    echoInputMap.put(2, "echo \"Hello".split(" "));
    echoInputMap.put(3, "echo Hello".split(" "));
    //No Files given
    echoInputMap.put(4, "echo \"Hello\" >".split(" "));
    echoInputMap.put(5, "echo \"Hello\" >>".split(" "));
    //Proper text but, file does not exist in relative directory
    echoInputMap.put(6, "echo \"Hello\" > file".split(" "));
    //Append content of a relative file
    echoInputMap.put(7, "echo \"Bye\" >> file".split(" "));
    //Overwrite content of a relative file
    echoInputMap.put(8, "echo \"Bye\" >> file".split(" "));
    //Overwrite content of a absolute path file
    echoInputMap.put(9, "echo \"KeyWASD\" > C/Sys/IO/keyboard/keys".split(" "));
    //Append content of a absolute path file
    echoInputMap.put(10,
        "echo \"QWERTY\" >> C/Sys/IO/keyboard/keys".split(" "));

    //Declares and initializes the echoCommandMap as a HashMap Object
    HashMap<Integer, String> echoCommandMap = new HashMap<Integer, String>();

    //Adds the respective key and value to the echoCommandMap
    echoCommandMap.put(0, "echo");
    echoCommandMap.put(1, "echo \"Hello\"");
    echoCommandMap.put(2, "echo \"Hello");
    echoCommandMap.put(3, "echo Hello");
    echoCommandMap.put(4, "echo \"Hello\" >");
    echoCommandMap.put(5, "echo \"Hello\" >>");
    echoCommandMap.put(6, "echo \"Hello\" > file");
    echoCommandMap.put(7, "echo \"Bye\" >> file");
    echoCommandMap.put(8, "echo \"okay\" > file");
    echoCommandMap.put(9, "echo \"KeyWASD\" > C/Sys/IO/keyboard/keys");
    echoCommandMap.put(10, "echo \"QWERTY\" >> C/Sys/IO/keyboard/keys");

    //Declares and initializes the echoCorrectOutputMap as a HashMap Object
    HashMap<Integer, String> echoCorrectOutputMap =
        new HashMap<Integer, String>();

    //Adds the respective key and value to the echoCorrectOutputMap
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
    echoCorrectOutputMap.put(10, "KeyWASD" + "\n" + "QWERTY");


    //Declares and initializes the catInputMap as a HashMap Object
    HashMap<Integer, String[]> catInputMap = new HashMap<Integer, String[]>();
    
    //Adds the respective key and value to the catInputMap
    catInputMap.put(6, "file".split(" "));
    catInputMap.put(7, "file".split(" "));
    catInputMap.put(8, "file".split(" "));
    catInputMap.put(9, "C/Sys/IO/keyboard/keys".split(" "));
    catInputMap.put(10, "C/Sys/IO/keyboard/keys".split(" "));

    //Runs through different test cases and checks if they return the correct 
    //values
    for (int i = 0; i < echoInputMap.size(); i++) {
      output = echo.run(echoInputMap.get(i), echoCommandMap.get(i), false);
      if (i < 6) {
        if (output.contains(echoCorrectOutputMap.get(i)))
          System.out.println("Case # " + (i + 1) + "  : Passed");
        else
          System.out.println("Case # " + (i + 1) + "  : Failed");
      } else {
        //Declares and Initializes a string to collect the output from cat
        String catOutput =
            cat.run(catInputMap.get(i), echoCommandMap.get(i), false);
        if (output == null && catOutput.equals(echoCorrectOutputMap.get(i)))
          System.out.println("Case # " + (i + 1) + "  : Passed");
        else
          System.out.println("Case # " + (i + 1) + "  : Failed");
      }
    }
    System.out.println();

  }
}
