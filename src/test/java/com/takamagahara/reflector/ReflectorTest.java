package com.takamagahara.reflector;

import org.dom4j.DocumentException;
import org.junit.Test;

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
        Reflector reflector = new Reflector("/home/kamisama/IdeaProjects/AutoLatex/src/main/resources/Structure.xml");
        reflector.Construct("/home/kamisama/IdeaProjects/AutoLatex/src/main/resources/output");
    }
}
