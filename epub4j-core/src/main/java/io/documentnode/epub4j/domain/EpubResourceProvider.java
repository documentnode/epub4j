package io.documentnode.epub4j.domain;


import net.sf.jazzlib.ZipEntry;
import net.sf.jazzlib.ZipFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author jake
 */
public class EpubResourceProvider implements LazyResourceProvider {

  private final String epubFilename;

  /**
   * @param epubFilename the file name for the epub we're created from.
   */
  public EpubResourceProvider(String epubFilename) {
    this.epubFilename = epubFilename;
  }

  @Override
  public InputStream getResourceStream(String href) throws IOException {
    ZipFile zipFile = new ZipFile(epubFilename);
    ZipEntry zipEntry = zipFile.getEntry(href);
    if (zipEntry == null) {
      zipFile.close();
      throw new IllegalStateException(
          "Cannot find entry " + href + " in epub file " + epubFilename);
    }
    return new ResourceInputStream(zipFile.getInputStream(zipEntry), zipFile);
  }
}
