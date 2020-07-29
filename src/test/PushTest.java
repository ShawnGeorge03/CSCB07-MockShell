package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import commands.Mkdir;
import commands.Push;
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
        push.run(fs,path, "pushd users/desktop", false);

        int expected = 2;
        int actual = fs.getStack().size();
        assertEquals(expected, actual);
    }

    @Test
    public void testBAbsolutePath() {
        String[] path = {"//Sys/IO/keyboard"};
        push.run(fs,path, "pushd /Sys/IO/keyboard", false);

        int expected = 2;
        int actual = fs.getStack().size();
        assertEquals(expected, actual);
    }

    @Test
    public void testCInvalidPath() {
        String[] path = {"wrongpath"};
        String actual = push.run(fs,path, "pushd wrongpath", false);
        String expected = "Error: Invalid Directory : wrongpath is not a valid directory";
        assertEquals(expected, actual);
    }

    @Test
    public void testDNoArguments() {
        String[] path = {};
        String actual = push.run(fs,path, "pushd ", false);
        String expected = "Error: Invalid Argument : 0 arguments, expecting 1 argument";
        assertEquals(expected, actual);
    }

    @Test
    public void testEMultipleArguments() {
        String[] path = {"hi", "hello", "bye"};
        String actual = push.run(fs,path, "pushd hi hello bye", false);
        String expected = "Error: Invalid Argument : 3 arguments, expecting 1 argument";
        assertEquals(expected, actual);
    }

}   