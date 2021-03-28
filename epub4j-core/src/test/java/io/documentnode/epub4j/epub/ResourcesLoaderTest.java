package io.documentnode.epub4j.epub;

import io.documentnode.epub4j.domain.LazyResource;
import io.documentnode.epub4j.domain.Resource;
import io.documentnode.epub4j.domain.Resources;
import io.documentnode.epub4j.domain.MediaTypes;
import io.documentnode.epub4j.util.IOUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ResourcesLoaderTest {

//  private static final String encoding = "UTF-8";
//  private static String testBookFilename;
//
//  @BeforeClass
//  public static void setUpClass() throws IOException {
//    File testBook = File.createTempFile("testBook", ".epub");
//    OutputStream out = new FileOutputStream(testBook);
//    IOUtil.copy(
//        ResourcesLoaderTest.class.getResourceAsStream("/testbook1.epub"),
//        out);
//    out.close();
//
//    ResourcesLoaderTest.testBookFilename = testBook.getAbsolutePath();
//  }
//
//  @AfterClass
//  public static void tearDownClass() {
//    //noinspection ResultOfMethodCallIgnored
//    new File(testBookFilename).delete();
//  }
//
//  /**
//   * Loads the Resources from a ZipInputStream
//   */
//  @Test
//  public void testLoadResources_ZipInputStream() throws IOException {
//    // given
//    ZipInputStream zipInputStream = new ZipInputStream(
//        new FileInputStream(new File(testBookFilename)));
//
//    // when
//    Resources resources = ResourcesLoader
//        .loadResources(zipInputStream, encoding);
//
//    // then
//    verifyResources(resources);
//  }
//
//  /**
//   * Loads the Resources from a zero length file, using ZipInputStream<br/>
//   * See <a href="https://github.com/psiegman/epublib/issues/122">Issue #122 Infinite loop</a>.
//   */
//  @Test(expected = ZipException.class)
//  public void testLoadResources_ZipInputStream_WithZeroLengthFile()
//      throws IOException {
//    // given
//    ZipInputStream zipInputStream = new ZipInputStream(
//        this.getClass().getResourceAsStream("/zero_length_file.epub"));
//
//    // when
//    ResourcesLoader.loadResources(zipInputStream, encoding);
//  }
//
//  /**
//   * Loads the Resources from a file that is not a valid zip, using ZipInputStream<br/>
//   * See <a href="https://github.com/psiegman/epublib/issues/122">Issue #122 Infinite loop</a>.
//   */
//  @Test(expected = ZipException.class)
//  public void testLoadResources_ZipInputStream_WithInvalidFile()
//      throws IOException {
//    // given
//    ZipInputStream zipInputStream = new ZipInputStream(
//        this.getClass().getResourceAsStream("/not_a_zip.epub"));
//
//    // when
//    ResourcesLoader.loadResources(zipInputStream, encoding);
//  }
//
//  /**
//   * Loads the Resources from a ZipFile
//   */
//  @Test
//  public void testLoadResources_ZipFile() throws IOException {
//    // given
//    ZipFile zipFile = new ZipFile(testBookFilename);
//
//    // when
//    Resources resources = ResourcesLoader.loadResources(zipFile, encoding);
//
//    // then
//    verifyResources(resources);
//  }
//
//  /**
//   * Loads all Resources lazily from a ZipFile
//   */
//  @Test
//  public void testLoadResources_ZipFile_lazy_all() throws IOException {
//    // given
//    ZipFile zipFile = new ZipFile(testBookFilename);
//
//    // when
//    Resources resources = ResourcesLoader.loadResources(zipFile, encoding,
//        Arrays.asList(MediaTypes.mediaTypes));
//
//    // then
//    verifyResources(resources);
//    Assert.assertEquals(Resource.class,
//        resources.getById("container").getClass());
//    Assert.assertEquals(LazyResource.class,
//        resources.getById("book1").getClass());
//  }
//
//  /**
//   * Loads the Resources from a ZipFile, some of them lazily.
//   */
//  @Test
//  public void testLoadResources_ZipFile_partial_lazy() throws IOException {
//    // given
//    ZipFile zipFile = new ZipFile(testBookFilename);
//
//    // when
//    Resources resources = ResourcesLoader.loadResources(zipFile, encoding,
//        Collections.singletonList(MediaTypes.CSS));
//
//    // then
//    verifyResources(resources);
//    Assert.assertEquals(Resource.class,
//        resources.getById("container").getClass());
//    Assert.assertEquals(LazyResource.class,
//        resources.getById("book1").getClass());
//    Assert
//        .assertEquals(Resource.class, resources.getById("chapter1").getClass());
//  }
//
//  private void verifyResources(Resources resources) throws IOException {
//    Assert.assertNotNull(resources);
//    Assert.assertEquals(12, resources.getAll().size());
//    List<String> allHrefs = new ArrayList<>(resources.getAllHrefs());
//    Collections.sort(allHrefs);
//
//    Resource resource;
//    byte[] expectedData;
//
//    // container
//    resource = resources.getByHref(allHrefs.get(0));
//    Assert.assertEquals("container", resource.getId());
//    Assert.assertEquals("META-INF/container.xml", resource.getHref());
//    Assert.assertNull(resource.getMediaType());
//    Assert.assertEquals(230, resource.getData().length);
//
//    // book1.css
//    resource = resources.getByHref(allHrefs.get(1));
//    Assert.assertEquals("book1", resource.getId());
//    Assert.assertEquals("OEBPS/book1.css", resource.getHref());
//    Assert.assertEquals(MediaTypes.CSS, resource.getMediaType());
//    Assert.assertEquals(74, resource.getData().length);
//    expectedData = IOUtil
//        .toByteArray(this.getClass().getResourceAsStream("/book1/book1.css"));
//    Assert.assertArrayEquals(expectedData, resource.getData());
//
//    // chapter1
//    resource = resources.getByHref(allHrefs.get(2));
//    Assert.assertEquals("chapter1", resource.getId());
//    Assert.assertEquals("OEBPS/chapter1.html", resource.getHref());
//    Assert.assertEquals(MediaTypes.XHTML, resource.getMediaType());
//    Assert.assertEquals(5100, resource.getData().length);
//    expectedData = IOUtil.toByteArray(
//        this.getClass().getResourceAsStream("/book1/chapter1.html"));
//    Assert.assertArrayEquals(expectedData, resource.getData());
//  }
}
