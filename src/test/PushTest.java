package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import commands.Cd;
import commands.Mkdir;
import commands.Push;
import commands.Echo;
import commands.DirectoryManager;
import data.FileSystem;

import java.lang.reflect.Field;


public class PushTest {
    
    private static FileSystem fs;

    private static Mkdir mkdir;

    private static Push push;

    @Before
    public void setUp() throws Exception {

        fs = FileSystem.getFileSys();
        mkdir = new Mkdir();
        push = new Push();
        
        // Sets up the C Folder
        mkdir.MakeDirectory("users".split(" "));
        mkdir.MakeDirectory("pics".split(" "));
        mkdir.MakeDirectory("Sys".split(" "));

        // Sets up the users Folder
        mkdir.MakeDirectory("/users/desktop".split(" "));

        // Sets up the Sys Folder
        mkdir.MakeDirectory("Sys/IO".split(" "));
        mkdir.MakeDirectory("Sys/LOL".split(" "));

        // Sets up the IO Folder
        mkdir.MakeDirectory("/Sys/IO/keyboard".split(" "));
        mkdir.MakeDirectory("/Sys/IO/Mouse".split(" "));
        
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
        Field feild = fs.getClass().getDeclaredField("fileSys");
        //Allows the value of this variable in FileSystem to be accessed
        feild.setAccessible(true);
        //Changes the value of the variable in FileSystem to null
        feild.set(null, null);
    }

    @Test
    public void testARelativePath() {
        String[] path = {"users/desktop"};
        String output = push.run(path, "pushd users/desktop", false);

        int expected = 2;
        int actual = fs.getStack().size();
        assertEquals(expected, actual);
    }

    @Test
    public void testBAbsolutePath() {
        String[] path = {"//Sys/IO/keyboard"};
        String output = push.run(path, "pushd /Sys/IO/keyboard", false);

        int expected = 2;
        int actual = fs.getStack().size();
        assertEquals(expected, actual);
    }

    @Test
    public void testCInvalidPath() {
        String[] path = {"wrongpath"};
        String actual = push.run(path, "pushd wrongpath", false);
        String expected = "Error: Invalid Directory : wrongpath is not a valid directory";
        assertEquals(expected, actual);
    }

    @Test
    public void testDNoArguments() {
        String[] path = {};
        String actual = push.run(path, "pushd ", false);
        String expected = "Error: Invalid Argument : 0 arguments, expecting 1 argument";
        assertEquals(expected, actual);
    }

    @Test
    public void testEMultipleArguments() {
        String[] path = {"hi", "hello", "bye"};
        String actual = push.run(path, "pushd hi hello bye", false);
        String expected = "Error: Invalid Argument : 3 arguments, expecting 1 argument";
        assertEquals(expected, actual);
    }

}   