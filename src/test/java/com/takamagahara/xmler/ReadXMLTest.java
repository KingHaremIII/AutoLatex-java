package com.takamagahara.xmler;

import com.takamagahara.converter.envNodes.document.body.Text;
import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
        document = reader.read(new File("/home/kamisama/IdeaProjects/AutoLatex/src/main/resources/IEEEtranTest/Structure.xml"));
    }

    @Test
    public void attrrChangeTest() {
        Element root = document.getRootElement();
        List<Attribute> list = new ArrayList<>();
        Attribute a = root.attribute("name");
        a.setValue("fuck");
        list.add(a);
        root.setAttributes(list);
        System.out.println(root.attributeValue("name"));
    }

    @Test
    public void Test() throws DocumentException {
        Element root = document.getRootElement();
        reader = new SAXReader();
        document = reader.read(new File("/home/kamisama/IdeaProjects/AutoLatex/src/main/resources/backup.xml"));
        Element testRoot = document.getRootElement();
        testRoot.add(root);
        List points = testRoot.elements();
        System.out.println(points.size());
        for (Iterator<?> it = points.iterator(); it.hasNext();) {
            Element e = (Element) it.next();
            System.out.println(e.getText());
        }
    }

    @Test
    public void copyTest() {
        Element root = document.getRootElement();
        Element sub = root.createCopy();

        Element b = sub.element("b");
        System.out.println(b.getText());
    }

    @Test
    public void addAttributeTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Element root = document.getRootElement();
        Operator.class.getMethod("addIgnore", Element.class).invoke((new Operator()), root);
        Attribute ig = root.attribute("ignore");
        System.out.println(ig.getValue());
    }

    @Test
    public void addAttributeTest2() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Element root = document.getRootElement();
        Method method = Operator.class.getMethod("addIgnore", Element.class, String.class);
        Operator operator = new Operator();
        supply.supplyaddAtt(operator, method, root, "successfully");
        Attribute ig = root.attribute("ignore");
        System.out.println(ig.getValue());
    }

    @Test
    public void getPathTest() {
        Element root = document.getRootElement();
        System.out.println(root.getPath());
    }

    @Test
    public void ForeachTest() throws DocumentException {
        XMLer.reader("/home/kamisama/IdeaProjects/AutoLatex/src/main/resources/IEEEtranTest/Structure.xml", "section");
    }

    @Test
    public void searchTest() {
        Element root = document.getRootElement();
        String path = "III Proposed Method0/2) A";
        Element target = XMLer.searchByName(root, path.split("/"));
        System.out.println(target.attributeValue("name"));
    }

    @Test
    public void sectionNodeReader() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Element root = document.getRootElement();
        SectionNode sectionNode = new SectionNode(root, "Documents");
        Element sub = XMLer.reader(
                sectionNode, (new Operator()),
                Operator.class.getMethod("show", SectionNode.class));
    }

    @Test
    public void readPath() throws DocumentException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        document = reader.read(new File("/home/kamisama/IdeaProjects/AutoLatex/src/main/resources/IEEEtranTest/Target/IEEEtranTest.xml"));
        Element textRoot = document.getRootElement().element("document").element("body").element("sections");
        SectionNode sectionNode = new SectionNode(textRoot, "Documents");
        List<String> pathText = new ArrayList<>();
        XMLer.reader(sectionNode, OperatorStore.getInstance(),
                Operator.class.getMethod("pathRecorderIgnore", SectionNode.class, List.class), pathText);
        for (String s : pathText) {
            System.out.println(s);
        }
    }

    @Test
    public void XMLerTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, DocumentException {
        document = reader.read(new File("/home/kamisama/IdeaProjects/AutoLatex/src/main/resources/IEEEtranTest/Target/IEEEtranTest.xml"));
        Element textRoot = document.getRootElement().element("document").element("body").element("sections");
        SectionNode sectionNode = new SectionNode(textRoot, "Documents");
        Text text = new Text();
        XMLer.reader(sectionNode, OperatorStore.getInstance(),
                Operator.class.getMethod("Tex2String", SectionNode.class, Text.class, String.class, String.class),
                text, "/home/kamisama/IdeaProjects/AutoLatex/src/main/resources/IEEEtranTest", "body");
        System.out.println(text.getContent());
    }
}


class supply {
    public static void supplyaddAtt(Object o, Method method, Object... args) throws InvocationTargetException, IllegalAccessException {
        method.invoke(o, args);
    }
}