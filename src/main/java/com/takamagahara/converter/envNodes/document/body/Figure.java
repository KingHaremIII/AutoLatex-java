package com.takamagahara.converter.envNodes.document.body;

import org.dom4j.Element;

/**
 * Created with IntelliJ IDEA.
 * Description: necessary attributes: name, id, size
 *              potential attributes: column, options
 * User: kamisama
 * Date: 2020-03-07
 * Time: 下午4:58
 */
public class Figure implements Strategy {
    @Override
    public String Process(Element element, String pathProject) {
        String text = "";

        String column = element.attributeValue("column");
        if (column == null) {
            text += "\\begin{figure}\n";
        } else {
            if (column.equals("double")) {
                text += "\\begin{figure*}\n";
            } else {
                System.out.println("Warning: column attribute set in element "+element.getPath()+
                        " with illegal content: "+column);
            }
        }
        String options = element.attributeValue("options");
        if (options != null) {
            text += "["+options+"]";
        }
        text += "\\centering\n\\includegraphics[width="+element.attributeValue("size");
        text += ", keepaspectratio]"+"{"+"Resources/"+element.attributeValue("name")+"}\\\\\n";
        text += "\\caption{"+element.getText()+"}\n";
        int length = element.attributeValue("name").length();
        text += "\\label{"+element.attributeValue("name").substring(0, length-4)+"}\n";

        if (column == null) {
            text += "\\end{figure}\n";
        } else {
            if (column.equals("double")) {
                text += "\\end{figure*}\n";
            } else {
                System.out.println("Warning: column attribute set in element "+element.getPath()+
                        " with illegal content: "+column);
            }
        }

        return text;
    }
}
