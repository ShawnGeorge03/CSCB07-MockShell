package test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import commands.Cd;
import commands.Echo;
import commands.Ls;
import commands.Mkdir;
import data.FileSystem;
import data.FileSystemI;

public class LsTest {

    /**
    * Declare instance of FileSystem so we can access the filesystem
    */
    private static MockFileSystem fs;
    /**
    * Declare instance of Mkdir to make new directories
    */
    private static Ls ls;

    @Before
    public void setup(){
        ls = new Ls();
        fs = MockFileSystem.getMockFileSys("MOCKENV");
    }

    @Test
    public void TestANoArgs(){
        String[] input = {};
        String actual = ls.run(fs, input, "ls", false);
        String expected = "users\ndocuments\ndownloads";
        assertEquals(expected, actual);
    }

    @Test
    public void TestARelativePath(){
        String[] input = {"users"};
        String actual = ls.run(fs, input, "ls users", false);
        String expected = "users\ndocuments\ndownloads";
        assertEquals(expected, actual);
    }
    
}