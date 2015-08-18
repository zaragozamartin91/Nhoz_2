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

	public void testExtension() {
		String fileName = "formulaDemo";
		String fileExt = ".xlsx";
		String fileNameExt = fileName + fileExt;

		File file = new File("D:\\workspaces\\testWorkspace64\\Nhoz_2\\testFiles\\" + fileNameExt);
		assertEquals(fileNameExt, file.getName());
		
		assertTrue( fileNameExt.toLowerCase().endsWith(fileExt) );
	}
}
