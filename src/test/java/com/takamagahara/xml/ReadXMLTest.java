package com.takamagahara.xml;

import com.takamagahara.reflector.SectionNode;
import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kamisama
 * Date: 2020-02-29
 * Time: 下午10:10
 */
public class ReadXMLTest {
    SAXReader reader;
    Document document;

    @Before
    public void Load() throws DocumentException {
        reader = new SAXReader();
        document = reader.read(new File("/home/kamisama/IdeaProjects/AutoLatex/src/main/resources/skills.xml"));
    }

    @Test
    public void Test() throws DocumentException {
        Element root = document.getRootElement();
        reader = new SAXReader();
        document = reader.read(new File("/home/kamisama/IdeaProjects/AutoLatex/src/main/resources/test.xml"));
        Element testRoot = document.getRootElement();
        testRoot.add(root);
        List points = testRoot.elements();
        System.out.println(points.size());
        for (Iterator<?> it = points.iterator(); it.hasNext();) {
            Element e = (Element) it.next();
            System.out.println(e.getText());
        }
    }
}
