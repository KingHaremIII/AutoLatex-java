package com.takamagahara.xml;

import java.io.FileOutputStream;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kamisama
 * Date: 2020-02-29
 * Time: 下午10:02
 */
public class XMLer {
    private String name;

    public XMLer(String name) {
        this.name = name;
    }

    public boolean InitializeXML() {
        boolean returnBoolean = false;

        // create a document
        Document document = DocumentHelper.createDocument();
        // construct XML initial file
        Element root = document.addElement("document");
        root.addComment("This is a XML boot for AutoLatex. ");
        Element first = root.addElement("skill");
        first.setText("fuck you!");

        try {
            // 创建格式化类
            OutputFormat format = OutputFormat.createPrettyPrint();
            // 设置编码格式，默认UTF-8
            format.setEncoding("UTF-8");
            // 创建输出流，此处要使用Writer，需要指定输入编码格式，使用OutputStream则不用
            FileOutputStream fos = new FileOutputStream("/home/kamisama/IdeaProjects/AutoLatex/src/main/resources/"+name+".xml");
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
}
