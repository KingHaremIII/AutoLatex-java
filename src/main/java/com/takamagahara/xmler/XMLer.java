package com.takamagahara.xmler;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
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
        SectionNode sectionNode;
        if (!root.attributeValue("id").equals("")) {
            sectionNode = new SectionNode(root,
                    root.attributeValue("id") + " " + root.attributeValue("name"));
        } else {
            sectionNode = new SectionNode(root, root.attributeValue("name"));
        }
        Foreach(sectionNode, object, method, args);

        return root;
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

    private static void Foreach(SectionNode sectionNode) {
        String name = sectionNode.getElement().attribute("name").getValue();
        System.out.println(sectionNode.toFullString());
        List<Element> elements = sectionNode.getElement().elements();
        for (Element e :
                elements) {
            Foreach((new SectionNode(e, name+"/"+e.attribute("name").getValue())));
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
                            sectionNode.getPath()+"/"+e.attributeValue("id")+" "+e.attributeValue("name"))),
                    methodObject, method, args);
        }
    }
}
