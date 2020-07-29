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
        String[] emptyArr = {};
        expectedPath = "/";
        actualPath = pwd.run(fs,emptyArr, "pwd", false);
        assertEquals(expectedPath, actualPath);
    }


    @Test
    public void testBChangeDirectoryRelative(){
        String[] emptyArr = {};
        fs.setCurrent(fs.users);
        expectedPath = "/users";
        actualPath = pwd.run(fs,emptyArr, "pwd", false);
        assertEquals(expectedPath, actualPath);
    }

    @Test
    public void testCChangeDirectoryAbsolute(){
        String[] emptyArr = {};
        fs.setCurrent(fs.homework);
        expectedPath = "/downloads/homework";
        actualPath = pwd.run(fs,emptyArr, "pwd", false);
        assertEquals(expectedPath, actualPath);
    }

    @Test
    public void testDChangeToRoot(){
        String[] emptyArr = {};
        fs.setCurrent(fs.root);
        expectedPath = "/";
        actualPath = pwd.run(fs,emptyArr, "pwd", false);
        assertEquals(expectedPath, actualPath);
    }

    @Test
    public void testEMultipleArgs(){
        String[] argsArr = {"/Sys/IO", "/users"};
        expectedPath = "Error: Invalid Argument : pwd doesn't take any arguments";
        actualPath = pwd.run(fs,argsArr, "pwd /Sys/IO /users", false);
        assertEquals(expectedPath, actualPath);
    }

    @Test
    public void testFOverwriteToFile(){
        String[] emptyArr = {};
        fs.setCurrent(fs.games);
        expectedPath = null;
        actualPath = pwd.run(fs,emptyArr, "pwd > /A2", false);
        assertTrue(expectedPath == actualPath && "/downloads/Games".equals(fs.findFile("/A2", false).getContent()));
    }


    @Test
    public void testGAppendToFile(){
        String[] emptyArr = {};
        fs.setCurrent(fs.games);
        expectedPath = null;
        actualPath = pwd.run(fs,emptyArr, "pwd >> /A2", false);
        assertTrue(expectedPath == actualPath && 
            "Wow what a project\n/downloads/Games".equals(fs.findFile("/A2", false).getContent()));
    }

    @Test
    public void testRedirectionErrorCase1(){
        String[] emptyArr = {};
        expectedPath = "Error : No parameters provided : ";
        actualPath = pwd.run(fs,emptyArr, "pwd >>", false);
        assertEquals(expectedPath, actualPath);
    }

    @Test
    public void testRedirectionErrorCase2(){
        expectedPath = "Error : Multiple Parameters have been provided : [lol, plz, work] Only one is required";
        actualPath = pwd.run(fs,">> lol plz work".split(" "), "pwd >> lol plz work", false);
        assertEquals(expectedPath, actualPath);
    }


    
}