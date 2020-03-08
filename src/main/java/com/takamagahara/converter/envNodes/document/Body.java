package com.takamagahara.converter.envNodes.document;

import com.takamagahara.converter.envNodes.EnvNode;
import com.takamagahara.converter.envNodes.document.body.Text;
import com.takamagahara.converter.envNodesUtils.Containable;
import com.takamagahara.converter.envNodesUtils.Convertible;
import com.takamagahara.converter.envNodesUtils.LabelName2ClassName;
import com.takamagahara.xmler.Operator;
import com.takamagahara.xmler.OperatorStore;
import com.takamagahara.xmler.SectionNode;
import com.takamagahara.xmler.XMLer;
import org.dom4j.Element;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kamisama
 * Date: 2020-03-05
 * Time: 上午11:27
 */
public class Body extends EnvNode implements Convertible {
    private Text text;
    private Element sections;
    private String pathProject;

    public Body(Element body, String pathProject) {
        this.pathProject = pathProject;
        text = new Text();
        this.sections = body.element("sections");
    }

    private void buildText() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        XMLer.reader((new SectionNode(sections, "Documents")), OperatorStore.getInstance(),
                Operator.class.getMethod("Tex2String", SectionNode.class, Text.class, String.class, String.class),
                text, pathProject, "body");
    }

    @Override
    public String getPrefix() {
        return "\n%!sectionsBEGIN\n";
    }

    @Override
    public String getText() {
        try {
            buildText();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return text.getContent();
    }

    @Override
    public String getPostfix() {
        return "\n%!sectionsEND\n";
    }
}
