package com.takamagahara.reflector;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

import org.dom4j.*;
import org.dom4j.io.SAXReader;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kamisama
 * Date: 2020-03-01
 * Time: 下午2:21
 */
public class Reflector {
    private String configFile;
    private List<String> pathes;

    public Reflector(String configFile) {
        this.configFile = configFile;
        System.out.println("Reflector source: "+this.configFile);
        pathes = new ArrayList<>();
    }

    public void Construct(String documentPath) throws DocumentException {
//        System.out.println("It is better to ensure that you backup current Document by like git. ");
        SAXReader reader = new SAXReader();
        Document document = reader.read(new File(configFile));

        Element root = document.getRootElement();
        SectionNode sectionNode = new SectionNode(root, documentPath);
        Recursive(sectionNode);
        Flash(documentPath+"/Documents");
    }

    private void Recursive(SectionNode sectionNode) {
        pathes.add(sectionNode.getFullPath());
        CreateDirectory(sectionNode.getFullPath(), sectionNode.getName());

        Element eRoot = sectionNode.getElement();
        List sections = eRoot.elements("section");
        for (Iterator<?> it = sections.iterator(); it.hasNext();) {
            Element e = (Element) it.next();
            SectionNode sn = new SectionNode(e, sectionNode.getFullPath());
            Recursive(sn);
        }
    }

    /**
     * flash the Document, delete old directories and files.
     */
    private void Flash(String path) {
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (null != files) {
                for (File file2 : files) {
                    if (jugeIn(file2.getAbsolutePath())) {
                        if (file2.isDirectory()) {
                            Flash(file2.getAbsolutePath());
                        }
                    } else {
                        // delete useless one
                        deleteFile(file2);
                    }
                }
            }
        } else {
            System.out.println("Flash: Wrong Path!");
        }
    }

    private boolean jugeIn(String path) {
        boolean juge = false;
        for (String s : pathes) {
            if (s.equals(path)) {
                juge = true;
            }
        }
        return juge;
    }

    private boolean deleteFile(File file) {
        if (!file.exists()) {
            return false;
        }

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                deleteFile(f);
            }
        }
        return file.delete();
    }

    private void CreateDirectory(String path, String name) {
        try {
            Files.createDirectory(Paths.get(path));
            Files.createFile(Paths.get(path+"/"+name+".tex"));
            pathes.add(path+"/"+name+".tex");
        } catch (IOException e) {
//            System.out.println(path+" failed");
        }
    }

    public List<String> getPathes() {
        return pathes;
    }
}
