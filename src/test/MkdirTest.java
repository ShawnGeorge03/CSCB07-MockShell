package test;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.junit.Test;

import commands.Cd;
import commands.Mkdir;
import data.FileSystem;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MkdirTest {
  
  private FileSystem fs;
  private Mkdir mkdir;
  
  private String expected;
  private String actual;
  
  private ArrayList<String> allContent;

  @Before
  public void setUp() throws Exception {
    this.fs = FileSystem.getFileSys();
    this.mkdir = new Mkdir();

    
    this.expected = "";
    this.actual = "";
    this.allContent = new ArrayList<String>();
  }

  public void collectContent(){
    for (int i = 0; i < fs.getCurrent().getList().size(); i++) 
    allContent.add(fs.getCurrent().getList().get(i).getName());
  }

  @Test
  public void testANoPath() {
    String[] testNoArg = {};
    expected = "Error: Invalid Argument : Expecting 1 Argument only";
    actual = mkdir.run(testNoArg, "mkdir", false);
    collectContent();
    assertTrue(allContent.isEmpty() && actual.equals(expected));
  }

  @Test
  public void testBInvalidName(){
    expected = "Error: Invalid Directory : ... is not a valid directory name";
    actual = mkdir.run("...".split(" "), "mkdir ..." , false); 
    collectContent();
    assertTrue(allContent.isEmpty() && actual.equals(expected));
  }

  @Test
  public void testCInvalidPath(){
    expected = "Error: Invalid Directory : C/hello is not a valid directory";
    actual = mkdir.run("C/hello/hi".split(" "), "mkdir C/hello/hi" , false); 
    collectContent();
    assertTrue(allContent.isEmpty() && actual.equals(expected));
  }
  
  @Test
  public void testDValidAbsolutePath() {
    expected = null;
    actual = mkdir.run("C/desktop".split(" "), "mkdir C/desktop" , false);
    collectContent();
    assertTrue(allContent.equals(Arrays.asList("desktop".split(" ")))  && actual == expected);
  }

  @Test
  public void testEValidRelativePath() {
    expected = null;
    actual = mkdir.run("users".split(" "), "mkdir users" , false);
    collectContent();
    assertTrue(allContent.equals(Arrays.asList("desktop users".split(" ")))  && actual == expected);
  }
  
  @Test
  public void testFRepeatedPath(){
    expected = "Error: Same Directory with that name already exists! : C already exists";
    actual = mkdir.run("C/users".split(" "), "mkdir C/users" , false); 
    collectContent();
    assertTrue(allContent.equals(Arrays.asList("desktop users".split(" ")))  && actual.equals(expected));
  }
  
  @Test
  public void testGRelativeFromDirPath(){
    expected = null;
    actual = mkdir.run("desktop/project".split(" "), "mkdir desktop/project" , false); 
    Cd cd = new Cd();
    cd.run("desktop".split(" "), "cd desktop", false);
    collectContent();
    assertTrue(allContent.equals(Arrays.asList("project".split(" ")))  && actual == expected);
    cd.run("..".split(" "), "cd ..", false);
  }
  
  @Test
  public void testHMultipleArgsBothExist(){
    
  }
  
  @Test
  public void testIMultipleArgsOneExist(){
    
  }
  
}
