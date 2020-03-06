package com.takamagahara.converter.envNodesUtils;

import com.takamagahara.converter.envNodes.EnvNode;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: create the chain of processing the converge request with containing a list of node instances.
 * User: kamisama
 * Date: 2020-03-04
 * Time: 下午10:48
 */
public class NodesChainManager {
    private List<EnvNode> list;
    private String prefix;
    private String postfix;

    public NodesChainManager(EnvNode rootNode) {
        try {
            Containable dcn = (Containable) rootNode;
        } catch (ClassCastException e) {
            System.out.println("root input into the chain manager must implement Containable interface. ");
            e.printStackTrace();
        }
        prefix = rootNode.getPrefix();
        postfix = rootNode.getPostfix();
        List<Element> nodeList = rootNode.getChilds();
        list = new ArrayList<>();
        buildChain(nodeList);
    }

    public void buildChain(List<Element> nodeList) {
        // TODO build elements (EnvNode) in chain.

    }

    public String Process() {
        String Tex = prefix+"";

        for (EnvNode e : list) {
            try {
                Containable containable = (Containable) e;
                NodesChainManager subManager = new NodesChainManager(e);
                // TODO build list.
                Tex += subManager.Process();
            } catch (ClassCastException e_) {
                Tex += e.getPrefix()+e.getText()+e.getPostfix();
            }
        }

        Tex += postfix;
        return Tex;
    }
}
