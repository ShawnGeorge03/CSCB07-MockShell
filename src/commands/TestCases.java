package commands;

import java.util.ArrayList;

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
    this.echo =  new Echo();
    this.man = new Man();
    this.history = new History();
    this.cat = new Cat();
    setupEnviro();
  }
  
  public void setupEnviro() {

    String[] setupDirs = new String[1];
    
    //C Folder
    setupDirs[0] = "users";
    mkdir.MakeDirectory(setupDirs);
    setupDirs[0] = "pics";
    mkdir.MakeDirectory(setupDirs);
    setupDirs[0] = "Sys";
    mkdir.MakeDirectory(setupDirs);
    echo.run(setupDirs, "echo \"Wow what a project\" > A2");
    
    //users Folder
    setupDirs[0] = "C/users";
    cd.run(setupDirs, "cd C/users");
    setupDirs[0] = "desktop";
    mkdir.MakeDirectory(setupDirs);
    
    //desktop Folder
    setupDirs[0] = "C/users/desktop";
    cd.run(setupDirs, "cd C/users/desktop");    
    echo.run(setupDirs, "echo \"Hello TA\" > CSCB07");
    echo.run(setupDirs, "echo \"2+2=5\" > Hwk");
    setupDirs[0] = "../../";
    cd.run(setupDirs, "cd ../../");
    
    //pics Folder
    echo.run(setupDirs, "echo \"this is a picturefile indeed\" > pics/picfile");
    setupDirs[0] = "pics";
    cd.run(setupDirs, "cd pics");
    echo.run(setupDirs, "echo \"Hello TA from the pics Folder\" > CSCB07");
    setupDirs[0] = "..";
    cd.run(setupDirs, "cd ..");

    //Sys Folder
    setupDirs[0] = "Sys";
    cd.run(setupDirs, "cd Sys");
    setupDirs[0] = "IO";
    mkdir.MakeDirectory(setupDirs);
    setupDirs[0] = "LOL";
    mkdir.MakeDirectory(setupDirs);
    
    //IO Folder
    setupDirs[0] = "IO";
    cd.run(setupDirs, "cd IO");
    setupDirs[0] = "keyboard";
    mkdir.MakeDirectory(setupDirs);
    setupDirs[0] = "Mouse";
    mkdir.MakeDirectory(setupDirs);
    
    //keyboard Folder
    setupDirs[0] = "keyboard";
    cd.run(setupDirs, "cd keyboard");
    echo.run(setupDirs, "echo \"QWERTY\" > keys");
    echo.run(setupDirs, "echo \"RGB == ways more      F    P   S\" > RGB");
    setupDirs[0] = "../";
    cd.run(setupDirs, "cd ../");
    
    //Mouse Folder
    setupDirs[0] = "Mouse";
    cd.run(setupDirs, "cd Mouse");
    echo.run(setupDirs, "echo \"Mouse is in Mouse Folder\" > Presses");

    //C Folder
    setupDirs[0] = "../../../";
    cd.run(setupDirs, "cd ../../../");
    
    //Setup History
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
    
    //Case 1: No Input
    output = cd.run(testCase1cd, "cd");
    correctOuput = pwd.run(inputArrayForPwd, "pwd");
    
    if(correctOuput.contains("C") && output.equals("Error : No parameters provided : ")) 
      System.out.println("Case #1 Passed");
    else
      System.out.println("Case #1 Failed");
    
   //Case 2: Change Directory to pics
    testCase2cd[0] = "pics";
        
    output = cd.run(testCase2cd, "cd pics");
    correctOuput = pwd.run(inputArrayForPwd, "pwd");
    
    if(correctOuput.equals("C/pics") && output == null) 
      System.out.println("Case #2 Passed");
    else
      System.out.println("Case #2 Failed");
    
    //Case 3: Change Directory back one
    testCase2cd[0] = "..";
    
    output = cd.run(testCase2cd, "cd ..");
    correctOuput = pwd.run(inputArrayForPwd, "pwd");
    
    if(correctOuput.equals("C") && output == null) 
      System.out.println("Case #3 Passed");
    else
      System.out.println("Case #3 Failed");
    
    //Case 4: Change Directory to users
    testCase2cd[0] = "users/desktop";
    
    output = cd.run(testCase2cd, "cd users/desktop");
    correctOuput = pwd.run(inputArrayForPwd, "pwd");
    
    if(correctOuput.equals("C/users/desktop") && output == null) 
      System.out.println("Case #4 Passed");
    else
      System.out.println("Case #4 Failed");
    
    //Case 5: Pattern -> ../..
    testCase2cd[0] = "../..";
    
    output = cd.run(testCase2cd, "cd ../..");
    correctOuput = pwd.run(inputArrayForPwd, "pwd");
    
    if(correctOuput.equals("C") && output == null) 
      System.out.println("Case #5 Passed");
    else
      System.out.println("Case #5 Failed");
    
    //Case 6: Pattern -> Change directory to a file
    testCase2cd[0] = "A2";
    
    output = cd.run(testCase2cd, "cd A2");
    correctOuput = pwd.run(inputArrayForPwd, "pwd");
    
    if(correctOuput.equals("C") && output.equals("Error: Invalid Directory : A2")) 
      System.out.println("Case #6 Passed");
    else
      System.out.println("Case #6 Failed");
    
    //Case 7: Changing directory to keyboard Folder
    testCase2cd[0] = "C/Sys/IO/keyboard";
    
    output = cd.run(testCase2cd, "cd C/Sys/IO/keyboard");
    correctOuput = pwd.run(inputArrayForPwd, "pwd");
    
    if(correctOuput.equals("C/Sys/IO/keyboard") && output == null) 
      System.out.println("Case #7 Passed");
    else
      System.out.println("Case #7 Failed");
    
    //Case 8: Change directory to C
    testCase2cd[0] = "../../../";
    
    output = cd.run(testCase2cd, "cd ../../../");
    correctOuput = pwd.run(inputArrayForPwd, "pwd");
    
    if(correctOuput.equals("C") && output == null) 
      System.out.println("Case #8 Passed");
    else
      System.out.println("Case #8 Failed");
    
    //Case 9: Multiple directory
    testCase3cd[0] = "C/pics";
    testCase3cd[1] = "C/Sys/LOL";
    
    output = cd.run(testCase3cd, "cd C/pics C/Sys/LoL");
    correctOuput = pwd.run(inputArrayForPwd, "pwd");
    
    if(correctOuput.equals("C") && output.equals("Error : Multiple Parameters have been provided : C/pics C/Sys/LoL")) 
      System.out.println("Case #9 Passed\n");
    else
      System.out.println("Case #9 Failed\n");    
  }
  public void manTestCases() {
	  
	  String output;
	  String[] testInput = {"ls"};
	  
	  System.out.println("Testing Command: man");
	  
	  //Case 1: Get the documentation for ls
	  output = man.run(testInput, "man");
	  
	  if (output.contains("Command: ls")) {
		  System.out.println("Case #1 Passed");
	  }else {
		  System.out.println("Case #1 Failed");
	  }
	  
	  //Case 2: Get documentation for invalid input
	  testInput[0] = "false command";
	  output = man.run(testInput, "man");
	  
	  if (output.contains("Invalid Argument")) {
		  System.out.println("Case #2 Passed");
	  }else {
		  System.out.println("Case #2 Failed");
	  }
	  
	  //Case 3: Get documentation for multiple arguments (not supported)
	  String[] multipleArguments = {"ls", "cd"};
	  output = man.run(testInput, "man");
	  
	  if (output.contains("Invalid Argument")) {
		  System.out.println("Case #3 Passed\n");
	  }else {
		  System.out.println("Case #3 Failed\n");
	  }
  }
  public void mkdirTestCases() {
	  System.out.println("Testing Command: mkdir");
	  
	  //Case 1: Make a directory under root directory
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
	  
	  //Case 2: Make a directory using absolute path
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
	  
	  //Case 3: Make a directory using relative path
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

    
    //Case 1: No Input
    correctOuput ="1. mkdir users\n" + 
                  "2. mkdir pics\n" + 
                  "3. mkdir Sys\n" + 
                  "4. echo \"Wow what a project\" > A2\n" + 
                  "5. cd C/users\n" + 
                  "6. mkdir desktop\n" + 
                  "7. history";
    
    String[] testCase1history = {};
    
    history.addCommands("history");   
    output = history.run(testCase1history, "history");
    
    if(output.contains(correctOuput))
      System.out.println("Case # 1 : Passed");
    else
      System.out.println("Case # 1 : Failed");
        
   //Case 2: An Integer number larger than size of History
    correctOuput = "1. mkdir users\n" + 
        "2. mkdir pics\n" + 
        "3. mkdir Sys\n" + 
        "4. echo \"Wow what a project\" > A2\n" + 
        "5. cd C/users\n" + 
        "6. mkdir desktop\n" + 
        "7. history\n" +
        "8. history 100";
    
    String[] testCase2history = new String[1];
    testCase2history[0] = "100";
    
    history.addCommands("history 100");    
    output = history.run(testCase2history, "history 100");
    
    if(output.contains(correctOuput))
      System.out.println("Case # 2 : Passed");
    else
      System.out.println("Case # 2 : Failed");  
    
    
    //Case 3: An Integer number smaller or equal than size of History
    correctOuput = "5. cd C/users\n" + 
        "6. mkdir desktop\n" + 
        "7. history\n" + 
        "8. history 100\n" + 
        "9. history 5";
    
    testCase2history[0] = "5";
    
    history.addCommands("history 5");    
    output = history.run(testCase2history, "history 5");
    
    if(output.contains(correctOuput))
      System.out.println("Case # 3 : Passed");
    else
      System.out.println("Case # 3 : Failed");
    
    //Case 4: Double or Float as the number
    correctOuput = "Invalid Argument : -3";
    
    testCase2history[0] = "-3";
    
    history.addCommands("history -3");    
    output = history.run(testCase2history, "history -3");
    
    if(output.contains(correctOuput))
      System.out.println("Case # 4 : Passed");
    else
      System.out.println("Case # 4 : Failed");

    //Case 5: Double or Float as the number
    correctOuput = "Invalid Argument : 1.0";
    
    testCase2history[0] = "1.0";
    
    history.addCommands("history 1.0");    
    output = history.run(testCase2history, "history 1.0");
    
    if(output.contains(correctOuput))
      System.out.println("Case # 5 : Passed");
    else
      System.out.println("Case # 5 : Failed");
    
    //Case 6 : Alphabets or words as number
    correctOuput = "Invalid Argument : hello";
    
    testCase2history[0] = "hello";
    
    history.addCommands("history hello");    
    output = history.run(testCase2history, "history hello");
    
    if(output.contains(correctOuput))
      System.out.println("Case # 6 : Passed\n");
    else
      System.out.println("Case # 6 : Failed\n");   
  } 
  public void pwdTestCases(){
    System.out.println("Testing Command: pwd");
    
    String[] testCase1_cd = {};  
    String[] testCase1_pwd = {};

    String[] testCase2_cd = new String[1];
    String[] testCase2_pwd = new String[2];

    //Case 1: Startup on FileSystem
    output = cd.run(testCase1_cd, "cd");
    correctOuput = pwd.run(testCase1_pwd, "pwd");
    
    if(correctOuput.contains("C")) 
      System.out.println("Case #1 Passed");
    else
      System.out.println("Case #1 Failed");
    
    //Case 2: Change Directory
    testCase2_cd[0] = "C/Sys/IO/keyboard";
        
    output = cd.run(testCase2_cd, "cd pics");
    correctOuput = pwd.run(testCase1_pwd, "pwd");
    
    if(correctOuput.equals(testCase2_cd[0])) 
      System.out.println("Case #2 Passed");
    else
      System.out.println("Case #2 Failed");
    
    //Case 3: Change directory back to C
    testCase2_cd[0] = "../../../";
    
    output = cd.run(testCase2_cd, "cd ../../../");
    correctOuput = pwd.run(testCase1_pwd, "pwd");
    
    if(correctOuput.equals("C") && output == null) 
      System.out.println("Case #3 Passed");
    else
      System.out.println("Case #3 Failed");
    
    //Case 4: Multiple arguments given
    testCase2_cd[0] = "C";
    testCase2_pwd[0] = "C/Sys/LOL";
    testCase2_pwd[1] = "C/pics";
    
    output = cd.run(testCase2_cd, "cd C");
    correctOuput = pwd.run(testCase2_pwd, "pwd");
    
    if(correctOuput.equals("Error: Invalid Argument : pwd doesn't take any arguments")) 
      System.out.println("Case #4 Passed\n");
    else
      System.out.println("Case #4 Failed\n");        
  }

  public void catTestCases() {
    System.out.println("Testing Command : cat");
    
    String[] emptyFilePaths = {};
    String[] onefilePath = new String[1];
    String[] twofilePaths = new String[2];
    String[] threefilePaths = new String[3];

    //Case 1 : No Input
    output = cat.run(emptyFilePaths, "cat");
    correctOuput = "Error : No parameters provided : ";
    
    if(output.equals(correctOuput)) 
      System.out.println("Case #1 Passed");
    else
      System.out.println("Case #1 Failed");
    
    //Case 2 : Single File but, does not exist
    onefilePath[0] = "C/pics/picfilex";
    
    output = cat.run(onefilePath, "cat C/pics/picfilex");
    correctOuput = "Error: File Not Found : C/pics/picfilex";

    if(output.equals(correctOuput)) 
      System.out.println("Case #2 Passed");
    else
      System.out.println("Case #2 Failed");
    
    //Case 3 : Single File but, exists
    onefilePath[0] = "C/pics/picfile";

    output = cat.run(onefilePath, "cat C/pics/picfile");
    correctOuput = "this is a picturefile indeed";

    if(output.equals(correctOuput)) 
      System.out.println("Case #3 Passed");
    else
      System.out.println("Case #3 Failed");
    
  }
}
