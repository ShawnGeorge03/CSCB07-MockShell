package test;

import static org.junit.Assert.assertEquals;


import org.junit.Before;
import org.junit.Test;

import commands.Cd;
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

    private static Cd cd;

    @Before
    public void setup(){
        rm = new Rm();
        cd = new Cd();
        fs = MockFileSystem.getMockFileSys("MOCKENV");
    }

    @Test
    public void TestARmDirectory(){
        String[] input = {"users"};
        rm.run(fs, input, "rm users", false);
        boolean actual = false;
        boolean expected = false;   
        for (int i = 0; i < fs.getCurrent().getList().size(); i++){
            if (fs.getCurrent().getList().get(i).getName().equals("users")){
                actual = true;
            }
        }
        assertEquals(expected, actual);
    }

    @Test
    public void TestBRmFile(){
        String[] input = {"A2"};
        rm.run(fs, input, "rm A2", false);
        boolean actual = false;
        boolean expected = false;   
        for (int i = 0; i < fs.getCurrent().getList().size(); i++){
            if (fs.getCurrent().getList().get(i).getName().equals("A2")){
                actual = true;
            }
        }
        assertEquals(expected, actual);
    }

    @Test
    public void TestCRmRelataive(){
        String[] input = {"users/skeshavaa"};
        String[] input2 = {"documents/txtone"};
        String[] traverseToInput2 = {"documents"};
        rm.run(fs, input, "rm users/skeshavaa", false);
        rm.run(fs, input2, "rm documents/txtone", false);
        boolean actual = false;
        boolean expected = false;   
        actual = cd.run(input, fs);
        cd.run(traverseToInput2, fs);
        if (fs.getCurrent().getList().get(0).getName().equals("txtone")){
            actual = true;
        }

        assertEquals(expected, actual);
    }

    @Test
    public void TestDRmAbsolute(){
        String[] input = {"/users/skeshavaa"};
        String[] input2 = {"/documents/txtone"};
        String[] traverseToInput2 = {"documents"};
        rm.run(fs, input, "rm /users/skeshavaa", false);
        rm.run(fs, input2, "rm /documents/txtone", false);
        boolean actual = false;
        boolean expected = false;   
        actual = cd.run(input, fs);
        cd.run(traverseToInput2, fs);
        if (fs.getCurrent().getList().get(0).getName().equals("txtone")){
            actual = true;
        }

        assertEquals(expected, actual);
    }

    
}