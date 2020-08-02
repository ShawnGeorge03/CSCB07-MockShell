package test;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import commands.Tree;

public class TreeTest {
	/**
	 * Declare instance of MockFileSystem so we can access the preset filesystem
	 * (empty and full)
	 */
	private MockFileSystem emptyFs;
	private MockFileSystem fullFs;

	/**
	 * Initialize instance of Tree to test it
	 */
	private Tree testTree;

	/**
	 * setup for TreeTest to initialize required instance variables
	 * 
	 * @throws Exception if there are any exceptions to be sent
	 */
	@Before
	public void setup() throws Exception {
		emptyFs = MockFileSystem.getMockFileSys("EMPTYSYS");
		fullFs = MockFileSystem.getMockFileSys("MOCKENV");
		testTree = new Tree();
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
		Field feildEmpty = emptyFs.getClass().getDeclaredField("filesys");
		Field feildFull = fullFs.getClass().getDeclaredField("filesys");
		// Allows the value of this variable in FileSystem to be accessed
		feildEmpty.setAccessible(true);
		feildFull.setAccessible(true);
		// Changes the value of the variable in FileSystem to null
		feildEmpty.set(null, null);
		feildFull.set(null, null);
	}

	/**
	 * Test 1: User provides no input in an empty filesystem
	 */
	@Test
	public void test1EmptyFsNoArgs() {
		String[] temp = {};
		assertEquals("/\n", testTree.run(emptyFs, temp, "tree", true));

	}

	/**
	 * Test 2: User provides input to an empty filesystem
	 */
	@Test
	public void test2EmptyFsWithArgs() {
		String[] temp = {};
		assertEquals("Error : Multiple Parameters have been provided", testTree.run(emptyFs, temp, "tree test", true));
	}
	
	/**
	 * Test 3: User provides no input in a full filesystem
	 */
	@Test
	public void test3FullFsNoArgs() {
		String[] temp = {};
		String expected = testTree.run(fullFs, temp, "tree", true);
		assertEquals(expected, testTree.run(fullFs, temp, "tree", true));
	}
	
	/**
	 * Test 4: User provides input to a full filesystem
	 */
	@Test
	public void test4FullFsWithArgs() {
		String[] temp = { "test" };
		assertEquals("Error : Multiple Parameters have been provided", testTree.run(fullFs, temp, "tree test", true));
	}
	
}
































