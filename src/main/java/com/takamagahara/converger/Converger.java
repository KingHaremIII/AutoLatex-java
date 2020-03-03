package com.takamagahara.converger;

import com.takamagahara.xmler.Operator;
import com.takamagahara.xmler.SectionNode;
import com.takamagahara.xmler.XMLer;
import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kamisama
 * Date: 2020-03-01
 * Time: 下午5:07
 */
public class Converger {
    SAXReader reader;
    Document document;
    String projectPath;
    String targetPath;

    public Converger(String projectPath) throws DocumentException {
        this.projectPath = projectPath;
        reader = new SAXReader();
        document = reader.read(new File(projectPath+"/Env.xml"));
        targetPath = projectPath+"/Target";
    }

    /**
     * converge environment configuration and structure configuration.
     * @throws DocumentException
     */
    public void Converge() throws DocumentException {
        Element root = document.getRootElement();
        Element documentElement = root.element("document");
        Element bodyElement = documentElement.element("body");
        Element structureRoot = (new SAXReader()).read(new File(projectPath+"/Structure.xml")).getRootElement();
        bodyElement.add(structureRoot);

        String nameProject = Paths.get(projectPath).getFileName().toString();
        XMLer.writer(document, targetPath+"/"+nameProject+".xml");
    }

    /**
     * overload Converge for unit test
     * @param unitPath absolute path of the unit to be tested.
     * @throws DocumentException
     * @throws NoSuchMethodException
     */
    public void Converge(String unitPath) throws DocumentException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Element root = document.getRootElement();
        Element documentElement = root.element("document");
        Element bodyElement = documentElement.element("body");

        /*
        drop all content of other sections except the unitPath.
         */
        // add ignore attribute to every element firstly using reflection mechanism of java.
        Element structureRoot = XMLer.reader(projectPath+"/Structure.xml", "section",
                (new Operator()),
                Operator.class.getMethod("addIgnore", SectionNode.class, String.class),
                unitPath);
        // delete all elements in ignored sections including figure, table and algorithm.
        SectionNode sn = new SectionNode(structureRoot, XMLer.getNamefromElement(structureRoot));
        structureRoot = XMLer.reader(sn, "section",
                (new Operator()),
                Operator.class.getMethod("deleteExcept", SectionNode.class, String.class),
                unitPath);

        bodyElement.add(structureRoot);
        String nameProject = Paths.get(projectPath).getFileName().toString();
        XMLer.writer(document, targetPath+"/"+nameProject+".xml");
    }
}