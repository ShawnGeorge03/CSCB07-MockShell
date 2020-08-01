package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import commands.Pwd;

import java.lang.reflect.Field;

public class PwdTest {

    private static MockFileSystem fs;
    private static Pwd pwd;

    private static String expectedPath, actualPath;

    @Before
    public void setup() throws Exception{
        fs = MockFileSystem.getMockFileSys("MOCKENV");
        pwd = new Pwd();
    }

    @After
    public void tearDown() throws Exception {
        Field feild = fs.getClass().getDeclaredField("filesys");
        feild.setAccessible(true);
        feild.set(null, null);
    } 

    @Test
    public void testAInitialPath(){
        expectedPath = "/";
        actualPath = pwd.run(fs, "pwd", false);
        assertEquals(expectedPath, actualPath);
    }


    @Test
    public void testBChangeDirectoryRelative(){
        fs.setCurrent(fs.users);
        expectedPath = "/users";
        actualPath = pwd.run(fs, "pwd", false);
        assertEquals(expectedPath, actualPath);
    }

    @Test
    public void testCChangeDirectoryAbsolute(){
        fs.setCurrent(fs.homework);
        expectedPath = "/downloads/homework";
        actualPath = pwd.run(fs, "pwd", false);
        assertEquals(expectedPath, actualPath);
    }

    @Test
    public void testDChangeToRoot(){
        fs.setCurrent(fs.root);
        expectedPath = "/";
        actualPath = pwd.run(fs, "pwd", false);
        assertEquals(expectedPath, actualPath);
    }

    @Test
    public void testEMultipleArgs(){
        expectedPath = "Error: Invalid Argument : pwd doesn't take any arguments";
        actualPath = pwd.run(fs, "pwd /Sys/IO /users", false);
        assertEquals(expectedPath, actualPath);
    }

    @Test
    public void testFOverwriteToFile(){
        fs.setCurrent(fs.games);
        expectedPath = null;
        actualPath = pwd.run(fs, "pwd > /A2", false);
        assertTrue(expectedPath == actualPath && "/downloads/Games".equals(fs.findFile("/A2", false).getContent()));
    }


    @Test
    public void testGAppendToFile(){
        fs.setCurrent(fs.games);
        expectedPath = null;
        actualPath = pwd.run(fs, "pwd >> /A2", false);
        assertTrue(expectedPath == actualPath && 
            "Wow what a project\n/downloads/Games".equals(fs.findFile("/A2", false).getContent()));
    }

    @Test
    public void testRedirectionErrorCase1(){
        expectedPath = "Error : No parameters provided";
        actualPath = pwd.run(fs,"pwd >>", false);
        assertEquals(expectedPath, actualPath);
    }

    @Test
    public void testRedirectionErrorCase2(){
        expectedPath = "Error : Multiple Parameters have been provided : [lol, plz, work] Only one is required for redirection";
        actualPath = pwd.run(fs, "pwd >> lol plz work", false);
        assertEquals(expectedPath, actualPath);
    }

    @Test
    public void testRedirectionErrorCase3(){
        expectedPath =  "Error: Invalid File : Hello$ is not a valid file name";
        actualPath = pwd.run(fs, "pwd >> Hello$", false);
        assertEquals(expectedPath, actualPath);
    }



    
}