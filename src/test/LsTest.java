package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import commands.Ls;

public class LsTest {

    /**
    * Declare instance of FileSystem so we can access the filesystem
    */
    private static MockFileSystem fs;

    /**
    * Declare instance of Mkdir to make new directories
    */
    private static Ls ls;

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
    public void setup(){
        //Initializes the class to be tested
        ls = new Ls();
        //Gets a specific preset FileSystem
        fs = MockFileSystem.getMockFileSys("MOCKENV");
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
    public void testANoArgs(){
        //Actual return from Ls after the operation has been run
        actual = ls.run(fs, "ls", false);
        //Expected return from Ls
        expected = "A2\ndesktop\nusers\ndocuments\ndownloads\n";
        //Checks if the values are equal or not
        assertEquals(expected, actual);
    }

    /**
     * User provides a relative path
     */
    @Test
    public void testBRelativePath(){
        //Actual return from Ls after the operation has been run 
        actual = ls.run(fs, "ls users", false);
        //Expected return from Ls
        expected = "Path: /users\nskeshavaa\nguest\n\n";
        //Checks if the values are equal or not
        assertEquals(expected, actual);
    }

    /**
     * Test C : User provides an absolute path 
     */
    @Test
    public void testCAbsolutePath(){
        //Actual return from Ls after the operation has been run 
        actual = ls.run(fs, "ls /documents", false);
        //Expected return from Ls
        expected = "Path: /documents\ntxtone\ntxttwo\njournal\n\n";
        //Checks if the values are equal or not
        assertEquals(expected, actual);
    }

    /**
     * Test D : User provides the special character -R 
     */
    @Test
    public void TestDRecurisveMode(){
        //Actual return from Ls after the operation has been run 
        actual = ls.run(fs, "ls -R", false);
        //Expected return from Ls
        expected = "Path: /\nA2\ndesktop\nusers\ndocuments\ndownloads\n\nPath: /desktop\n\nPath: "
                                + "/users\nskeshavaa\nguest\n\nPath: /users/skeshavaa"
                                + "\n\nPath: /users/guest\n\nPath: /documents\ntxtone"
                                + "\ntxttwo\njournal\n\nPath: /documents/journal\nweek1\n\nPath: /documents/journal/week1\n\nPath: /downloads\nhomework\nGames\n\nPath: /downloads/homework"
                                + "\nHW8\n\nPath: /downloads/Games\n\n";
        //Checks if the values are equal or not
        assertEquals(expected, actual);
    }

    /**
     * Test E : User provides multiple valid paths
     */
    @Test
    public void TestEMultiplePaths(){
        //Actual return from Ls after the operation has been run 
        actual = ls.run(fs, "ls users documents", false);
        //Expected return from Ls
        expected = "Path: /users\nskeshavaa\nguest\n\nPath: /documents\ntxtone\ntxttwo\njournal\n\n";
        //Checks if the values are equal or not
        assertEquals(expected, actual);
    }

    /**
     * Test F : User provides an invalid path
     */
    @Test
    public void TestFInvalidPath(){
        //Actual return from Ls after the operation has been run
        actual = ls.run(fs, "ls falsepaths", false);
        //Expected return from Ls
        expected = "Error: Invalid Directory : falsepaths is not a valid directory\n";
        //Checks if the values are equal or not
        assertEquals(expected, actual);
    }

    /**
     * Test G : User provides multiple paths, with one invalid path
     */
    @Test 
    public void TestGMultiplePathsWithInvalidPath(){
        //Actual return from Ls after the operation has been run
        actual = ls.run(fs, "ls users falsepaths", false);
        //Expected return from Ls
        expected = "Error: Invalid Directory : falsepaths is not a valid directory\n";
        //Checks if the values are equal or not
        assertEquals(expected, actual);
    }

    /**
     * Test H : User uses redirection to overwrite with new content to file with result from Ls
     */
    @Test
    public void testHRedirectionOverwrite(){
        //Actual return from Ls after the operation has been run
        actual = ls.run(fs, "ls > A2", false);
        //Expected return from Ls
        expected = "A2\ndesktop\nusers\ndocuments\ndownloads\n";
        //Checks if the values are equal or not
        assertTrue(actual == null && expected.equals(fs.findFile("A2", false).getContent()));  
    }

    /**
     * Test I : User uses redirection to append new content to file with result from Ls
     */
    @Test
    public void testIRedirectionAppend(){
        //Actual return from Ls after the operation has been run
        actual = ls.run(fs, "ls -R >> A2", false);
        //Expected return from Ls
        expected = "Wow what a project\nPath: /\nA2\ndesktop\nusers\ndocuments\ndownloads\n\nPath: /desktop\n\nPath: "
                                + "/users\nskeshavaa\nguest\n\nPath: /users/skeshavaa"
                                + "\n\nPath: /users/guest\n\nPath: /documents\ntxtone"
                                + "\ntxttwo\njournal\n\nPath: /documents/journal\nweek1\n\nPath: /documents/journal/week1\n\nPath: /downloads\nhomework\nGames\n\nPath: /downloads/homework"
                                + "\nHW8\n\nPath: /downloads/Games\n\n";
        //Checks if the values are equal or not
        assertTrue(actual == null && expected.equals(fs.findFile("A2", false).getContent()));  
    }

    /**
     * Test J : User provides no file name for redirection
     */
    @Test
    public void testJRedirectionErrorCase1(){
        //Actual return from Ls after the operation has been run
        actual = ls.run(fs, "ls >", false);
        //Expected return from Ls
        expected = "Error : No parameters provided for redirection";
        //Checks if the values are equal or not
        assertEquals(expected, actual);       
    }

    /**
     * Test K : User provides multiple file names for redirection
     */
    @Test
    public void testKRedirectionErrorCase2(){
        //Actual return from Ls after the operation has been run
        actual = ls.run(fs, "ls > LOL polo", false);
        //Expected return from Ls
        expected = "Error : Multiple Parameters have been provided : [LOL, polo] Only one is required for redirection";
        //Checks if the values are equal or not
        assertEquals(expected, actual);       
    }

    /**
     * Test L : User provides invalid command and uses redirection
     */
    @Test 
    public void TestLRedirectionErrorCase3(){
        //Actual return from Ls after the operation has been run
        actual = ls.run(fs, "ls users falsepaths > file", false);
        //Expected return from Ls
        expected = "Error: Invalid Directory : falsepaths is not a valid directory\n";
        //Checks if the values are equal or not
        assertEquals(expected, actual);
    }

    /**
     * test M : User provides an invalid file name for redirection
     */
    @Test 
    public void TestMRedirectionErrorCase4(){
        //Actual return from Ls after the operation has been run
        actual = ls.run(fs, "ls users >> Hello$", false);
        //Expected return from Ls
        expected =  "Error: Invalid File : Hello$ is not a valid file name";
        //Checks if the values are equal or not
        assertEquals(expected, actual);
    }
}