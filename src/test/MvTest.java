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

    @Before
    public void setup(){
        mv = new Mv();
        fs = MockFileSystem.getMockFileSys("MOCKENV");
    }

    @Test
    public void TestAMoveRelativePath(){
        String[] input = {"users", "documents"};
        mv.run(fs, input, "mv users documents", false);
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
        String[] input = {"/users", "/downloads"};
        mv.run(fs, input, "mv /users /downloads", false);
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
        String[] input = {"/users", "clearlyfake"};
        String actual = mv.run(fs, input, "mv /users clearlyfake", false);
        String expected = "Error: Invalid Directory : clearlyfake does not exist!";
        assertEquals(expected, actual);
    }

    @Test
    public void TestDMoveInvalidDirtoPath(){
        String[] input = {"fake", "/users"};
        String actual = mv.run(fs, input, "mv fake /users", false);
        String expected = "Error: Directory Not Found : fake does not exist in the path you specified!";
        assertEquals(expected, actual);
    }

    @Test
    public void TestEMoveInvalidPathDirToPath(){
        String[] input = {"fake/user", "documents"};
        String actual = mv.run(fs, input, "mv fake/user documents", false);
        String expected = "Error: Invalid Directory : fake does not exist!";
        assertEquals(expected, actual);
    }

    @Test
    public void TestFMoveRoot(){
        String[] input = {"/", "documents"};
        String actual = mv.run(fs, input, "mv / documents", false);
        String expected = "Error: Invalid Directory : Cannot move the root directory";
        assertEquals(expected, actual);
    }

}