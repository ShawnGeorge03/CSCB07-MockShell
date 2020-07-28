package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import commands.Mkdir;
import commands.Pop;
import commands.Push;
import data.FileSystem;

import java.lang.reflect.Field;


public class PopTest {
    
    private static FileSystem fs;

    private static Mkdir mkdir;

    private static Push push;

    private static Pop pop;


    private static String[] input = {};

    @Before
    public void setUp() throws Exception {

        fs = FileSystem.getFileSys();
        mkdir = new Mkdir();
        push = new Push();
        pop = new Pop();
        
        // Sets up the C Folder
        mkdir.MakeDirectory("users".split(" "), fs);
        mkdir.MakeDirectory("pics".split(" "), fs);
        mkdir.MakeDirectory("Sys".split(" "), fs);

        // Sets up the users Folder
        mkdir.MakeDirectory("/users/desktop".split(" "), fs);

        // Sets up the Sys Folder
        mkdir.MakeDirectory("Sys/IO".split(" "), fs);
        mkdir.MakeDirectory("Sys/LOL".split(" "), fs);

        // Sets up the IO Folder
        mkdir.MakeDirectory("/Sys/IO/keyboard".split(" "), fs);
        mkdir.MakeDirectory("/Sys/IO/Mouse".split(" "), fs);

        String[] path = {"users/desktop"};
        push.run(fs, path, "pushd users/desktop", false);

        path[0] = "/Sys/IO/keyboard";
        push.run(fs, path, "pushd /Sys/IO/keyboard", false);
    }

     /**
     * Destroys the FileSystem after all the testcases have run
     * 
     * @throws Exception
    */
    @After
    public void tearDown() throws Exception {
        //Declares and initializes a Feild variable 
        //to the fileSys variable in FileSystem
        Field feild = fs.getClass().getDeclaredField("fileSys");
        //Allows the value of this variable in FileSystem to be accessed
        feild.setAccessible(true);
        //Changes the value of the variable in FileSystem to null
        feild.set(null, null);
    }

    @Test
    public void testAAbsolutePath(){
        pop.run(fs, input, "popd", false);
        String expected = "/Sys/IO/keyboard";
        String actual = fs.getCurrentPath().substring(1);
        assertEquals(expected, actual);
    }

    @Test
    public void testBRelativePath(){
        pop.run(fs, input, "popd", false);
        pop.run(fs, input, "popd", false);
        String expected = "/users/desktop";
        String actual = fs.getCurrentPath().substring(1);
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
