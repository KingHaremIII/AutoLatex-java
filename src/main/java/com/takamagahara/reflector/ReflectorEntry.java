package com.takamagahara.reflector;

import org.dom4j.DocumentException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kamisama
 * Date: 2020-03-01
 * Time: 下午7:44
 */
public class ReflectorEntry {
    public static void main(String[] args) throws DocumentException {
        String path = args[0];
        String source = path+"/Structure.xml";
        Reflector reflector = new Reflector(source);
        reflector.Construct(path);
    }
}
