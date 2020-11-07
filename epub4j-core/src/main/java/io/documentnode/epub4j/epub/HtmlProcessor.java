package io.documentnode.epub4j.epub;

import io.documentnode.epub4j.domain.Resource;
import java.io.OutputStream;

public interface HtmlProcessor {

  void processHtmlResource(Resource resource, OutputStream out);
}
