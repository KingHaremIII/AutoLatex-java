package com.takamagahara.xmler;

import com.takamagahara.converter.envNodes.document.body.Text;
import org.dom4j.Element;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kamisama
 * Date: 2020-03-02
 * Time: 上午11:24
 */
public class Operator {
    public void show(SectionNode sectionNode) {
        System.out.println(sectionNode.getPath());
    }

    public void addIgnore(SectionNode sectionNode, String path) {
//        System.out.println(sectionNode.getPath());
        if (!sectionNode.getPath().equals(path)) {
            sectionNode.getElement().addAttribute("ignore", "true");
        } else {
            System.out.println(path);
        }
    }

    public void deleteExcept(SectionNode sectionNode, String path) {
        // clean non-target node.
        if (!sectionNode.getPath().equals(path)) {
            List<Element> elementList = sectionNode.getElement().elements();
            for (Element e : elementList) {
                // delete non-section node.
                if (!e.getName().equals("section")) {
                    sectionNode.getElement().remove(e);
                }
            }
        }
    }

    public void pathRecorder(SectionNode sectionNode, List<String> recorder) {
        recorder.add(sectionNode.getPath());
    }

    public void Tex2String(SectionNode sectionNode, Text text, List<String> legalItems) {
        String path = sectionNode.getPath();
        String[] splited = path.split("/");
        int layer = splited.length-2;
        if (layer >= 0) {
            String head = splited[layer+1].substring(0, 5);
            if (legalItems.contains(head)) {
                // TODO Strategy pattern to process different kind of items.
            } else {
                if (head.equals("section")) {
                    String tmp = "\\";
                    for (int i = 0; i < layer; i++) {
                        tmp += "sub";
                    }
                    tmp += "{" + sectionNode.getElement().attributeValue("name") + "}\n";
                    text.add(
                            tmp + readToString(path) + "\n");
                } else {
                    System.out.println("Warning: unregistered label: "+sectionNode.getPath());
                }
            }
        }
    }

   private String readToString(String fileName) {
        String encoding = "UTF-8";
        File file = new File(fileName);
        Long fileLength = file.length();
       FileInputStream in;
       byte[] fileContent = new byte[fileLength.intValue()];
        try {
            in = new FileInputStream(file);
            in.read(fileContent);
            in.close();
            return new String(fileContent, encoding);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}