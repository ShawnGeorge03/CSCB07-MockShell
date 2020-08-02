package test;

import java.lang.reflect.Field;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import commands.Find;

public class FindTest {

	private MockFileSystem fs;

	private Find findTest;

	/**
	 * setup for findTest to initialize required instance variables
	 * 
	 * @throws Exception if there are any exceptions to be sent
	 */
	@Before
	public void setup() throws Exception {
		fs = MockFileSystem.getMockFileSys("MOCKENV");
		findTest = new Find();
	}

	/**
	 * Destroys mock filesystem after testcases are run
	 * 
	 * @throws Exception if there are any exceptions to be sent
	 */
	@After
	public void teardown() throws Exception {
		// Declares and initializes a Field variable
		// to the fileSys variable in FileSystem
		Field feild = fs.getClass().getDeclaredField("filesys");
		// Allows the value of this variable in FileSystem to be accessed
		feild.setAccessible(true);
		// Changes the value of the variable in FileSystem to null
		feild.set(null, null);
	}

	/**
	 * Test 1: Finding single dir in filesystem
	 */
	@Test
	public void test1SingleDir() {
		String[] args = { "/", "-type", "d", "-name", "\"homework\"" };
		assertEquals("/downloads\n", findTest.run(fs, args, "find / -type d -name \"homework\"", true));
	}

	/**
	 * Test 2: Finding single file in filesystem
	 */
	@Test
	public void test2SingleFileEntireFs() {
		String[] args = { "/", "-type", "f", "-name", "\"txtone\"" };
		assertEquals("/documents\n", findTest.run(fs, args, "find / -type f -name \"txtone\"", true));
	}

	/**
	 * Test 3: Finding dir from multiple paths in filesystem
	 */
	@Test
	public void test3MultipleDirs() {
		String[] args = { "/", "/downloads", "-type", "d", "-name", "\"homework\"" };
		assertEquals("/downloads\n", findTest.run(fs, args, "find / /downloads -type d -name \"homework\"", true));
	}

	/**
	 * Test 4: Finding dir that doesnt exist
	 */
	@Test
	public void test4NoDir() {
		String[] args = { "/", "-type", "d", "-name", "\"test123\"" };
		assertEquals("", findTest.run(fs, args, "find / -type d -name \"test123\"", true));
	}

	/**
	 * Test 5: Finding file that doesn't exist
	 */
	@Test
	public void test5NoFile() {
		String[] args = { "/", "-type", "f", "-name", "\"test123\"" };
		assertEquals("", findTest.run(fs, args, "find / -type f -name \"test123\"", true));
	}

	/**
	 * Test 6: Finding single dir using multiple paths
	 */
	@Test
	public void test6MultiplePathsForDir() {
		String[] args = { "/", "/downloads", "-type", "d", "-name", "\"homework\"" };
		assertEquals("/downloads\n", findTest.run(fs, args, "find / /downloads -type d -name \"homework\"", true));
	}

	/**
	 * Test 7: Finding single file using multiple paths
	 */
	@Test
	public void test7MultiplePathsForFile() {
		String[] args = { "/", "/documents", "-type", "f", "-name", "\"txtone\"" };
		assertEquals("/documents\n", findTest.run(fs, args, "find / /documents -type f -name \"txtone\"", true));
	}

}