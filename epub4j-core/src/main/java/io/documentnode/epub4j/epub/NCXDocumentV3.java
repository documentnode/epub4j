package io.documentnode.epub4j;

//import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
//import org.xmlpull.v1.XmlSerializer;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
//import java.util.zip.ZipEntry;
//import java.util.zip.ZipOutputStream;

import io.documentnode.epub4j;
//import me.ag2s.epublib.domain.Author;
importio.documentnode.epub4j.domain.Book;
//import me.ag2s.epublib.domain.Identifier;
//import me.ag2s.epublib.domain.MediaTypes;
import io.documentnode.epub4j.domain.Resource;
import io.documentnode.epub4j.domain.TOCReference;
import io.documentnode.epub4j.domain.TableOfContents;
import io.documentnode.epub4j.util.ResourceUtil;
import io.documentnode.epub4j.util.StringUtil;

public class NCXDocumentV3 extends NCXDocument {
    public static final String NAMESPACE_NCX = "";
    public static final String PREFIX_NCX = "html";
    public static final String NCX_ITEM_ID = "ncx";
    public static final String DEFAULT_NCX_HREF = "toc.xhtml";
    public static final String PREFIX_DTB = "dtb";
    private static String TAG = NCXDocumentV3.class.getName();

    private interface NCXTags {
        //String nav="nav";
        //String li="li";
        //String ncx = "ncx";
        String meta = "meta";
        String navPoint = "li";
        String navMap = "nav";
        String navLabel = "a";
        String content = "a";
        String text = "text";
        String docTitle = "docTitle";
        String docAuthor = "docAuthor";
        String head = "head";
    }

    private interface NCXAttributes {

        String src = "href";
        String name = "name";
        String content = "content";
        String id = "id";
        String playOrder = "playOrder";
        String clazz = "class";
        String version = "version";
    }

    private interface NCXAttributeValues {

        String chapter = "chapter";
        String version = "2007";

    }

    /**
     * 解析epub的目录文件
     * @param book Book
     * @param epubReader epubreader
     * @return
     */

    public static Resource read(Book book, EpubReader epubReader) {
        Resource ncxResource = null;
        if (book.getSpine().getTocResource() == null) {
            Log.e(TAG, "Book does not contain a table of contents file");
            return ncxResource;
        }
        try {
            ncxResource = book.getSpine().getTocResource();
            if (ncxResource == null) {
                return ncxResource;
            }
            //Log.d(TAG, ncxResource.getHref());

            Document ncxDocument = ResourceUtil.getAsDocument(ncxResource);
            //Log.d(TAG, ncxDocument.getNodeName());

            Element navMapElement = (Element) ncxDocument.getElementsByTagName("nav").item(0);
            navMapElement = (Element) navMapElement.getElementsByTagName("ol").item(0);
            Log.d(TAG, navMapElement.getTagName());

            TableOfContents tableOfContents = new TableOfContents(
                    readTOCReferences(navMapElement.getChildNodes(), book));
            Log.d(TAG, tableOfContents.toString());
            book.setTableOfContents(tableOfContents);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
        return ncxResource;
    }

    private static List<TOCReference> doToc(Node n, Book book) {
        List<TOCReference> result = new ArrayList<>();

        if (n == null || n.getNodeType() != Document.ELEMENT_NODE) {
            return result;
        } else {
            Element el = (Element) n;
            NodeList nodeList = el.getElementsByTagName("li");
            for (int i = 0; i < nodeList.getLength(); i++) {
                result.add(readTOCReference((Element) nodeList.item(i), book));
            }
        }


        return result;
    }


    static List<TOCReference> readTOCReferences(NodeList navpoints,
                                                Book book) {
        if (navpoints == null) {
            return new ArrayList<>();
        }
        //Log.d(TAG, "readTOCReferences:navpoints.getLength()" + navpoints.getLength());
        List<TOCReference> result = new ArrayList<>();
        for (int i = 0; i < navpoints.getLength(); i++) {
            Node node = navpoints.item(i);
            //如果该node是null,或者不是Element,跳出本次循环
            if (node == null || node.getNodeType() != Document.ELEMENT_NODE) {
                continue;
            } else {
                Element el = (Element) node;
                //如果该Element的name为”li“,将其添加到目录结果
                if (el.getTagName().equals("li")) {
                    result.add(readTOCReference(el, book));
                }
                //NodeList nodeList=el.getElementsByTagName("li");
                //for (int i=0;i<nodeList.getLength();i++){
                //result.add(readTOCReference((Element) nodeList.item(i),book));
                // }
            }
            //result.addAll(doToc(node, book));
        }


        return result;
    }


    static TOCReference readTOCReference(Element navpointElement, Book book) {
        //章节的名称
        String label = readNavLabel(navpointElement);
        //Log.d(TAG, "label:" + label);
        String tocResourceRoot = StringUtil
                .substringBeforeLast(book.getSpine().getTocResource().getHref(), '/');
        if (tocResourceRoot.length() == book.getSpine().getTocResource().getHref()
                .length()) {
            tocResourceRoot = "";
        } else {
            tocResourceRoot = tocResourceRoot + "/";
        }

        String reference = StringUtil
                .collapsePathDots(tocResourceRoot + readNavReference(navpointElement));
        String href = StringUtil
                .substringBefore(reference, Constants.FRAGMENT_SEPARATOR_CHAR);
        String fragmentId = StringUtil
                .substringAfter(reference, Constants.FRAGMENT_SEPARATOR_CHAR);
        Resource resource = book.getResources().getByHref(href);
        if (resource == null) {
            Log.e(TAG, "Resource with href " + href + " in NCX document not found");
        }
        //父级目录
        TOCReference result = new TOCReference(label, resource, fragmentId);
        //解析子级目录
        List<TOCReference> childTOCReferences = doToc(navpointElement, book);
        //readTOCReferences(
        //navpointElement.getChildNodes(), book);
        result.setChildren(childTOCReferences);
        return result;
    }

    /**
     * 获取目录节点的href
     * @param navpointElement
     * @return
     */
    private static String readNavReference(Element navpointElement) {
        //Log.d(TAG, "readNavReference:" + navpointElement.getTagName());
        Element contentElement = DOMUtil
                .getFirstElementByTagNameNS(navpointElement, NAMESPACE_NCX,
                        NCXDocumentV3.NCXTags.content);
        String result = DOMUtil
                .getAttribute(contentElement, NAMESPACE_NCX, NCXDocumentV3.NCXAttributes.src);
        try {
            result = URLDecoder.decode(result, Constants.CHARACTER_ENCODING);
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, e.getMessage());
        }

        return result;

    }

    /**
     * 获取目录节点里面的章节名
     * @param navpointElement
     * @return
     */
    private static String readNavLabel(Element navpointElement) {
        //Log.d(TAG, "readNavLabel:" + navpointElement.getTagName());
        Element navLabel = DOMUtil
                .getFirstElementByTagNameNS(navpointElement, NAMESPACE_NCX,
                        NCXDocumentV3.NCXTags.navLabel);
        String label="";
        if(navLabel!=null){
            label=navLabel.getTextContent();
        }
        return label;//DOMUtil.getTextChildrenContent(DOMUtil
        //.getFirstElementByTagNameNS(navLabel, NAMESPACE_NCX, NCXDocumentV3.NCXTags.text));
    }


}
