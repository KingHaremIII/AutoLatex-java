package com.takamagahara.reflector;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.takamagahara.xmler.SectionNode;
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

    /**
     * construct the Documents according to Structure.xml by reflector.
     * @param projectPath absolute path of the project
     * @throws DocumentException
     */
    public void Construct(String projectPath) throws DocumentException {
//        System.out.println("It is better to ensure that you backup current Document by like git. ");
        SAXReader reader = new SAXReader();
        Document document = reader.read(new File(configFile));

        Element root = document.getRootElement();
        SectionNode sectionNode = new SectionNode(root, projectPath);
        Recursive(sectionNode);
        for (String s : pathes) System.out.println(s);
        Flash(projectPath+"/Documents");
    }

    /*
    create directories and files with a recursive way
     */
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

    /*
    flash the Document, delete old directories and files.
     */
    private void Flash(String path) {
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (null != files) {
                for (File file2 : files) {
                    if (file2.getName().equals("Documents.tex")) {
                        file2.delete();
                        continue;
                    }
                    if (jugeIn(file2.getAbsolutePath())) {
                        if (file2.isDirectory()) {
                            Flash(file2.getAbsolutePath());
                        }
                    } else {
                        System.out.println(file2.getAbsolutePath());
                        // delete useless one
//                        deleteFile(file2);
                        if (file2.isDirectory()) {
                            delDir(file2);
                        } else {
                            file2.delete();
                        }
                    }
                }
            }
        } else {
            System.out.println("Flash: Wrong Path!");
        }
    }

    /*
    juge whether the path is in the useful path list or not
     */
    private boolean jugeIn(String path) {
        boolean juge = false;
        for (String s : pathes) {
            if (s.equals(path)) {
                juge = true;
            }
        }
        return juge;
    }

    /*
    delete useless file or directory
     */
    private void deleteFile(File file) {
//        if (file.isDirectory()) {
//            File[] files = file.listFiles();
//            for (File f : files) {
//                deleteFile(f);
//            }
//        } else {
//            String parent = file.getParent();
//            File fileParent = new File(parent);
//            File[] files = fileParent.listFiles();
//            int i = 0;
//            for (File f : files) {
//                if (f.isFile()) {
//                    i++;
//                }
//            }
//            if (i>1) {
//                file.delete();
//            } else {
//                file.renameTo(new File(parent+"/"+fileParent.getName()+".tex"));
//            }
//        }
    }

    /*
    delete useless file or directory
     */
    public boolean delDir(File dir) {
        if (dir.isDirectory()){
            String[] children = dir.list();
            for (int i=0; i<children.length;i++) {
                boolean success = delDir(new File(dir, children[i]));
                if (!success)
                    return false;
            }

        }
        if (dir.delete()){
            return true;
        }else {
            System.out.println("cannot delete directory: "+dir.getAbsolutePath());
        }
        return false;
    }

    /*
    create the directory and file
     */
    private void CreateDirectory(String path, String name) {
        pathes.add(path+"/"+name+".tex");
        try {
            Files.createDirectory(Paths.get(path));
        } catch (IOException e) {
//            System.out.println(path+" failed");
        }
        try {
            Files.createFile(Paths.get(path+"/"+name+".tex"));
        } catch (IOException e) {

        }
    }

    public List<String> getPathes() {
        return pathes;
    }
}
