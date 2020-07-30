package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import commands.Rm;

public class RmTest {

    /**
    * Declare instance of FileSystem so we can access the filesystem
    */
    private static MockFileSystem fs;

    /**
    * Declare instance of Mkdir to make new directories
    */
    private static Rm rm;

    private boolean actual = false, expected = false;
    private String returnRm;

    @Before
    public void setup(){
        rm = new Rm();
        fs = MockFileSystem.getMockFileSys("MOCKENV");
    }

    /**
     * Test A : User removes a relative directory path
     */
    @Test
    public void testARelativeDir(){
        returnRm = rm.run(fs, "users".split(" "), "rm users", false);
        actual = fs.getCurrent().getList().contains(fs.users);
        assertTrue(returnRm == null && expected == actual);
    }

    /**
     * Test B : User removes a relative file path
     */
    @Test
    public void testBRelativeFile(){
        returnRm = rm.run(fs, "A2".split(" "), "rm A2", false);
        actual = fs.getCurrent().getList().contains(fs.A2);
        assertTrue(returnRm.equals("Error: Invalid Directory : A2 is not a directory"));
    }

    /**
     * Test C : User removes a relative subdirectory path
     */
    @Test
    public void testCRelativeSubdirectory(){
        returnRm = rm.run(fs, "users/skeshavaa".split(" "), "rm users/skeshavaa", false);
        fs.setCurrent(fs.users);
        actual = fs.getCurrent().getList().contains(fs.user1);
        assertTrue(returnRm == null && expected == actual );
    }

    /**
     * Test D : User removes a subdirectory file path
     */
    @Test
    public void testDSubdirectoryFile(){
        returnRm = rm.run(fs, "documents/txtone".split(" "), "rm documents/txtone", false);
        fs.setCurrent(fs.documents);
        System.out.println(returnRm);
        actual = fs.getCurrent().getList().contains(fs.doc1);
        assertTrue(returnRm.equals("Error: Invalid Directory : documents/txtone is not a directory") );
    }

    /**
     * Test E : User removes an absolute directory path
     */
    @Test
    public void testEAbsoluteDir(){
        returnRm = rm.run(fs, "/users/skeshavaa".split(" "), "rm /users/skeshavaa", false);
        fs.setCurrent(fs.users);
        actual = fs.getCurrent().getList().contains(fs.user1);
        assertTrue(returnRm == null && expected == actual );
    }

    /**
     * Test F : User removes a absolute file path
     */
    @Test
    public void testFAbsoluteFile(){
        returnRm = rm.run(fs, "/documents/txtone".split(" "), "rm /documents/txtone", false);
        fs.setCurrent(fs.documents);
        actual = fs.getCurrent().getList().contains(fs.doc1);
        System.out.println(returnRm);
        assertTrue(returnRm.equals("Error: Invalid Directory : /documents/txtone is not a directory"));
    }
    
    /**
     * Test G  : User uses redirection for a non redirectionable command
     */
    @Test
    public void testGRedirectionError(){
        String[] input = {"documents", ">", "text"};
        String actual = rm.run(fs, input, "rm documents > text", false);
        String expected = "Error : Redirection Error : rm does not support redirection";
        assertEquals(expected, actual); 
    }

}