package com.takamagahara.xmler;

import com.takamagahara.converter.envNodes.document.body.Text;
import com.takamagahara.converter.envNodesUtils.LabelName2ClassName;
import org.dom4j.Element;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kamisama
 * Date: 2020-03-02
 * Time: 上午11:24
 */
public class Operator {
    /**
     * expansion entry for xml.Operator.
     * After extending OperatorExpanded class and implementing the only one method Execute(Object... objects),
     *     the developers can execute their own function while search every nodes in xml using
     *     XMLer's API--reader function set in:
     *         XMLer.reader(...(other parameters), OperatorStore.getInstance(),
     *             YouOperator.class.getMethod("Execute", OperatorExpanded.class Object[].class),
     *             (new YourOperator()), YourParameters)
     * @param ExpandedInstance
     * @param objects
     */
    public void Expand(OperatorExpanded ExpandedInstance, Object... objects) {
        ExpandedInstance.Execute(objects);
    }

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

    public void pathRecorderIgnore(SectionNode sectionNode, List<String> recorder) {
        if (sectionNode.getPath().split("/").length>1) {
            if (sectionNode.getElement().attribute("ignore") == null) {
                recorder.add(sectionNode.getPath());
            }
        }
    }

    public void Tex2String(SectionNode sectionNode, Text text, String pathProject) throws IOException {
        // TODO figure process.
        Map<String, String> legalMap = (new LabelName2ClassName(pathProject)).getConfig().get("body");
        Set<String> legals = legalMap.keySet();
        String path = sectionNode.getPath();
        String[] splited = path.split("/");
        int layer = splited.length-2;
        if (layer >= 0) {
            if (sectionNode.getElement().getName().equals("section")) {
                String tmp = "\\";
                for (int i = 0; i < layer; i++) {
                    tmp += "sub";
                }
                tmp += "section{" + sectionNode.getElement().attributeValue("name") + "}\n";
                text.add(tmp);
                // ignore root label.
                if (sectionNode.getPath().split("/").length>1) {
                    // ignore other section except the target section in unit test model.
                    if (sectionNode.getElement().attribute("ignore") == null) {
                        path += "/"+splited[splited.length-1]+".tex";
                        text.add(readToString(pathProject+"/"+path) + "\n");
                    }
                }
            } else {
                if (legals.contains(sectionNode.getElement().getName())) {
                    // TODO strategy selection.
                } else {
                    System.out.println("Warning: unregistered label: " + sectionNode.getPath());
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