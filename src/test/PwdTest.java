package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import commands.Cd;
import commands.Mkdir;
import commands.Pwd;
import data.FileSystem;

import java.lang.reflect.Field;

public class PwdTest {

    private static FileSystem fs;
    private static Cd cd;
    private static Mkdir mkdir;
    private static Pwd pwd;

    private static String expectedPath;
    private static String actualPath;


    @Before
    public void setup() throws Exception{
        fs = FileSystem.getFileSys();
        cd = new Cd();
        mkdir = new Mkdir();
        pwd = new Pwd();

        expectedPath = "";
        actualPath = "";

        mkdir.MakeDirectory("C/users".split(" "));
        mkdir.MakeDirectory("C/users/desktop".split(" "));
        mkdir.MakeDirectory("C/Sys".split(" "));
        mkdir.MakeDirectory("C/Sys/IO".split(" "));
        mkdir.MakeDirectory("C/Sys/IO/keyboard".split(" "));
        mkdir.MakeDirectory("C/Sys/IO/Mouse".split(" "));
    }

    @After
    public void tearDown() throws Exception {
        Field feild = fs.getClass().getDeclaredField("fileSys");
        feild.setAccessible(true);
        feild.set(null, null);
    } 

    @Test
    public void testAInitialPath(){
        String[] emptyArr = {};
        expectedPath = "C";
        actualPath = pwd.run(emptyArr, "pwd", false);
        assertEquals(expectedPath, actualPath);
    }


    @Test
    public void testBChangeDirectoryRelative(){
        String[] emptyArr = {};
        expectedPath = "C/users";
        cd.run(expectedPath.split(" "), "cd " + expectedPath, false);
        actualPath = pwd.run(emptyArr, "pwd", false);
        assertEquals(expectedPath, actualPath);
    }

    @Test
    public void testCChangeDirectoryAbsolute(){
        String[] emptyArr = {};
        expectedPath = "C/Sys/IO/keyboard";
        cd.run(expectedPath.split(" "), "cd " + expectedPath, false);
        actualPath = pwd.run(emptyArr, "pwd", false);
        assertEquals(expectedPath, actualPath);
    }

    @Test
    public void testDChangeToRoot(){
        String[] emptyArr = {};
        expectedPath = "C";
        cd.run(expectedPath.split(" "), "cd " + expectedPath, false);
        actualPath = pwd.run(emptyArr, "pwd", false);
        assertEquals(expectedPath, actualPath);
    }

    @Test
    public void testEMultipleArgs(){
        String[] argsArr = {"C/Sys/IO", "C/users"};
        expectedPath = "Error: Invalid Argument : pwd doesn't take any arguments";
        actualPath = pwd.run(argsArr, "pwd", false);
        assertEquals(expectedPath, actualPath);
    }
    
}