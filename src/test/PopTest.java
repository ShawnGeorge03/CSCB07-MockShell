package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import commands.Mkdir;
import commands.Pop;
import commands.Push;
import data.FileSystem;
import data.FileSystemI;

import java.lang.reflect.Field;


public class PopTest {
    
    private static FileSystemI fs;

    private static Push push;

    private static Pop pop;


    private static String[] input = {};

    @Before
    public void setUp() throws Exception {

        fs = MockFileSystem.getMockFileSys("MOCKENV");
        push = new Push();
        pop = new Pop();
        String[] input = {"users/skeshavaa"};
        push.run(fs, input, "pushd users/skeshavaa", false);
        input[0] = "/documents/journal";
        push.run(fs, input, "pushd /documents/journal", false);
        input[0] = "/desktop";
        push.run(fs, input, "pushd /desktop", false);
    }

    @Test
    public void testAAbsolutePath(){
        pop.run(fs, input, "popd", false);
        String expected = "/documents/journal";
        String actual = fs.getCurrentPath();
        assertEquals(expected, actual);
    }

    @Test
    public void testBRelativePath(){
        pop.run(fs, input, "popd", false);
        pop.run(fs, input, "popd", false);
        String expected = "/users/skeshavaa";
        String actual = fs.getCurrentPath();
        assertEquals(expected, actual);
    }

    @Test
    public void testCLastPop(){
        pop.run(fs, input, "popd", false);
        pop.run(fs, input, "popd", false);
        pop.run(fs, input, "popd", false);

        String expected = "/";;
        String actual = fs.getCurrentPath();
        assertEquals(expected, actual);
    }

    @Test
    public void testDEmptyStack(){
        pop.run(fs, input, "popd", false);
        pop.run(fs, input, "popd", false);
        pop.run(fs, input, "popd", false);
       
        String expected = "Stack is empty";;
        String actual =  pop.run(fs, input, "popd", false);
        assertEquals(expected, actual);
    }

    @Test 
    public void testEArguments(){
        String input[] = {"unrequired args"};
        String actual = pop.run(fs, input, "popd unrequired args", false);
        String expected = "Error: Invalid Argument : No arguments should be given";
        assertEquals(expected, actual);
    }

}
