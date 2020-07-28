package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import commands.Cd;

import java.lang.reflect.Field;

/**
 * Class CdTest runs all the different test cases for Cd
 */
public class CdTest {
    
    /**
    * Declare instance of FileSystem so we can access the filesystem
    */
    private static MockFileSystem fs;
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
        fs = MockFileSystem.getMockFileSys("MOCKENV");
        //Initializes a Cd Object
        cd = new Cd();
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
        Field feild = fs.getClass().getDeclaredField("filesys");
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
        actualCd = cd.run(fs, emptyArr, "cd ", false);
        //Returns the current working directory
        actualPath = fs.getCurrentPath();
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
        expectedPath = "/users";
        //Actual return from Cd
        actualCd = cd.run(fs, "users".split(" "), "cd users ", false);
        //Returns the current working directory
        actualPath = fs.getCurrentPath();
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
        cd.run(fs, "pics".split(" "), "cd pics ", false);
        //Actual return from Cd
        actualCd = cd.run(fs, "..".split(" "), "cd .. ", false);
        //Returns the current working directory
        actualPath = fs.getCurrentPath();
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
        actualCd = cd.run(fs,"users/desktop".split(" "), "cd users/desktop", false);
        //Returns the current working directory
        actualPath = fs.getCurrentPath();
        //Checks if the values are equal or not
        assertTrue(actualCd == expectedCd && actualPath.equals(expectedPath));
    }

    @Test
    public void testEPatternCase2() {
        //Expected return from Cd
        expectedCd = null;
        //Expected current working directory
        expectedPath = "/";
        cd.run(fs,"users/desktop".split(" "), "cd users/desktop", false);
        //Actual return from Cd
        actualCd = cd.run(fs,"../..".split(" "), "cd ../.. ", false);
        //Returns the current working directory
        actualPath = fs.getCurrentPath();
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
        actualCd = cd.run(fs,"A2".split(" "), "cd A2", false);
        //Returns the current working directory
        actualPath = fs.getCurrentPath();
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
        actualCd = cd.run(fs,"/Sys/IO/keyboard".split(" "), "cd /Sys/IO/keyboard", false);
        //Returns the current working directory
        actualPath = fs.getCurrentPath();
        //Checks if the values are equal or not
        assertTrue(actualCd == expectedCd && actualPath.equals(expectedPath));
    }

    @Test
    public void testHPatternCase3() {
        //Expected return from Cd
        expectedCd = null;
        //Expected current working directory
        expectedPath = "/";
        cd.run(fs,"/Sys/IO/keyboard".split(" "), "cd /Sys/IO/keyboard", false);
        //Actual return from Cd
        actualCd = cd.run(fs,"../../../".split(" "), "cd ../../../", false);
        //Returns the current working directory
        actualPath = fs.getCurrentPath();
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
        actualCd = cd.run(fs,"/pics /Sys/LOL".split(" "), "cd /pics /Sys/LOL", false);
        //Returns the current working directory
        actualPath = fs.getCurrentPath();
        //Checks if the values are equal or not
        assertTrue(actualCd.equals(expectedCd) && actualPath.equals(expectedPath));
    }

}