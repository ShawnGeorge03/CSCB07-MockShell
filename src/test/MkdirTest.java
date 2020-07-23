package test;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import commands.Cd;
import commands.Mkdir;
import data.FileSystem;

import java.lang.reflect.Field;

public class MkdirTest {
  
  private static FileSystem fs;
  private static Mkdir mkdir;
  
  private String expected;
  private String actual;
  
  private static ArrayList<String> allContent;

  @Before
  public void setUp() throws Exception {
    fs = FileSystem.getFileSys();
    mkdir = new Mkdir();
    
    allContent = new ArrayList<String>();
  }

  @After
  public void tearDown() throws Exception {
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
    cd.run("/".split(" "), "cd /", false);
  }

  @Test
  public void testANoPath() {
    String[] testNoArg = {};
    expected = "Error: Invalid Argument : Expected at least 1 argument";
    actual = mkdir.run(testNoArg, "mkdir", false);
    collectContent("/");
    assertTrue(allContent.isEmpty() && actual.equals(expected));
  }

  @Test
  public void testBInvalidName(){
    expected = "Error: Invalid Directory : ... is not a valid directory name";
    actual = mkdir.run("...".split(" "), "mkdir ..." , false); 
    collectContent("/");
    assertTrue(allContent.isEmpty() && actual.equals(expected));
  }

  @Test
  public void testCInvalidPath(){
    expected = "Error: Invalid Directory : /hello is not a valid directory";
    actual = mkdir.run("/hello/hi".split(" "), "mkdir /hello/hi" , false); 
    collectContent("/");
    assertTrue(allContent.isEmpty() && actual.equals(expected));
  }
  
  @Test
  public void testDValidAbsolutePath() {
    expected = null;
    actual = mkdir.run("/desktop".split(" "), "mkdir /desktop" , false);
    collectContent("/");
    assertTrue(allContent.equals(Arrays.asList("desktop".split(" ")))  && actual == expected);
  }

  @Test
  public void testEValidRelativePath() {
    expected = null;
    actual = mkdir.run("users".split(" "), "mkdir users" , false);
    collectContent("/");
    assertTrue(allContent.equals(Arrays.asList("users".split(" ")))  && actual == expected);
  }
  
  @Test
  public void testFRepeatedPath(){
    expected = "Error: Same Directory with that name already exists! : LOL already exists";
    mkdir.run("/LOL".split(" "), "mkdir /LOL" , false);
    actual = mkdir.run("/LOL".split(" "), "mkdir /LOL" , false); 
    collectContent("/");
    assertTrue(allContent.equals(Arrays.asList("LOL".split(" ")))  && actual.equals(expected));
  }
  
  @Test
  public void testGRelativeFromDirPath(){
    expected = null;
    mkdir.run("desktop".split(" "), "mkdir desktop" , false);
    actual = mkdir.run("desktop/project".split(" "), "mkdir desktop/project" , false); 
    collectContent("desktop");
    assertTrue(allContent.equals(Arrays.asList("project".split(" ")))  && actual == expected);
  }
  
  @Test
  public void testHMultipleArgsRelativePaths(){
    expected = null;
    actual = mkdir.run("Business A2".split(" "), "mkdir Business A2", false);
    collectContent("/");
    assertTrue(allContent.equals(Arrays.asList("Business A2".split(" ")))  && actual == expected);
  }

  @Test
  public void testIAbsolutePaths(){
    expected = null;
    actual = mkdir.run("/A3 /A3/do".split(" "), "mkdir /A3 /A3/do", false);
    collectContent("A3");
    assertTrue(allContent.equals(Arrays.asList("do".split(" ")))  && actual == expected);
  }
  
  @Test
  public void testJMultipleArgsOneDoesNotExist(){
    expected = "Error: Invalid Directory : /lol is not a valid directory";
    actual = mkdir.run("/lol/F anotherFile".split(" "), "mkdir /lol/F anotherFile", false);
    collectContent("/");
    assertTrue(allContent.equals(Arrays.asList("anotherFile".split(" ")))  && actual.equals(expected));
  }

  

}
