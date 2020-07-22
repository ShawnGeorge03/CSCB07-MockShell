package test;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.junit.Test;

import commands.Cd;
import commands.Mkdir;
import data.FileSystem;

import java.lang.reflect.Field;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MkdirTest {
  
  private static FileSystem fs;
  private static Mkdir mkdir;
  
  private String expected;
  private String actual;
  
  private static ArrayList<String> allContent;

  @BeforeClass
  public static void setUp() throws Exception {
    fs = FileSystem.getFileSys();
    mkdir = new Mkdir();
    
    allContent = new ArrayList<String>();
  }

  @AfterClass
  public static void tearDown() throws Exception {
      Field feild = fs.getClass().getDeclaredField("fileSys");
      feild.setAccessible(true);
      feild.set(null, null);
  } 

  public void collectContent(String path){
    allContent.clear();
    Cd cd = new Cd();
    cd.run(path.split(" "), "cd " + path, false);
    for (int i = 0; i < fs.getCurrent().getList().size(); i++) 
    allContent.add(fs.getCurrent().getList().get(i).getName());
    cd.run("C".split(" "), "cd C", false);
  }

  @Test
  public void testANoPath() {
    String[] testNoArg = {};
    expected = "Error: Invalid Argument : Expected at least 1 argument";
    actual = mkdir.run(testNoArg, "mkdir", false);
    collectContent("C");
    assertTrue(allContent.isEmpty() && actual.equals(expected));
  }

  @Test
  public void testBInvalidName(){
    expected = "Error: Invalid Directory : ... is not a valid directory name";
    actual = mkdir.run("...".split(" "), "mkdir ..." , false); 
    collectContent("C");
    assertTrue(allContent.isEmpty() && actual.equals(expected));
  }

  @Test
  public void testCInvalidPath(){
    expected = "Error: Invalid Directory : C/hello is not a valid directory";
    actual = mkdir.run("C/hello/hi".split(" "), "mkdir C/hello/hi" , false); 
    collectContent("C");
    assertTrue(allContent.isEmpty() && actual.equals(expected));
  }
  
  @Test
  public void testDValidAbsolutePath() {
    expected = null;
    actual = mkdir.run("C/desktop".split(" "), "mkdir C/desktop" , false);
    collectContent("C");
    assertTrue(allContent.equals(Arrays.asList("desktop".split(" ")))  && actual == expected);
  }

  @Test
  public void testEValidRelativePath() {
    expected = null;
    actual = mkdir.run("users".split(" "), "mkdir users" , false);
    collectContent("C");
    assertTrue(allContent.equals(Arrays.asList("desktop users".split(" ")))  && actual == expected);
  }
  
  @Test
  public void testFRepeatedPath(){
    expected = "Error: Same Directory with that name already exists! : LOL already exists";
    mkdir.run("C/LOL".split(" "), "mkdir C/LOL" , false);
    actual = mkdir.run("C/LOL".split(" "), "mkdir C/LOL" , false); 
    collectContent("C");
    assertTrue(allContent.equals(Arrays.asList("desktop users LOL".split(" ")))  && actual.equals(expected));
  }
  
  @Test
  public void testGRelativeFromDirPath(){
    expected = null;
    actual = mkdir.run("desktop/project".split(" "), "mkdir desktop/project" , false); 
    collectContent("desktop");
    assertTrue(allContent.equals(Arrays.asList("project".split(" ")))  && actual == expected);
  }
  
  @Test
  public void testHMultipleArgsRelativePaths(){
    expected = null;
    actual = mkdir.run("Business A2".split(" "), "mkdir Business A2", false);
    collectContent("C");
    assertTrue(allContent.equals(Arrays.asList(
      "desktop users LOL Business A2".split(" ")))  && actual == expected);
  }

  @Test
  public void testIAbsolutePaths(){
    expected = null;
    actual = mkdir.run("C/A3 C/A3/do".split(" "), "mkdir C/A3 C/A3/do", false);
    collectContent("A3");
    assertTrue(allContent.equals(Arrays.asList("do".split(" ")))  && actual == expected);
  }
  
  @Test
  public void testJMultipleArgsOneDoesNotExist(){
    expected = "Error: Invalid Directory : C/lol is not a valid directory";
    actual = mkdir.run("C/lol/F anotherFile".split(" "), "mkdir C/lol/F anotherFile", false);
    collectContent("C");
    System.out.println(allContent + "|" + actual);
    assertTrue(allContent.equals(Arrays.asList(
      "desktop users LOL Business A2 A3 anotherFile".split(" ")))  && actual.equals(expected));
  }

  

}
