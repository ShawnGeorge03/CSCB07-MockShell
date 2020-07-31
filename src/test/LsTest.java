package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

    String actual, expected;

    @Before
    public void setup(){
        ls = new Ls();
        fs = MockFileSystem.getMockFileSys("MOCKENV");
    }

    @Test
    public void testANoArgs(){
        String[] input = {};
        actual = ls.run(fs, input, "ls", false);
        expected = "A2\ndesktop\nusers\ndocuments\ndownloads\n";
        assertEquals(expected, actual);
    }

    @Test
    public void testBRelativePath(){
        String[] input = {"users"};
        actual = ls.run(fs, input, "ls users", false);
        expected = "Path: /users\nskeshavaa\nguest\n\n";
        assertEquals(expected, actual);
    }

    @Test
    public void testCAbsolutePath(){
        String[] input = {"/documents"};
        actual = ls.run(fs, input, "ls /documents", false);
        expected = "Path: /documents\ntxtone\ntxttwo\njournal\n\n";
        assertEquals(expected, actual);
    }

    @Test
    public void TestDRecurisveMode(){
        String[] input = {"-R"};
        actual = ls.run(fs, input, "ls -R", false);
        expected = "Path: /\nA2\ndesktop\nusers\ndocuments\ndownloads\n\nPath: /desktop\n\nPath: "
                                + "/users\nskeshavaa\nguest\n\nPath: /users/skeshavaa"
                                + "\n\nPath: /users/guest\n\nPath: /documents\ntxtone"
                                + "\ntxttwo\njournal\n\nPath: /documents/journal\nweek1\n\nPath: /documents/journal/week1\n\nPath: /downloads\nhomework\nGames\n\nPath: /downloads/homework"
                                + "\nHW8\n\nPath: /downloads/Games\n\n";
        assertEquals(expected, actual);
    }

    @Test
    public void TestEMultiplePaths(){
        String[] input = {"users", "documents"};
        actual = ls.run(fs, input, "ls users documents", false);
        expected = "Path: /users\nskeshavaa\nguest\n\nPath: /documents\ntxtone\ntxttwo\njournal\n\n";
        assertEquals(expected, actual);
    }

    @Test
    public void TestFInvalidPath(){
        String[] input = {"falsepaths"};
        actual = ls.run(fs, input, "ls falsepaths", false);
        expected = "Error: Invalid Directory : falsepaths is not a valid directory\n";
        assertEquals(expected, actual);
    }

    @Test 
    public void TestGMultiplePathsWithInvalidPath(){
        String[] input = {"users", "falsepaths"};
        actual = ls.run(fs, input, "ls users falsepaths", false);
        expected = "Error: Invalid Directory : falsepaths is not a valid directory\n";
        assertEquals(expected, actual);
    }

    @Test
    public void testHRedirectionOverwrite(){
        String[] input = {">", "A2"};
        actual = ls.run(fs, input, "ls > A2", false);
        expected = "A2\ndesktop\nusers\ndocuments\ndownloads\n";
        assertTrue(actual == null && expected.equals(fs.findFile("A2", false).getContent()));  
    }

    @Test
    public void testIRedirectionAppend(){
        String[] input = {"-R", ">>", "A2"};
        actual = ls.run(fs, input, "ls -R >> A2", false);
        expected = "Wow what a project\nPath: /\nA2\ndesktop\nusers\ndocuments\ndownloads\n\nPath: /desktop\n\nPath: "
                                + "/users\nskeshavaa\nguest\n\nPath: /users/skeshavaa"
                                + "\n\nPath: /users/guest\n\nPath: /documents\ntxtone"
                                + "\ntxttwo\njournal\n\nPath: /documents/journal\nweek1\n\nPath: /documents/journal/week1\n\nPath: /downloads\nhomework\nGames\n\nPath: /downloads/homework"
                                + "\nHW8\n\nPath: /downloads/Games\n\n";
        assertTrue(actual == null && expected.equals(fs.findFile("A2", false).getContent()));  
    }

    @Test
    public void testJRedirectionErrorCase1(){
        String[] input = {">"};
        actual = ls.run(fs, input, "ls >", false);
        expected = "Error : No parameters provided";
        assertEquals(expected, actual);       
    }

    @Test
    public void testKRedirectionErrorCase2(){
        String[] input = {">", "LOL", "polo"};
        actual = ls.run(fs, input, "ls > LOL polo", false);
        expected = "Error : Multiple Parameters have been provided : [LOL, polo] Only one is required";
        assertEquals(expected, actual);       
    }

    @Test 
    public void TestLRedirectionErrorCase3(){
        String[] input = {"users", "falsepaths", ">>", "LOL"};
        actual = ls.run(fs, input, "ls users falsepaths", false);
        expected = "Error: Invalid Directory : falsepaths is not a valid directory\n";
        assertEquals(expected, actual);
    }
}