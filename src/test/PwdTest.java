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

        mkdir.MakeDirectory("/users".split(" "),fs);
        mkdir.MakeDirectory("/users/desktop".split(" "),fs);
        mkdir.MakeDirectory("/Sys".split(" "),fs);
        mkdir.MakeDirectory("/Sys/IO".split(" "),fs);
        mkdir.MakeDirectory("/Sys/IO/keyboard".split(" "),fs);
        mkdir.MakeDirectory("/Sys/IO/Mouse".split(" "),fs);
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
        expectedPath = "/";
        actualPath = pwd.run(fs,emptyArr, "pwd", false);
        assertEquals(expectedPath, actualPath);
    }


    @Test
    public void testBChangeDirectoryRelative(){
        String[] emptyArr = {};
        expectedPath = "/users";
        cd.run(fs,expectedPath.split(" "), "cd " + expectedPath, false);
        actualPath = pwd.run(fs,emptyArr, "pwd", false);
        assertEquals(expectedPath, actualPath);
    }

    @Test
    public void testCChangeDirectoryAbsolute(){
        String[] emptyArr = {};
        expectedPath = "/Sys/IO/keyboard";
        cd.run(fs,expectedPath.split(" "), "cd " + expectedPath, false);
        actualPath = pwd.run(fs,emptyArr, "pwd", false);
        assertEquals(expectedPath, actualPath);
    }

    @Test
    public void testDChangeToRoot(){
        String[] emptyArr = {};
        expectedPath = "/";
        cd.run(fs,expectedPath.split(" "), "cd " + expectedPath, false);
        actualPath = pwd.run(fs,emptyArr, "pwd", false);
        assertEquals(expectedPath, actualPath);
    }

    @Test
    public void testEMultipleArgs(){
        String[] argsArr = {"/Sys/IO", "/users"};
        expectedPath = "Error: Invalid Argument : pwd doesn't take any arguments";
        actualPath = pwd.run(fs,argsArr, "pwd", false);
        assertEquals(expectedPath, actualPath);
    }
    
}