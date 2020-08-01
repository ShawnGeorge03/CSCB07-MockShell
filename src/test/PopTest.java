package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import commands.Pop;

import java.lang.reflect.Field;


public class PopTest {
    
    private static MockFileSystem fs;

    private static Pop pop;

    String actual, expected;

    @Before
    public void setUp() throws Exception {
        fs = MockFileSystem.getMockFileSys("MOCKENV");
        pop = new Pop();
        fs.getStack().add("/documents/journal");
        fs.getStack().add("/users/skeshavaa");
        fs.getStack().add("/");
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

    @Test
    public void testAAbsolutePath(){
        pop.run(fs, "popd", false);
        expected = "/documents/journal";
        actual = fs.getCurrentPath();
        assertEquals(expected, actual);
    }

    @Test
    public void testBRelativePath(){
        pop.run(fs,  "popd", false);
        pop.run(fs,  "popd", false);
        expected = "/users/skeshavaa";
        actual = fs.getCurrentPath();
        assertEquals(expected, actual);
    }

    @Test
    public void testCLastPop(){
        pop.run(fs,  "popd", false);
        pop.run(fs,  "popd", false);
        pop.run(fs,  "popd", false);

        expected = "/";;
        actual = fs.getCurrentPath();
        assertEquals(expected, actual);
    }

    @Test
    public void testDEmptyStack(){
        pop.run(fs,  "popd", false);
        pop.run(fs,  "popd", false);
        pop.run(fs,  "popd", false);
       
        expected = "Error: Stack is empty";;
        actual =  pop.run(fs,  "popd", false);
        assertEquals(expected, actual);
    }

    @Test 
    public void testEArguments(){
        actual = pop.run(fs, "popd unrequired args", false);
        expected = "Error: Invalid Argument : No arguments should be given";
        assertEquals(expected, actual);
    }

    @Test
    public void testFRedirectionError(){
        expected = "Error : Redirection Error : popd does not support redirection";
        actual = pop.run(fs, "popd > test", false);
        assertEquals(expected, actual);
    }

}
