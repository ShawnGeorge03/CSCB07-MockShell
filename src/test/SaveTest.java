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
     * Test A : 
     */
    @Test
    public void testANoArgs(){
        String[] testNoArg = {};
        expected = "Error : No parameters provided : save";
        actual = save.run(fs,testNoArg, "save", false);
        System.out.println(actual);
        assertEquals(expected, actual);
    }

    /**
     * Test B : 
     */
    @Test
    public void testBInvalidFileName(){
        String[] testNoArg = {"!"};
        expected = "Error: Invalid File : save !";
        actual = save.run(fs,testNoArg, "save !", false);
        assertEquals(expected, actual);
    }

    /**
     * Test C : 
     */
    @Test
    public void testCInvalidPath(){
        String[] testNoArg = {"//testing"};
        expected = "Error: Invalid Path : //testing";
        actual = save.run(fs,testNoArg, "save //testing", false);
        assertEquals(expected, actual);
    }

    /**
     * Test D : 
     */
    @Test
    public void testDGivenOnlyFileName(){
        String[] testNoArg = {"saveFile"};
        expected = "\"name\" : \"/\"\n"
                 + "\t\"isDir\" : \"true\"\n" 
                 + "\t\"parent\" : \"null\"\n"
                 + "\n"
                 + "\t\"/\"";
        actual = save.run(fs,testNoArg, "save //testing", false);
        System.out.println(actual);
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
        actual = save.run(fs,testNoArg, "save //testing", false);
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
        actual = save.run(fs,testNoArg, "save //testing", false);
        System.out.println(actual);
        assertEquals(expected, actual);
    }*/
}