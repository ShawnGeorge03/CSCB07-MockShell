package test;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import commands.Push;

public class PushTest {

    /**
     * Declare instance of FileSystem so we can access the filesystem
     */
    private static MockFileSystem fs;

    /**
     * Declares instance of Push to be tested
     */
    private static Push push;

    /**
     * Declare two different instance of a String objects called expected and actual
     */
    private String actual, expected;

    /**
     * Sets up the test environment and initializes the instance variables
     * 
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        // Gets a specific preset FileSystem
        fs = MockFileSystem.getMockFileSys("MOCKENV");
        // Initializes the class to be tested
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

    /**
     * Test A : User provides an relative directory path
     */
    @Test
    public void testARelativePath() {
        //Expected return from Push
        expected = null;
        //Actual return from Push
        actual = push.run(fs, "pushd users/skeshavaa", false);
        //Check the return from Push and the current working directory
        assertTrue(actual == expected && fs.getCurrentPath().equals("/users/skeshavaa"));
    }

    /**
     * Test B : User provides an absolute directory path
     */
    @Test
    public void testBAbsolutePath() {
        //Expected return from Push
        expected = null;
        //Actual return from Push
        actual = push.run(fs, "pushd /documents/journal", false);
        //Check the return from Push and the current working directory
        assertTrue(actual == expected && fs.getCurrentPath().equals("/documents/journal"));
    }

    /**
     * Test C : User provides an invalid directory path
     */
    @Test
    public void testCInvalidPath() {
        //Expected return from Push
        expected = "Error: Invalid Directory : wrongpath is not a valid directory";
        //Actual return from Push
        actual = push.run(fs, "pushd wrongpath", false);
        //Check the return from Push and the current working directory
        assertTrue(actual.equals(expected) && fs.getCurrentPath().equals("/"));
    }

    /**
     * Test D : User provides no arguments
     */
    @Test
    public void testDNoArguments() {
        //Expected return from Push
        expected = "Error : No parameters provided";
        //Actual return from Push
        actual = push.run(fs, "pushd ", false);
        //Check the return from Push and the current working directory
        assertTrue(actual.equals(expected) && fs.getCurrentPath().equals("/"));
    }

    /**
     * Test E : User provides multiple paths
     */
    @Test
    public void testEMultipleArguments() {
        //Expected return from Push
        expected = "Error : Multiple Parameters have been provided : "
        + "hi hello bye Only 1 valid directory path is required";
        //Actual return from Push
        actual = push.run(fs, "pushd hi hello bye", false);    
        //Check the return from Push and the current working directory
        assertTrue(actual.equals(expected) && fs.getCurrentPath().equals("/"));
    }


    /**
     * Test F : User tries to use redirection on a non redirectionable command
     */
    @Test
    public void testFRedirectionError() {
        //Expected return from Push
        expected = "Error : Redirection Error : pushd does not support redirection";
        //Actual return from Push
        actual = push.run(fs, "pushd /users > test", false);
        //Check the return from Push and the current working directory
        assertTrue(actual.equals(expected) && fs.getCurrentPath().equals("/"));
    }

}