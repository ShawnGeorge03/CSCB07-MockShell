
package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import commands.Curl;

import java.lang.reflect.Field;

/**
 * Class CurlTest runs all the different test cases for Curl
 */
public class CurlTest {

  /**
   * Declare instance of FileSystem so we can access the filesystem
   */
  private static MockFileSystem fs;
  
  /**
   * Declare instance of Curl so it can be tested
   */
  private static Curl curl;

  /**
   * Declare three different instance of a String objects called website,
   * expected, and actual
   */
  private static String website, expected, actual;

  /**
   * Sets up the test environment and initializes the instance variables
   * 
   * @throws Exception
   */
  @Before
  public void setUp() throws Exception {
    // Get the current FileSystem
    fs = MockFileSystem.getMockFileSys("MOCKENV");
    // Initializes a Curl Object
    curl = new Curl();
  }

  /**
   * Destroys the FileSystem after all the testcases have run
   * 
   * @throws Exception
   */
  @After
  public void tearDown() throws Exception {
    // Declares and initializes a Feild variable
    // to the fileSys variable in FileSystem
    Field feild = fs.getClass().getDeclaredField("filesys");
    // Allows the value of this variable in FileSystem to be accessed
    feild.setAccessible(true);
    // Changes the value of the variable in FileSystem to null
    feild.set(null, null);
  }

  /**
   * Test A : User provides no input
  */
  @Test
  public void testANoArg() {
    //Declares and initializes an empty array
    String[] testCase1Curl = {};
    //Expected return from Curl
    expected = "Error : No parameters provided : []";
    //Actual return from Curl after the operation is complete
    actual = curl.run(fs, testCase1Curl, "curl "+ website, false);
    //Checks if the values are equal or not
    assertEquals(expected, actual);
  }

  /**
   * Test B : Malformed URL
   */
  @Test
  public void testBMalformedArg() {
    //User provides a malformed URL
    website = "raw.githubusercontent.com/jjangsta/GamseeAI/master/README.md";
    //Expected return from Curl
    expected = "Parameter given is invalid " + website;
    //Actual return from Curl after the operation is complete
    actual = curl.run(fs, website.split(" "), "curl "+ website, false);
    //Checks if the values are equal or not
    assertEquals(expected, actual);
  }

  /**
   * Test C : File not Found at given URL
   */

  @Test
  public void testCFileNotFoundArg() {
    //User provides a valid URL but that file does not exist there
    website = "https://raw.githubusercontent.com/" + 
    "jjangsta/GamseeAI/master/LOL.md";
    //Expected return from Curl
    expected = "URL provided does not contain a file " + website;
    //Actual return from Curl after the operation is complete
    actual = curl.run(fs, website.split(" "), "curl "+ website, false);
    //Checks if the values are equal or not
    assertEquals(expected, actual);
  }

  /**
   * Test D : URL with not file agrumentation at the end
   */

  @Test
  public void testDNoFileNameArg() {
    //User provides a URL with no file name 
    website = "http://www.cs.utoronto.ca/~trebla/CSCB09-2020-Summer/";
    //Expected return from Curl
    expected = "URL provided does not contain a file " + website;
    //Actual return from Curl after the operation is complete
    actual = curl.run(fs, website.split(" "), "curl "+ website, false);
    //Checks if the values are equal or not
    assertEquals(expected, actual);
  }

  /**
   * Test E : Unable to Connect to URL
   */
  @Test
  public void testEFailConnectArg() {
    //User provides a valid URL but Curl does not have acess to
    website = "https://markus.utsc.utoronto.ca/svn/cscb07s20/" + 
    "group_0096/Assignment2/sprints/readMeSprints.txt";
    //Expected return from Curl
    expected = "Connection could not be made to " + website;
    //Actual return from Curl after the operation is complete
    actual = curl.run(fs, website.split(" "), "curl "+ website, false);
    //Checks if the values are equal or not
    assertEquals(expected, actual);
  }

  /**
   * Test F : Read a .html file
   */
  @Test
  public void testFReadHTML() {
    //User provides a valid URL with a html file to be read 
    website = "https://raw.githubusercontent.com/ShawnGeorge03/"+
    "Learn-HTML-CSS/master/Lesson%201/lesson1.html";
    //Expected return from Curl
    expected = "<h1>Hello World</h1>\n";
    //Actual return from Curl after the operation is complete
    curl.run(fs, website.split(" "), "curl "+ website, false);
    //Checks if the values are equal or not
    assertEquals(expected, fs.findFile("lesson1", false).getContent());
  }

  /**
   * Test G : Read a .txt file
   */
  @Test
  public void testGReadTXT() {
    //User provides a valid URL with a text file to be read 
    website = "https://raw.githubusercontent.com/ShawnGeorge03/Learn-HTML-CSS" + 
    "/master/Lesson%201/Lesson1.txt";
    expected = "Write your name on the Browser \n";
    //Actual return from Curl after the operation is complete
    curl.run(fs, website.split(" "), "curl "+ website, false);
    //Checks if the values are equal or not
    assertEquals(expected, fs.findFile("Lesson1", false).getContent());
  }

  /**
   * Test H : Multiple URLs
   */
  @Test
  public void testHMultipleArgs() {
    //User provided multiple URLs instead of only one
    website = "https://raw.githubusercontent.com/ShawnGeorge03/Learn-HTML-CSS/master/README.md"
        + " https://raw.githubusercontent.com/ShawnGeorge03/Learn-HTML-CSS/master/Lesson%201/Lesson1.txt";
    //Expected return from Curl
    expected = "Error : Multiple Parameters have been provided : "
        + "[https://raw.githubusercontent.com/ShawnGeorge03/Learn-HTML-CSS/master/README.md, "
        + "https://raw.githubusercontent.com/ShawnGeorge03/Learn-HTML-CSS/master/Lesson%201/Lesson1.txt]";
    //Actual return from Curl after the operation is complete
    actual = curl.run(fs, website.trim().split(" "), "curl "+ website, false);
    //Checks if the values are equal or not
    assertEquals(expected, actual);
  }

  @Test
  public void testIRedirectionError(){
    website = "https://raw.githubusercontent.com/ShawnGeorge03/Learn-HTML-CSS/master/README.md";
    expected = "Error : Redirection Error : curl does not support redirection";
    actual = curl.run(fs, website.trim().split(" "), "curl "+ website + " > text", false);
    assertEquals(expected, actual);
  }

}
