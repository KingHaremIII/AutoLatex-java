package com.takamagahara.converter;

import com.takamagahara.converter.envNodes.EnvNode;
import com.takamagahara.converter.envNodesUtils.Containable;
import com.takamagahara.converter.envNodesUtils.NodeFactory;
import com.takamagahara.xmler.SAXReaderStore;
import com.takamagahara.xmler.XMLer;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kamisama
 * Date: 2020-03-03
 * Time: 下午4:37
 */
public class ConverterTest {
    private static String pathProject = "/home/kamisama/IdeaProjects/AutoLatex/src/main/resources/IEEEtranTest";

    @Test
    public void Test() {
        Converter converter = new Converter(
                "/home/kamisama/IdeaProjects/AutoLatex/src/main/resources/IEEEtranTest");
        converter.Convert();
    }
}
