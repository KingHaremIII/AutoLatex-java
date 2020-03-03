package com.takamagahara.reflector;

import com.takamagahara.xmler.XMLer;
import com.takamagahara.xmler.isSimilarCollection;
import org.dom4j.DocumentException;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kamisama
 * Date: 2020-03-01
 * Time: 下午2:33
 */
public class ReflectorTest {
    @Test
    public void Test() throws DocumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Reflector reflector = new Reflector("/home/kamisama/IdeaProjects/AutoLatex/src/main/resources/IEEEtranTest/Structure.xml");
        reflector.ReflectFast("/home/kamisama/IdeaProjects/AutoLatex/src/main/resources/IEEEtranTest");
    }

    @Test
    public void isTest() throws DocumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        isSimilarCollection re = XMLer.similar("/home/kamisama/IdeaProjects/AutoLatex/src/main/resources/IEEEtranTest/Structure.xml",
                "/home/kamisama/IdeaProjects/AutoLatex/src/main/resources/IEEEtranTest/.StructureBackup.xml");
        System.out.println(re.isResult()+"");
    }
}
