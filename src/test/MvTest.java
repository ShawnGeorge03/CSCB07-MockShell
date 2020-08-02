package test;

import commands.Mv;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class MvTest {
    
    /**
    * Declare instance of FileSystem so we can access the filesystem
    */
    private static MockFileSystem fs;

    /**
    * Declare instance of Mkdir to make new directories
    */
    private static Mv mv;

    /**
     * Sets up the test environment and initializes the instance variables
     * 
     * @throws Exception
    */
    @Before
    public void setup(){
        //Initializes the class to be tested
        mv = new Mv();
        //Gets a specific preset FileSystem
        fs = MockFileSystem.getMockFileSys("MOCKENV");
    }

    @Test
    public void TestAMoveRelativePath(){
        mv.run(fs,"users documents".split(" "), "mv users documents", false);
        boolean expected = false;
        boolean actual = false;
        for (int i = 0; i < fs.getCurrent().getList().size(); i++){
            if (fs.getCurrent().getList().get(i).getName().equals("users")){
                actual = true;
                assertEquals(expected, actual);
            }else if (fs.getCurrent().getList().get(i).getName().equals("documents")){
                for (int j = 0; j < fs.getCurrent().getList().get(i).getList().size(); j++){
                    if (fs.getCurrent().getList().get(i).getList().get(j).getName().equals("users")){
                        actual = false;
                        assertEquals(expected, actual);
                    }
                }
            }
        }
    }

    @Test
    public void TestBMoveAbsolutePath(){
        mv.run(fs,"/users /documents".split(" "), "mv users documents", false);
        boolean expected = false;
        boolean actual = false;
        for (int i = 0; i < fs.getCurrent().getList().size(); i++){
            if (fs.getCurrent().getList().get(i).getName().equals("users")){
                actual = true;
                assertEquals(expected, actual);
            }else if (fs.getCurrent().getList().get(i).getName().equals("downloads")){
                for (int j = 0; j < fs.getCurrent().getList().get(i).getList().size(); j++){
                    if (fs.getCurrent().getList().get(i).getList().get(j).getName().equals("users")){
                        actual = false;
                        assertEquals(expected, actual);
                    }
                }
            }
        }
    }

    @Test
    public void TestCMoveDirToInvalidPath(){
        String actual = mv.run(fs,"/users clearlyfake".split(" "), "mv /users clearlyfake", false);
        String expected = null;
        assertEquals(expected, actual);
    }

    @Test
    public void TestDMoveInvalidDirtoPath(){
        String actual = mv.run(fs, "fake /users".split(" "), "mv fake /users", false);
        String expected = "Error: Directory Not Found : fake does not exist in the path you specified!";
        assertEquals(expected, actual);
    }

    @Test
    public void TestEMoveInvalidPathDirToPath(){
        String actual = mv.run(fs, "fake/user documents".split(" "),  "mv fake/user documents", false);
        String expected = "Error: Invalid Directory : fake does not exist!";
        assertEquals(expected, actual);
    }

    @Test
    public void TestFMoveRoot(){
        String actual = mv.run(fs, "/ documents".split(" "), "mv / documents", false);
        String expected = "Error: Invalid Directory : Cannot move the root directory";
        assertEquals(expected, actual);
    }

    @Test
    public void testGRedirectionError(){
        String actual = mv.run(fs, "/ documents > text".split(" "), "mv / documents > text", false);
        String expected = "Error : Redirection Error : mv does not support redirection";
        assertEquals(expected, actual); 
    }

}