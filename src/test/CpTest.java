package test;

import commands.Mv;
import commands.Cd;
import commands.Cp;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import data.FileSystemI;
import data.Node;

public class CpTest {
    
    /**
    * Declare instance of FileSystem so we can access the filesystem
    */
    private static MockFileSystem fs;
    /**
    * Declare instance of Mkdir to make new directories
    */
    private static Cp cp;

    private static Cd cd;

    @Before
    public void setup(){
        cp = new Cp();
        cd = new Cd();
        fs = MockFileSystem.getMockFileSys("MOCKENV");
    }

    @Test
    public void TestACopyRelativePath(){
        String[] input = {"users", "documents"};
        cp.run(fs, input, "mv users documents", false);

        boolean check1 = false;
        boolean check2 = false;

        for (int i = 0; i < fs.getCurrent().getList().size(); i++){
            if (fs.getCurrent().getList().get(i).getName().equals("users")){
                check1 = true;
            }else if (fs.getCurrent().getList().get(i).getName().equals("documents")){
                for (int j = 0; j < fs.getCurrent().getList().get(i).getList().size(); j++){
                    if (fs.getCurrent().getList().get(i).getList().get(j).getName().equals("users")){
                        check2 = true;
                    }
                }
            }
        }

        assertEquals(check1 == check2, check1 == true);
    }

    @Test
    public void TestBCopyAbsolutePath(){
        String[] input = {"/users", "/downloads"};
        cp.run(fs, input, "mv /users /downloads", false);
        
        boolean check1 = false;
        boolean check2 = false;

        for (int i = 0; i < fs.getCurrent().getList().size(); i++){
            if (fs.getCurrent().getList().get(i).getName().equals("users")){
                check1 = true;
            }else if (fs.getCurrent().getList().get(i).getName().equals("downloads")){
                for (int j = 0; j < fs.getCurrent().getList().get(i).getList().size(); j++){
                    if (fs.getCurrent().getList().get(i).getList().get(j).getName().equals("users")){
                        check2 = true;
                    }
                }
            }
        }

        assertEquals(check1 == check2, check1 == true);
    }

    @Test
    public void TestCCopyDirToInvalidPath(){
        String[] input = {"/users", "clearlyfake"};
        String actual = cp.run(fs, input, "mv /users clearlyfake", false);
        String expected = "Error: Invalid Directory : clearlyfake does not exist!";
        assertEquals(expected, actual);
    }

    @Test
    public void TestDCopyInvalidDirtoPath(){
        String[] input = {"fake", "/users"};
        String actual = cp.run(fs, input, "mv fake /users", false);
        String expected = "Error: Directory Not Found : fake does not exist in the path you specified!";
        assertEquals(expected, actual);
    }

    @Test
    public void TestEMoveInvalidPathDirToPath(){
        String[] input = {"fake/user", "documents"};
        String actual = cp.run(fs, input, "mv fake/user documents", false);
        String expected = "Error: Invalid Directory : fake does not exist!";
        assertEquals(expected, actual);
    }

    @Test
    public void TestFMoveRoot(){
        String[] input = {"/", "documents"};
        String actual = cp.run(fs, input, "mv / documents", false);
        String expected = "Error: Invalid Directory : Cannot move the root directory";
        assertEquals(expected, actual);
    }

}