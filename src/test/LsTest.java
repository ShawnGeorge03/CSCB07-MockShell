package test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import commands.Ls;

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
    public void testANoArgs(){
        String[] input = {};
        String actual = ls.run(fs, input, "ls", false);
        String expected = "A2\ndesktop\nusers\ndocuments\ndownloads\n";
        System.out.println(fs.getCurrentPath());
        assertEquals(expected, actual);
    }

    @Test
    public void testBRelativePath(){
        String[] input = {"users"};
        String actual = ls.run(fs, input, "ls users", false);
        String expected = "Path: /users\nskeshavaa\nguest\n\n";
        assertEquals(expected, actual);
    }

    @Test
    public void testCAbsolutePath(){
        String[] input = {"/documents"};
        String actual = ls.run(fs, input, "ls /documents", false);
        String expected = "Path: /documents\ntxtone\ntxttwo\njournal\n\n";
        assertEquals(expected, actual);
    }

    @Test
    public void TestDRecurisveMode(){
        String[] input = {"-R"};
        String actual = ls.run(fs, input, "ls -R", false);
        String expected = "Path: /\nA2\ndesktop\nusers\ndocuments\ndownloads\n\nPath: /desktop\n\nPath: "
                                + "/users\nskeshavaa\nguest\n\nPath: /users/skeshavaa"
                                + "\n\nPath: /users/guest\n\nPath: /documents\ntxtone"
                                + "\ntxttwo\njournal\n\nPath: /documents/journal\nweek1\n\nPath: /documents/journal/week1\n\nPath: /downloads\nhomework\nGames\n\nPath: /downloads/homework"
                                + "\nHW8\n\nPath: /downloads/Games\n\n";
        System.out.println(actual);
        System.out.println(expected);
        assertEquals(expected, actual);
    }

    @Test
    public void TestEMultiplePaths(){
        String[] input = {"users", "documents"};
        String actual = ls.run(fs, input, "ls users documents", false);
        String expected = "Path: /users\nskeshavaa\nguest\n\nPath: /documents\ntxtone\ntxttwo\njournal\n\n";
        assertEquals(expected, actual);
    }

    @Test
    public void TestFInvalidPath(){
        String[] input = {"falsepaths"};
        String actual = ls.run(fs, input, "ls falsepaths", false);
        System.out.println(actual);
        String expected = "Error: Invalid Directory : falsepaths is not a valid directory\n\n";
        assertEquals(expected, actual);
    }

    @Test 
    public void TestGMultiplePathsWithInvalidPath(){
        String[] input = {"users", "falsepaths"};
        String actual = ls.run(fs, input, "ls users falsepaths", false);
        System.out.println(actual);
        String expected = "Path: /users\nskeshavaa\nguest\n\nError: Invalid Directory : falsepaths is not a valid directory\n\n";
        assertEquals(expected, actual);
    }
    
}