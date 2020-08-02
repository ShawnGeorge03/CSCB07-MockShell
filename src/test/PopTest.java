package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import commands.Pop;

import java.lang.reflect.Field;

public class PopTest {

    /**
     * Declare instance of FileSystem so we can access the filesystem
     */
    private static MockFileSystem fs;

    /**
     * Declares instance of Pop to be tested
     */
    private static Pop pop;

    /**
     * Declare two different instance of a String objects called expected and actual
     */
    private String actual, expected;

    /**
     * Sets up the test environment and initializes the instance variables
     * 
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        // Gets a specific preset FileSystem
        fs = MockFileSystem.getMockFileSys("MOCKENV");
        // Initializes the class to be tested
        pop = new Pop();
        // Sets up the Directory Stack
        fs.getStack().add("/documents/journal");
        fs.getStack().add("/users/skeshavaa");
        fs.getStack().add("/");
        // Sets the current working directory
        fs.setCurrent(fs.desktop);
    }

    /**
     * Destroys the MockFileSystem after each testcases have run
     * 
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        // Declares and initializes a Feild variable
        // to the fileSys variable in MockFileSystem
        Field feild = fs.getClass().getDeclaredField("filesys");
        // Allows the value of this variable in MockFileSystem to be accessed
        feild.setAccessible(true);
        // Changes the value of the variable in MockFileSystem to null
        feild.set(null, null);
    }

    /**
     * Test A : User calls pop once
     */
    @Test
    public void testAAbsolutePath() {
        //Expected return from Pop
        expected = null;
        //Actual return from Pop
        actual = pop.run(fs, "popd", false);
        //Check the return from Pop and the current working directory
        assertTrue(actual == expected && fs.getCurrentPath().equals("/documents/journal"));
    }
    /**
     * Test B : User calls pop twice
     */
    @Test
    public void testBRelativePath() {
        //Expected return from Pop
        expected = null;
        //Actual return from Pop
        actual = pop.run(fs, "popd", false);
        actual = pop.run(fs, "popd", false);
        //Check the return from Pop and the current working directory
        assertTrue(actual == expected && fs.getCurrentPath().equals("/users/skeshavaa"));
    }

    /**
     * Test C : User calls pop three times
     */
    @Test
    public void testCLastPop() {
        //Expected return from Pop
        expected = null;
        //Actual return from Pop
        actual = pop.run(fs, "popd", false);
        actual = pop.run(fs, "popd", false);
        actual = pop.run(fs, "popd", false);
        //Check the return from Pop and the current working directory
        assertTrue(actual == expected && fs.getCurrentPath().equals("/"));
    }

    /**
     * Test D : User calls pop when the stack is empty
     */
    @Test
    public void testDEmptyStack() {
        //Expected return from Pop
        expected = "Error: Stack is empty";
        //Actual return from Pop
        actual = pop.run(fs, "popd", false);
        actual = pop.run(fs, "popd", false);
        actual = pop.run(fs, "popd", false);
        actual = pop.run(fs, "popd", false);
        //Check the return from Pop and the current working directory
        assertTrue(actual.equals(expected) && fs.getCurrentPath().equals("/")); 
    }

    /**
     * Test E : User provides arguments
     */
    @Test
    public void testEArguments() {
        //Expected return from Pop
        expected = "Error: Invalid Argument : No arguments should be given";
        //Actual return from Pop
        actual = pop.run(fs, "popd unrequired args", false);
        System.out.println(actual + "|" + fs.getCurrentPath());
        //Check the return from Pop and the current working directory
        assertTrue(actual.equals(expected) && fs.getCurrentPath().equals("/desktop"));  
    }

    /**
     * Test F : User tries to use redirection on a non redirectionable command
     */
    @Test
    public void testFRedirectionError() {
        //Expected return from Pop
        expected = "Error : Redirection Error : popd does not support redirection";
        //Actual return from Pop
        actual  = pop.run(fs, "popd > test", false);
        //Check the return from Pop and the current working directory
        assertTrue(actual.equals(expected) && fs.getCurrentPath().equals("/desktop"));  
    }
}
