package test;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import commands.Cd;
import commands.Mkdir;
import commands.Echo;
import commands.DirectoryManager;
import data.FileSystem;

import java.lang.reflect.Field;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
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
    @BeforeClass
    public static void setUp() throws Exception {
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
        echo.run("Sys".split(" "), "echo \"Wow what a project\" > A2", false);

        // Sets up the users Folder
        mkdir.MakeDirectory("C/users/desktop".split(" "));

        // Sets up the Sys Folder
        mkdir.MakeDirectory("Sys/IO".split(" "));
        mkdir.MakeDirectory("Sys/LOL".split(" "));

        // Sets up the IO Folder
        mkdir.MakeDirectory("C/Sys/IO/keyboard".split(" "));
        mkdir.MakeDirectory("C/Sys/IO/Mouse".split(" "));
    }

    /**
     * Destroys the FileSystem after all the testcases have run
     * 
     * @throws Exception
    */
    @AfterClass
    public static void tearDown() throws Exception {
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
        expectedPath = "C";
        //Actual return from Cd
        actualCd = cd.run(emptyArr, "cd ", false);
        //Returns the current working directory
        actualPath = path.getCurrentPath();
        //Checks if the values are equal or not
        assertTrue(actualCd.equals(expectedCd) && actualPath.equals(expectedPath));
    }

    @Test
    public void testBRelativePath() {
        //Expected return from Cd
        expectedCd = null;
        //Expected current working directory
        expectedPath = "C/pics";
        //Actual return from Cd
        actualCd = cd.run("pics".split(" "), "cd pics ", false);
        //Returns the current working directory
        actualPath = path.getCurrentPath();
        //Checks if the values are equal or not
        assertTrue(actualCd == expectedCd && actualPath.equals(expectedPath));
    }

    @Test
    public void testCPatternCase1() {
        expectedCd = null;
        expectedPath = "C";
        actualCd = cd.run("..".split(" "), "cd .. ", false);
        actualPath = path.getCurrentPath();
        assertTrue(actualCd == expectedCd && actualPath.equals(expectedPath));
    }

    @Test
    public void testDRelativeDir() {
        expectedCd = null;
        expectedPath = "C/users/desktop";
        actualCd = cd.run("users/desktop".split(" "), "cd users/desktop", false);
        actualPath = path.getCurrentPath();
        assertTrue(actualCd == expectedCd && actualPath.equals(expectedPath));
    }

    @Test
    public void testEPatternCase2() {
        expectedCd = null;
        expectedPath = "C";
        actualCd = cd.run("../..".split(" "), "cd ../.. ", false);
        actualPath = path.getCurrentPath();
        assertTrue(actualCd == expectedCd && actualPath.equals(expectedPath));
    }

    @Test
    public void testFTraverseFile() {
        expectedCd = "Error: Invalid Directory : A2";
        expectedPath = "C";
        actualCd = cd.run("A2".split(" "), "cd A2", false);
        actualPath = path.getCurrentPath();
        assertTrue(actualCd.equals(expectedCd) && actualPath.equals(expectedPath));
    }

    @Test
    public void testGAbsolutePath() {
        expectedCd = null;
        expectedPath = "C/Sys/IO/keyboard";
        actualCd = cd.run("C/Sys/IO/keyboard".split(" "), "cd C/Sys/IO/keyboard", false);
        actualPath = path.getCurrentPath();
        assertTrue(actualCd == expectedCd && actualPath.equals(expectedPath));
    }

    @Test
    public void testHPatternCase3() {
        expectedCd = null;
        expectedPath = "C";
        actualCd = cd.run("../../../".split(" "), "cd ../../../", false);
        actualPath = path.getCurrentPath();
        assertTrue(actualCd == expectedCd && actualPath.equals(expectedPath));
    }

    @Test
    public void testIMultipleArgs() {
        expectedCd = "Error : Multiple Parameters have been provided : C/pics C/Sys/LOL";
        expectedPath = "C";
        actualCd = cd.run("C/pics C/Sys/LOL".split(" "), "cd C/pics C/Sys/LOL", false);
        actualPath = path.getCurrentPath();
        assertTrue(actualCd.equals(expectedCd) && actualPath.equals(expectedPath));
    }

}