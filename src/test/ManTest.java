package test;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import commands.Man;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ManTest {

    private static Man man;

    private static String expected;
    private static String actual;

    @BeforeClass
    public static void setup() throws Exception{
        man = new Man();

        expected = "";
        actual = "";
    }

    @Test
    public void testANoArgs(){
        String[] emptyArr = {};
        expected = "Error: Invalid Argument :  are/is not supported command(s)";
        actual = man.run(emptyArr, "man ", false);
        assertEquals(expected, actual);      
    }

    @Test
    public void testBUnsupportedCommand(){
        expected = "Error: Invalid Argument : LOL are/is not supported command(s)";
        actual = man.run("LOL".split(" "), "man LOL", false);
        assertEquals(expected, actual);      
    }

    @Test
    public void testCSupportedCommand(){
        expected = "Command : exit" + "\n\n\tCloses the current session and leaves the Shell"
        + "\n\n\tParameter : None";
        actual = man.run("exit".split(" "), "man exit", false);
        assertEquals(expected, actual);      
    }

    @Test
    public void testDCommandMan(){
        expected =  "Command: man" + "\nProvides documentation on all commands within THIS Java Shell"
        + "\nProvides information such as arguments and function.";
        actual = man.run("man".split(" "), "man man", false);
        assertEquals(expected, actual);      
    }

    @Test
    public void testEMultipleArgs(){
        expected = "Error: Invalid Argument : speak ls are/is not supported command(s)";
        actual = man.run("speak ls".split(" "), "man speak ls", false);
        assertEquals(expected, actual);  
    }


    
}