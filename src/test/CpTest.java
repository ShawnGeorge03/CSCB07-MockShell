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

    @Test
    public void TestACopyRelativePath(){
        cp.run(fs, "cp users documents", false);
        boolean check1 = false;
        boolean check2 = false;

        for (int i = 0; i < fs.getCurrent().getList().size(); i++){
            //System.out.println(fs.getCurrent().getList().get(i).getName());
            if (fs.getCurrent().getList().get(i).getName().equals("users")){
                check1 = true;
            }else if (fs.getCurrent().getList().get(i).getName().equals("documents")){
                for (int j = 0; j < fs.getCurrent().getList().get(i).getList().size(); j++){
                    //System.out.println(fs.getCurrent().getList().get(i).getList().get(j).getName());
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
        cp.run(fs, "cp /users /downloads", false);
        
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
        String actual = cp.run(fs, "cp /users clearlyfake", false);
        String expected = null;
        assertEquals(expected, actual);
    }

    @Test
    public void TestDCopyInvalidDirtoPath(){
        String actual = cp.run(fs, "cp fake /users", false);
        String expected = "Error: Directory Not Found : fake does not exist in the path you specified!";
        assertEquals(expected, actual);
    }

    @Test
    public void TestEMoveInvalidPathDirToPath(){
        String actual = cp.run(fs, "cp fake/user documents", false);
        String expected = "Error: Invalid Directory : fake does not exist!";
        assertEquals(expected, actual);
    }

    @Test
    public void TestFMoveRoot(){
        String actual = cp.run(fs, "cp / documents", false);
        String expected = "Error: Invalid Directory : Cannot copy the root directory";
        assertEquals(expected, actual);
    }

    @Test
    public void testGRedirectionError(){
        String actual = cp.run(fs, "cp /users /downloads > test", false);
        String expected = "Error : Redirection Error : cp does not support redirection";
        assertEquals(expected, actual);
    }
}