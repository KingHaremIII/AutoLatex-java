package com.takamagahara.xmler;

import org.dom4j.Element;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kamisama
 * Date: 2020-03-02
 * Time: 上午11:24
 */
public class Operator {
    public void addIgnore(SectionNode sectionNode, String path) {
        if (!sectionNode.getPath().equals(path)) {
            sectionNode.getElement().addAttribute("ignore", "true");
        } else {
            System.out.println(path);
        }
    }
}