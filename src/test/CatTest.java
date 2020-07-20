package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import commands.Mkdir;
import commands.Echo;
import commands.Cat;
import data.FileSystem;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CatTest {

    private FileSystem fs;
    private Mkdir mkdir;
    private Echo echo;
    private Cat cat;

    private String expected, actual;

    @Before
    public void setup() throws Exception{
        this.fs = FileSystem.getFileSys();
        this.mkdir = new Mkdir();
        this.echo = new Echo();
        this.cat = new Cat();

        this.expected = "";
        this.actual = "";

        // Sets up the C Folder
        mkdir.MakeDirectory("users".split(" "));
        mkdir.MakeDirectory("pics".split(" "));
        mkdir.MakeDirectory("Sys".split(" "));
        echo.run("Sys".split(" "), "echo \"Wow what a project\" > A2", false);

        // Sets up the users Folder
        mkdir.MakeDirectory("C/users/desktop".split(" "));

        // Sets up the desktop Folder
        echo.run("C/users/desktop".split(" "), "echo \"Hello TA\" > C/users/desktop/CSCB07", false);
        echo.run("C/users/desktop".split(" "), "echo \"2+2=5\" > C/users/desktop/Hwk", false);

        // Sets up the pics Folder
        echo.run("pics".split(" "), "echo \"this is a picturefile indeed\" > pics/picfile", false);
        echo.run("pics".split(" "), "echo \"Hello TA from the pics Folder\" > pics/CSCB07", false);

        // Sets up the Sys Folder
        mkdir.MakeDirectory("Sys/IO".split(" "));
        mkdir.MakeDirectory("Sys/LOL".split(" "));

        // Sets up the IO Folder
        mkdir.MakeDirectory("C/Sys/IO/keyboard".split(" "));
        mkdir.MakeDirectory("C/Sys/IO/Mouse".split(" "));

        // Sets up the keyboard Folder
        echo.run("C/Sys/IO/keyboard".split(" "), "echo \"QWERTY\" > C/Sys/IO/keyboard/keys", false);
        echo.run("C/Sys/IO/keyboard".split(" "), "echo \"RGB == ways more      F    P   S\" > C/Sys/IO/keyboard/RGB",false);

        // Sets up the Mouse Folder
        echo.run("C/Sys/IO/Mouse".split(" "), "echo \"Mouse is in Mouse Folder\" > C/Sys/IO/Mouse/Presses", false);
    }

    @Test
    public void testANoArgs(){
        String[] emptyArr = {};
        expected = "Error : No parameters provided : ";
        actual = cat.run(emptyArr, "cat ", false);
        assertEquals(expected, actual);
    }

    @Test
    public void testBFileNotFoundCase1(){
        expected = "Error: File Not Found : C/pics/picflex";
        actual = cat.run("C/pics/picflex".split(" "), "cat C/pics/picflex", false);
        assertEquals(expected, actual);
    }

    @Test
    public void testCAbsolutePath(){
        expected = "this is a picturefile indeed";
        actual = cat.run("C/pics/picfile".split(" "), "cat C/pics/picfile", false);
        assertEquals(expected, actual);
    }

    @Test
    public void testDFileNotFoundCase2(){
        expected = "Error: File Not Found : A0";
        actual = cat.run("A0".split(" "), "cat A0", false);
        assertEquals(expected, actual);
    }

    @Test
    public void testERelativePath(){
        expected = "Wow what a project";
        actual = cat.run("A2".split(" "), "cat A2", false);
        assertEquals(expected, actual);
    }

    @Test
    public void testFMultipleFiles(){
        expected = "Hello TA" + "\n" + "\n" + "\n" + "Wow what a project";
        actual = cat.run("C/users/desktop/CSCB07 A2".split(" "), "cat C/users/desktop/CSCB07 A2", false);
        assertEquals(expected, actual);
    }

    @Test
    public void testGFileNotFoundCase3(){
        expected =  "2+2=5" + "\n" + "\n" + "\n" + "Error: File Not Found : C/Sys/LOL"
        + "\n" + "\n" + "\n" + "RGB == ways more      F    P   S";
        actual = cat.run("C/users/desktop/Hwk C/Sys/LOL C/Sys/IO/keyboard/RGB".split(" "), "cat C/users/desktop/Hwk C/Sys/LOL C/Sys/IO/keyboard/RGB", false);
        assertEquals(expected, actual);
    }

}