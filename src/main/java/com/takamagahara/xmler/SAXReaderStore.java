package com.takamagahara.xmler;

import org.dom4j.io.SAXReader;

/**
 * Created with IntelliJ IDEA.
 * Description: Singleton Pattern for SAXReader construction. 
 * User: kamisama
 * Date: 2020-03-06
 * Time: 下午12:51
 */
public class SAXReaderStore {
    private static volatile SAXReader reader;

    private SAXReaderStore() {}

    public static SAXReader getInstance() {
        if (reader == null) {
            synchronized (SAXReaderStore.class) {
                if (reader == null) {
                    reader = new SAXReader();
                }
            }
        }
        return reader;
    }
}
