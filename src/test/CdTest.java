package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import commands.Cd;
import commands.Mkdir;
import commands.Echo;
import commands.FileManager;
import data.FileSystem;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CdTest {

    private FileSystem fs;
    private Cd cd;
    private Mkdir mkdir;
    private Echo echo;

    @Before
    public void setUp() throws Exception{
        this.fs = FileSystem.getFileSys();
        this.cd =  new Cd();
        this.mkdir = new Mkdir();
        this.echo = new Echo();
    
        //Sets up the C Folder
        mkdir.MakeDirectory("users".split(" "));
        mkdir.MakeDirectory("pics".split(" "));
        mkdir.MakeDirectory("Sys".split(" "));
        echo.run("Sys".split(" "), "echo \"Wow what a project\" > A2", false);

        //Sets up the users Folder
        mkdir.MakeDirectory("C/users/desktop".split(" "));

        //Sets up the desktop Folder
        echo.run("C/users/desktop".split(" "), "echo \"Hello TA\" > C/users/desktop/CSCB07", false);
        echo.run("C/users/desktop".split(" "), "echo \"2+2=5\" > C/users/desktop/Hwk", false);

        //Sets up the pics Folder
        echo.run("pics".split(" "), "echo \"this is a picturefile indeed\" > pics/picfile",false);
        echo.run("pics".split(" "), "echo \"Hello TA from the pics Folder\" > pics/CSCB07", false);

        //Sets up the Sys Folder
        mkdir.MakeDirectory("Sys/IO".split(" "));
        mkdir.MakeDirectory("Sys/LOL".split(" "));

        //Sets up the IO Folder
        mkdir.MakeDirectory("C/Sys/IO/keyboard".split(" "));
        mkdir.MakeDirectory("C/Sys/IO/Mouse".split(" "));

        //Sets up the keyboard Folder
        echo.run("C/Sys/IO/keyboard".split(" "), "echo \"QWERTY\" > C/Sys/IO/keyboard/keys", false);
        echo.run("C/Sys/IO/keyboard".split(" "), "echo \"RGB == ways more      F    P   S\" > C/Sys/IO/keyboard/RGB",false);

        //Sets up the Mouse Folder
        echo.run("C/Sys/IO/Mouse".split(" "), "echo \"Mouse is in Mouse Folder\" > C/Sys/IO/Mouse/Presses", false);
    }

    
    
}