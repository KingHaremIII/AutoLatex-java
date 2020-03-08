package com.takamagahara.converter.envNodes.document.body;

import com.takamagahara.xmler.Operator;
import com.takamagahara.xmler.OperatorStore;
import org.dom4j.Element;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kamisama
 * Date: 2020-03-07
 * Time: 下午4:59
 */
public class Insert implements Strategy {
    @Override
    public String Process(Element element, String pathProject) {
        String text = "";

        text += OperatorStore.getInstance().readToString(pathProject+"/Target/resources/"+
                element.attributeValue("id")+element.attributeValue("name"));

        return text;
    }
}
