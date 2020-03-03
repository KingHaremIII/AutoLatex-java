package com.takamagahara.reflector;

import com.takamagahara.xmler.XMLer;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import java.io.File;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kamisama
 * Date: 2020-03-01
 * Time: 下午2:33
 */
public class ReflectorTest {
    @Test
    public void Test() throws DocumentException {
        Reflector reflector = new Reflector("/home/kamisama/IdeaProjects/AutoLatex/src/main/resources/IEEEtranTest/Structure.xml");
        reflector.Construct("/home/kamisama/IdeaProjects/AutoLatex/src/main/resources/IEEEtranTest");
    }

    @Test
    public void isTest() throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(new File("/home/kamisama/IdeaProjects/AutoLatex/src/main/resources/IEEEtranTest/Structure.xml"));
        SAXReader reader1 = new SAXReader();
        Document document1 = reader1.read(new File("/home/kamisama/IdeaProjects/AutoLatex/src/main/resources/IEEEtranTest/StructureBackup.xml"));
        boolean re = XMLer.similar(document.getRootElement(), document1.getRootElement(), false);
        System.out.println(re+"");
    }
}
