package com.takamagahara.xmler;

import com.takamagahara.converter.envNodes.document.body.StrategierStore;
import com.takamagahara.converter.envNodes.document.body.Text;
import com.takamagahara.converter.envNodesUtils.LabelName2ClassName;
import org.dom4j.Element;
import org.dom4j.Namespace;

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

    public void Test(SectionNode sectionNode, Text text, String pathProject) {
        text.add("test\n");
    }

    /**
     * ignore sections without unitTest attribute
     * @param sectionNode
     */
    public void InverseIgnore(SectionNode sectionNode) {
        Element element = sectionNode.getElement();
        if (element.attributeValue("unitTest") == null) {
            element.addAttribute("ignore", "true");
        } else {
            element.remove(Namespace.get("unitTest"));
        }
    }

    public void Tex2String(SectionNode sectionNode, Text text, String pathProject, String configSectionName) {
        Map<String, String> legalMap = null;
        try {
            legalMap = (new LabelName2ClassName(pathProject)).getConfig().get(configSectionName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Set<String> legals = legalMap.keySet();

        String path = sectionNode.getPath();
        String[] splited = path.split("/");
        // layer is used to identify the number of cascading "sub".
        int layer = splited.length-2;
        // filter root node <sections></sections>
        if (layer >= 0) {
            // normal section node
            if (sectionNode.getElement().getName().equals("section")) {
                String tmp = "\\";
                for (int i = 0; i < layer; i++) {
                    tmp += "sub";
                }

                if (sectionNode.getElement().attributeValue("noNumber") == null) {
                    tmp += "section{" + sectionNode.getElement().attributeValue("name") + "}\n";
                } else {
                    tmp += "section*{" + sectionNode.getElement().attributeValue("name") + "}\n";
                }
                text.add(tmp);
                // ignore root label.
                if (sectionNode.getPath().split("/").length>1) {
                    // ignore other section except the target section in unit test model.
                    if (sectionNode.getElement().attribute("ignore") == null) {
                        path += "/" + splited[splited.length - 1] + ".tex";
                        text.add(readToString(pathProject + "/" + path) + "\n");
                    }
                }
            } else {
                // check whether the label is legally registered or not.
                if (legals.contains(sectionNode.getElement().getName())) {
                    if (sectionNode.getElement().attribute("ignore") == null) {
                        /*
                        Strategy Model
                         */
                        // 1. acquire full strategy name.
                        String strategyName = legalMap.get(sectionNode.getElement().getName());
                        // 2. set strategy.
                        StrategierStore.getInstance().setStrategy(strategyName, pathProject);
                        // 3. process label and acquire the text.
                        text.add(StrategierStore.getInstance().Process(sectionNode.getElement()));
                    }
                } else {
                    System.err.println("Warning: unregistered label: " + sectionNode.getPath());
                }
            }
        }
    }

    public String readToString(String fileName) {
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