package test;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import commands.Cd;
import commands.Mkdir;
import commands.Echo;
import commands.DirectoryManager;
import data.FileSystem;

import java.lang.reflect.Field;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CdTest {

    private static FileSystem fs;
    private static Cd cd;
    private static Mkdir mkdir;
    private static Echo echo;
    private static DirectoryManager pwd;

    private static String expectedCd, expectedPath;
    private static String actualCd, actualPath;

    @BeforeClass
    public static void setUp() throws Exception {
        fs = FileSystem.getFileSys();
        cd = new Cd();
        mkdir = new Mkdir();
        echo = new Echo();
        pwd = new DirectoryManager();

        expectedCd = "";
        expectedPath = "";
        actualCd = "";
        actualPath = "";

        // Sets up the C Folder
        mkdir.MakeDirectory("users".split(" "));
        mkdir.MakeDirectory("pics".split(" "));
        mkdir.MakeDirectory("Sys".split(" "));
        echo.run("Sys".split(" "), "echo \"Wow what a project\" > A2", false);

        // Sets up the users Folder
        mkdir.MakeDirectory("C/users/desktop".split(" "));

        // Sets up the Sys Folder
        mkdir.MakeDirectory("Sys/IO".split(" "));
        mkdir.MakeDirectory("Sys/LOL".split(" "));

        // Sets up the IO Folder
        mkdir.MakeDirectory("C/Sys/IO/keyboard".split(" "));
        mkdir.MakeDirectory("C/Sys/IO/Mouse".split(" "));
    }

    @AfterClass
    public static void tearDown() throws Exception {
        Field feild = fs.getClass().getDeclaredField("fileSys");
        feild.setAccessible(true);
        feild.set(null, null);
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