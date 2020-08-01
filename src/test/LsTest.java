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
        actual = ls.run(fs, "ls", false);
        expected = "A2\ndesktop\nusers\ndocuments\ndownloads\n";
        assertEquals(expected, actual);
    }

    @Test
    public void testBRelativePath(){
        actual = ls.run(fs, "ls users", false);
        expected = "Path: /users\nskeshavaa\nguest\n\n";
        assertEquals(expected, actual);
    }

    @Test
    public void testCAbsolutePath(){
        actual = ls.run(fs, "ls /documents", false);
        expected = "Path: /documents\ntxtone\ntxttwo\njournal\n\n";
        assertEquals(expected, actual);
    }

    @Test
    public void TestDRecurisveMode(){
        actual = ls.run(fs, "ls -R", false);
        expected = "Path: /\nA2\ndesktop\nusers\ndocuments\ndownloads\n\nPath: /desktop\n\nPath: "
                                + "/users\nskeshavaa\nguest\n\nPath: /users/skeshavaa"
                                + "\n\nPath: /users/guest\n\nPath: /documents\ntxtone"
                                + "\ntxttwo\njournal\n\nPath: /documents/journal\nweek1\n\nPath: /documents/journal/week1\n\nPath: /downloads\nhomework\nGames\n\nPath: /downloads/homework"
                                + "\nHW8\n\nPath: /downloads/Games\n\n";
        assertEquals(expected, actual);
    }

    @Test
    public void TestEMultiplePaths(){
        actual = ls.run(fs, "ls users documents", false);
        expected = "Path: /users\nskeshavaa\nguest\n\nPath: /documents\ntxtone\ntxttwo\njournal\n\n";
        assertEquals(expected, actual);
    }

    @Test
    public void TestFInvalidPath(){
        actual = ls.run(fs, "ls falsepaths", false);
        expected = "Error: Invalid Directory : falsepaths is not a valid directory\n";
        assertEquals(expected, actual);
    }

    @Test 
    public void TestGMultiplePathsWithInvalidPath(){
        actual = ls.run(fs, "ls users falsepaths", false);
        expected = "Error: Invalid Directory : falsepaths is not a valid directory\n";
        assertEquals(expected, actual);
    }

    @Test
    public void testHRedirectionOverwrite(){
        actual = ls.run(fs, "ls > A2", false);
        expected = "A2\ndesktop\nusers\ndocuments\ndownloads\n";
        assertTrue(actual == null && expected.equals(fs.findFile("A2", false).getContent()));  
    }

    @Test
    public void testIRedirectionAppend(){
        actual = ls.run(fs, "ls -R >> A2", false);
        expected = "Wow what a project\nPath: /\nA2\ndesktop\nusers\ndocuments\ndownloads\n\nPath: /desktop\n\nPath: "
                                + "/users\nskeshavaa\nguest\n\nPath: /users/skeshavaa"
                                + "\n\nPath: /users/guest\n\nPath: /documents\ntxtone"
                                + "\ntxttwo\njournal\n\nPath: /documents/journal\nweek1\n\nPath: /documents/journal/week1\n\nPath: /downloads\nhomework\nGames\n\nPath: /downloads/homework"
                                + "\nHW8\n\nPath: /downloads/Games\n\n";
        assertTrue(actual == null && expected.equals(fs.findFile("A2", false).getContent()));  
    }

    @Test
    public void testJRedirectionErrorCase1(){
        actual = ls.run(fs, "ls >", false);
        expected = "Error : No parameters provided for redirection";
        assertEquals(expected, actual);       
    }

    @Test
    public void testKRedirectionErrorCase2(){
        actual = ls.run(fs, "ls > LOL polo", false);
        expected = "Error : Multiple Parameters have been provided : [LOL, polo] Only one is required for redirection";
        assertEquals(expected, actual);       
    }

    @Test 
    public void TestLRedirectionErrorCase3(){
        actual = ls.run(fs, "ls users falsepaths", false);
        expected = "Error: Invalid Directory : falsepaths is not a valid directory\n";
        assertEquals(expected, actual);
    }

    @Test 
    public void TestMRedirectionErrorCase4(){
        actual = ls.run(fs, "ls users >> Hello$", false);
        expected =  "Error: Invalid File : Hello$ is not a valid file name";
        assertEquals(expected, actual);
    }
}