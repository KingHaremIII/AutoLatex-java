package com.takamagahara.converger;

import com.takamagahara.xmler.Operator;
import com.takamagahara.xmler.XMLer;
import org.dom4j.Attribute;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kamisama
 * Date: 2020-03-01
 * Time: 下午10:53
 */
public class ConvergerTest {
    @Test
    public void Test() throws DocumentException {
        Converger converger = new Converger("/home/kamisama/IdeaProjects/AutoLatex/src/main/resources/IEEEtranTest");
        converger.Converge();
    }

    @Test
    public void unitConvergeTest() throws DocumentException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Converger converger = new Converger("/home/kamisama/IdeaProjects/AutoLatex/src/main/resources/IEEEtranTest");
        converger.Converge("Documents/I Introduction/B dfd");
    }

    @Test
    public void reflectTest() throws NoSuchMethodException, DocumentException, IllegalAccessException, InvocationTargetException {
        Element e = XMLer.reader("/home/kamisama/IdeaProjects/AutoLatex/src/main/resources/test.xml",
                (new Operator()), Operator.class.getMethod("addIgnore", Element.class, String.class), "successfully");
        Attribute ig = e.attribute("ignore");
        System.out.println(ig.getValue());
    }
}
