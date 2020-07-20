package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import commands.Cd;
import commands.Mkdir;
import commands.Pwd;
import data.FileSystem;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PwdTest {

    private FileSystem fs;
    private Cd cd;
    private Mkdir mkdir;
    private Pwd pwd;

    private String expectedPath;
    private String actualPath;


    @Before
    public void setup() throws Exception{
        this.fs = FileSystem.getFileSys();
        this.cd = new Cd();
        this.mkdir = new Mkdir();
        this.pwd = new Pwd();

        this.expectedPath = "";
        this.actualPath = "";

        mkdir.MakeDirectory("C/users".split(" "));
        mkdir.MakeDirectory("C/users/desktop".split(" "));
        mkdir.MakeDirectory("C/Sys".split(" "));
        mkdir.MakeDirectory("C/Sys/IO".split(" "));
        mkdir.MakeDirectory("C/Sys/IO/keyboard".split(" "));
        mkdir.MakeDirectory("C/Sys/IO/Mouse".split(" "));
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