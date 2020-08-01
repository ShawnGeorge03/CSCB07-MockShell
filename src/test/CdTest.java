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
        //Expected return from Cd
        expectedCd = "Error : No parameters provided";
        //Expected current working directory
        expectedPath = "/";
        //Actual return from Cd
        actualCd = cd.run(fs, "cd ", false);
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
        actualCd = cd.run(fs, "cd users ", false);
        //Returns the current working directory
        actualPath = fs.getCurrentPath();
        //Checks if the values are equal or not
        assertTrue(actualCd == expectedCd && actualPath.equals(expectedPath));
    }

    /**
     * Test C : User uses a special pattern to change directory
    */
    @Test
    public void testCPatternCase1() {
        //Expected return from Cd
        expectedCd = null;
        //Expected current working directory
        expectedPath = "/";
        //Sets the current working directory
        fs.setCurrent(fs.documents);
        //Actual return from Cd
        actualCd = cd.run(fs, "cd .. ", false);
        //Returns the current working directory
        actualPath = fs.getCurrentPath();
        //Checks if the values are equal or not
        assertTrue(actualCd == expectedCd && actualPath.equals(expectedPath));
    }

    /**
     * Test D : User provides a relative directory
     */
    @Test
    public void testDRelativeDir() {
        //Expected return from Cd
        expectedCd = null;
        //Expected current working directory
        expectedPath = "/users/skeshavaa";
        //Actual return from Cd
        actualCd = cd.run(fs, "cd users/skeshavaa", false);
        //Returns the current working directory
        actualPath = fs.getCurrentPath();
        //Checks if the values are equal or not
        assertTrue(actualCd == expectedCd && actualPath.equals(expectedPath));
    }

    /**
     * Test E : User uses a special pattern to change directory
     */
    @Test
    public void testEPatternCase2() {
        //Expected return from Cd
        expectedCd = null;
        //Expected current working directory
        expectedPath = "/";
        fs.setCurrent(fs.user1);
        //Actual return from Cd
        actualCd = cd.run(fs, "cd ../.. ", false);
        //Returns the current working directory
        actualPath = fs.getCurrentPath();
        //Checks if the values are equal or not
        assertTrue(actualCd == expectedCd && actualPath.equals(expectedPath));
    }

    /**
     * Test F : User provides a file path instead of a directory
     */
    @Test
    public void testFTraverseFile() {
        //Expected return from Cd
        expectedCd = "Error: Invalid Directory : A2";
        //Expected current working directory
        expectedPath = "/";
        //Actual return from Cd
        actualCd = cd.run(fs, "cd A2", false);
        //Returns the current working directory
        actualPath = fs.getCurrentPath();
        //Checks if the values are equal or not
        assertTrue(actualCd.equals(expectedCd) && actualPath.equals(expectedPath));
    }

    /**
     * Test G : User provides an absolute path to a directory
     */
    @Test
    public void testGAbsolutePath() {
        //Expected return from Cd
        expectedCd = null;
        //Expected current working directory
        expectedPath = "/documents/journal/week1";
        //Actual return from Cd
        actualCd = cd.run(fs, "cd /documents/journal/week1", false);
        //Returns the current working directory
        actualPath = fs.getCurrentPath();
        //Checks if the values are equal or not
        assertTrue(actualCd == expectedCd && actualPath.equals(expectedPath));
    }

    /**
     * Test H : User uses a special pattern to change directory
     */
    @Test
    public void testHPatternCase3() {
        //Expected return from Cd
        expectedCd = null;
        //Expected current working directory
        expectedPath = "/";
        fs.setCurrent(fs.week1);
        //Actual return from Cd
        actualCd = cd.run(fs, "cd ../../../", false);
        //Returns the current working directory
        actualPath = fs.getCurrentPath();
        //Checks if the values are equal or not
        assertTrue(actualCd == expectedCd && actualPath.equals(expectedPath));
    }

    /**
     * Test I : User provides multiple arguments
     */
    @Test
    public void testIMultipleArgs() {
        //Expected return from Cd
        expectedCd = "Error : Multiple Parameters have been provided : /pics /Sys/LOL";
        //Expected current working directory
        expectedPath = "/";
        //Actual return from Cd
        actualCd = cd.run(fs, "cd /pics /Sys/LOL", false);
        //Returns the current working directory
        actualPath = fs.getCurrentPath();
        //Checks if the values are equal or not
        assertTrue(actualCd.equals(expectedCd) && actualPath.equals(expectedPath));
    }

    /**
     * Test J : User uses a special pattern to access a subdirectory
     */
    @Test
    public void testJPatternCase4(){
        //Expected return from Cd
        expectedCd = null;
        //Expected current working directory
        expectedPath = "/documents";
        //Actual return from Cd
        actualCd = cd.run(fs, "cd ../../documents", false);
        //Returns the current working directory
        actualPath = fs.getCurrentPath();
        //Checks if the values are equal or not
        assertTrue(actualCd == expectedCd && actualPath.equals(expectedPath));
    }

    /**
     * Test K : User uses redirection for a non redirectionable command
     */
    @Test
    public void testKRedirectionError(){
        //Expected return from Cd
        expectedCd = "Error : Redirection Error : cd does not support redirection";
        //Expected current working directory
        expectedPath = "/";
        //Actual return from Cd
        actualCd = cd.run(fs, "cd /documents > file", false);
        //Returns the current working directory
        actualPath = fs.getCurrentPath();
        //Checks if the values are equal or not
        assertTrue(actualCd.equals(expectedCd) && actualPath.equals(expectedPath));
    }

    /**
    * Test L : User uses a special pattern to change directory
    */
    @Test
    public void testLPatternCase5() {
        //Expected return from Cd
        expectedCd = null;
        //Expected current working directory
        expectedPath = "/documents";
        //Sets the current working directory
        fs.setCurrent(fs.documents);
        //Actual return from Cd
        actualCd = cd.run(fs, "cd .", false);
        //Returns the current working directory
        actualPath = fs.getCurrentPath();
        //Checks if the values are equal or not
        assertTrue(actualCd == expectedCd && actualPath.equals(expectedPath));
    }

}