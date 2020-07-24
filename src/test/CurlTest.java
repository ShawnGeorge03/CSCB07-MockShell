
package test;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Test;

import commands.Curl;
import commands.FileManager;
import data.FileSystem;

import java.lang.reflect.Field;

public class CurlTest {

  private static FileSystem fs;
  private static Curl curl;
  private static FileManager file;

  private static String website;
  private static String expected;
  private static String actual;

  @BeforeClass
  public static void setUp() throws Exception {
    fs = FileSystem.getFileSys();
    curl = new Curl();
    file = new FileManager();

    website = "";
    expected = "";
    actual = "";
  }

  @AfterClass
  public static void tearDown() throws Exception {
    Field feild = fs.getClass().getDeclaredField("fileSys");
    feild.setAccessible(true);
    feild.set(null, null);
  }

  @Test
  public void testANoArg() {
    String[] testCase1Curl = {};
    expected = "Error : No parameters provided : []";
    actual = curl.run(fs,testCase1Curl, website, false);
    assertEquals(expected, actual);
  }

  @Test
  public void testBMalformedArg() {
    website = "raw.githubusercontent.com/jjangsta/GamseeAI/master/README.md";
    expected = "Parameter given is invalid " + website;
    actual = curl.run(fs,website.split(" "), website, false);
    assertEquals(expected, actual);
  }

  @Test
  public void testCFileNotFoundArg() {
    website = "https://raw.githubusercontent.com/" + "jjangsta/GamseeAI/master/LOL.md";
    expected = "URL provided does not contain a file " + website;
    actual = curl.run(fs,website.split(" "), website, false);
    assertEquals(expected, actual);
  }

  @Test
  public void testDNoFileNameArg() {
    website = "http://www.cs.utoronto.ca/~trebla/CSCB09-2020-Summer/";
    expected = "URL provided does not contain a file " + website;
    actual = curl.run(fs,website.split(" "), website, false);
    assertEquals(expected, actual);
  }

  @Test
  public void testEFailConnectArg() {
    website = "https://markus.utsc.utoronto.ca/svn/cscb07s20/" + "group_0096/Assignment2/sprints/readMeSprints.txt";
    expected = "Connection could not be made to " + website;
    actual = curl.run(fs,website.split(" "), website, false);
    assertEquals(expected, actual);
  }

  @Test
  public void testFReadHTML() {
    website = "https://raw.githubusercontent.com/ShawnGeorge03/Learn-HTML-CSS/master/Lesson%201/lesson1.html";
    expected = " <h1>Hello World</h1>\n";
    curl.run(fs,website.split(" "), website, false);
    assertEquals(expected, file.findFileGivenRelative("lesson1",fs).getContent());
  }

  @Test
  public void testGReadTXT() {
    website = "https://raw.githubusercontent.com/ShawnGeorge03/Learn-HTML-CSS" + "/master/Lesson%201/Lesson1.txt";
    expected = " Write your name on the Browser \n";
    curl.run(fs,website.split(" "), website, false);
    assertEquals(expected, file.findFileGivenRelative("Lesson1", fs).getContent());
  }

  @Test
  public void testHMultipleArgs() {
    website = "https://raw.githubusercontent.com/ShawnGeorge03/Learn-HTML-CSS/master/README.md"
        + " https://raw.githubusercontent.com/ShawnGeorge03/Learn-HTML-CSS/master/Lesson%201/Lesson1.txt";
    expected = "Error : Multiple Parameters have been provided : "
        + "[https://raw.githubusercontent.com/ShawnGeorge03/Learn-HTML-CSS/master/README.md, "
        + "https://raw.githubusercontent.com/ShawnGeorge03/Learn-HTML-CSS/master/Lesson%201/Lesson1.txt]";
    actual = curl.run(fs, website.trim().split(" "), website, false);
    assertEquals(expected, actual);
  }

}
