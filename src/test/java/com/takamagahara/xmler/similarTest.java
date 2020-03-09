package com.takamagahara.xmler;

import org.dom4j.DocumentException;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kamisama
 * Date: 2020-03-09
 * Time: 下午7:54
 */
public class similarTest {
    @Test
    public void Test() throws InvocationTargetException, NoSuchMethodException, DocumentException, IllegalAccessException {
        String home = "/home/kamisama/IdeaProjects/AutoLatex/src/main/resources/IEEEtranTest";
        isSimilarCollection result = XMLer.similar(home+"/Structure.xml",
                home+"/.StructureBackup.xml");
        System.out.println(result.isResult());
        List<String> origin = result.getOrigin();
        List<String> current = result.getCurrent();
        System.out.println("origin:");
        for (String so : origin) {
            System.out.println(so);
        }
        System.out.println("current:");
        for (String sc : current) {
            System.out.println(sc);
        }
        System.out.println("delete: "+result.isDelete());
        System.out.println("modify: "+result.isModify());
        System.out.println("increment: "+result.isIncrement());
    }
}
