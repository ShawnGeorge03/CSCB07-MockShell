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

import commands.Save;

import java.lang.reflect.Field;

/**
 * Class SaveTest runs all the different test cases for Save
 */
public class SaveTest {

    /**
    * Declare instance of MockFileSystem so we can access the filesystem
    */
    private MockFileSystem fs;

    /**
     * Declares instance of Save to be tested
     */
    private Save save;

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
        save = new Save();
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
     * Test A : User does not input a filepath or filename
     */
    @Test
    public void testANoArgs(){
        //String array that holds the input parameters
        String[] emptyArr = {};
        //Expected string output from save
        expected = "Error : No parameters provided";
        //Actual output when running save
        actual = save.getFileContent(fs, emptyArr, "save", false);
        //Checks if the values are equal or not
        assertEquals(expected, actual);
    }

    /**
     * Test B : User inputs an invalid filename
     */
    @Test
    public void testBInvalidFileName(){
        //Expected string output from save
        expected = "Error: Invalid File : save !";
        //Actual output when running save
        actual = save.getFileContent(fs, "!".split(" "), "save !", false);
        //Checks if the values are equal or not
        assertEquals(expected, actual);
    }

    /**
     * Test C : User inputs an invalid filepath
     */
    @Test
    public void testCInvalidPath(){
        //Expected string output from save
        expected = "Error: Invalid Path : //testing";
        //Actual output when running save
        actual = save.getFileContent(fs, "//testing".split(" "), "save //testing", false);
        //Checks if the values are equal or not
        assertEquals(expected, actual);
    }

    /**
     * Test D : User only inputs the filename (without a ".")
     */
    @Test
    public void testDGivenFileNameWithoutFileType(){
        //Expected string output from save
        expected = "\"name\" : \"/\"\n"
                 + "\t\"isDir\" : \"true\"\n" 
                 + "\t\"parent\" : \"null\"\n"
                 + "\n"
                 + "\t\"/\"";
        //Actual output when running save
        actual = save.getFileContent(fs, "savefile".split(" "), "save savefile", false);
        //Checks if the values are equal or not
        assertEquals(expected, actual);
    }

    /**
     * Test E : User uses redirection for a non redirectionable command
     */
    @Test
    public void testERedirectionError(){
        //Actual output when running save
        actual = save.getFileContent(fs, "input > text".split(" "), "save input > text", false);
        //Expected string output from save
        expected = "Error : Redirection Error : save does not support redirection";
        //Checks if the values are equal or not
        assertEquals(expected, actual); 
    }

    /**
     * Test F : User only inputs the filename that is not a .json
     */
    @Test
    public void testFGivenFileNameWithIncorrectFileType(){
        //Expected string output from save
        expected = "Error: Invalid File : save savefile.txt";
        //Actual output when running save
        actual = save.getFileContent(fs, "savefile".split(" "), "save savefile.txt", false);
        //Checks if the values are equal or not
        assertEquals(expected, actual);
    }

    /**
     * Test E : 
     */
/*    @Test
    public void testEGivenRelativePath(){
        String[] testNoArg = {"saveFile"};
        expected = "\t\"name\" : \"/\"\n"
                 + "\t\"isDir\" : \"true\"\n" 
                 + "\t\"parent\" : \"null\"\n"
                 + "\n"
                 + "\t\"/\"\n\n";
        actual = save.run(fs, "save //testing", false);
        System.out.println(actual);
        assertEquals(expected, actual);
    }
*/    
    /**
     * Test F : 
     */
/*    @Test
    public void testFGivenAbsolutePath(){
        String[] testNoArg = {"saveFile"};
        expected = "\t\"name\" : \"/\"\n"
                 + "\t\"isDir\" : \"true\"\n" 
                 + "\t\"parent\" : \"null\"\n"
                 + "\n"
                 + "\t\"/\"\n\n";
        actual = save.run(fs, "save //testing", false);
        System.out.println(actual);
        assertEquals(expected, actual);
    }*/


}