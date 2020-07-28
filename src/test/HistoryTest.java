package test;

import static org.junit.Assert.*;
import java.lang.reflect.Field;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import commands.History;

/**
 * Class HistoryTest runs all the different test cases for History
 */
public class HistoryTest {

  /**
   * Declare instance of MockFileSystem so we can access the preset filesystem
   */
  private static MockFileSystem fs;

  /**
   * Declares instance of History to be tested
   */
  private static History history;

  /**
   * Declare two different instance of a String objects called expected and actual
   */
  private static String expected, actual;

  /**
   * Sets up the test environment and initializes the instance variables
   * 
   * @throws Exception
   */
  @Before
  public void setUp() throws Exception {
    // Gets a specific preset FileSystem
    fs = MockFileSystem.getMockFileSys("MOCKENV");
    // Initializes a History object
    history = new History();

    // Setups the History Stack
    fs.getCommandLog().add("mkdir users");
    fs.getCommandLog().add("mkdir pics");
    fs.getCommandLog().add("mkdir Sys");
    fs.getCommandLog().add("echo \"Wow what a project\" > A2");
    fs.getCommandLog().add("cd C/users");
    fs.getCommandLog().add("mkdir desktop");
  }

  /**
   * Destroys the MockFileSystem after each testcases have run
   * 
   * @throws Exception
   */
  @After
  public void tearDown() throws Exception {
    // Declares and initializes a Feild variable
    // to the fileSys variable in MockFileSystem
    Field feild = fs.getClass().getDeclaredField("filesys");
    // Allows the value of this variable in MockFileSystem to be accessed
    feild.setAccessible(true);
    // Changes the value of the variable in MockFileSystem to null
    feild.set(null, null);
  }
  
  /**
   * Test A : User provides no parameter
   */
  @Test
  public void testANoArgs() {
    //Add the command to the history stack
    fs.getCommandLog().add("history");
    //Declares and initializes an empty array
    String[] testCase1history = {};
    //Actual return from History after the operation has been run
    actual = history.run(fs, testCase1history, "history", false);
    //Expected return from History
    expected = "1. mkdir users\n" + "2. mkdir pics\n" + "3. mkdir Sys\n" 
    + "4. echo \"Wow what a project\" > A2\n"
    + "5. cd C/users\n" + "6. mkdir desktop\n" + "7. history";
    //Checks if the values are equal or not
    assertEquals(expected, actual);
  }

  /**
   * Test B : Users provides a large integer parameter
   */
  @Test
  public void testBLargeInteger() {    
    //Add the command to the history stack
    fs.getCommandLog().add("history 100");
    //Actual return from History after the operation has been run
    actual = history.run(fs, "100".split(" "), "history 100", false);
    //Expected return from History
    expected = "1. mkdir users\n" + "2. mkdir pics\n" + "3. mkdir Sys\n" 
    + "4. echo \"Wow what a project\" > A2\n"
    + "5. cd C/users\n" + "6. mkdir desktop\n" + "7. history 100";
    //Checks if the values are equal or not    
    assertEquals(expected, actual);
  }

  /**
   * Test C : User provides a small intger parameter
   */
  @Test
  public void testCSmallInteger() {
    //Add the command to the history stack
    fs.getCommandLog().add("history 5");
    //Actual return from History after the operation has been run
    actual = history.run(fs, "5".split(" "), "history 5", false);
    //Expected return from History
    expected = "3. mkdir Sys\n" + "4. echo \"Wow what a project\" > A2\n" 
    + "5. cd C/users\n" + "6. mkdir desktop\n" + "7. history 5";
    //Checks if the values are equal or not
    assertEquals(expected, actual);
  }

  /**
   * Test D : User provides a parameter lower than zero
   */
  @Test
  public void testDNegativeInteger() {
    //Add the command to the history stack
    fs.getCommandLog().add("history -3");
    //Actual return from History after the operation has been run
    actual = history.run(fs, "-3".split(" "), "history -3", false);
    //Expected return from History
    expected = "Error: Invalid Argument : -3 is not either a number or " 
    + "positive or an integer";
    //Checks if the values are equal or not
    assertEquals(expected, actual);
  }

  /**
   * Test E : User provides an non-integer parameter
   */
  @Test
  public void testENotAnIntegerCase1() {
    //Add the command to the history stack
    fs.getCommandLog().add("history 1.0");
    //Actual return from History after the operation has been run
    actual = history.run(fs, "1.0".split(" "), "history -3", false);
    //Expected return from History
    expected = "Error: Invalid Argument : 1.0 is not either a number or " 
    + "positive or an integer";
    //Checks if the values are equal or not
    assertEquals(expected, actual);
  }

  /**
   * Test F : User provides a non-numeric parameter
   */
  @Test
  public void testFNotAnIntegerCase2() {
    //Add the command to the history stack
    fs.getCommandLog().add("history hello");
    //Actual return from History after the operation has been run
    actual = history.run(fs, "hello".split(" "), "history hello", false);
    //Expected return from History
    expected = "Error: Invalid Argument : hello is not either a number or " + "positive or an integer";
    //Checks if the values are equal or not    
    assertEquals(expected, actual);
  }

  /**
   * Test G : User provides multiple parameter
   */
  @Test
  public void testGMultipleArgs() {
    //Add the command to the history stack
    fs.getCommandLog().add("history 1 3 5");
    //Actual return from History after the operation has been run
    actual = history.run(fs, "1 3 5".split(" "), "history 1 3 5", false);
    //Expected return from History
    expected = "Error : Multiple Parameters have been provided : 1 3 5 , " + "either one or no input";
    //Checks if the values are equal or not
    assertEquals(expected, actual);
  }
}
