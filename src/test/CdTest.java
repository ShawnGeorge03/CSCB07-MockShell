package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import commands.Cd;
import commands.Mkdir;
import commands.Echo;
import commands.DirectoryManager;
import data.FileSystem;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CdTest {

    private FileSystem fs;
    private Cd cd;
    private Mkdir mkdir;
    private Echo echo;
    private DirectoryManager pwd;

    private String expectedCd, expectedPath;
    private String actualCd, actualPath;

    @Before
    public void setUp() throws Exception {
        this.fs = FileSystem.getFileSys();
        this.cd = new Cd();
        this.mkdir = new Mkdir();
        this.echo = new Echo();
        this.pwd = new DirectoryManager();

        this.expectedCd = "";
        this.expectedPath = "";
        this.actualCd = "";
        this.actualPath = "";

        // Sets up the C Folder
        mkdir.MakeDirectory("users".split(" "));
        mkdir.MakeDirectory("pics".split(" "));
        mkdir.MakeDirectory("Sys".split(" "));
        echo.run("Sys".split(" "), "echo \"Wow what a project\" > A2", false);

        // Sets up the users Folder
        mkdir.MakeDirectory("C/users/desktop".split(" "));

        // Sets up the desktop Folder
        //echo.run("C/users/desktop".split(" "), "echo \"Hello TA\" > C/users/desktop/CSCB07", false);
        //echo.run("C/users/desktop".split(" "), "echo \"2+2=5\" > C/users/desktop/Hwk", false);

        // Sets up the pics Folder
        //echo.run("pics".split(" "), "echo \"this is a picturefile indeed\" > pics/picfile", false);
        //echo.run("pics".split(" "), "echo \"Hello TA from the pics Folder\" > pics/CSCB07", false);

        // Sets up the Sys Folder
        mkdir.MakeDirectory("Sys/IO".split(" "));
        mkdir.MakeDirectory("Sys/LOL".split(" "));

        // Sets up the IO Folder
        mkdir.MakeDirectory("C/Sys/IO/keyboard".split(" "));
        mkdir.MakeDirectory("C/Sys/IO/Mouse".split(" "));

        // Sets up the keyboard Folder
        //echo.run("C/Sys/IO/keyboard".split(" "), "echo \"QWERTY\" > C/Sys/IO/keyboard/keys", false);
        //echo.run("C/Sys/IO/keyboard".split(" "), "echo \"RGB == ways more      F    P   S\" > C/Sys/IO/keyboard/RGB",
        //        false);

        // Sets up the Mouse Folder
        //echo.run("C/Sys/IO/Mouse".split(" "), "echo \"Mouse is in Mouse Folder\" > C/Sys/IO/Mouse/Presses", false);
    }

    @Test
    public void testANoArgs() {
        String[] emptyArr = {};
        expectedCd = "Error : No parameters provided : ";
        expectedPath = "C";
        actualCd = cd.run(emptyArr, "cd ", false);
        actualPath = pwd.getCurrentPath();
        assertTrue(actualCd.equals(expectedCd) && actualPath.equals(expectedPath));
    }

    @Test
    public void testBRelativePath() {
        expectedCd = null;
        expectedPath = "C/pics";
        actualCd = cd.run("pics".split(" "), "cd pics ", false);
        actualPath = pwd.getCurrentPath();
        assertTrue(actualCd == expectedCd && actualPath.equals(expectedPath));
    }

    @Test
    public void testCPatternCase1() {
        expectedCd = null;
        expectedPath = "C";
        actualCd = cd.run("..".split(" "), "cd .. ", false);
        actualPath = pwd.getCurrentPath();
        assertTrue(actualCd == expectedCd && actualPath.equals(expectedPath));
    }

    @Test
    public void testDRelativeDir() {
        expectedCd = null;
        expectedPath = "C/users/desktop";
        actualCd = cd.run("users/desktop".split(" "), "cd users/desktop", false);
        actualPath = pwd.getCurrentPath();
        assertTrue(actualCd == expectedCd && actualPath.equals(expectedPath));
    }

    @Test
    public void testEPatternCase2() {
        expectedCd = null;
        expectedPath = "C";
        actualCd = cd.run("../..".split(" "), "cd ../.. ", false);
        actualPath = pwd.getCurrentPath();
        assertTrue(actualCd == expectedCd && actualPath.equals(expectedPath));
    }

    @Test
    public void testFTraverseFile() {
        expectedCd = "Error: Invalid Directory : A2";
        expectedPath = "C";
        actualCd = cd.run("A2".split(" "), "cd A2", false);
        actualPath = pwd.getCurrentPath();
        assertTrue(actualCd.equals(expectedCd) && actualPath.equals(expectedPath));
    }

    @Test
    public void testGAbsolutePath() {
        expectedCd = null;
        expectedPath = "C/Sys/IO/keyboard";
        actualCd = cd.run("C/Sys/IO/keyboard".split(" "), "cd C/Sys/IO/keyboard", false);
        actualPath = pwd.getCurrentPath();
        assertTrue(actualCd == expectedCd && actualPath.equals(expectedPath));
    }

    @Test
    public void testHPatternCase3() {
        expectedCd = null;
        expectedPath = "C";
        actualCd = cd.run("../../../".split(" "), "cd ../../../", false);
        actualPath = pwd.getCurrentPath();
        assertTrue(actualCd == expectedCd && actualPath.equals(expectedPath));
    }

    @Test
    public void testIMultipleArgs() {
        expectedCd = "Error : Multiple Parameters have been provided : C/pics C/Sys/LOL";
        expectedPath = "C";
        actualCd = cd.run("C/pics C/Sys/LOL".split(" "), "cd C/pics C/Sys/LOL", false);
        actualPath = pwd.getCurrentPath();
        assertTrue(actualCd.equals(expectedCd) && actualPath.equals(expectedPath));
    }

}