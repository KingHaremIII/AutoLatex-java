package com.takamagahara.converger;

import com.takamagahara.converter.envNodesUtils.Convertible;
import com.takamagahara.xmler.Operator;
import com.takamagahara.xmler.XMLer;
import org.dom4j.Attribute;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.junit.Before;
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
    Converger converger;

    @Before
    public void beforeTest() throws DocumentException {
        converger = new Converger("/home/kamisama/IdeaProjects/AutoLatex/src/main/resources/IEEEtranTest");
    }

    @Test
    public void Test() throws DocumentException {
        converger.Converge();
    }

    @Test
    public void LunitTest() throws NoSuchMethodException, IllegalAccessException, DocumentException, InvocationTargetException {
        converger.ConvergeLunit();
    }

    @Test
    public void unitConvergeTest() throws DocumentException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        converger.Converge("Documents/I Introduction/B dfd");
    }
}
