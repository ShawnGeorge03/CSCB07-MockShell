package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import commands.Cat;

import java.lang.reflect.Field;

/**
 * Class CatTest runs all the different test cases for Cat
 */
public class CatTest {

    /**
    * Declare instance of FileSystem so we can access the filesystem
    */
    private static MockFileSystem fs;
    /**
     * Declares instance of Cat to be tested
     */
    private static Cat cat;

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
        //Gets a specific preset FileSystem
        fs = MockFileSystem.getMockFileSys("MOCKENV");
        cat = new Cat();
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
        //Declares and initializes an empty array
        String[] emptyArr = {};
        //Expected return from Cat
        expected = "Error : No parameters provided : ";
        //Actual return from Cat after the operation has been run
        actual = cat.run(fs, emptyArr, "cat ", false);
        //Checks if the values are equal or not
        assertEquals(expected, actual);
    }

    /**
     * Test B : File not Found , Absolute Path
    */
    @Test
    public void testBFileNotFoundCase1(){
        //Expected return from Cat
        expected = "Error: File Not Found : /pics/picflex";
        //Actual return from Cat after the operation has been run
        actual = cat.run(fs, "/pics/picflex".split(" "), "cat /pics/picflex", false);
         //Checks if the values are equal or not
        assertEquals(expected, actual);
    }
    /**
     * Test C : File Exists Absolute Path
    */
    @Test
    public void testCAbsolutePath(){
        //Expected return from Cat
        expected = "this is a document";
        //Actual return from Cat after the operation has been run
        actual = cat.run(fs, "/documents/txtone".split(" "), "cat /documents/txtone", false);
        //Checks if the values are equal or not
        assertEquals(expected, actual);
    }

    /**
     * Test D : File not Found , Relative Path
    */
    @Test
    public void testDFileNotFoundCase2(){
        //Expected return from Cat
        expected = "Error: File Not Found : A0";
        //Actual return from Cat after the operation has been run
        actual = cat.run(fs, "A0".split(" "), "cat A0", false);
        //Checks if the values are equal or not
        assertEquals(expected, actual);
    }
    /**
     * Test E : File Exists , Relative Path
    */
    @Test
    public void testERelativePath(){
        //Expected return from Cat
        expected = "Wow what a project";
        //Actual return from Cat after the operation has been run
        actual = cat.run(fs, "A2".split(" "), "cat A2", false);
        //Checks if the values are equal or not
        assertEquals(expected, actual);
    }

    /**
     * Test F : Multiple Files of different file paths
    */
    @Test
    public void testFMultipleFiles(){
        //Expected return from Cat    
        expected = "this is a document 2" + "\n" + "\n" + "\n" + "Wow what a project";
        //Actual return from Cat after the operation has been run
        actual = cat.run(fs, "/documents/txttwo A2".split(" "), "cat /documents/txttwo A2", false);
        //Checks if the values are equal or not
        assertEquals(expected, actual);
    }

    /**
     * Test G : Multiple Files of different file paths but one File Not Found
    */
    @Test
    public void testGFileNotFoundCase3(){
        //Expected return from Cat
        expected =  "Error: File Not Found : LOL";
        //Actual return from Cat after the operation has been run
        actual = cat.run(fs, "downloads/homework/HW8 LOL /documents/txttwo".split(" "), 
                "cat downloads/homework/HW8 LOL /documents/txttwo", false);
        //Checks if the values are equal or not
        assertEquals(expected, actual);
    }

    /**
     * Test H : User chooses to overwrite contents of a file with the results
     */
    @Test
    public void testHRedirectionOverwrite(){
        //Expected return from Cat
        expected =  "2+2=5" + "\n" + "\n" + "\n" + "this is a document 2";
        //Actual return from Cat after the operation has been run
        actual = cat.run(fs, "downloads/homework/HW8 /documents/txttwo > A2".split(" "), 
                "cat downloads/homework/HW8 /documents/txttwo > A2", false);
        //Checks if the values are equal or not
        assertEquals(expected, fs.findFile("A2", false).getContent());
    }

    /**
     * Test I : User chooses to append the result to the contents of a file
     */
    @Test  
    public void testIRedirectionAppend(){
        //Expected return from Cat
        expected =  "2+2=5" + "\n" + "\n" + "\n" + "this is a document 2";
        //Actual return from Cat after the operation has been run
        actual = cat.run(fs, "downloads/homework/HW8 /documents/txttwo > downloads/homework/HW8".split(" "), 
                "cat downloads/homework/HW8 /documents/txttwo > downloads/homework/HW8", false);
        //Checks if the values are equal or not
        assertEquals(expected, fs.findFile("downloads/homework/HW8", false).getContent());
    }

    /**
     * Test J : User provides no files for the redirection
     */
    @Test
    public void testJRedirectionErrorCase1(){
        //Expected return from Cat
        expected = "Error : No parameters provided : ";
        //Actual return from Cat after the operation has been run
        actual = cat.run(fs, "args >".split(" "), "cat args >", false);
        //Checks if the values are equal or not
        assertEquals(expected, actual);       
    }

    /**
     * Test K : User provides multiple files for the redirection
     */
    @Test
    public void testKRedirectionErrorCase2(){
        //Expected return from Cat
        expected = "Error : Multiple Parameters have been provided : [LOL, zip] Only one is required";
        //Actual return from Cat after the operation has been run
        actual = cat.run(fs, "args > LOL zip".split(" "), "cat args >", false);
        //Checks if the values are equal or not
        assertEquals(expected, actual);   
    }

    /**
     * Test L : User provides an invalid file and redirects input to file
     */
    @Test  
    public void testLRedirectionErrorCase3(){
        //Expected return from Cat
        expected =  "Error: File Not Found : LOL";
        //Actual return from Cat after the operation has been run
        actual = cat.run(fs, "downloads/homework/HW8 LOL /documents/txttwo > downloads/homework/HW8".split(" "), 
                "cat downloads/homework/HW8 LOL /documents/txttwo > downloads/homework/HW8", false);
        //Checks if the values are equal or not
        assertEquals(expected, actual);
    }
}