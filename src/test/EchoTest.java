package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import commands.Mkdir;
import commands.Echo;
import commands.Cat;
import data.FileSystem;

import java.lang.reflect.Field;
/**
 * Class EchoTest runs all the different cases for Echo
 */
public class EchoTest {

    /**
     * Declares instance of FileSystem so we can acess the filesystem
     */
    private static FileSystem fs;
    /**
     * Declare instance of Mkdir to make new directories
     */
    private static Mkdir mkdir;
    /**
     * Declare instance of Cat to read a file for changes
     */
    private static Cat cat;
    /**
     * Declares instance of Echo to be tested
     */
    private static Echo echo;

    private static String expectedEcho, actualEcho, expectedCat, actualCat;

    @Before
    public void setup() throws Exception {
        fs = FileSystem.getFileSys();
        mkdir = new Mkdir();
        echo = new Echo();
        cat = new Cat();

        expectedEcho = "";
        expectedCat = "";
        actualEcho = "";
        actualCat = "";

        // Sets up the C Folder
        mkdir.MakeDirectory("users".split(" "),fs);
        mkdir.MakeDirectory("pics".split(" "),fs);
        mkdir.MakeDirectory("Sys".split(" "),fs);

        // Sets up the users Folder
        mkdir.MakeDirectory("/users/desktop".split(" "),fs);

        // Sets up the Sys Folder
        mkdir.MakeDirectory("Sys/IO".split(" "),fs);
        mkdir.MakeDirectory("Sys/LOL".split(" "),fs);

        // Sets up the IO Folder
        mkdir.MakeDirectory("/Sys/IO/keyboard".split(" "),fs);
        mkdir.MakeDirectory("/Sys/IO/Mouse".split(" "),fs);
    }

    @After
    public void tearDown() throws Exception {
        Field feild = fs.getClass().getDeclaredField("fileSys");
        feild.setAccessible(true);
        feild.set(null, null);
    }

    @Test
    public void testANoArgs() {
        String[] emptyArr = {};
        expectedEcho = "Error : No parameters provided : ";
        actualEcho = echo.run(fs,emptyArr, "echo ", false);
        assertEquals(expectedEcho, actualEcho);
    }

    @Test
    public void testBProperText() {
        expectedEcho = "Hello";
        actualEcho = echo.run(fs,"\"Hello\"".split(" "), "echo \"Hello\"", false);
        assertEquals(expectedEcho, actualEcho);
    }

    @Test
    public void testCMalformedTextCase1() {
        expectedEcho = "Error : Missing Quotes : \"Hello";
        actualEcho = echo.run(fs,"\"Hello".split(" "), "echo \"Hello", false);
        assertEquals(expectedEcho, actualEcho);
    }

    @Test
    public void testDMalformedTextCase2() {
        expectedEcho = "Error : Missing Quotes : Hello\"";
        actualEcho = echo.run(fs,"Hello\"".split(" "), "echo Hello\"", false);
        assertEquals(expectedEcho, actualEcho);
    }

    /*@Test
    public void testENoFileCase1() {
        expectedEcho = "Error: Invalid File : echo \"Hello\" >";
        actualEcho = echo.run(fs,"echo \"Hello\" >".split(" "), "echo \"Hello\" >", false);
        assertEquals(expectedEcho, actualEcho);
    }
    
    @Test
    public void testFNoFileCase2() {
        expectedEcho = "Error: Invalid File : echo \"Hello\" >>";
        actualEcho = echo.run(fs,"echo \"Hello\" >>".split(" "), "echo \"Hello\" >>", false);
        assertEquals(expectedEcho, actualEcho);
    }

    @Test
    public void testGWriteToRelativeFile() {
        expectedEcho = null;
        expectedCat = "Hello";
        actualEcho = echo.run(fs,"echo \"Hello\" > file".split(" "), "echo \"Hello\" > file", false);
        actualCat = cat.run(fs,"file".split(" "), "cat file", false);
        assertTrue(actualEcho == expectedEcho && actualCat.equals(expectedCat));
    }

    @Test
    public void testHAppendRelativeFile() {
        expectedEcho = null;
        expectedCat = "Hello" + "\n" + "Bye";
        echo.run(fs,"echo \"Hello\" > file".split(" "), "echo \"Hello\" > file", false);
        actualEcho = echo.run(fs,"echo \"Bye\" >> file".split(" "), "echo \"Bye\" >> file", false);
        actualCat = cat.run(fs,"file".split(" "), "cat file", false);
        assertTrue(actualEcho == expectedEcho && actualCat.equals(expectedCat));
    }

    @Test
    public void testIOverwriteRelativeFile() {
        expectedEcho = null;
        expectedCat = "okay";
        //Setup Calls
        echo.run(fs,"echo \"Hello\" > file".split(" "), "echo \"Hello\" > file", false);
        echo.run(fs,"echo \"Bye\" >> file".split(" "), "echo \"Bye\" >> file", false);
        //Acutal test case
        actualEcho = echo.run(fs,"echo \"okay\" > file".split(" "), "echo \"okay\" > file", false);
        actualCat = cat.run(fs,"file".split(" "), "cat file", false);
        assertTrue(actualEcho == expectedEcho && actualCat.equals(expectedCat));
    }

    @Test
    public void testJWriteToAbsoluteFile() {
        expectedEcho = null;
        expectedCat = "KeyWASD";
        actualEcho = echo.run(fs,"echo \"KeyWASD\" > /Sys/IO/keyboard/keys".split(" "),
                "echo \"KeyWASD\" > /Sys/IO/keyboard/keys", false);
        actualCat = cat.run(fs,"/Sys/IO/keyboard/keys".split(" "), "cat /Sys/IO/keyboard/keys", false);
        assertTrue(actualEcho == expectedEcho && actualCat.equals(expectedCat));
    }

    @Test
    public void testKAppendAbsoluteFile() {
        expectedEcho = null;
        expectedCat = "KeyWASD" + "\n" + "QWERTY";
        //Setup Call
        echo.run(fs,"echo \"KeyWASD\" > /Sys/IO/keyboard/keys".split(" "), 
                "echo \"KeyWASD\" > /Sys/IO/keyboard/keys", false);
        //Actual test case
        actualEcho = echo.run(fs,"echo \"QWERTY\" >> /Sys/IO/keyboard/keys".split(" "),
                "echo \"QWERTY\" >> /Sys/IO/keyboard/keys", false);
        actualCat = cat.run(fs,"/Sys/IO/keyboard/keys".split(" "), "cat /Sys/IO/keyboard/keys", false);
        assertTrue(actualEcho == expectedEcho && actualCat.equals(expectedCat));
    }

    @Test
    public void testLOverwriteRelativeFile() {
        expectedEcho = null;
        expectedCat = "RGB == ways more      F    P   S";
        actualEcho = echo.run(fs,"echo \"RGB == ways more      F    P   S\" > /Sys/IO/keyboard/keys".split(" "),
                "echo \"RGB == ways more      F    P   S\" > /Sys/IO/keyboard/keys", false);
        actualCat = cat.run(fs,"/Sys/IO/keyboard/keys".split(" "), "cat /Sys/IO/keyboard/keys", false);
        assertTrue(actualEcho == expectedEcho && actualCat.equals(expectedCat));
    }*/

}