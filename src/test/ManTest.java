package test;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import commands.Man;
import data.FileSystem;

public class ManTest {

    private static Man man;
    private static FileSystem fs;

    private static String expected;
    private static String actual;

    @BeforeClass
    public static void setup() throws Exception{
        man = new Man();
        fs = FileSystem.getFileSys();

        expected = "";
        actual = "";
    }

    @Test
    public void testANoArgs(){
        String[] emptyArr = {};
        expected = "Error: Invalid Argument :  are/is not supported command(s)";
        actual = man.run(fs,emptyArr, "man ", false);
        assertEquals(expected, actual);      
    }

    @Test
    public void testBUnsupportedCommand(){
        expected = "Error: Invalid Argument : LOL are/is not supported command(s)";
        actual = man.run(fs,"LOL".split(" "), "man LOL", false);
        assertEquals(expected, actual);      
    }

    @Test
    public void testCSupportedCommand(){
        expected = "Command : exit" + "\n\n\tCloses the current session and leaves the Shell"
        + "\n\n\tParameter : None";
        actual = man.run(fs,"exit".split(" "), "man exit", false);
        assertEquals(expected, actual);      
    }

    @Test
    public void testDCommandMan(){
        expected = "Command: man CMD" + "\nProvides documentation on all commands within THIS Java Shell"
        + "\nProvides information such as arguments and function."
        +"\n\n\tParameter: None";
        actual = man.run(fs,"man".split(" "), "man man", false);
        assertEquals(expected, actual);      
    }

    @Test
    public void testEMultipleArgs(){
        expected = "Error: Invalid Argument : speak ls are/is not supported command(s)";
        actual = man.run(fs,"speak ls".split(" "), "man speak ls", false);
        assertEquals(expected, actual);  
    }


    
}