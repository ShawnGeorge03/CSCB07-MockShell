package test;

import static org.junit.Assert.*;
import java.lang.reflect.Field;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import commands.History;
import data.FileSystem;

public class HistoryTest {
  
  private FileSystem fs;
  private History history;
  private String expected;
  private String actual;

  @Before
  public void setUp() throws Exception {    
    fs = FileSystem.getFileSys();
    history = new History();
    
    this.expected = "";
    this.actual = "";

    //Setups the  History
    fs.getCommandLog().add("mkdir users");
    fs.getCommandLog().add("mkdir pics");
    fs.getCommandLog().add("mkdir Sys");
    fs.getCommandLog().add("echo \"Wow what a project\" > A2");
    fs.getCommandLog().add("cd C/users");
    fs.getCommandLog().add("mkdir desktop");
  }

  @Test
  public void testANoArgs() {
    fs.getCommandLog().add("history");
    String[] testCase1history = {};
    actual = history.run(testCase1history, "history", false);
    expected = "1. mkdir users\n" + "2. mkdir pics\n" + "3. mkdir Sys\n"
        + "4. echo \"Wow what a project\" > A2\n" + "5. cd C/users\n"
        + "6. mkdir desktop\n" + "7. history";
    assertEquals(expected,actual);
  }
  
  @Test
  public void testBLargeInteger() {
    fs.getCommandLog().add("history");
    fs.getCommandLog().add("history 100");
    actual = history.run("100".split(" "), "history 100", false);
    expected = "1. mkdir users\n" + "2. mkdir pics\n" + "3. mkdir Sys\n"
        + "4. echo \"Wow what a project\" > A2\n" + "5. cd C/users\n"
        + "6. mkdir desktop\n" + "7. history\n" + "8. history 100";
    assertEquals(expected,actual);
  }
  
  @Test
  public void testCSmallInteger() {
    fs.getCommandLog().add("history");
    fs.getCommandLog().add("history 100");
    fs.getCommandLog().add("history 5");
    actual = history.run("5".split(" "), "history 5", false);
    expected = "5. cd C/users\n" + "6. mkdir desktop\n"
        + "7. history\n" + "8. history 100\n" + "9. history 5";
    assertEquals(expected,actual);
  }
  
  @Test
  public void testDNegativeInteger() {
    fs.getCommandLog().add("history");
    fs.getCommandLog().add("history 100");
    fs.getCommandLog().add("history 5");
    fs.getCommandLog().add("history -3");
    actual = history.run("-3".split(" "), "history -3", false);
    expected = "Error: Invalid Argument : -3 is not either a number or "
        + "positive or an integer";
    assertEquals(expected,actual);
  }
  
  
  @Test
  public void testENotAnIntegerCase1() {
    fs.getCommandLog().add("history");
    fs.getCommandLog().add("history 100");
    fs.getCommandLog().add("history 5");
    fs.getCommandLog().add("history -3");
    fs.getCommandLog().add("history 1.0");
    actual = history.run("1.0".split(" "), "history -3", false);
    expected = "Error: Invalid Argument : 1.0 is not either a number or "
        + "positive or an integer";
    assertEquals(expected,actual);
  }
  
  @Test
  public void testFNotAnIntegerCase2() {
    fs.getCommandLog().add("history");
    fs.getCommandLog().add("history 100");
    fs.getCommandLog().add("history 5");
    fs.getCommandLog().add("history -3");
    fs.getCommandLog().add("history 1.0");
    fs.getCommandLog().add("history hello");
    actual = history.run("hello".split(" "), "history hello", false);
    expected = "Error: Invalid Argument : hello is not either a number or "
        + "positive or an integer";
    assertEquals(expected,actual);
  }
  
  @Test
  public void testGMultipleArgs() {
    fs.getCommandLog().add("history");
    fs.getCommandLog().add("history 100");
    fs.getCommandLog().add("history 5");
    fs.getCommandLog().add("history -3");
    fs.getCommandLog().add("history 1.0");
    fs.getCommandLog().add("history hello");
    fs.getCommandLog().add("history 1 3 5");
    actual = history.run("1 3 5".split(" "), "history hello", false);
    expected = "Error : Multiple Parameters have been provided : 1 3 5 , "
        + "either one or no input";
    assertEquals(expected,actual);
  }
  
  @After
  public void tearDown() throws Exception {
    Field feild = fs.getClass().getDeclaredField("fileSys");
    feild.setAccessible(true);
    feild.set(null, null);
  }
  
}
