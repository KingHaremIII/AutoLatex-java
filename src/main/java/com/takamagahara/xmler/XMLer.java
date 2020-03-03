package com.takamagahara.xmler;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

/**
 * Created with IntelliJ IDEA.
 * Description: xml processor conducting reading and writing using dom4j.
 * User: kamisama
 * Date: 2020-02-29
 * Time: 下午10:02
 */
public class XMLer {
    /**
     * traversal elements in xml.
     * @param path absolute path of xml.
     * @return true/false
     */
    public static Element reader(String path) throws DocumentException {
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(new File(path));
        Element root = document.getRootElement();
        SectionNode sectionNode = new SectionNode(root, root.getPath());

        Foreach(sectionNode);

        return root;
    }

    /**
     * overloading reader with the input of a SectionNode.
     * @param sectionNode
     * @return
     */
    public static Element reader(SectionNode sectionNode) {
        Foreach(sectionNode);
        return sectionNode.getElement();
    }

    /**
     * overloading reader, with specific name to each element.
     * @param path absolute path of xml.
     * @param nameSection specific name of elements.
     * @return true/false
     */
    public static Element reader(String path, String nameSection) throws DocumentException {
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(new File(path));
        Element root = document.getRootElement();
        SectionNode sectionNode = new SectionNode(root, root.getPath());

        Foreach(sectionNode, nameSection);

        return root;
    }

    /**
     * overloading reader with the input of a SectionNode.
     * @param sectionNode
     * @return
     */
    public static Element reader(SectionNode sectionNode, String nameSection) {
        Foreach(sectionNode, nameSection);
        return sectionNode.getElement();
    }

    /**
     * overloading reader, with specific method to each element.
     * @param path absolute path of xml.
     * @param object a instance of the class in which method exists.
     * @param method specific method to be conducted on elements.
     * @param args parameters, if any, for method.
     * @return true/false
     */
    public static Element reader(String path, Object object, Method method, Object... args) throws DocumentException, InvocationTargetException, IllegalAccessException {
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(new File(path));
        Element root = document.getRootElement();
        SectionNode sectionNode = new SectionNode(root, getNamefromElement(root));

        Foreach(sectionNode, object, method, args);

        return root;
    }

    /**
     * overloading reader with the input of a SectionNode.
     * @param sectionNode
     * @return
     */
    public static Element reader(SectionNode sectionNode, Object object, Method method, Object... args) throws InvocationTargetException, IllegalAccessException {
        Foreach(sectionNode, object, method, args);
        return sectionNode.getElement();
    }

    /**
     * overloading reader, with specific method and name to each element.
     * @param path absolute path of xml.
     * @param nameSection specific name of elements.
     * @param object a instance of the class in which method exists.
     * @param method specific method to be conducted on elements.
     * @param args parameters, if any, for method.
     * @return true/false
     */
    public static Element reader(String path, String nameSection, Object object, Method method, Object... args) throws DocumentException, InvocationTargetException, IllegalAccessException {
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(new File(path));
        Element root = document.getRootElement();
        SectionNode sectionNode = new SectionNode(root, getNamefromElement(root));

        Foreach(sectionNode, nameSection, object, method, args);

        return root;
    }

    /**
     * overloading reader with the input of a SectionNode.
     * @param sectionNode
     * @return
     */
    public static Element reader(SectionNode sectionNode, String nameSection, Object object, Method method, Object... args) throws InvocationTargetException, IllegalAccessException {
        Foreach(sectionNode, nameSection, object, method, args);
        return sectionNode.getElement();
    }

    /**
     * writer elements to xml
     * @param document Document containing elements
     * @param path target absolute path
     * @return true/false
     */
    public static boolean writer(Document document, String path) {
        boolean returnBoolean = false;

        try {
            // 创建格式化类
            OutputFormat format = OutputFormat.createPrettyPrint();
            // 设置编码格式，默认UTF-8
            format.setEncoding("UTF-8");
            // 创建输出流，此处要使用Writer，需要指定输入编码格式，使用OutputStream则不用
            FileOutputStream fos = new FileOutputStream(path);
            // 创建xml输出流
            XMLWriter writer = new XMLWriter(fos, format);
            // 生成xml文件
            writer.write(document);
            writer.close();
            returnBoolean = true;
        } catch (Exception e) {
            System.out.println("Failed to create initial XML file, please create it manually. ");
            e.printStackTrace();
        }

        return returnBoolean;
    }

    public static Element searcher(Element root, String[] paths) {
        String[] path;
        if (paths[0].equals("Documents")) {
            path = new String[paths.length-1];
            for (int i=1;i<paths.length;i++) {
                path[i-1] = paths[i];
            }
        } else {
            path = paths;
        }

        Element target = null;
        if (path.length > 1) {
            String[] subPath = new String[path.length - 1];
            for (int i = 1; i < path.length; i++) {
                subPath[i - 1] = path[i];
            }
            String currentPoint = path[0];

            // recursive searching for the target element.
            List<Element> elements = root.elements("section");
            for (Element e : elements) {
                if (getNamefromElement(e).equals(currentPoint)) {
                    target = searcher(e, subPath);
                }
            }
        } else {
            if (path.length == 1) {
                String currentPoint = path[0];
                List<Element> elements = root.elements("section");
                for (Element e : elements) {
                    if (getNamefromElement(e).equals(currentPoint)) {
                        /*
                        found the target.
                         */
                        target = e;
                    }
                }
            }
        }
        return target;
    }

    /**
     * judge the current structure configuration is similar with the previous one ot not.
     * @param currentConfig absolute path of current configuration file.
     * @param originConfig absolute path of previous configuration file.
     * @return a collection of the result of judgement, whether absolute the same and both lists of current paths and origin paths.
     * @throws NoSuchMethodException
     * @throws DocumentException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static isSimilarCollection similar(String currentConfig, String originConfig) throws NoSuchMethodException, DocumentException, IllegalAccessException, InvocationTargetException {
        List<String> currentPathes = new ArrayList<>();
        List<String> originPathes = new ArrayList<>();
        XMLer.reader(currentConfig, "section", (new Operator()),
                Operator.class.getMethod("pathRecorder", SectionNode.class, List.class),
                currentPathes);
        XMLer.reader(originConfig, "section", (new Operator()),
                Operator.class.getMethod("pathRecorder", SectionNode.class, List.class),
                originPathes);

        int count = 0;
        for (String sc : currentPathes) {
            for (String so: originPathes) {
                if (sc.equals(so)) {
                    count++;
                }
            }
        }
        isSimilarCollection returnCollection = new isSimilarCollection();
        returnCollection.setCurrent(currentPathes);
        returnCollection.setOrigin(originPathes);

        if (count < currentPathes.size()-1) {
            returnCollection.setResult(false);
        } else {
            returnCollection.setResult(true);
            if (count == currentPathes.size()) {
                returnCollection.setAbsoluteSame(true);
            }
        }
        return returnCollection;
    }

    private static void Foreach(SectionNode sectionNode) {
        String name = sectionNode.getElement().attribute("name").getValue();
//        System.out.println(sectionNode.toFullString());
        List<Element> elements = sectionNode.getElement().elements();
        for (Element e :
                elements) {
            Foreach((new SectionNode(e, name+"/"+e.attribute("name").getValue())));
        }
    }

    public static void Foreach(SectionNode sectionNode, String nameSection) {
        String name = sectionNode.getElement().attribute("name").getValue();
//        System.out.println(sectionNode.toFullString());
        List<Element> elements = sectionNode.getElement().elements(nameSection);
        for (Element e :
                elements) {
//            System.out.println(e.attributeValue("name"));
            Foreach((new SectionNode(e, name+"/"+e.attribute("name").getValue())), nameSection);
        }
    }

    private static void Foreach(SectionNode sectionNode, Object methodObject, Method method, Object... args) throws InvocationTargetException, IllegalAccessException {
        int num = args.length+1;
        Object[] objects = new Object[num];
        objects[0] = sectionNode;
        for(int i=1;i<num;i++) {
            objects[i] = args[i-1];
        }

        method.invoke(methodObject, objects);
//        System.out.println(sectionNode.getPath());
        List<Element> elements = sectionNode.getElement().elements();
        for (Element e :
                elements) {
            Foreach((new SectionNode(e,
                            sectionNode.getPath()+"/"+getNamefromElement(e))),
                    methodObject, method, args);
        }
    }

    private static void Foreach(SectionNode sectionNode, String nameSection, Object methodObject, Method method, Object... args) throws InvocationTargetException, IllegalAccessException {
        int num = args.length+1;
        Object[] objects = new Object[num];
        objects[0] = sectionNode;
        for(int i=1;i<num;i++) {
            objects[i] = args[i-1];
        }

        method.invoke(methodObject, objects);
//        System.out.println(sectionNode.getPath());
        List<Element> elements = sectionNode.getElement().elements(nameSection);
        for (Element e :
                elements) {
            Foreach((new SectionNode(e,
                            sectionNode.getPath()+"/"+getNamefromElement(e))),
                    nameSection, methodObject, method, args);
        }
    }

    public static String getNamefromElement(Element e) {
        String name;
        if (!e.attributeValue("id").equals("")) {
            name = e.attributeValue("id") + " " + e.attributeValue("name");
        } else {
            name = e.attributeValue("name");
        }
        return name;
    }
}
