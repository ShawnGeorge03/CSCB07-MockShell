package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import commands.Save;

import java.lang.reflect.Field;

public class SaveTest {

    private MockFileSystem fs;
    private Save save;

    String expected, actual;

    @Before
    public void setup(){
        fs = MockFileSystem.getMockFileSys("MOCKENV");
        save = new Save();
    }

    @After
    public void tearDown() throws Exception {
        //Declares and initializes a Feild variable 
        //to the fileSys variable in FileSystem
        Field feild = fs.getClass().getDeclaredField("filesys");
        //Allows the value of this variable in FileSystem to be accessed
        feild.setAccessible(true);
        //Changes the value of the variable in FileSystem to null
        feild.set(null, null);
    }

    /**
     * Test A : 
     */
    @Test
    public void testANoArgs(){

    }
    
}