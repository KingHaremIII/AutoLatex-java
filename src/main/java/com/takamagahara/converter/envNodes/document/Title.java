package com.takamagahara.converter.envNodes.document;

import com.takamagahara.converter.envNodes.EnvNode;
import org.dom4j.Element;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kamisama
 * Date: 2020-03-05
 * Time: 上午11:23
 */
public class Title extends EnvNode {
    private String title;
    private Element titleElement;

    public Title(Element element) {
        title = element.attributeValue("name");
        titleElement = element;
    }

    @Override
    public String getPrefix() {
        return "\\title{"+title+"}\n";
    }

    @Override
    public String getText() {
        String text = "";

        for (Object e : titleElement.elements()) {
            switch (((Element) e).getName()) {
                case "author":
                    text += "\\author{"+((Element) e).attributeValue("name");
                    if (((Element) e).elements().size() != 0) {
                        text += "\n";
                        List<Element> subList = ((Element) e).elements();
                        for (Element subE : subList) {
                            if (subE.getName().equals("thanks")) {
                                text += "\\thanks{"+subE.getText()+"}\n";
                            }
                        }
                    }
                    text += "}\n";
                    break;
                case "markboth":
                    text += "\\markboth";
                    List<Element> subList = ((Element) e).elements();
                    for (Element subE : subList) {
                        text += "{"+subE.getText()+"}";
                    }
                    text += "\n";
                    break;
                default:
                    System.out.println("illegal label in <title></title>: "+((Element) e).getName());
            }
        }
        return text;
    }

    @Override
    public String getPostfix() {
        return "\\maketitle\n";
    }
}
