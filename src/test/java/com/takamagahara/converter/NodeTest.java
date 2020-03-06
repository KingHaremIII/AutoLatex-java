package com.takamagahara.converter;

import com.takamagahara.converter.envNodes.Documents;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kamisama
 * Date: 2020-03-05
 * Time: 下午3:32
 */
public class NodeTest {
    SAXReader reader;
    Document document;

    @Before
    public void pre() throws DocumentException {
        reader = new SAXReader();
        document = reader.read(new File("/home/kamisama/IdeaProjects/AutoLatex/src/main/resources/IEEEtranTest/Target/IEEEtranTest.xml"));
    }

    @Test
    public void DocumentsTest() {
        Element root = document.getRootElement();
        Documents d = new Documents(root);
        System.out.println(d.getPrefix()+d.getText()+d.getPostfix());
    }
}
