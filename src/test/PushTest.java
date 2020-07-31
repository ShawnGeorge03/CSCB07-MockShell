package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import commands.Mkdir;
import commands.Push;
import data.FileSystem;
import data.FileSystemI;

import java.lang.reflect.Field;


public class PushTest {
    
    private static FileSystemI fs;

    private static Mkdir mkdir;

    private static Push push;

    @Before
    public void setUp() throws Exception {
        fs = MockFileSystem.getMockFileSys("MOCKENV");
        push = new Push();
    }

     /**
     * Destroys the FileSystem after all the testcases have run
     * 
     * @throws Exception
    */

    @Test
    public void testARelativePath() {
        String[] path = {"users/skeshavaa"};
        push.run(fs,path, "pushd users/desktop", false);
        String expected = "/users/skeshavaa";
        String actual = fs.getCurrentPath();
        System.out.println(fs.getCurrentPath());
        assertEquals(expected, actual);
    }

    @Test
    public void testBAbsolutePath() {
        String[] path = {"/documents/journal"};
        push.run(fs,path, "pushd /documents/journal", false);
        String actual = fs.getCurrentPath();
        String expected = "/documents/journal";
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