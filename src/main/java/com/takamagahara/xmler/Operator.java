package com.takamagahara.xmler;

import org.dom4j.Element;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kamisama
 * Date: 2020-03-02
 * Time: 上午11:24
 */
public class Operator {
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
}