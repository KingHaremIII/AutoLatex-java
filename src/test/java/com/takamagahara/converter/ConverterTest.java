package com.takamagahara.converter;

import com.takamagahara.xmler.XMLer;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kamisama
 * Date: 2020-03-03
 * Time: 下午4:37
 */
public class ConverterTest {
    SAXReader reader;
    Document document;
    String[] strings = {"documents", "document", "body"};

    @Before
    public void load() throws DocumentException {
//        String name = "/Structure.xml";
//        String name = "/test.xml";
        String name = "/Target/IEEEtranTest.xml";
        reader = new SAXReader();
        document = reader.read(new File("/home/kamisama/IdeaProjects/AutoLatex/src/main/resources/IEEEtranTest"+name));
        if (new File("/home/kamisama/IdeaProjects/AutoLatex/src/main/resources/IEEEtranTest"+name).exists()) {
            System.out.println("/home/kamisama/IdeaProjects/AutoLatex/src/main/resources/IEEEtranTest" + name);
        }
    }

    @Test
    public void Test() {
        Element root = document.getRootElement();
        if (root != null) {
            Element body = XMLer.searcher(root, strings);
            if (body != null) {
                System.out.println(body.attributeValue("name"));
            } else {
                System.out.println("null");
            }
        }
        List<Element> list = root.elements();
        for (Element e : list) {
            System.out.println(e.getName());
        }
        System.out.println(((Element) root.elements().get(2)).getName());
    }

    @Test
    public void stringEmpty() {
        String a = document.getRootElement().attributeValue("a");
        if (a == null) {
            System.out.println("empty");
        }
    }
}
