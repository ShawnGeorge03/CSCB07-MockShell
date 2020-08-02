package test;

import commands.Cp;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;


public class CpTest {
    
    /**
    * Declare instance of FileSystem so we can access the filesystem
    */
    private static MockFileSystem fs;
    /**
    * Declare instance of Mkdir to make new directories
    */
    private static Cp cp;


    @Before
    public void setup(){
        cp = new Cp();
        fs = MockFileSystem.getMockFileSys("MOCKENV");
    }

    /**
     * Test A : Copy a relative path to another relative path
     */
    @Test
    public void TestACopyRelativePath(){
        String[] input = {"users", "documents"};
        cp.run(fs, input, "cp users documents", false);
        boolean check1 = false;
        boolean check2 = false;

        for (int i = 0; i < fs.getCurrent().getList().size(); i++){
            System.out.println(fs.getCurrent().getList().get(i).getName());
            if (fs.getCurrent().getList().get(i).getName().equals("users")){
                check1 = true;
            }else if (fs.getCurrent().getList().get(i).getName().equals("documents")){
                for (int j = 0; j < fs.getCurrent().getList().get(i).getList().size(); j++){
                    System.out.println(fs.getCurrent().getList().get(i).getList().get(j).getName());
                    if (fs.getCurrent().getList().get(i).getList().get(j).getName().equals("users")){
                        check2 = true;
                    }
                }
            }
        }

        assertEquals(check1 == check2, check1 == true);
    }

    /**
     * Test B : Copy an absolute path to an absolute path
     */
    @Test
    public void TestBCopyAbsolutePath(){
        String[] input = {"/users", "/downloads"};
        cp.run(fs, input, "cp /users /downloads", false);
        
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

    /**
     * Test C : Copy a valid path to a path that doesn't exist, (cp must create the directory)
     */
    @Test
    public void TestCCopyDirToInvalidPath(){
        String[] input = {"/users", "clearlyfake"};
        String actual = cp.run(fs, input, "cp /users clearlyfake", false);
        String expected = null;
        assertEquals(expected, actual);
    }

    /**
     * Test D: Copy an invalid path
     */
    @Test
    public void TestDCopyInvalidDirtoPath(){
        String[] input = {"fake", "/users"};
        String actual = cp.run(fs, input, "cp fake /users", false);
        String expected = "Error: Directory Not Found : fake does not exist in the path you specified!";
        assertEquals(expected, actual);
    }

    /**
     * Copy an invalid path (absolute)
     */
    @Test
    public void TestEMoveInvalidPathDirToPath(){
        String[] input = {"fake/user", "documents"};
        String actual = cp.run(fs, input, "cp fake/user documents", false);
        String expected = "Error: Invalid Directory : fake does not exist!";
        assertEquals(expected, actual);
    }

    /**
     * Test F : Copy the root directory
     */
    @Test
    public void TestFCopyRoot(){
        String[] input = {"/", "documents"};
        String actual = cp.run(fs, input, "cp / documents", false);
        String expected = "Error: Invalid Directory : Cannot copy the root directory";
        assertEquals(expected, actual);
    }

    /**
     * Test G : Test redirection manager on cp
     */
    @Test
    public void testGRedirectionError(){
        String[] input = {"/users", "/downloads", ">", "test"};
        String actual = cp.run(fs, input, "cp /users /downloads > test", false);
        String expected = "Error : Redirection Error : cp does not support redirection";
        assertEquals(expected, actual);
    }
}