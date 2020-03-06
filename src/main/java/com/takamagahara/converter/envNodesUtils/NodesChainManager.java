package com.takamagahara.converter.envNodesUtils;

import com.takamagahara.converter.envNodes.EnvNode;
import org.dom4j.Element;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description: create the chain of processing the converge request with containing a list of node instances.
 * User: kamisama
 * Date: 2020-03-04
 * Time: 下午10:48
 */
public class NodesChainManager {
    private List<Element> ElementList;
    private String sectionName;
    private String prefix;
    private String postfix;
    private Map<String, Map<String, String>> config;
    private String pathProject;

    /**
     * constructor leveraging dom4j.element
     * @param root
     * @param pathProject
     */
    public NodesChainManager(Element root, String pathProject) {
        this.pathProject = pathProject;
        try {
            config = (new LabelName2ClassName(pathProject + "/LabelConfig.ini")).getConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
        sectionName = root.getName();
        String ClassName = config.get(sectionName).get(sectionName);
        EnvNode rootNode = NodeFactory.build(ClassName, root);
        try {
            Containable dcn = (Containable) rootNode;
        } catch (ClassCastException e) {
            System.out.println("root input into the chain manager must implement Containable interface. ");
            e.printStackTrace();
        }
        prefix = rootNode.getPrefix();
        postfix = rootNode.getPostfix();
        ElementList = root.elements();
    }

    /**
     * constructor leveraging dom4j.element with constructed config
     * @param root
     * @param pathProject
     * @param config
     */
    public NodesChainManager(Element root, String pathProject, Map<String, Map<String, String>> config) {
        this.pathProject = pathProject;
        this.config = config;
        sectionName = root.getName();
        String ClassName = config.get(sectionName).get(sectionName);
        EnvNode rootNode = NodeFactory.build(ClassName, root);
        try {
            Containable dcn = (Containable) rootNode;
        } catch (ClassCastException e) {
            System.out.println("root input into the chain manager must implement Containable interface. ");
            e.printStackTrace();
        }
        prefix = rootNode.getPrefix();
        postfix = rootNode.getPostfix();
        ElementList = root.elements();
    }

    /**
     * build tex content within the root node.
     * @return Tex string
     */
    public String Process() {
        String Tex = prefix+"";

        for (Element element : ElementList) {
            EnvNode e = NodeFactory.build(config.get(sectionName).get(element.getName()), element);
            try {
                Containable containable = (Containable) e;
                NodesChainManager subManager = new NodesChainManager(element, pathProject, config);
                Tex += subManager.Process();
            } catch (ClassCastException e_) {
                Tex += e.getPrefix()+e.getText()+e.getPostfix();
            }
        }

        Tex += postfix;
        return Tex;
    }
}
