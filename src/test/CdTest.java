package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import commands.Cd;
import commands.Mkdir;
import commands.Echo;
import commands.DirectoryManager;
import data.FileSystem;

import java.lang.reflect.Field;

/**
 * Class CdTest runs all the different test cases for Cd
 */
public class CdTest {
    
    /**
    * Declare instance of FileSystem so we can access the filesystem
    */
    private static FileSystem fs;
    /**
    * Declare instance of Mkdir to make new directories
    */
    private static Mkdir mkdir;
    /**
    * Declare instance of Echo to make new file
    */
    private static Echo echo;
    /**
    * Declare instance of DirectoryManager to verify the cd operation
    */
    private static DirectoryManager path;
    /**
    * Declare instance of Cd to be tested
    */
    private static Cd cd;

    
    /**
    * Declare two different instance of a String 
    * objects called expectedCd and expectedPath 
    */
    private static String expectedCd, expectedPath;

    /**
    * Declare two different instance of a String 
    * objects called actualCd and actualPath 
    */
    private static String actualCd, actualPath;

    /**
     * Sets up the test environment and initializes the instance variables
     * 
     * @throws Exception
    */
    @Before
    public void setUp() throws Exception {
        //Get the current FileSystem
        fs = FileSystem.getFileSys();
        // Initializes a Mkdir Object
        mkdir = new Mkdir();
        //Initializes a Echo Object
        echo = new Echo();
        //Initializes a DirectoryManager Object
        path = new DirectoryManager();
        //Initializes a Cd Object
        cd = new Cd();

        // Sets up the C Folder
        mkdir.MakeDirectory("users".split(" "));
        mkdir.MakeDirectory("pics".split(" "));
        mkdir.MakeDirectory("Sys".split(" "));
        echo.run("/".split(" "), "echo \"Wow what a project\" > A2", false);

        // Sets up the users Folder
        mkdir.MakeDirectory("/users/desktop".split(" "));

        // Sets up the Sys Folder
        mkdir.MakeDirectory("Sys/IO".split(" "));
        mkdir.MakeDirectory("Sys/LOL".split(" "));

        // Sets up the IO Folder
        mkdir.MakeDirectory("/Sys/IO/keyboard".split(" "));
        mkdir.MakeDirectory("/Sys/IO/Mouse".split(" "));
    }

    /**
     * Destroys the FileSystem after all the testcases have run
     * 
     * @throws Exception
    */
    @After
    public void tearDown() throws Exception {
        //Declares and initializes a Feild variable 
        //to the fileSys variable in FileSystem
        Field feild = fs.getClass().getDeclaredField("fileSys");
        //Allows the value of this variable in FileSystem to be accessed
        feild.setAccessible(true);
        //Changes the value of the variable in FileSystem to null
        feild.set(null, null);
    }

    /**
     * Test A : User provides no input
    */
    @Test
    public void testANoArgs() {
        //Declares and initializes an empty array
        String[] emptyArr = {};
        //Expected return from Cd
        expectedCd = "Error : No parameters provided : ";
        //Expected current working directory
        expectedPath = "/";
        //Actual return from Cd
        actualCd = cd.run(emptyArr, "cd ", false);
        //Returns the current working directory
        actualPath = path.getCurrentPath();
        //Checks if the values are equal or not
        assertTrue(actualCd.equals(expectedCd) && actualPath.equals(expectedPath));
    }

    /**
     * Test B : User provides a relative path
    */
    @Test
    public void testBRelativePath() {
        //Expected return from Cd
        expectedCd = null;
        //Expected current working directory
        expectedPath = "/pics";
        //Actual return from Cd
        actualCd = cd.run("pics".split("/"), "cd pics ", false);
        //Returns the current working directory
        actualPath = path.getCurrentPath();
        //Checks if the values are equal or not
        assertTrue(actualCd == expectedCd && actualPath.equals(expectedPath));
    }

    /**
     * Test C : User provides a certain pattern
    */
    @Test
    public void testCPatternCase1() {
        //Expected return from Cd
        expectedCd = null;
        //Expected current working directory
        expectedPath = "/";
        cd.run("pics".split("/"), "cd pics ", false);
        //Actual return from Cd
        actualCd = cd.run("..".split(" "), "cd .. ", false);
        //Returns the current working directory
        actualPath = path.getCurrentPath();
        //Checks if the values are equal or not
        assertTrue(actualCd == expectedCd && actualPath.equals(expectedPath));
    }

    @Test
    public void testDRelativeDir() {
        //Expected return from Cd
        expectedCd = null;
        //Expected current working directory
        expectedPath = "/users/desktop";
        //Actual return from Cd
        actualCd = cd.run("users/desktop".split("/"), "cd users/desktop", false);
        //Returns the current working directory
        actualPath = path.getCurrentPath();
        //Checks if the values are equal or not
        assertTrue(actualCd == expectedCd && actualPath.equals(expectedPath));
    }

    @Test
    public void testEPatternCase2() {
        //Expected return from Cd
        expectedCd = null;
        //Expected current working directory
        expectedPath = "/";
        cd.run("users/desktop".split("/"), "cd users/desktop", false);
        //Actual return from Cd
        actualCd = cd.run("../..".split(" "), "cd ../.. ", false);
        //Returns the current working directory
        actualPath = path.getCurrentPath();
        //Checks if the values are equal or not
        assertTrue(actualCd == expectedCd && actualPath.equals(expectedPath));
    }

    @Test
    public void testFTraverseFile() {
        //Expected return from Cd
        expectedCd = "Error: Invalid Directory : A2";
        //Expected current working directory
        expectedPath = "/";
        //Actual return from Cd
        actualCd = cd.run("A2".split(" "), "cd A2", false);
        //Returns the current working directory
        actualPath = path.getCurrentPath();
        //Checks if the values are equal or not
        assertTrue(actualCd.equals(expectedCd) && actualPath.equals(expectedPath));
    }

    @Test
    public void testGAbsolutePath() {
        //Expected return from Cd
        expectedCd = null;
        //Expected current working directory
        expectedPath = "/Sys/IO/keyboard";
        //Actual return from Cd
        actualCd = cd.run("/Sys/IO/keyboard".split("/"), "cd /Sys/IO/keyboard", false);
        //Returns the current working directory
        actualPath = path.getCurrentPath();
        //Checks if the values are equal or not
        assertTrue(actualCd == expectedCd && actualPath.equals(expectedPath));
    }

    @Test
    public void testHPatternCase3() {
        //Expected return from Cd
        expectedCd = null;
        //Expected current working directory
        expectedPath = "/";
        cd.run("/Sys/IO/keyboard".split("/"), "cd /Sys/IO/keyboard", false);
        //Actual return from Cd
        actualCd = cd.run("../../../".split(" "), "cd ../../../", false);
        //Returns the current working directory
        actualPath = path.getCurrentPath();
        //Checks if the values are equal or not
        assertTrue(actualCd == expectedCd && actualPath.equals(expectedPath));
    }

    @Test
    public void testIMultipleArgs() {
        //Expected return from Cd
        expectedCd = "Error : Multiple Parameters have been provided : /pics /Sys/LOL";
        //Expected current working directory
        expectedPath = "/";
        //Actual return from Cd
        actualCd = cd.run("/pics /Sys/LOL".split("/"), "cd /pics /Sys/LOL", false);
        //Returns the current working directory
        actualPath = path.getCurrentPath();
        //Checks if the values are equal or not
        assertTrue(actualCd.equals(expectedCd) && actualPath.equals(expectedPath));
    }

}