package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import commands.Save;

import java.lang.reflect.Field;

public class SaveTest {

    private MockFileSystem fs;
    private Save save;

    String expected, actual;

    @Before
    public void setup(){
        fs = MockFileSystem.getMockFileSys("EMPTYSYS");
        save = new Save();
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
     * Test A : User does not input a filepath or filename
     */
    @Test
    public void testANoArgs(){
        String[] emptyArr = {};
        expected = "Error : No parameters provided";
        actual = save.getFileContent(fs, emptyArr, "save", false);
        assertEquals(expected, actual);
    }

    /**
     * Test B : User inputs an invalid filename
     */
    @Test
    public void testBInvalidFileName(){
        expected = "Error: Invalid File : save !";
        actual = save.getFileContent(fs, "!".split(" "), "save !", false);
        assertEquals(expected, actual);
    }

    /**
     * Test C : User inputs an invalid filepath
     */
    @Test
    public void testCInvalidPath(){
        expected = "Error: Invalid Path : //testing";
        actual = save.getFileContent(fs, "//testing".split(" "), "save //testing", false);
        assertEquals(expected, actual);
    }

    /**
     * Test D : User only inputs the filename (without a ".")
     */
    @Test
    public void testDGivenFileNameWithoutFileType(){
        expected = "\"name\" : \"/\"\n"
                 + "\t\"isDir\" : \"true\"\n" 
                 + "\t\"parent\" : \"null\"\n"
                 + "\n"
                 + "\t\"/\"";
        actual = save.getFileContent(fs, "savefile".split(" "), "save savefile", false);
        assertEquals(expected, actual);
    }

    /**
     * Test E : User uses redirection for a non redirectionable command
     */
    @Test
    public void testERedirectionError(){
        actual = save.getFileContent(fs, "input > text".split(" "), "save input > text", false);
        expected = "Error : Redirection Error : save does not support redirection";
        assertEquals(expected, actual); 
    }

    /**
     * Test F : User only inputs the filename that is not a .json
     */
    @Test
    public void testFGivenFileNameWithIncorrectFileType(){
        expected = "Error: Invalid File : save savefile.txt";
        actual = save.getFileContent(fs, "savefile".split(" "), "save savefile.txt", false);
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