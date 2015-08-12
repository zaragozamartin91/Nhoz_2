package misc;

import java.io.File;

import junit.framework.TestCase;

public class FileTest extends TestCase {
	public void testGetFileName() {
		String dirName = "/dir1/dir2";
		String fileName = "someFile";
		String pathname = dirName + "/" + fileName;
		File file = new File(pathname);

		assertTrue(file.getName().contentEquals(fileName));
	}
}
