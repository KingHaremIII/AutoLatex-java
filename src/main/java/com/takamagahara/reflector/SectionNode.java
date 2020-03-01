package com.takamagahara.reflector;

import org.dom4j.Attribute;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kamisama
 * Date: 2020-03-01
 * Time: 下午3:09
 */
public class SectionNode {
    private Element element;
    private String name;
    private String path;

    public SectionNode(Element e, String path) {
        element = e;
        this.path = path;
        if (e.attribute("id").getValue().equals("")) {
            name = e.attribute("name").getValue();
        } else {
            name = e.attribute("id").getValue()+" "+e.attribute("name").getValue();
        }
    }

    @Override
    public String toString() {
        Attribute id = element.attribute("id");
        Attribute name = element.attribute("name");
        if (id.getValue().equals("")) {
            return name.getValue();
        } else {
            return id.getValue() + " " + name.getValue();
        }
    }

    public Element getElement() {
        return element;
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    public String getFullPath() {
        return getPath()+"/"+toString();
    }
}
