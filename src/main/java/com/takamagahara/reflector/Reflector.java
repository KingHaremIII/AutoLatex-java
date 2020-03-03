package com.takamagahara.reflector;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.takamagahara.xmler.SectionNode;
import com.takamagahara.xmler.XMLer;
import com.takamagahara.xmler.isSimilarCollection;
import org.dom4j.*;
import org.dom4j.io.SAXReader;

/**
 * Created with IntelliJ IDEA.
 * Description: make the structure corresponding to the configuration file.
 * User: kamisama
 * Date: 2020-03-01
 * Time: 下午2:21
 */
public class Reflector {
    private String configFile;
    private String pProject;
    private List<String> paths;

    public Reflector(String configFile) {
        this.configFile = configFile;
        System.out.println("Reflector source: "+this.configFile);
        paths = new ArrayList<>();
    }

    /**
     * construct the Documents according to Structure.xml by reflector.
     * @param projectPath absolute path of the project
     * @throws DocumentException
     */
    public void Reflect(String projectPath) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(new File(configFile));
        Element root = document.getRootElement();

        SectionNode sectionNode = new SectionNode(root, projectPath);
        Recursive(sectionNode);
        for (String s : paths) System.out.println(s);
        Flash(projectPath + "/Documents");
        // backup configuration, then reflector can check whether it needs to reconstruct or just rename next time.
        XMLer.writer(document, projectPath+"/.StructureBackup.xml");
    }

    /**
     * enable rename function with similar structure between the previous version and the current one.
     * @param projectPath absolute path of the project
     * @throws DocumentException
     */
    public void ReflectFast(String projectPath) throws DocumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        pProject = projectPath;
        SAXReader reader = new SAXReader();
        Document document = reader.read(new File(configFile));
        Element root = document.getRootElement();
        File file = new File(projectPath+"/.StructureBackup.xml");
        if (file.exists()) {
            Element rootLast = (new SAXReader()).read(file).getRootElement();
            isSimilarCollection result = XMLer.similar(configFile, projectPath+"/.StructureBackup.xml");
            if (!result.isResult()) {
                SectionNode sectionNode = new SectionNode(root, projectPath);
                Recursive(sectionNode);
                for (String s : paths) System.out.println(s);
                Flash(projectPath + "/Documents");
            } else {
                if (!result.isAbsoluteSame()) {
                    Rename(rootLast, result.getCurrent(), result.getOrigin());
                }
            }
        } else {
            SectionNode sectionNode = new SectionNode(root, projectPath);
            Recursive(sectionNode);
            for (String s : paths) System.out.println(s);
            Flash(projectPath + "/Documents");
        }
        // backup configuration, then reflector can check whether it needs to reconstruct or just rename next time.
        XMLer.writer(document, projectPath+"/.StructureBackup.xml");
    }

    /*
    create directories and files with a recursive way
     */
    private void Recursive(SectionNode sectionNode) {
        paths.add(sectionNode.getFullPath());
        CreateDirectory(sectionNode.getFullPath(), sectionNode.getName());

        Element eRoot = sectionNode.getElement();
        List sections = eRoot.elements("section");
//        System.out.println(sectionNode.getElement().attributeValue("name")+"'s len"+" = "+sections.size());
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
                    if (judgeIn(file2.getAbsolutePath())) {
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
    rename the only one different file.
     */
    private void Rename(Element root, List<String> current, List<String> origin) {
        System.out.println("similar structure, just rename. ");
        List<String> backup = new ArrayList<>();
        List<String> backupOrigin = new ArrayList<>();
        for (String s : current) {
            backup.add(s);
        }
        for (String s : origin) {
            backupOrigin.add(s);
        }
        // find the only one changed file.
        for (String s : current) {
            for (String so : origin) {
                if (s.equals(so)) {
                    backup.remove(s);
                    backupOrigin.remove(so);
                }
            }
        }

        // rename the target file.
        if (backup.size() == 1) {
            String pathOrigin = backupOrigin.get(0);
            String parentPath = Paths.get(pathOrigin).getParent().toString();
            String[] pathOriginList = pathOrigin.split("/");
            String nameOrigin = pathOriginList[pathOriginList.length-1];
            Element target = XMLer.searcher(root, pathOriginList);
            if (target != null) {
                String[] newPathList = backup.get(0).split("/");
                String newIDName = newPathList[newPathList.length - 1];
                File targetFile = new File(pProject+"/"+pathOrigin);
                if (targetFile.exists()) {
                    // rename directory and its tex file.
                     targetFile.renameTo(new File(pProject+"/"+parentPath+"/"+newIDName));
                    (new File(pProject+"/"+parentPath+"/"+newIDName+"/"+nameOrigin+".tex")).renameTo(new File(pProject+"/"+parentPath+"/"+newIDName+"/"+newIDName+".tex"));
                } else {
                    System.out.println("Reflector.Rename: ========Not Found the File: "+targetFile);
                }
            } else {
                System.out.println("Reflector.Rename: =======================Not Found the Target: "+backupOrigin.get(0));
            }
        } else {
            System.out.println("Error in Reflector.Rename with length of left paths: "+backup.size());
        }
    }

    /*
    juge whether the path is in the useful path list or not
     */
    private boolean judgeIn(String path) {
        boolean juge = false;
        for (String s : paths) {
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
        paths.add(path+"/"+name+".tex");
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

    public List<String> getPaths() {
        return paths;
    }
}
