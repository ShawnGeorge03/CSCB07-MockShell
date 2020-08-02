package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import commands.Pwd;

import java.lang.reflect.Field;

public class PwdTest {
    /**
     * Declare instance of FileSystem so we can access the filesystem
     */
    private static MockFileSystem fs;

    /**
     * Declares instance of Pwd to be tested
     */
    private static Pwd pwd;

    /**
     * Delacres an empty String array
     */
    private String[] emptyArr = {};

    /**
     * Declare two different instance of a String 
     * objects called expectedPath and actualPath
     */
    private static String expectedPath, actualPath;

    /**
     * Sets up the test environment and initializes the instance variables
     * 
     * @throws Exception
     */
    @Before
    public void setup() throws Exception{
        // Gets a specific preset FileSystem
        fs = MockFileSystem.getMockFileSys("MOCKENV");
        // Initializes the class to be tested
        pwd = new Pwd();
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
     * Test A : User runs the first command as pwd 
     */
    @Test
    public void testAInitialPath(){
        //Expected return from pwd
        expectedPath = "/";
        //Actual return from pwd
        actualPath = pwd.run(fs, emptyArr, "pwd", false);
        //Compares the values
        assertEquals(expectedPath, actualPath);
    }

    /**
     * Test B : User runs the command after changing working directory
     */
    @Test
    public void testBChangeDirectory(){
        //Changes the current working directory
        fs.setCurrent(fs.users);
        //Expected return from pwd
        expectedPath = "/users";
        //Actual return from pwd
        actualPath = pwd.run(fs, emptyArr, "pwd", false);
        //Compares the values
        assertEquals(expectedPath, actualPath);
    }

    /**
     * Test C : Users changes the working 
     * directory to a subdirectory
     */
    @Test
    public void testCChangeSubDirectory(){
        //Changes the current working directory
        fs.setCurrent(fs.homework);
        //Expected return from pwd
        expectedPath = "/downloads/homework";
        //Actual return from pwd
        actualPath = pwd.run(fs, emptyArr, "pwd", false);
        //Compares the values
        assertEquals(expectedPath, actualPath);
    }

    /**
     * Test D : User changes to the root directory
     */
    @Test
    public void testDChangeToRoot(){
        //Changes the current working directory
        fs.setCurrent(fs.root);
        //Expected return from pwd
        expectedPath = "/";
        //Actual return from pwd
        actualPath = pwd.run(fs, emptyArr, "pwd", false);
        //Compares the values
        assertEquals(expectedPath, actualPath);
    }

    /**
     * Test E : User provides multiple 
     */
    @Test
    public void testEMultipleArgs(){
        //Expected return from pwd
        expectedPath = "Error: Invalid Argument : pwd doesn't take any arguments";
        //Actual return from pwd
        actualPath = pwd.run(fs, "/Sys/IO /users".split(" "), "pwd /Sys/IO /users", false);
        //Compares the values
        assertEquals(expectedPath, actualPath);
    }

    @Test
    public void testFOverwriteToFile(){
        fs.setCurrent(fs.games);
        expectedPath = null;
        actualPath = pwd.run(fs, "> /A2".split(" "), "pwd > /A2", false);
        assertTrue(expectedPath == actualPath && "/downloads/Games".equals(fs.findFile("/A2", false).getContent()));
    }


    @Test
    public void testGAppendToFile(){
        fs.setCurrent(fs.games);
        expectedPath = null;
        actualPath = pwd.run(fs, ">> /A2".split(" "), "pwd >> /A2", false);
        assertTrue(expectedPath == actualPath && 
            "Wow what a project\n/downloads/Games".equals(fs.findFile("/A2", false).getContent()));
    }

    @Test
    public void testRedirectionErrorCase1(){
        expectedPath = "Error : No parameters provided";
        actualPath = pwd.run(fs, ">>".split(" "), "pwd >>", false);
        assertEquals(expectedPath, actualPath);
    }

    @Test
    public void testRedirectionErrorCase2(){
        expectedPath = "Error : Multiple Parameters have been provided : [lol, plz, work] Only one is required for redirection";
        actualPath = pwd.run(fs, ">> lol plz work".split(" "), "pwd >> lol plz work", false);
        assertEquals(expectedPath, actualPath);
    }

    @Test
    public void testRedirectionErrorCase3(){
        expectedPath =  "Error: Invalid File : Hello$ is not a valid file name";
        actualPath = pwd.run(fs, ">> Hello$".split(" "), "pwd >> Hello$", false);
        assertEquals(expectedPath, actualPath);
    }



    
}