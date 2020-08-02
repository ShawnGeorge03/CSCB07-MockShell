package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import commands.Load;

import java.lang.reflect.Field;

public class LoadTest {

    private MockFileSystem fs;
    private Load load;

    String expected, actual;

    @Before
    public void setup(){
        fs = MockFileSystem.getMockFileSys("EMPTYSYS");
        load = new Load();
    }

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
     * Test A : User does not input a filepath or file location
     */
    @Test
    public void testANoArgs(){
        String[] fileArgs = {};
        expected = "Error: Invalid Argument : No arguments should be given";
        actual = load.getFileContents(fs, fileArgs, "load", false);
        assertEquals(expected, actual);
    }

    /**
     * Test B : User inputs an invalid filename 
     */
    @Test
    public void testBInvalidFileName(){
        String[] fileArgs = {"!"};
        expected = "Error: Invalid File : load !";
        //System.out.println(fs.getCommandLog().size());
        actual = load.getFileContents(fs, fileArgs, "load !", false);
        assertEquals(expected, actual);
    }

    /**
     * Test C : User inputs an invalid file path
     */
    @Test
    public void testCInvalidPath(){
        String[] fileArgs = {"//testing"};
        expected = "Error: Invalid Path : //testing";
        actual = load.getFileContents(fs, fileArgs, "load //testing", false);
        assertEquals(expected, actual);
    }

    /**
     * Test D : User only inputs the filename and not an absolute path
     */
    @Test
    public void testDGivenIncorrectFileType(){
        String[] fileArgs = {"loadFile.txt"};
        expected = "Error: Invalid File : load loadfile.txt";
        actual = load.getFileContents(fs, fileArgs, "load loadfile.txt", false);
        //System.out.println(actual);
        assertEquals(expected, actual);
    }

    /**
     * Test E : User uses redirection for a non redirectionable command
     */
    @Test
    public void testERedirectionError(){
        String[] fileArgs = {"input", ">", "text"};
        expected = "Error : Redirection Error : load does not support redirection";
        actual = load.getFileContents(fs, fileArgs, "load input > text", false);
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