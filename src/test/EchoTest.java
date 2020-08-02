package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import commands.Echo;

import java.lang.reflect.Field;
/**
 * Class EchoTest runs all the different cases for Echo
 */
public class EchoTest {

    /**
    * Declare instance of FileSystem so we can access the filesystem
    */
    private static MockFileSystem fs;

    /**
     * Declares instance of Cat to be tested
     */
    private static Echo echo;

    /**
    * Declare two different instance of a String 
    * objects called expected and actual 
    */
    private static String expected, actual;

    /**
     * Sets up the test environment and initializes the instance variables
     * 
     * @throws Exception
    */
    @Before
    public void setup() throws Exception {
        fs = MockFileSystem.getMockFileSys("MOCKENV");
        echo = new Echo();
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
        //Expected return from Echo
        expected = "Error : No parameters provided";
        //Actual return from Echo after the operation has been run
        actual = echo.run(fs, "echo ", false);
        //Checks if the values are equal or not
        assertEquals(expected, actual);
    }

    /**
     * Test B : User provides proper input
     */
    @Test
    public void testBProperText() {
        //Expected return from Echo
        expected = "Hello";
        //Actual return from Echo after the operation has been run
        actual = echo.run(fs, "echo \"Hello\"", false);
        //Checks if the values are equal or not
        assertEquals(expected, actual);
    }

    /**
     * Test C : User provides malformed input
     */
    @Test
    public void testCMalformedTextCase1() {
        //Expected return from Echo
        expected = "Error : Missing Quotes : \"Hello";
        //Actual return from Echo after the operation has been run
        actual = echo.run(fs, "echo \"Hello", false);
        //Checks if the values are equal or not
        assertEquals(expected, actual);
    }

    /**
     * Test D : User provides malformed input
     */
    @Test
    public void testDMalformedTextCase2() {
        //Expected return from Echo
        expected = "Error : Missing Quotes : Hello\"";
        //Actual return from Echo after the operation has been run
        actual = echo.run(fs, "echo Hello\"", false);
        //Checks if the values are equal or not
        assertEquals(expected, actual);
    }

    
    /**
     * Test E : User chooses to redirect output but no file to overwrite to 
     */
    @Test
    public void testENoFileCase1() {
        //Expected return from Echo
        expected = "Error : No parameters provided for redirection";
        //Actual return from Echo after the operation has been run
        actual = echo.run(fs, "echo \"Hello\" >", false);
        //Checks if the values are equal or not
        assertEquals(expected, actual);
    }

    /**
     * Test F : User chooses to redirect output but no file to append to 
     */
    @Test
    public void testFNoFileCase2() {
        //Expected return from Echo
        expected = "Error : No parameters provided for redirection";
        //Actual return from Echo after the operation has been run
        actual = echo.run(fs, "echo \"Hello\" >>", false);
        //Checks if the values are equal or not
        assertEquals(expected, actual);
    }

    /**
     * Test G : User chooses to redirect output to a relative path file
     */
    @Test
    public void testGWriteToRelativeFile() {
        //Expected return from Echo
        expected = null;
        //Actual return from Echo after the operation has been run
        actual = echo.run(fs, "echo \"Hello\" > file", false);
        //Checks if the values are equal or not
        assertTrue(expected == actual && "Hello".equals(fs.findFile("file", false).getContent()));
    }

    /**
     * Test H : User chooses to redirect output to a absolute path file
     */
    @Test
    public void testHWriteToAbsoluteFile() {
        //Expected return from Echo
        expected = null;
        //Actual return from Echo after the operation has been run
        actual = echo.run(fs, "echo \"Hello\" > file", false);
        //Checks if the values are equal or not
        assertTrue(expected == actual && "Hello".equals(fs.findFile("file", false).getContent()));
    }

    /**
     * Test I : User chooses to overwrite contents of a file with the contents to relative filepath
     */
    @Test
    public void testIOverwriteRelativeFile() {
        //Expected return from Echo
        expected = null;
        //Actual return from Echo after the operation has been run
        actual= echo.run(fs, "echo \"okay\" > A2", false);
        //Checks if the values are equal or not
        assertTrue(actual == expected && "okay".equals(fs.findFile("A2", false).getContent()));
    }

    /**
     * Test J : User chooses to append the result to the contents of a file to relative path
     */
    @Test
    public void testJAppendRelativeFile() {
        //Expected return from Echo
        expected = null;
        //Actual return from Echo after the operation has been run
        actual = echo.run(fs,"echo \"Bye\" >> A2", false);
        //Checks if the values are equal or not
        assertTrue(actual == expected && "Wow what a project\nBye".equals(fs.findFile("A2", false).getContent()));
    }

    /**
     * Test K : User chooses to overwrite contents of a file with the contents to absolute filepath
     */
    @Test
    public void testKOverwriteAbsoluteFile() {
        //Expected return from Echo
        expected= null;
        //Actual return from Echo after the operation has been run
        actual = echo.run(fs, "echo \"KeyWASD\" > /downloads/homework/HW8", false);
        //Checks if the values are equal or not
        assertTrue(actual == expected && 
                "KeyWASD".equals(fs.findFile("/downloads/homework/HW8", false).getContent()));
    }

    /**
     * Test L : User chooses to append the result to the contents of a file to absolute path
     */
    @Test
    public void testLAppendAbsoluteFile() {
        //Expected return from Echo
        expected = null;
        //Actual return from Echo after the operation has been run
        actual = echo.run(fs, "echo \"QWERTY\" >> /documents/txtone", false);
        //Checks if the values are equal or not
        assertTrue(actual == expected && 
            "this is a document\nQWERTY".equals(fs.findFile("/documents/txtone", false).getContent()));
    }

    /**
     * Test M : User provides no filename for redirection
     */
    @Test
    public void testMRedirectionErrorCase1(){
        //Expected return from Echo
        expected = "Error : No parameters provided for redirection";
        //Actual return from Echo after the operation has been run
        actual = echo.run(fs, "echo \"QWERTY\" >>", false);
        //Checks if the values are equal or not
        assertEquals(expected, actual);
    }

    /**
     * Test N : User provides multiple file names fro redirection
     */
    @Test
    public void testNRedirectionErrorCase2(){
        //Expected return from Echo
        expected = "Error : Multiple Parameters have been provided : [pks, loops] Only one is required for redirection";
        //Actual return from Echo after the operation has been run
        actual = echo.run(fs,  "echo \"QWERTY\" >> pks loops", false);
        //Checks if the values are equal or not
        assertEquals(expected, actual);
    }

    /**
     * Test O : User provides a file anme for redirection but it an invalid name
     */
    @Test
    public void testORedirectionErrorCase3() {
        //Expected return from Echo
        expected =  "Error: Invalid File : Hello$ is not a valid file name";
        //Actual return from Echo after the operation has been run
        actual = echo.run(fs,"echo \"Hello\" > Hello$", false);
        //Checks if the values are equal or not
        assertEquals(expected, actual);
    }

}