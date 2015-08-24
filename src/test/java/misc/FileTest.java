package misc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

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

		assertTrue(fileNameExt.toLowerCase().endsWith(fileExt));
	}

	public void testReadEscapeFromFile() throws IOException {
		File txtFile = new File("testFiles/testReadEscapeFromFile.txt");
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(txtFile)));

		System.out.println(bufferedReader.readLine());

		bufferedReader.close();

		/*no es posible leer la contrabarra desde un archivo properties.*/
		File propertiesFile = new File("testFiles/testReadEscapeFromFile.properties");

		Properties properties = new Properties();
		properties.load(new FileInputStream(propertiesFile));
		System.out.println(properties.get("archivoDbf"));

		bufferedReader.close();

	}
}
