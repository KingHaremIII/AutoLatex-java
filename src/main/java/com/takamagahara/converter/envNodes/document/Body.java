package com.takamagahara.converter.envNodes.document;

import com.takamagahara.converter.envNodes.EnvNode;
import com.takamagahara.converter.envNodes.document.body.Text;
import com.takamagahara.converter.envNodesUtils.Containable;
import com.takamagahara.converter.envNodesUtils.Convertible;
import com.takamagahara.xmler.Operator;
import com.takamagahara.xmler.SectionNode;
import com.takamagahara.xmler.XMLer;
import org.dom4j.Element;

import java.lang.reflect.InvocationTargetException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kamisama
 * Date: 2020-03-05
 * Time: 上午11:27
 */
public class Body extends EnvNode implements Containable, Convertible {
    private Text text;
    private Element sections;

    public Body(Element body) {
        text = new Text();
        this.sections = body.element("sections");
    }

    private void buildText() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // TODO get key-value after tex in XMLer.reader
        XMLer.reader((new SectionNode(sections, "Documents")), (new Operator()),
                Operator.class.getMethod("Tex2String", SectionNode.class, Text.class), text);
    }

    @Override
    public String getPrefix() {
        return "\\begin{document}\n";
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
        return "\\end{document}\n";
    }
}
