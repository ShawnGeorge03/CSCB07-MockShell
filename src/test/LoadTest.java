// **********************************************************
// Assignment2:
// Student1:
// UTORID user_name: santhos7
// UT Student #: 1006094673
// Author: Shawn Santhoshgeorge
//
// Student2:
// UTORID user_name: shaiskan
// UT Student #: 1006243940
// Author: Keshavaa Shaiskandan
//
// Student3:
// UTORID user_name: patelt26
// UT Student #: 1005904103
// Author: Tirth Patel
//
// Student4:
// UTORID user_name: pate1101
// UT Student #: 1006315765
// Author: Abhay Patel
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC B07 and understand the consequences.
// *********************************************************
package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import commands.Load;

import java.lang.reflect.Field;

/**
 * Class LoadTest runs all the different test cases for Load
 */
public class LoadTest {

    /**
    * Declare instance of MockFileSystem so we can access the filesystem
    */
    private MockFileSystem fs;

    /**
     * Declares instance of Load to be tested
     */
    private Load load;

    /**
    * Declare two different instance of a String 
    * objects called expected and actual 
    */
    String expected, actual;

    /**
     * Sets up the test environment and initializes the instance variables
     * 
     * @throws Exception
    */
    @Before
    public void setup(){
        fs = MockFileSystem.getMockFileSys("EMPTYSYS");
        load = new Load();
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
     * Test : User does not input a filepath or file location
     */
    @Test
    public void testNoArgs(){
        //String array that stores the input paramaters
        String[] fileArgs = {};
        //Expected output when load has been executed
        expected = "Error: Invalid Argument : No arguments should be given";
        //Actual output when running Load
        actual = load.getFileContents(fs, fileArgs, "load", false);
        //Checks if the values are equal or not
        assertEquals(expected, actual);
    }

    /**
     * Test : User inputs an invalid filename 
     */
    @Test
    public void testInvalidFileName(){
        //String array that stores the input paramaters
        String[] fileArgs = {"!"};
        //Expected output when load has been executed
        expected = "Error: Invalid File : load !";
        //Actual output when running Load
        actual = load.getFileContents(fs, fileArgs, "load !", false);
        //Checks if the values are equal or not
        assertEquals(expected, actual);
    }

    /**
     * Test : User inputs an invalid file path
     */
    @Test
    public void testInvalidPath(){
        //String array that stores the input paramaters
        String[] fileArgs = {"//testing"};
        //Expected output when load has been executed
        expected = "Error: Invalid Path : //testing";
        //Actual output when running Load
        actual = load.getFileContents(fs, fileArgs, "load //testing", false);
        //Checks if the values are equal or not
        assertEquals(expected, actual);
    }

    /**
     * Test : User only inputs the filename and not an absolute path
     */
    @Test
    public void testGivenIncorrectFileType(){
        //String array that stores the input paramaters
        String[] fileArgs = {"loadFile.txt"};
        //Expected output when load has been executed
        expected = "Error: Invalid File : load loadfile.txt";
        //Actual output when running Load
        actual = load.getFileContents(fs, fileArgs, "load loadfile.txt", false);
        //Checks if the values are equal or not
        assertEquals(expected, actual);
    }

    /**
     * Test : User uses redirection for a non redirectionable command
     */
    @Test
    public void testRedirectionError(){
        //String array that stores the input paramaters
        String[] fileArgs = {"input", ">", "text"};
        //Expected output when load has been executed
        expected = "Error : Redirection Error : load does not support redirection";
        //Actual output when running Load
        actual = load.getFileContents(fs, fileArgs, "load input > text", false);
        //Checks if the values are equal or not
        assertEquals(expected, actual); 
    }
}