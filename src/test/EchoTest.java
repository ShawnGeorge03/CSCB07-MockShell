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

public class EchoTest {

    private static FileSystem fs;
    private static Mkdir mkdir;
    private static Echo echo;
    private static Cat cat;

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
        mkdir.MakeDirectory("users".split(" "));
        mkdir.MakeDirectory("pics".split(" "));
        mkdir.MakeDirectory("Sys".split(" "));

        // Sets up the users Folder
        mkdir.MakeDirectory("C/users/desktop".split(" "));

        // Sets up the Sys Folder
        mkdir.MakeDirectory("Sys/IO".split(" "));
        mkdir.MakeDirectory("Sys/LOL".split(" "));

        // Sets up the IO Folder
        mkdir.MakeDirectory("C/Sys/IO/keyboard".split(" "));
        mkdir.MakeDirectory("C/Sys/IO/Mouse".split(" "));
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
        actualEcho = echo.run(emptyArr, "echo ", false);
        assertEquals(expectedEcho, actualEcho);
    }

    @Test
    public void testBProperText() {
        expectedEcho = "Hello";
        actualEcho = echo.run("echo \"Hello\"".split(" "), "echo \"Hello\"", false);
        assertEquals(expectedEcho, actualEcho);
    }

    @Test
    public void testCMalformedTextCase1() {
        expectedEcho = "Error : Missing Quotes : echo \"Hello";
        actualEcho = echo.run("echo \"Hello".split(" "), "echo \"Hello", false);
        assertEquals(expectedEcho, actualEcho);
    }

    @Test
    public void testDMalformedTextCase2() {
        expectedEcho = "Error : Missing Quotes : echo Hello\"";
        actualEcho = echo.run("echo Hello\"".split(" "), "echo Hello\"", false);
        assertEquals(expectedEcho, actualEcho);
    }

    @Test
    public void testENoFileCase1() {
        expectedEcho = "Error: Invalid File : echo \"Hello\" >";
        actualEcho = echo.run("echo \"Hello\" >".split(" "), "echo \"Hello\" >", false);
        assertEquals(expectedEcho, actualEcho);
    }

    @Test
    public void testFNoFileCase2() {
        expectedEcho = "Error: Invalid File : echo \"Hello\" >>";
        actualEcho = echo.run("echo \"Hello\" >>".split(" "), "echo \"Hello\" >>", false);
        assertEquals(expectedEcho, actualEcho);
    }

    @Test
    public void testGWriteToRelativeFile() {
        expectedEcho = null;
        expectedCat = "Hello";
        actualEcho = echo.run("echo \"Hello\" > file".split(" "), "echo \"Hello\" > file", false);
        actualCat = cat.run("file".split(" "), "cat file", false);
        assertTrue(actualEcho == expectedEcho && actualCat.equals(expectedCat));
    }

    @Test
    public void testHAppendRelativeFile() {
        expectedEcho = null;
        expectedCat = "Hello" + "\n" + "Bye";
        echo.run("echo \"Hello\" > file".split(" "), "echo \"Hello\" > file", false);
        actualEcho = echo.run("echo \"Bye\" >> file".split(" "), "echo \"Bye\" >> file", false);
        actualCat = cat.run("file".split(" "), "cat file", false);
        assertTrue(actualEcho == expectedEcho && actualCat.equals(expectedCat));
    }

    @Test
    public void testIOverwriteRelativeFile() {
        expectedEcho = null;
        expectedCat = "okay";
        //Setup Calls
        echo.run("echo \"Hello\" > file".split(" "), "echo \"Hello\" > file", false);
        echo.run("echo \"Bye\" >> file".split(" "), "echo \"Bye\" >> file", false);
        //Acutal test case
        actualEcho = echo.run("echo \"okay\" > file".split(" "), "echo \"okay\" > file", false);
        actualCat = cat.run("file".split(" "), "cat file", false);
        assertTrue(actualEcho == expectedEcho && actualCat.equals(expectedCat));
    }

    @Test
    public void testJWriteToAbsoluteFile() {
        expectedEcho = null;
        expectedCat = "KeyWASD";
        actualEcho = echo.run("echo \"KeyWASD\" > C/Sys/IO/keyboard/keys".split(" "),
                "echo \"KeyWASD\" > C/Sys/IO/keyboard/keys", false);
        actualCat = cat.run("C/Sys/IO/keyboard/keys".split(" "), "cat C/Sys/IO/keyboard/keys", false);
        assertTrue(actualEcho == expectedEcho && actualCat.equals(expectedCat));
    }

    @Test
    public void testKAppendAbsoluteFile() {
        expectedEcho = null;
        expectedCat = "KeyWASD" + "\n" + "QWERTY";
        //Setup Call
        echo.run("echo \"KeyWASD\" > C/Sys/IO/keyboard/keys".split(" "), 
                "echo \"KeyWASD\" > C/Sys/IO/keyboard/keys", false);
        //Actual test case
        actualEcho = echo.run("echo \"QWERTY\" >> C/Sys/IO/keyboard/keys".split(" "),
                "echo \"QWERTY\" >> C/Sys/IO/keyboard/keys", false);
        actualCat = cat.run("C/Sys/IO/keyboard/keys".split(" "), "cat C/Sys/IO/keyboard/keys", false);
        assertTrue(actualEcho == expectedEcho && actualCat.equals(expectedCat));
    }

    @Test
    public void testLOverwriteRelativeFile() {
        expectedEcho = null;
        expectedCat = "RGB == ways more      F    P   S";
        actualEcho = echo.run("echo \"RGB == ways more      F    P   S\" > C/Sys/IO/keyboard/keys".split(" "),
                "echo \"RGB == ways more      F    P   S\" > C/Sys/IO/keyboard/keys", false);
        actualCat = cat.run("C/Sys/IO/keyboard/keys".split(" "), "cat C/Sys/IO/keyboard/keys", false);
        assertTrue(actualEcho == expectedEcho && actualCat.equals(expectedCat));
    }

}