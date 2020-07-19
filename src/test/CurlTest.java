 package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import commands.Curl;
import commands.FileManager;
import data.FileSystem;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CurlTest {
  
  private FileSystem fs;
  private Curl curl;
  private FileManager file;
  
  private String website;
  private String expected;
  private String actual;

  @Before
  public void setUp() throws Exception {
    fs = FileSystem.getFileSys();
    curl = new Curl();
    file = new FileManager();
    
    this.website = "";
    this.expected = "";
    this.actual = "";
  }

  @Test
  public void testANoArg() {
    String[] testCase1Curl = {};
    expected = "Error : No parameters provided : []";
    actual = curl.run(testCase1Curl, website, false);
    assertEquals(expected,actual);
  }
  
  @Test
  public void testBMalformedArg() {
    website = "raw.githubusercontent.com/jjangsta/GamseeAI/master/README.md";
    expected = "Parameter given is invalid " + website;
    actual = curl.run(website.split(" "), website, false);
    assertEquals(expected,actual);
  }
  
  @Test
  public void testCFileNotFoundArg() {
    website = "https://raw.githubusercontent.com/"
        + "jjangsta/GamseeAI/master/LOL.md";
    expected = "URL provided does not contain a file " + website;
    actual = curl.run(website.split(" "), website, false);
    assertEquals(expected,actual);
  }
  
  @Test
  public void testDNoFileNameArg() {
    website = "http://www.cs.utoronto.ca/~trebla/CSCB09-2020-Summer/";
    expected = "URL provided does not contain a file " + website;
    actual = curl.run(website.split(" "), website, false);
    assertEquals(expected,actual);
  }
  
  @Test
  public void testEFailConnectArg() {
    website = "https://markus.utsc.utoronto.ca/svn/cscb07s20/"
        + "group_0096/Assignment2/sprints/readMeSprints.txt";
    expected = "Connection could not be made to " + website;
    actual = curl.run(website.split(" "), website, false);
    assertEquals(expected,actual);
  }
  
  @Test
  public void testFReadHTML() {
    website = "https://raw.githubusercontent.com/ShawnGeorge03/Learn-HTML-CSS/master/Lesson%201/lesson1.html";
    expected = " <h1>Hello World</h1>\n";
        curl.run(website.split(" "), website, false);
    assertEquals(expected, file.findFileGivenRelative("lesson1").getContent());
  }
  
  @Test
  public void testGReadTXT() {
    website = "https://raw.githubusercontent.com/ShawnGeorge03/Learn-HTML-CSS"
        + "/master/Lesson%201/Lesson1.txt";
    expected = " Write your name on the Browser \n";
    curl.run(website.split(" "), website, false);
    assertEquals(expected, file.findFileGivenRelative("Lesson1").getContent());
  }
  
  @Test
  public void testHMultipleArgs() {
    website = "https://raw.githubusercontent.com/ShawnGeorge03/Learn-HTML-CSS/master/README.md"
        + " https://raw.githubusercontent.com/ShawnGeorge03/Learn-HTML-CSS/master/Lesson%201/Lesson1.txt";
    expected = "Error : Multiple Parameters have been provided : "
        + "[https://raw.githubusercontent.com/ShawnGeorge03/Learn-HTML-CSS/master/README.md, "
        + "https://raw.githubusercontent.com/ShawnGeorge03/Learn-HTML-CSS/master/Lesson%201/Lesson1.txt]";
    actual = curl.run(website.trim().split(" "), website, false);
    assertEquals(expected,actual);
  }
  
}
