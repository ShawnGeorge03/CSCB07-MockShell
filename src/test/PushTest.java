package test;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import commands.Push;
import data.FileSystemI;



public class PushTest {
    
    private static FileSystemI fs;
    private static Push push;

    String actual, expected;

    @Before
    public void setUp() throws Exception {
        fs = MockFileSystem.getMockFileSys("MOCKENV");
        push = new Push();
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

    @Test
    public void testARelativePath() {
        push.run(fs, "pushd users/skeshavaa", false);
        expected = "/users/skeshavaa";
        actual = fs.getCurrentPath();
        assertEquals(expected, actual);
    }

    @Test
    public void testBAbsolutePath() {
        push.run(fs, "pushd /documents/journal", false);
        actual = fs.getCurrentPath();
        expected = "/documents/journal";
        assertEquals(expected, actual);
    }

    @Test
    public void testCInvalidPath() {
        actual = push.run(fs, "pushd wrongpath", false);
        expected = "Error: Invalid Directory : wrongpath is not a valid directory";
        assertEquals(expected, actual);
    }

    @Test
    public void testDNoArguments() {
        actual = push.run(fs, "pushd ", false);
        expected ="Error : No parameters provided";
        assertEquals(expected, actual);
    }

    @Test
    public void testEMultipleArguments() {
        actual = push.run(fs, "pushd hi hello bye", false);
        expected = "Error : Multiple Parameters have been provided : "
                        +"hi hello bye Only 1 valid directory path is required";
        assertEquals(expected, actual);
    }

    @Test
    public void testFRedirectionError(){
        expected = "Error : Redirection Error : pushd does not support redirection";
        actual = push.run(fs, "pushd /users > test", false);
        assertEquals(expected, actual);
    }

   

}   