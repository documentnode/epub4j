package io.documentnode.epub4j.hhc;

import io.documentnode.epub4j.Constants;
import java.util.Iterator;

import junit.framework.TestCase;
import io.documentnode.epub4j.chm.ChmParser;
import io.documentnode.epub4j.domain.Book;

import org.apache.commons.io.IOUtils;
import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemManager;
import org.apache.commons.vfs.NameScope;
import org.apache.commons.vfs.VFS;

public class ChmParserTest extends TestCase {
	
	public void test1() {
		try {
			FileSystemManager fsManager = VFS.getManager();
			FileObject dir = fsManager.resolveFile("ram://chm_test_dir");
			dir.createFolder();
			String chm1Dir = "/chm1";
			Iterator<String> lineIter = IOUtils.lineIterator(ChmParserTest.class.getResourceAsStream(chm1Dir + "/filelist.txt"), Constants.CHARACTER_ENCODING);
			while(lineIter.hasNext()) {
				String line = lineIter.next();
				FileObject file = dir.resolveFile(line, NameScope.DESCENDENT);
				file.createFile();
				IOUtils.copy(this.getClass().getResourceAsStream(chm1Dir + "/" + line), file.getContent().getOutputStream());
				file.getContent().close();
			}
			
			Book chmBook = ChmParser.parseChm(dir, Constants.CHARACTER_ENCODING);
			assertEquals(45, chmBook.getResources().size());
			assertEquals(18, chmBook.getSpine().size());
			assertEquals(19, chmBook.getTableOfContents().size());
			assertEquals("chm-example", chmBook.getMetadata().getTitles().get(0));
		} catch(Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}


}