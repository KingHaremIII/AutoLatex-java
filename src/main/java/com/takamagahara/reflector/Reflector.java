package com.takamagahara.reflector;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.takamagahara.xmler.*;
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
        addIgnore(projectPath);
        SAXReader reader = SAXReaderStore.getInstance();
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
        addIgnore(projectPath);
        pProject = projectPath;
        BackupForBackup();
        SAXReader reader = SAXReaderStore.getInstance();
        Document document = reader.read(new File(configFile));
        Element root = document.getRootElement();
        File file = new File(projectPath+"/.StructureBackup.xml");
        if (file.exists()) {
            Element rootLast = SAXReaderStore.getInstance().read(file).getRootElement();
            isSimilarCollection result = XMLer.similar(configFile, projectPath+"/.StructureBackup.xml");
            if (!result.isResult()) {
                SectionNode sectionNode = new SectionNode(root, projectPath);
                Recursive(sectionNode);
                for (String s : paths) System.out.println(s);
                Flash(projectPath + "/Documents");
            } else {
                if (!result.isAbsoluteSame()) {
                    Coordinate(rootLast, result.getCurrent(), result.getOrigin(), result);
                } else {
                    System.out.println("Structure configuration not changed! ");
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

    /**
     * Only allow to rename or delete one section and increment arbitrary sections.
     * @param projectPath absolute path of the project
     * @throws DocumentException
     */
    public void ReflectSafe(String projectPath) throws DocumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        addIgnore(projectPath);
        pProject = projectPath;
        BackupForBackup();
        SAXReader reader = SAXReaderStore.getInstance();
        Document document = reader.read(new File(configFile));
        Element root = document.getRootElement();
        File file = new File(projectPath+"/.StructureBackup.xml");
        if (file.exists()) {
            Element rootLast = SAXReaderStore.getInstance().read(file).getRootElement();
            isSimilarCollection result = XMLer.similar(configFile, projectPath+"/.StructureBackup.xml");
            if (result.isResult()) {
                if (!result.isAbsoluteSame()) {
                    Coordinate(rootLast, result.getCurrent(), result.getOrigin(), result);
                    // backup configuration, then reflector can check whether it needs to reconstruct or just rename next time.
                    XMLer.writer(document, projectPath+"/.StructureBackup.xml");
                } else {
                    System.out.printf("\n\nStructure configuration not changed! \n\n");
                }
            }
            // not similar, reject the reflect command
            else {
                System.err.println("Illegal reflection, you can just 1) rename or delete only one section;" +
                        " 2) increment arbitrary number of sections. ");
                System.err.println("reflect command not executed! ");
            }
        } else {
            SectionNode sectionNode = new SectionNode(root, projectPath);
            Recursive(sectionNode);
            for (String s : paths) System.out.println(s);
            Flash(projectPath + "/Documents");
            // backup configuration, then reflector can check whether it needs to reconstruct or just rename next time.
            XMLer.writer(document, projectPath+"/.StructureBackup.xml");
        }
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
                            System.out.printf("\ndelete: " + file2.getAbsolutePath()+"\n");
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
    rename or delete one section; increment arbitrary sections.
     */
    private void Coordinate(Element root, List<String> current, List<String> origin,
                            isSimilarCollection result) {
        System.out.println("similar structure. ");
        List<String> backup = new ArrayList<>();
        List<String> backupOrigin = new ArrayList<>();
        for (String s : current) {
            backup.add(s);
        }
        for (String s : origin) {
            backupOrigin.add(s);
        }

        // incrementing directories.
        if (result.isIncrement()) {
            System.out.println("adding sections...");
            // find files to be created (left in backup list).
            for (String s : origin) {
                for (String sc : current) {
                    if (s.equals(sc)) {
                        backup.remove(sc);
                        backupOrigin.remove(s);
                    }
                }
            }
            for (String sCreate : backup) {
                String[] tmp = sCreate.split("/");
                String name = tmp[tmp.length-1];
                try {
                    Files.createDirectory(Paths.get(pProject+"/"+sCreate));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    Files.createFile(Paths.get(pProject+"/"+sCreate + "/" + name + ".tex"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return;
        }

        if (result.isModify()) {
            System.out.println("renaming target...");
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
                String nameOrigin = pathOriginList[pathOriginList.length - 1];
                Element target = XMLer.searchByName(root, pathOriginList);
                if (target != null) {
                    String[] newPathList = backup.get(0).split("/");
                    String newIDName = newPathList[newPathList.length - 1];
                    File targetFile = new File(pProject + "/" + pathOrigin);
                    if (targetFile.exists()) {
                        // rename directory and its tex file.
                        targetFile.renameTo(new File(pProject + "/" + parentPath + "/" + newIDName));
                        (new File(pProject + "/" + parentPath + "/" + newIDName + "/" + nameOrigin + ".tex")).renameTo(new File(pProject + "/" + parentPath + "/" + newIDName + "/" + newIDName + ".tex"));
                    } else {
                        System.out.println("Reflector.Rename: ========Not Found the File: " + targetFile);
                    }
                } else {
                    System.out.println("Reflector.Rename: =======================Not Found the Target: " + backupOrigin.get(0));
                }
            } else {
                System.out.println("Error in Reflector.Rename with length of left paths: " + backup.size());
            }
            return;
        }

        // delete only one file.
        if (result.isDelete()) {
            System.out.println("deleting target...");
            // find the only one changed file.
            for (String s : current) {
                for (String so : origin) {
                    if (s.equals(so)) {
                        backup.remove(s);
                        backupOrigin.remove(so);
                    }
                }
            }

            for (String sDelete : backupOrigin) {
                delDir(new File(pProject+"/"+sDelete));
            }
        }
    }

    /*
    judge whether the path is in the useful path list or not
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
        String[] tmpList = path.split("/");
        String tmp = "/";
        int i=0;
        for (;i<tmpList.length-2;i++) {
            tmp += tmpList[i]+"/";
        }
        tmp += tmpList[i];
        if (tmp.equals(pProject)) {
            return;
        }

        paths.add(path+"/"+name+".tex");
        try {
            Files.createDirectory(Paths.get(path));
        } catch (IOException e) {
//            System.out.println(path+" failed");
//            e.printStackTrace();
        }
        try {
            Files.createFile(Paths.get(path+"/"+name+".tex"));
        } catch (IOException e) {
//            System.out.println(path+"/"+name+".tex"+" failed");
//            e.printStackTrace();
        }
    }

    public List<String> getPaths() {
        return paths;
    }

    private void addIgnore(String projectPath) {
        String[] ignoreList = OperatorStore.getInstance().readToString(projectPath+"/.reflectignore").split("\n");
        for (String s : ignoreList) {
            paths.add(projectPath + "/Documents/"+s);
        }
    }

    private void BackupForBackup() {
        File detect = new File(pProject+"/"+".StructureBackup.xml");
        if (detect.exists()) {
            try {
                FileInputStream fis = new FileInputStream(pProject + "/" + ".StructureBackup.xml");
                FileOutputStream fos = new FileOutputStream(pProject + "/" + "StructureBackup.xml");

                //创建搬运工具
                byte datas[] = new byte[1024 * 8];
                //创建长度
                int len = 0;
                //循环读取数据
                while ((len = fis.read(datas)) != -1) {
                    fos.write(datas, 0, len);
                }
                //3.释放资源
                fis.close();
                fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
